package it.unicam.cs.ids.digitalterritory.apicontroller;

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
            OsmResponse osm = osmService.getComuneByNomeRegione("Camerino", "Marche");
            return new ResponseEntity<>(osm, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public ResponseEntity<Object> test2(@RequestHeader("Authorization") String header) {
        // controllo per ottenere il token dall'header della chiamata
        if(header != null && header.startsWith("Bearer ")) {
            String jwtToken = header.substring(7);
            return new ResponseEntity<>(jwt.getEmailFromJwt(jwtToken), HttpStatus.OK);
        }
        return null;
//        try {
//            OsmDetails osm = osmService.getComuneById(99704152);
//            return new ResponseEntity<>(osm, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
