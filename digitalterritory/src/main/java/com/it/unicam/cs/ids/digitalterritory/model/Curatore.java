package com.it.unicam.cs.ids.digitalterritory.model;

import java.util.List;

public class Curatore extends UtenteAutenticato {
    private List<Object> contenuti_da_approvare;

    public Curatore(String nome, String cognome, String email) {
        super();
    }

    private void verificaContenuti() {

    }

    public void setPassword(String password) {
        this.password = password;
    }
}

