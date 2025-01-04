package com.kpi;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import java.lang.Math;

public class Path implements Chromosome{
    final public Vector<City> cities;
    final private int cityNum;
    private Integer totalDistance;

    public Path(int cityNum) {
        this.cityNum = cityNum;
        this.cities = generatePath();
        this.totalDistance = this.calcDist();
    }

    public Path(Vector<City> path, int cityNum) {
        this.cityNum = cityNum;
        this.cities = path;
        this.totalDistance = this.calcDist();
    }

    private Vector<City> generatePath() {
        CitySet citySet = CitySet.getCitySet(cityNum);
        Vector<City> newPath = new Vector<>(citySet.cities);

        Collections.shuffle(newPath.subList(1, cityNum), new Random());

        return newPath;
    }

    private int calcDist() {
        int dist = 0;

        for (int i = 0; i < cities.size(); i++) {
            City city1 = cities.elementAt(i);
            City city2 = i == cities.size() - 1 ? cities.elementAt(0) : cities.elementAt(i+1);
            dist += Math.sqrt((city1.getX() - city2.getX()) * (city1.getX() - city2.getX()) +
                    (city1.getY() - city2.getY()) * (city1.getY() - city2.getY()));
        }
        return dist;
    }

    public double getKey() {
        double hash = 0.0;
        for (int i = 0; i < cities.size(); i++)
            hash += (double) i / cities.elementAt(i).getId();

        return hash;
    }

    @Override
    public Chromosome crossoverWith(Chromosome chromosome) {
        if (! (chromosome instanceof Path))
            throw new ClassCastException("Method crossoverWith() in class \"Path\". Unable to crossover objects with different types.");

        Vector<City> parentPath1 = this.cities;
        Vector<City> parentPath2 = ((Path) chromosome).cities;
        Vector<City> childPath = new Vector<>(parentPath1);

        int index = new Random().nextInt(cityNum-1)+1;
        City startVal = parentPath1.elementAt(index);
        City path2Val = parentPath2.elementAt(index);

        while (path2Val.getId() != startVal.getId()) {
            childPath.setElementAt(path2Val, index);
            index = parentPath1.indexOf(path2Val);
            path2Val = parentPath2.elementAt(index);
        }
        childPath.setElementAt(path2Val, index);

        return new Path(childPath, cityNum);
    }

    /*@Override
    public Chromosome crossoverWith(Chromosome chromosome) {
        if (! (chromosome instanceof Path))
            throw new ClassCastException("Method crossoverWith() in class \"Path\". Unable to crossover objects with different types.");

        Vector<City> parentPath1 = this.cities;
        Vector<City> parentPath2 = ((Path) chromosome).cities;
        Vector<City> childPath = new Vector<>(parentPath1);

        int index = new Random().nextInt(cityNum);
        City startVal = parentPath1.elementAt(index);
        City path2Val = parentPath2.elementAt(index);

        while (path2Val.id != startVal.id) {
            childPath.setElementAt(path2Val, index);
            index = parentPath1.indexOf(path2Val);
            path2Val = parentPath2.elementAt(index);
        }
        childPath.setElementAt(path2Val, index);

        return new Path(childPath, cityNum);
    }*/

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

    @Override
    public int compareTo(Chromosome o) {
        if (! (o instanceof Path))
            throw new ClassCastException("Method compareTo() in class \"Path\". Unable to compare objects with different types.");

        if (totalDistance > ((Path) o).totalDistance)
            return 1;
        else if (totalDistance == ((Path) o).totalDistance)
            return 0;
        else
            return -1;
    }

    @Override
    public String toString() {
        String resStr = "Path [ ";
        for (City city : cities)
            resStr += city.getId() + " ";

        resStr += "]\tdistance = " + totalDistance + "\n";

        /*String resStr = "Path[" + this.hashCode() +"], dist = " + totalDistance + ":\n";
        for (City city : cities)
            resStr += city.toString();*/

        return resStr;
    }
}
