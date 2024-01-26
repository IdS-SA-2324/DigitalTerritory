package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.controllers.UpdaterDeleter;
import it.unicam.cs.ids.digitalterritory.dto.Geojson;
import it.unicam.cs.ids.digitalterritory.services.ControllerPersistenza;
import it.unicam.cs.ids.digitalterritory.services.OsmService;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;

import java.util.UUID;

public class GestorePiattaforma extends UtenteAutenticato{
    private UUID id;
    private UpdaterDeleter updaterDeleter;
    public GestorePiattaforma(){
        this.updaterDeleter=new UpdaterDeleter();
    }

    private boolean cercaCuratore(String nome, String cognome){
       return this.updaterDeleter.search(new Curatore(nome,cognome,"email"));
    }

    private boolean cercaComune(UUID id){
       return this.updaterDeleter.search(new Comune(id,"nome","regione",new Coordinate(1,1)));
    }

    private boolean cercaComune(Coordinate coordinate){
        return this.updaterDeleter.search(new Comune(UUID.randomUUID(),"nome","regione",coordinate));
    }

    private void creaComune(UUID id, String nome, String regione, Coordinate coordinate) throws Exception {
        OsmService service = new OsmService();
        Geojson newjoson=service.getComuneByNomeRegione(nome,regione).getGeoJson();
        Comune nuovoComune = new Comune(id,nome,regione,new Coordinate(newjoson.getCoordinates().get(0).get(0).get(1),newjoson.getCoordinates().get(0).get(0).get(0)));
        this.updaterDeleter.save(nuovoComune);
    }

    private void creaCuratore(String nome, String cognome, String email){
        Curatore newCuratore = new Curatore(nome,cognome,email);
        newCuratore.setPassword(this.generaPassword());
        this.updaterDeleter.save(newCuratore);
    }


    private String generaPassword(){
        StringBuilder password = null;
        for(int i=0;i<6;i++){
            assert false;
            password.append((char) (Math.random() * 256));
        }
        return password.toString();
    }
}


