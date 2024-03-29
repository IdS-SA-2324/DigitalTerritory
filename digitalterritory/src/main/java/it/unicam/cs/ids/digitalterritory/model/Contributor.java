package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.services.ControllerPersistenza;
import it.unicam.cs.ids.digitalterritory.services.OsmService;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;
import it.unicam.cs.ids.digitalterritory.utils.PointContained;
import it.unicam.cs.ids.digitalterritory.utils.PuntoInteresse;

public class Contributor extends UtenteAutenticato{

    private String regione; //regione di residenza del contributor
    private String comune; //comune di residenza del contributor
    private boolean isAutorizzato;



    public boolean isAutorizzato() {
        return isAutorizzato;
    }

    public void setAutorizzato(boolean autorizzato) {
        isAutorizzato = autorizzato;
    }

    public void invita(String message){
        System.out.println("Sei stato invitato a partecipare al contest che ha l'obbiettivo di: ");
        System.out.println(message);
    }


    public void caricaPuntoInteresse(String nome, int tipologia, Coordinate coordinate) throws Exception {
        OsmService osm = new OsmService();
       PointContained point = new PointContained(coordinate,osm.getComuneByNomeRegione(this.comune,this.regione).getGeoJson().getCoordinates().get(0));

       if(point.isPointContained()){
            PuntoInteresse nuovo= new PuntoInteresse(nome,tipologia,coordinate);
            ControllerPersistenza.getInstance().insert(nuovo);
        }


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
