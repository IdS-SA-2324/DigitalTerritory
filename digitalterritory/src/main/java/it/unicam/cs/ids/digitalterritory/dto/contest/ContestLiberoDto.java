package it.unicam.cs.ids.digitalterritory.dto.contest;

import java.util.Date;
import java.util.UUID;

public record ContestLiberoDto(String nome, String obbiettivo, UUID comune, Date data) {
}
