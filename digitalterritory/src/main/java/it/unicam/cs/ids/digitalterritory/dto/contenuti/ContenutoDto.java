package it.unicam.cs.ids.digitalterritory.dto.contenuti;

import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoContenuto;

public record ContenutoDto(StatoApprovazione statoApprovazione, TipoContenuto tipoContenuto, String textContent, String poi) {
    public ContenutoDto(StatoApprovazione statoApprovazione, TipoContenuto tipoContenuto, String textContent) {
        this(statoApprovazione, tipoContenuto, textContent, "");
    }
}
