package it.unicam.cs.ids.digitalterritory.dto.auth;

import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;

import java.util.UUID;

public record AuthDto(String email, TipoUtente tipoUtente, String token, UUID id) {
}
