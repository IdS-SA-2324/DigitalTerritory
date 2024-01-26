package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.controllers.DBMSTouristSearcher;
import it.unicam.cs.ids.digitalterritory.controllers.GestoreSegnalazioni;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;


public class Turista {
    private boolean exitFlag;
    public void creaSegnalazione(PuntoInteresse puntoInteresse,ContenutoMultimediale contenuto){
        GestoreSegnalazioni gestoreSegnalazioni= new GestoreSegnalazioni();
        Segnalazione segnalazione = new Segnalazione();
        if(gestoreSegnalazioni.controllaContenuto(puntoInteresse)){
            gestoreSegnalazioni.uploadContenuto(contenuto);
        }

    }

    public void visualizzaContenuti(Comune comune){
        DBMSTouristSearcher personalSearcehr = new DBMSTouristSearcher();
        personalSearcehr.getPuntiInteresse(comune);
        while (!exitFlag){
            //il turista scorre i contenuti 
        }
    }

    public void exit(){
        this.exitFlag=true;
    }
}
