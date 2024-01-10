package it.unicam.cs.ids.digitalterritory.apicontroller;

import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import it.unicam.cs.ids.digitalterritory.dto.OsmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/api/puntiinteresse")
public class PuntiInteresseController {
    private final PuntoInteresseRepository repository;

    @Autowired
    public PuntiInteresseController(PuntoInteresseRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<Object> test() {
//        try {
//            OsmResponse osm = osmService.getComuneByNomeRegione("Camerino", "Marche");
//            return new ResponseEntity<>(osm, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @RequestMapping(value = "/checkpoicomune", method = RequestMethod.POST)
    public ResponseEntity<Object> checkPoiComune() {
        // logica
        return null;
    }
}
