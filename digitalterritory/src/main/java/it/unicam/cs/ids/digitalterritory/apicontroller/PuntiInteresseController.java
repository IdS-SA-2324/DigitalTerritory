package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoContenuto;
import it.unicam.cs.ids.digitalterritory.db.repositories.ComuneRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import it.unicam.cs.ids.digitalterritory.dto.OsmResponse;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.poi.InfoDaApprovareDto;
import it.unicam.cs.ids.digitalterritory.dto.poi.PuntoInteresseDto;
import it.unicam.cs.ids.digitalterritory.dto.poi.TipoInformazione;
import it.unicam.cs.ids.digitalterritory.services.PuntiInteresseService;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
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

    @GetMapping(value = "/VisualizzaInfoDaApprovare")
    public Response<List<InfoDaApprovareDto>> visualizzaInfoDaApprovare(HttpServletRequest req) {
        try {
            return poiService.visualizzaInfoDaApprovare(req.getHeader("Authorization"));
        } catch (Exception e) {
            return ResponseFactory.createFromResult(null, false, "C'è stato un errore. Riprova");
        }
    }

    @GetMapping(value = "/VisualizzaInfoDaApprovareContest/{contestId}")
    public Response<List<InfoDaApprovareDto>> visualizzaInfoDaApprovareContest(@PathVariable UUID contestId, HttpServletRequest req) {
        try {
            return poiService.visualizzaInfoDaApprovareContest(req.getHeader("Authorization"), contestId);
        } catch (Exception e) {
            return ResponseFactory.createFromResult(null, false, "C'è stato un errore. Riprova");
        }
    }

    @PostMapping(value = "/CambiaStatoValutazione/{id}/{stato}/{tipo}")
    public Response<Boolean> cambiaStatoValutazione(@PathVariable UUID id, @PathVariable StatoApprovazione stato, @PathVariable TipoInformazione tipo) {
        try {
            return poiService.cambiaStatoApprovazione(id, stato, tipo);
        } catch (Exception e) {
            return ResponseFactory.createFromResult(false, false, "C'è stato un errore. Riprova");
        }
    }
}
