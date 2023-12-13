package com.it.unicam.cs.ids.digitalterritory.model;

import java.util.UUID;

public class Contenuto {
    private String stato;
    private String attribute;
    private UUID id;

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public UUID getId() {
        return id;
    }
}
