package com.it.unicam.cs.ids.digitalterritory.controllers;

import com.it.unicam.cs.ids.digitalterritory.model.Comune;
import com.it.unicam.cs.ids.digitalterritory.model.PuntoInteresse;

import java.util.LinkedList;

public class DBMSTouristSearcher {
    private LinkedList<PuntoInteresse> ContenutiComune;
    public void getPuntiInteresse(Comune comune){
        /*for each (PuntoInteresse in Comune)
         {this.ContenutiComune.add(PuntoInteresse)}*/
    }

    public void next(){}

    public void closeComm(){
        this.ContenutiComune.clear();
        System.out.println("chiuso con successo");
    }

}
