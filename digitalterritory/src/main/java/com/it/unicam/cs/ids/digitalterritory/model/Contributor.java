package com.it.unicam.cs.ids.digitalterritory.model;

public class Contributor extends UtenteAutenticato{
    private boolean isAutorizzato;

    public boolean isAutorizzato() {
        return isAutorizzato;
    }

    public void setAutorizzato(boolean autorizzato) {
        isAutorizzato = autorizzato;
    }
}
