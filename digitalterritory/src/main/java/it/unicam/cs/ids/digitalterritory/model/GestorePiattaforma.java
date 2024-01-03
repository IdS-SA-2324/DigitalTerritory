package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.utils.Coordinate;

import java.util.UUID;

public class GestorePiattaforma extends UtenteAutenticato{
    private UUID id;

    private boolean cercaCuratore(String nome, String cognome){return false;}

    private boolean cercaComune(UUID id){return false;}

    private boolean cercaComune(Coordinate coordinate){return false;}

    private void creaComune(UUID id, String nome, String regione, Coordinate coordinate){
        Comune nuovoComune = new Comune(id,nome,regione,coordinate);
    }

    private void creaCuratore(String nome, String cognome, String email){
        Curatore newCuratore = new Curatore(nome,cognome,email);
        newCuratore.setPassword(this.generaPassword());
    }


    private String generaPassword(){
        StringBuilder password = null;
        for(int i=0;i<6;i++){
            assert false;
            password.append((char) (Math.random() * 256));
        }
        return password.toString();
    }
}


