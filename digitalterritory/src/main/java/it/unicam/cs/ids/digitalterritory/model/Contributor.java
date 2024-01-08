package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.services.OsmService;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;

public class Contributor extends UtenteAutenticato{

    private String regione;
    private String comune;
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

    public void creaPIPerContest(Contest contest){
        PuntoInteresse puntoInteresse=new PuntoInteresse(); //crea un punto di interesse e lo aggiunge
        contest.addContenuto(puntoInteresse);
    }

    public void caricaPuntoInteresse(String nome, int tipologia, Coordinate coordinate) throws Exception {
        OsmService osm = new OsmService();
        System.out.println(osm.getComuneByNomeRegione(this.nome,this.regione).getBoundingBox());

    }
}
