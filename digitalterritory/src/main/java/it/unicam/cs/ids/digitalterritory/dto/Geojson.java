package it.unicam.cs.ids.digitalterritory.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Geojson{
    private String type;
    private ArrayList<ArrayList<ArrayList<Double>>> coordinates;
}
