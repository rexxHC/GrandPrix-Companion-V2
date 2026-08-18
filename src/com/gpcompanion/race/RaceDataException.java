package com.gpcompanion.race;

public class RaceDataException extends Exception {
    public RaceDataException(String message) {
        super(message);
    }

    public RaceDataException(String message, Throwable cause) {
        super(message, cause);
    }
}