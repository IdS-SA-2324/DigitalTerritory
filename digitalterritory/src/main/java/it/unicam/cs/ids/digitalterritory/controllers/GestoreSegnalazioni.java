package it.unicam.cs.ids.digitalterritory.controllers;

import it.unicam.cs.ids.digitalterritory.model.ContenutoMultimediale;
import it.unicam.cs.ids.digitalterritory.model.Curatore;

import java.util.List;

public class GestoreSegnalazioni {
    private List<Object> segnalazioni;
    private List<ContenutoMultimediale> contenuti;
    private Curatore curatore;

    private void notificaCuratore(Object segnalazione){
        this.curatore.notifica();
    }

    public boolean controllaContenuto(Object contenuto){
        if(this.contenuti.contains(contenuto)){
            return true;
        }
        return false;
    }
    public void uploadContenuto(Object contenuto){
        this.segnalazioni.add(contenuto);
        this.notificaCuratore(this.segnalazioni.get(segnalazioni.size()));
    }
}
