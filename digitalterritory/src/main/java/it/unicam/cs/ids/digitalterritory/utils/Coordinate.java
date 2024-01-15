package it.unicam.cs.ids.digitalterritory.utils;

public record Coordinate(double longitude, double latitude) {
    @Override
    public String toString() {
        return String.format("%f-%f", longitude, latitude);
    }
}
