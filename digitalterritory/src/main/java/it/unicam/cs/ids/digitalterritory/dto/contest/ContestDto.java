package it.unicam.cs.ids.digitalterritory.dto.contest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record ContestDto(String nome, String obiettivo, UUID comune, Date data, List<UUID> invitati, boolean isClosed) {
}
