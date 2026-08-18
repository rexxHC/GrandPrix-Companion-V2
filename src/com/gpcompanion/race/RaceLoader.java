package com.gpcompanion.race;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceLoader {
    public List<LapRecord> load(String filePath) throws RaceDataException {
        List<LapRecord> records = new ArrayList<>();
        Map<String, Driver> driversByName = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;

                String[] parts = line.split(",");
                if (parts.length != 6) {
                    throw new RaceDataException("Malformed race data on line " + lineNumber + ": " + line);
                }

                int lapNumber = Integer.parseInt(parts[0].trim());
                String driverName = parts[1].trim();
                int carNumber = Integer.parseInt(parts[2].trim());
                Color teamColor = Color.decode(parts[3].trim());
                double lapTime = Double.parseDouble(parts[4].trim());
                String tireCompound = parts[5].trim();

                Driver driver = driversByName.computeIfAbsent(driverName,
                    name -> new Driver(name, carNumber, teamColor));

                records.add(new LapRecord(lapNumber, lapTime, tireCompound, driver));
            }
        } catch (IOException e) {
            throw new RaceDataException("Could not read race data file: " + filePath, e);
        } catch (NumberFormatException e) {
            throw new RaceDataException("Invalid number in race data file: " + filePath, e);
        }

        if (records.isEmpty()) {
            throw new RaceDataException("Race data file was empty: " + filePath);
        }

        return records;
    }
}