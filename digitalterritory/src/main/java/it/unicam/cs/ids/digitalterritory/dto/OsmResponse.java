package it.unicam.cs.ids.digitalterritory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class OsmResponse {
    @JsonProperty("place_id")
    private long placeId;
    private String licence;
    @JsonProperty("osm_type")
    private String osmType;
    @JsonProperty("osm_id")
    private int osmId;
    private String lat;
    private String lon;
    private String category;
    private String type;
    @JsonProperty("place_rank")
    private int placeRank;
    private double importance;
    @JsonProperty("addresstype")
    private String addressType;
    private String name;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("boundingbox")
    private ArrayList<String> boundingBox;
    @JsonProperty("geojson")
    private Geojson geoJson;
}
