package it.unicam.cs.ids.digitalterritory.dto.poi;

import it.unicam.cs.ids.digitalterritory.db.enums.TipologiaTipoInteresse;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;

public record PuntoInteresseDto(String nome, TipologiaTipoInteresse tipologia, Coordinate coordinate) {
}
