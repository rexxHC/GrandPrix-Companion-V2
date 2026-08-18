package com.gpcompanion.race;

import java.awt.Color;

public class Driver {
    private final String name;
    private final int carNumber;
    private final String teamName;
    private final Color teamColor;

    public Driver(String name, int carNumber, String teamName, Color teamColor) {
        this.name = name;
        this.carNumber = carNumber;
        this.teamName = teamName;
        this.teamColor = teamColor;
    }

    public String getName() { return name; }
    public int getCarNumber() { return carNumber; }
    public String getTeamName() { return teamName; }
    public Color getTeamColor() { return teamColor; }
}