package it.unicam.cs.ids.digitalterritory.dto.osmdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OsmDetails {
    @JsonProperty("place_id")
    private int placeId;
    @JsonProperty("parent_place_id")
    private int parentPlaceId;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("osm_id")
    private int osmId;

    @JsonProperty("category")
    private String category;

    @JsonProperty("type")
    private String type;

    @JsonProperty("admin_level")
    private int adminLevel;

    @JsonProperty("localname")
    private String localname;

    @JsonProperty("names")
    private Names names;

    @JsonProperty("addresstags")
    private AddressTag addresstags;

    @JsonProperty("calculated_postcode")
    private String calculatedPostcode;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("indexed_date")
    private Date indexedDate;

    @JsonProperty("importance")
    private double importance;

    @JsonProperty("calculated_importance")
    private double calculatedImportance;

    @JsonProperty("extratags")
    private Extratags extratags;

    @JsonProperty("calculated_wikipedia")
    private String calculatedWikipedia;

    @JsonProperty("rank_address")
    private int rankAddress;

    @JsonProperty("rank_search")
    private int rankSearch;

    @JsonProperty("isarea")
    private boolean isarea;

    @JsonProperty("centroid")
    private Geometry centroid;

    @JsonProperty("geometry")
    private Geometry geometry;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("address")
    private ArrayList<Address> address;

    @JsonProperty("linked_places")
    private ArrayList<Address> linkedPlaces;
}
