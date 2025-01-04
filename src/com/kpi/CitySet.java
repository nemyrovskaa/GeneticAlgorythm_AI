package com.kpi;

import java.util.Random;
import java.util.Vector;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public final class CitySet {
    private static CitySet citySet;
    public Vector<City> cities;

    private CitySet(int cityNum) {
        cities = new Vector<>(cityNum);
        generateCircPos(cityNum);
    }

    private void generateRandPos(int cityNum) {
        Random random = new Random();

        for (int i = 0; i < cityNum; i++) {
            cities.add(i, new City(i+1, random.nextInt(960) + 20, random.nextInt(960)+20));
        }
    }

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

    public static CitySet getCitySet(int cityNum) {
        if (citySet == null)
            citySet = new CitySet(cityNum);

        return citySet;
    }

    @Override
    public String toString() {
        String resStr = "CitySet[" + this.hashCode() +"]:\n";
        for (City city : cities)
            resStr += city.toString();

        return resStr;
    }
}
