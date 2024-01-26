package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {
    private List<PuntoInteresse> listPunti;

    public Itinerary(){
        this.listPunti=new ArrayList<>();
    }
    public void addPunto(PuntoInteresse punto){
        this.listPunti.add(punto);
    }
    public void removePunto(PuntoInteresse punto){
        this.listPunti.remove(punto);
    }
}
