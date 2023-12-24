package com.it.unicam.cs.ids.digitalterritory.model;

import com.it.unicam.cs.ids.digitalterritory.controllers.DBMSTouristSearcher;


public class Turista {
    private boolean exitFlag;
    public void creaSegnalazione(){}

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
