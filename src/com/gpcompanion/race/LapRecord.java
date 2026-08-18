package com.gpcompanion.race;

public class LapRecord {
    public int lapNumber;
    public double lapTime;
    public String tireCompound;
    public Driver driver;
    public LapRecord(int n, double t, String c, Driver d) { lapNumber=n; lapTime=t; tireCompound=c; driver=d; }
}
