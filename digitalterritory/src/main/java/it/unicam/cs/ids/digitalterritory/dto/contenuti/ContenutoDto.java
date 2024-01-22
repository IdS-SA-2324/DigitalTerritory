package it.unicam.cs.ids.digitalterritory.dto.contenuti;

import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoContenuto;

public record ContenutoDto(StatoApprovazione statoApprovazione, TipoContenuto tipoContenuto, String textContent) {
}
