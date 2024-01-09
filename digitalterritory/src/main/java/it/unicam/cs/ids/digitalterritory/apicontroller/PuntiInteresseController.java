package it.unicam.cs.ids.digitalterritory.apicontroller;

import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342")

@RestController
public class PuntiInteresseController {
    private PuntoInteresseRepository repository;

    @Autowired
    public PuntiInteresseController(PuntoInteresseRepository repository) {
        this.repository = repository;
    }
}
