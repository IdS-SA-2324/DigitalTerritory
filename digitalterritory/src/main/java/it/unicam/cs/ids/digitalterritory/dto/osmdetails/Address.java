package it.unicam.cs.ids.digitalterritory.dto.osmdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {
    @JsonProperty("localname")
    private String localName;

    @JsonProperty("place_id")
    private int placeId;

    @JsonProperty("osm_id")
    private int osmId;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("class")
    private String myClass;

    private String type;
    @JsonProperty("admin_level")
    private int adminLevel;

    @JsonProperty("rank_address")
    private int rankAddress;

    private double distance;

    @JsonProperty("isaddress")
    private boolean isAddress;
}
