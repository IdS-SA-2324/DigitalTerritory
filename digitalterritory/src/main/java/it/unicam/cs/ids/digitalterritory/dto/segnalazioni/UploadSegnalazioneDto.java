package it.unicam.cs.ids.digitalterritory.dto.segnalazioni;

import it.unicam.cs.ids.digitalterritory.dto.poi.TipoInformazione;

import java.util.UUID;

public record UploadSegnalazioneDto(String descrizione, TipoInformazione tipo, boolean isAnonimo, UUID infoId) {
}
