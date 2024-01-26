package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.controllers.UpdaterDeleter;
import it.unicam.cs.ids.digitalterritory.db.entities.Itinerario;
import it.unicam.cs.ids.digitalterritory.dto.Geojson;
import it.unicam.cs.ids.digitalterritory.services.ControllerPersistenza;
import it.unicam.cs.ids.digitalterritory.services.OsmService;
import it.unicam.cs.ids.digitalterritory.utils.PuntoInteresse;

import java.util.List;

public class Curatore extends UtenteAutenticato {
    private List<Object> contenuti_da_approvare;

    public Curatore(String nome, String cognome, String email) {
        super();
    }

    private void verificaContenuti() {
     this.contenuti_da_approvare.forEach((contenuto) -> {this.approva(contenuto) ;
     } // oppure this.disapprova(contenuto)
     );
    }

    private void approva(Object object){
        UpdaterDeleter update = new UpdaterDeleter();
        update.save(object);
    }

    private void disapprova(Object object){
        this.contenuti_da_approvare.remove(object);
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void notifica(){
        System.out.println("nuova segnalazione");
    }
}

