package com.it.unicam.cs.ids.digitalterritory.model;

/**
 * Enum per la gestione dello stato di approvazione di un contenuto
 */
public enum StatoApprovazione {
    Approvato(1),
    DaApprovare(0);

    private int valore;

    StatoApprovazione(int v) {
        this.valore = v;
    }

    public int getValore() {
        return valore;
    }
}
