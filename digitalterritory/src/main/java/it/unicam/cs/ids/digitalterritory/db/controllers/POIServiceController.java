package it.unicam.cs.ids.digitalterritory.db.controllers;

import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import it.unicam.cs.ids.digitalterritory.model.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;
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

        PuntoInteresse testPoint = new PuntoInteresse("test",0,new Coordinate(5,5));
        POIRepository.save(testPoint);

}
}
