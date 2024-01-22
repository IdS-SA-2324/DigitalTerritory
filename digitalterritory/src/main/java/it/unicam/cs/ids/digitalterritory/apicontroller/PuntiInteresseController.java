package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.repositories.ComuneRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import it.unicam.cs.ids.digitalterritory.dto.OsmResponse;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.poi.PuntoInteresseDto;
import it.unicam.cs.ids.digitalterritory.services.PuntiInteresseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/puntiinteresse")
@Tag(name = "Punti Interesse")
public class PuntiInteresseController {
    private final PuntiInteresseService poiService;

    @Autowired
    public PuntiInteresseController(PuntiInteresseService poi) {
        this.poiService = poi;
    }

//    @GetMapping(value = "/test")
//    public ResponseEntity<Object> test() {
//        Comune c = comuneRepository.getComuneByNome("Montone").orElse(new Comune());
//
//        return new ResponseEntity<>(c.getPuntiInteresse().stream().map(PuntoInteresse::getNome).toList(), HttpStatus.OK);
//    }

    @GetMapping("VisualizzaPuntiInteresse/{comune}")
    public Response<List<PuntoInteresseDto>> getAllPuntiInteresseOfComune(@PathVariable String comune) {
        try {
            var result = poiService.getAllPoiOfComune(comune);
            return result;
        } catch (Exception e) {
            return new Response<>(new ArrayList<>(), false, e.getMessage());
        }
    }

    @PostMapping(value = "/CaricaPuntoInteresse")
    public ResponseEntity<Response<Boolean>> checkPoiComune(@RequestBody()PuntoInteresseDto dto, HttpServletRequest req) {
        try {
            return new ResponseEntity<>(poiService.inserisciPuntoInteresse(dto, req.getHeader("Authorization")), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(false, false, e.getMessage()), HttpStatus.OK);
        }
    }
}
