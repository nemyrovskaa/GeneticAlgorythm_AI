package com.kpi;

public class City {
    final private int id;
    final private int xPos;
    final private int yPos;

    public City(int id, int xPos, int yPos) {
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getId() { return id; }

    public int getX() { return xPos; }

    public int getY() { return yPos; }

    @Override
    public String toString() {
        return "City [" + id + "]\tx=" + xPos + " y=" + yPos + "\n";
    }
}
