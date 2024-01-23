package it.unicam.cs.ids.digitalterritory.dto.poi;

import java.util.UUID;

public record InfoDaApprovareDto(String nome, UUID id, String creatore, TipoInformazione tipo) {
}
