package it.unicam.cs.ids.digitalterritory.db.controllers;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342" )

@RestController
public class POIServiceController   {
    private PuntoInteresseRepository POIRepository ;

    @Autowired
    public POIServiceController (PuntoInteresseRepository POIRepository) {
        this.POIRepository = POIRepository;

        PuntoInteresse testPoint= new PuntoInteresse();
        POIRepository.save(testPoint);

}
}
