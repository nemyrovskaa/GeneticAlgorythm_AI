package com.kpi;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import java.lang.Math;

/**
 * Represents a path in the Traveling Salesman Problem, implemented as a chromosome
 * for use in a genetic algorithm. A path is a sequence of cities, starting and ending
 * at the same city, with methods for mutation and crossover.
 */
public class Path implements Chromosome {
    /** The ordered sequence of cities in this path. */
    final public Vector<City> cities;
    /** The total number of cities in the path. */
    final private int cityNum;
    /** The total distance of the path. */
    private Integer totalDistance;

    /**
     * Constructs a new Path with a randomly shuffled sequence of cities.
     *
     * @param cityNum   the number of cities in the path
     */
    public Path(int cityNum) {
        this.cityNum = cityNum;
        this.cities = generatePath();
        this.totalDistance = this.calcDist();
    }

    /**
     * Constructs a Path with a specified sequence of cities.
     *
     * @param path      the sequence of cities
     * @param cityNum   the number of cities in the path
     */
    public Path(Vector<City> path, int cityNum) {
        this.cityNum = cityNum;
        this.cities = path;
        this.totalDistance = this.calcDist();
    }

    /**
     * Generates a random path by shuffling cities from the {@link CitySet}.
     * The first city remains fixed to avoid issues with equivalent cyclic permutations.
     *
     * @return a new randomly generated path
     */
    private Vector<City> generatePath() {
        CitySet citySet = CitySet.getCitySet(cityNum);
        Vector<City> newPath = new Vector<>(citySet.cities);

        // Shuffle cities except for the first one
        Collections.shuffle(newPath.subList(1, cityNum), new Random());

        return newPath;
    }

    /**
     * Calculates the total distance of the path, including the return to the starting city.
     *
     * @return the total distance of the path
     */
    private int calcDist() {
        int dist = 0;

        for (int i = 0; i < cities.size(); i++) {
            City city1 = cities.elementAt(i);
            City city2 = i == cities.size() - 1 ? cities.elementAt(0) : cities.elementAt(i+1);
            dist += (int) Math.sqrt((city1.getX() - city2.getX()) * (city1.getX() - city2.getX()) +
                    (city1.getY() - city2.getY()) * (city1.getY() - city2.getY()));
        }
        return dist;
    }

    /**
     * Generates a unique key for the path based on its city order.
     * The key helps compare paths and detect identical or equivalent paths.
     *
     * @return a double value representing the path's unique key
     */
    public double getKey() {
        double hash = 0.0;
        for (int i = 0; i < cities.size(); i++)
            hash += (double) i / cities.elementAt(i).getId();

        return hash;
    }

    /**
     * Performs a cyclic crossover with another path to produce an offspring path.
     *
     * @param chromosome    the other chromosome (Path) to crossover with
     * @return  a new Path resulting from the crossover
     * @throws  ClassCastException if the provided chromosome is not a Path
     */
    @Override
    public Chromosome crossoverWith(Chromosome chromosome) {
        if (! (chromosome instanceof Path))
            throw new ClassCastException("Method crossoverWith() in class \"Path\". Unable to crossover objects with different types.");

        // assign the city sequences of the current and provided paths to local variables
        // the childPath is initially a copy of the current path's city sequence
        Vector<City> parentPath1 = this.cities;
        Vector<City> parentPath2 = ((Path) chromosome).cities;
        Vector<City> childPath = new Vector<>(parentPath1);

        // select a random index (excluding the starting city at index 0) to begin the crossover cycle
        int index = new Random().nextInt(cityNum-1)+1;
        City startVal = parentPath1.elementAt(index); // city from the first parent at the selected index
        City path2Val = parentPath2.elementAt(index); // corresponding city from the second parent

        // replace cities in childPath with values from parentPath2
        // until the cycle is complete (path2Val matches the startVal)
        while (path2Val.getId() != startVal.getId()) {
            childPath.setElementAt(path2Val, index);
            index = parentPath1.indexOf(path2Val);
            path2Val = parentPath2.elementAt(index);
        }
        // place the final city of the cycle back into the child path
        childPath.setElementAt(path2Val, index);

        return new Path(childPath, cityNum);
    }

    /**
     * Mutates the path by swapping two random cities, excluding the starting city.
     */
    @Override
    public void mutate() {
        Random random = new Random();
        int index1, index2;
        do {
            index1 = random.nextInt(cityNum-1)+1;
            index2 = random.nextInt(cityNum-1)+1;

            City temp = cities.elementAt(index1);
            cities.setElementAt(cities.elementAt(index2), index1);
            cities.setElementAt(temp, index2);
        } while (index1 == index2);

        totalDistance = calcDist();
    }

    /**
     * Compares this path with another chromosome based on their total distances.
     *
     * @param o the chromosome to compare with
     * @return  1 if this path's distance is greater, -1 if less, and 0 if equal
     * @throws  ClassCastException if the provided chromosome is not a Path
     */
    @Override
    public int compareTo(Chromosome o) {
        if (! (o instanceof Path))
            throw new ClassCastException("Method compareTo() in class \"Path\". Unable to compare objects with different types.");

        if (totalDistance > ((Path) o).totalDistance)
            return 1;
        else if (totalDistance.equals(((Path) o).totalDistance))
            return 0;
        else
            return -1;
    }

    /**
     * Returns a string representation of the path, including the city sequence and total distance.
     *
     * @return a string representation of the Path
     */
    @Override
    public String toString() {
        String resStr = "Path [ ";
        for (City city : cities)
            resStr += city.getId() + " ";

        resStr += "]\tdistance = " + totalDistance + "\n";

        return resStr;
    }
}
