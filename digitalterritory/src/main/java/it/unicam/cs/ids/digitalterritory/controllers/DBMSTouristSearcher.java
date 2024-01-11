package it.unicam.cs.ids.digitalterritory.controllers;

import it.unicam.cs.ids.digitalterritory.model.Comune;
import it.unicam.cs.ids.digitalterritory.utils.PuntoInteresse;

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
