package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.utils.PuntoInteresse;

import java.util.List;
import java.util.UUID;

public class InfoVisita {
    private UUID id;
    private String descrizione;
    private List<PuntoInteresse> informazioni;

    public UUID getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<PuntoInteresse> getInformazioni() {
        return informazioni;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
