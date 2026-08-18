package com.gpcompanion.race;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceLoader {
    public List<LapRecord> load(String filePath) {
        List<LapRecord> records = new ArrayList<>();
        String[] names = {"VER", "PER", "HAM", "RUS", "LEC", "SAI", "NOR", "PIA", "ALO", "STR", "OCO", "GAS"};
        int[] numbers = {1, 11, 44, 63, 16, 55, 4, 81, 14, 18, 31, 10};
        Color[] colors = {Color.BLUE, Color.BLUE, Color.CYAN, Color.CYAN, Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, Color.GREEN, Color.GREEN, Color.PINK, Color.PINK};
        
        Driver[] drivers = new Driver[12];
        for(int i = 0; i < 12; i++) {
            drivers[i] = new Driver(names[i], numbers[i], colors[i]);
        }
        
        Random rand = new Random();
        for(int lap = 1; lap <= 15; lap++) {
            for(int i = 0; i < 12; i++) {
                int tier = i / 4;
                int posInTier = i % 4;
                double basePace = 80.0 + (tier * 0.3) + (posInTier * 0.05);
                double time = basePace - 0.35 + (rand.nextDouble() * 0.7);
                records.add(new LapRecord(lap, time, "S", drivers[i]));
            }
        }
        
        return records;
    }
}
