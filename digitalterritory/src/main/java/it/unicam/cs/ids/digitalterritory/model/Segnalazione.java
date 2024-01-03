package it.unicam.cs.ids.digitalterritory.model;

import java.util.UUID;

public class Segnalazione {
    private UUID id;
    private String descrizione;
    private PuntoInteresse punto;

    public UUID getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public PuntoInteresse getPunto() {
        return punto;
    }

    public void setPunto(PuntoInteresse punto) {
        this.punto = punto;
    }
}
