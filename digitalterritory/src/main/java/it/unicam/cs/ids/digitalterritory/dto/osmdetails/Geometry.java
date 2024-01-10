package it.unicam.cs.ids.digitalterritory.dto.osmdetails;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Geometry {
    private String type;
    private ArrayList<Double> coordinates;
}
