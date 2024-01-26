package it.unicam.cs.ids.digitalterritory.utils;

public record Coordinate(double longitude, double latitude) {
    @Override
    public String toString() {
        return String.format("%f-%f", longitude, latitude);
    }

    public static Coordinate fromString(String coordinate) {
        var splitted = coordinate.replace(',','.').split("-");
        return new Coordinate(Double.parseDouble(splitted[0]),
                Double.parseDouble(splitted[1]));
    }
}
