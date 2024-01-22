package it.unicam.cs.ids.digitalterritory.dto.poi;

import it.unicam.cs.ids.digitalterritory.db.enums.TipologiaTipoInteresse;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.ContenutoDto;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record PuntoInteresseDto(String nome, TipologiaTipoInteresse tipologia, Coordinate coordinate, List<ContenutoDto> contenuti) {
    public PuntoInteresseDto(String nome, TipologiaTipoInteresse tipologia, Coordinate coordinate) {
        this(nome, tipologia, coordinate, new ArrayList<>());
    }
}
