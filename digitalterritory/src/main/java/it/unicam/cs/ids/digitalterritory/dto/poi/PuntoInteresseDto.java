package it.unicam.cs.ids.digitalterritory.dto.poi;

import it.unicam.cs.ids.digitalterritory.db.enums.TipologiaTipoInteresse;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.ContenutoDto;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public record PuntoInteresseDto(String nome, TipologiaTipoInteresse tipologia, Coordinate coordinate, List<ContenutoDto> contenuti, UUID contest) {
    public PuntoInteresseDto(String nome, TipologiaTipoInteresse tipologia, Coordinate coordinate) {
        this(nome, tipologia, coordinate, new ArrayList<>(), UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }
}
