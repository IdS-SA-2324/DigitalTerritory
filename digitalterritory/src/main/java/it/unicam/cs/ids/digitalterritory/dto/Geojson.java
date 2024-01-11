package it.unicam.cs.ids.digitalterritory.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Geojson{
    private String type;
    private Object coordinates;
}
