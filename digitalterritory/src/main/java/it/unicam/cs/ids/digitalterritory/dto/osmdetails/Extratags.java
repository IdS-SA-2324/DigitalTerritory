package it.unicam.cs.ids.digitalterritory.dto.osmdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Extratags {
    @JsonProperty("ele")
    private String ele;

    @JsonProperty("website")
    private String website;

    @JsonProperty("gfoss_id")
    private String gfossId;

    @JsonProperty("istat_id")
    private String istatId;

    @JsonProperty("wikidata")
    private String wikidata;

    @JsonProperty("ref:ISTAT")
    private String refISTAT;

    @JsonProperty("wikipedia")
    private String wikipedia;

    @JsonProperty("population")
    private String population;

    @JsonProperty("ref:catasto")
    private String refCatasto;

    @JsonProperty("linked_place")
    private String linkedPlace;

    @JsonProperty("population:date")
    private String populationDate;

    @JsonProperty("population:note")
    private String populationNote;

    @JsonProperty("source:population")
    private String sourcePopulation;

    @JsonProperty("population:note:en")
    private String populationNoteEn;
}
