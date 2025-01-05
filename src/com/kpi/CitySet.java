package com.kpi;

import java.util.Random;
import java.util.Vector;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Represents a set of cities used in the Traveling Salesman Problem.
 * The cities are immutable during the program's execution and can be generated
 * either randomly or positioned on a circle.
 */
public final class CitySet {
    /** Singleton instance of the CitySet. */
    private static CitySet citySet;
    /** A collection of City objects representing the city set. */
    public Vector<City> cities;

    /**
     * Private constructor to initialize the CitySet with a specified number of cities.
     * Cities are positioned on a circle by default.
     *
     * @param cityNum   the number of cities to generate
     */
    private CitySet(int cityNum) {
        cities = new Vector<>(cityNum);
        generateCircPos(cityNum);
    }

    /**
     * Generates city positions randomly within a 960x960 grid.
     *
     * @param cityNum   the number of cities to generate
     */
    private void generateRandPos(int cityNum) {
        Random random = new Random();

        for (int i = 0; i < cityNum; i++) {
            cities.add(i, new City(i+1, random.nextInt(960) + 20, random.nextInt(960)+20));
        }
    }

    /**
     * Generates city positions arranged in a circular formation.
     *
     * @param cityNum   the number of cities to generate
     */
    private void generateCircPos(int cityNum) {
        double rotAngle = 2 * PI / cityNum;
        int radius = 300;
        int xPos, yPos;

        for (int i = 0; i < cityNum; i++) {
            xPos = (int) (500 + radius * cos(i*rotAngle));
            yPos = (int) (500 + radius * sin(i*rotAngle));

            cities.add(i, new City(i+1, xPos, yPos));
        }
    }

    /**
     * Provides access to the singleton instance of CitySet. If the instance does not exist,
     * it is created with the specified number of cities.
     *
     * @param cityNum   the number of cities to generate (only used when creating the instance)
     * @return  the singleton instance of CitySet
     */
    public static CitySet getCitySet(int cityNum) {
        if (citySet == null)
            citySet = new CitySet(cityNum);

        return citySet;
    }

    /**
     * Returns a string representation of the city set, including the hash code and details
     * of each city in the set.
     *
     * @return a string representation of the CitySet
     */
    @Override
    public String toString() {
        String resStr = "CitySet[" + this.hashCode() +"]:\n";
        for (City city : cities)
            resStr += city.toString();

        return resStr;
    }
}
