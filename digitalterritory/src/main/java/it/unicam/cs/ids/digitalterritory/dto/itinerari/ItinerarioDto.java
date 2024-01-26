package it.unicam.cs.ids.digitalterritory.dto.itinerari;

import java.util.List;
import java.util.UUID;

public record ItinerarioDto(String nome, List<UUID> poiList, boolean isPrivato, String emailCreatore) {
    public ItinerarioDto(String nome, List<UUID> poiList, boolean isPrivato) {
        this(nome, poiList, isPrivato, "");
    }
}
