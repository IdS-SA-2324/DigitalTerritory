package com.it.unicam.cs.ids.digitalterritory.model;

import com.it.unicam.cs.ids.digitalterritory.controllers.DBMSVerifiedTouristSearcher;

public class TuristaAutenticato extends UtenteAutenticato{
    private boolean exitFlag=false;
    public void creaSegnalazione(){}

    public void visualizzaContenuti(Comune comune){
        DBMSVerifiedTouristSearcher personalSearcehr = new DBMSVerifiedTouristSearcher();
        personalSearcehr.getPuntiInteresse(comune);
        while (!exitFlag){
            //il turista scorre i contenuti e può fare save
        }
    }

    public void exit(){
        this.exitFlag=true;
    }

    public void save(){

    };
}
