package com.gpcompanion.race;

public class LapRecord {
    private final int lapNumber;
    private final double lapTime;
    private final String tireCompound;
    private final Driver driver;

    public LapRecord(int lapNumber, double lapTime, String tireCompound, Driver driver) {
        this.lapNumber = lapNumber;
        this.lapTime = lapTime;
        this.tireCompound = tireCompound;
        this.driver = driver;
    }

    public int getLapNumber() { return lapNumber; }
    public double getLapTime() { return lapTime; }
    public String getTireCompound() { return tireCompound; }
    public Driver getDriver() { return driver; }
}