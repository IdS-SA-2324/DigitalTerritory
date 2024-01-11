package it.unicam.cs.ids.digitalterritory.dto.utenti;

import it.unicam.cs.ids.digitalterritory.dto.auth.RegisterDto;

public record AbilitaComuneCuratoreDto(RegisterDto curatore, String nomeComune, String nomeRegione) {
}
