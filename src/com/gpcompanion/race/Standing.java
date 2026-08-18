package com.gpcompanion.race;

public class Standing implements Comparable<Standing> {
    public int position;
    public Driver driver;
    public double lastLapTime;
    public double gapToLeader;
    public double intervalToCarAhead;
    public String currentTire;
    public boolean isPersonalBest;
    public boolean isFastestLap;
    public double totalTime;

    @Override
    public int compareTo(Standing other) {
        return Double.compare(this.totalTime, other.totalTime);
    }
}
