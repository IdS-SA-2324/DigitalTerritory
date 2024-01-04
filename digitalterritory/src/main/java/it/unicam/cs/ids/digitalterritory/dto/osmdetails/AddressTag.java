package it.unicam.cs.ids.digitalterritory.dto.osmdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressTag {
    @JsonProperty("postcode")
    private String postCode;
}
