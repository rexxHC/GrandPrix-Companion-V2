package com.gpcompanion.race;

public class Standing implements Comparable<Standing> {
    private int position;
    private Driver driver;
    private double lastLapTime;
    private double gapToLeader;
    private double intervalToCarAhead;
    private String currentTire;
    private boolean personalBest;
    private boolean fastestLap;
    private double totalTime;

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }

    public double getLastLapTime() { return lastLapTime; }
    public void setLastLapTime(double lastLapTime) { this.lastLapTime = lastLapTime; }

    public double getGapToLeader() { return gapToLeader; }
    public void setGapToLeader(double gapToLeader) { this.gapToLeader = gapToLeader; }

    public double getIntervalToCarAhead() { return intervalToCarAhead; }
    public void setIntervalToCarAhead(double intervalToCarAhead) { this.intervalToCarAhead = intervalToCarAhead; }

    public String getCurrentTire() { return currentTire; }
    public void setCurrentTire(String currentTire) { this.currentTire = currentTire; }

    public boolean isPersonalBest() { return personalBest; }
    public void setPersonalBest(boolean personalBest) { this.personalBest = personalBest; }

    public boolean isFastestLap() { return fastestLap; }
    public void setFastestLap(boolean fastestLap) { this.fastestLap = fastestLap; }

    public double getTotalTime() { return totalTime; }
    public void setTotalTime(double totalTime) { this.totalTime = totalTime; }

    @Override
    public int compareTo(Standing other) {
        return Double.compare(this.totalTime, other.totalTime);
    }
}