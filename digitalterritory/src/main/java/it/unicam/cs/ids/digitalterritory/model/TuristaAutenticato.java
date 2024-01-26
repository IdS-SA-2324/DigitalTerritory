package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.controllers.DBMSVerifiedTouristSearcher;
import it.unicam.cs.ids.digitalterritory.services.ControllerPersistenza;
import it.unicam.cs.ids.digitalterritory.utils.PuntoInteresse;

import java.util.ArrayList;
import java.util.List;

public class TuristaAutenticato extends UtenteAutenticato{
    private boolean exitFlag=false;
    private List<Object> puntiSalvati= new ArrayList<>();

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

    public void save(PuntoInteresse punto, Comune comune){
     DBMSVerifiedTouristSearcher searcher = new DBMSVerifiedTouristSearcher();
     searcher.getPuntiInteresse(comune);
     searcher.saveContent(punto);
    }

    public void caricaContenutoMultimediale(PuntoInteresse poi, Object contenuto){
        if(ControllerPersistenza.getInstance().search(poi)){
            poi.addMultimedia(contenuto);
        }
    }

    public void caricaItinerario(){
        //TODO non implementabile in java in quanto il relativo controller è implementato con Spring
        //Simulazione:

        Itinerary itinerary=new Itinerary();
        itinerary.addPunto(new it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse());
        ControllerPersistenza.getInstance().insert(itinerary);
    }
}
