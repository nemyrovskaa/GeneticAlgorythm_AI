package com.kpi;

/**
 * Represents a city in the Traveling Salesman Problem.
 * Each city has a unique identifier and coordinates in a 2D space.
 */
public class City {
    /** The unique identifier of the city. */
    final private int id;
    /** The x-coordinate of the city. */
    final private int xPos;
    /** The y-coordinate of the city. */
    final private int yPos;

    /**
     * Constructs a new City object with the given identifier and coordinates.
     *
     * @param id    the unique identifier of the city
     * @param xPos  the x-coordinate of the city
     * @param yPos  the y-coordinate of the city
     */
    public City(int id, int xPos, int yPos) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Returns the unique identifier of the city.
     *
     * @return the city identifier
     */
    public int getId() { return id; }

    /**
     * Returns the x-coordinate of the city.
     *
     * @return the x-coordinate
     */
    public int getX() { return xPos; }

    /**
     * Returns the y-coordinate of the city.
     *
     * @return the y-coordinate
     */
    public int getY() { return yPos; }

    /**
     * Returns a string representation of the city, including its ID and coordinates.
     *
     * @return a string in the format "City [id]    x=xPos y=yPos"
     */
    @Override
    public String toString() {
        return "City [" + id + "]\tx=" + xPos + " y=" + yPos + "\n";
    }
}
