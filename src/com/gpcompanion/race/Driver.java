package com.gpcompanion.race;

import java.awt.Color;

public class Driver {
    private final String name;
    private final int carNumber;
    private final Color teamColor;

    public Driver(String name, int carNumber, Color teamColor) {
        this.name = name;
        this.carNumber = carNumber;
        this.teamColor = teamColor;
    }

    public String getName() { return name; }
    public int getCarNumber() { return carNumber; }
    public Color getTeamColor() { return teamColor; }
}