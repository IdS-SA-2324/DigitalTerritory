package it.unicam.cs.ids.digitalterritory.apicontroller;

import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.ComuneRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.OsmResponse;
import it.unicam.cs.ids.digitalterritory.dto.osmdetails.OsmDetails;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.services.OsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/api/tests")
public class TestController {
    @Autowired
    private OsmService osmService;
    @Autowired
    private JwtGenerator jwt;
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<Object> test() {
        try {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public ResponseEntity<Object> test2() {
        try {
            OsmDetails osm = osmService.getComuneById("R42428");
            return new ResponseEntity<>(osm, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
