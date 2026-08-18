package com.gpcompanion.race;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceEngine {
    private int currentLap = 0;
    private int totalLaps = 0;
    private List<Standing> standings = new ArrayList<>();
    private Runnable listener;
    private final List<LapRecord> allLapRecords;
    private final Map<Driver, Double> personalBests = new HashMap<>();
    private double fastestLapOfRace = Double.MAX_VALUE;

    public RaceEngine(List<LapRecord> records) {
        this.allLapRecords = records;
        for (LapRecord r : records) {
            if (r.lapNumber > totalLaps) totalLaps = r.lapNumber;
        }
        
        String[] tires = {"Soft", "Medium", "Hard"};
        int index = 0;
        for (LapRecord r : records) {
            if (r.lapNumber == 1) {
                Standing s = new Standing();
                s.driver = r.driver;
                s.currentTire = tires[index % 3];
                s.position = index + 1;
                standings.add(s);
                index++;
            }
        }
    }

    public void setListener(Runnable l) { listener = l; }

    public void advanceLap() {
        if (currentLap < totalLaps) {
            currentLap++;
            
            List<LapRecord> currentLapRecords = new ArrayList<>();
            for (LapRecord r : allLapRecords) {
                if (r.lapNumber == currentLap) currentLapRecords.add(r);
            }
            
            Map<Driver, Standing> standingMap = new HashMap<>();
            for (Standing s : standings) {
                standingMap.put(s.driver, s);
            }
            
            List<Standing> newStandings = new ArrayList<>();
            
            for (LapRecord r : currentLapRecords) {
                Standing s = standingMap.getOrDefault(r.driver, new Standing());
                s.driver = r.driver;
                s.totalTime += r.lapTime;
                s.lastLapTime = r.lapTime;
                
                double pb = personalBests.getOrDefault(r.driver, Double.MAX_VALUE);
                if (r.lapTime < pb) {
                    personalBests.put(r.driver, r.lapTime);
                    s.isPersonalBest = true;
                } else {
                    s.isPersonalBest = false;
                }
                
                if (r.lapTime < fastestLapOfRace) fastestLapOfRace = r.lapTime;
                s.isFastestLap = r.lapTime <= fastestLapOfRace;
                
                newStandings.add(s);
            }
            
            Collections.sort(newStandings);
            
            for (int i = 0; i < newStandings.size(); i++) {
                Standing s = newStandings.get(i);
                s.position = i + 1;
                s.gapToLeader = s.totalTime - newStandings.get(0).totalTime;
                s.intervalToCarAhead = i == 0 ? 0 : s.totalTime - newStandings.get(i - 1).totalTime;
            }
            
            standings = newStandings;
            if (listener != null) listener.run();
        }
    }

    public int getCurrentLap() { return currentLap; }
    public int getTotalLaps() { return totalLaps; }
    public List<Standing> getStandings() { return standings; }
}
