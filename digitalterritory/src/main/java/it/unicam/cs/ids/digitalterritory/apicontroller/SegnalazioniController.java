package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.segnalazioni.SegnalazioneDto;
import it.unicam.cs.ids.digitalterritory.dto.segnalazioni.UploadSegnalazioneDto;
import it.unicam.cs.ids.digitalterritory.services.SegnalazioniService;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/segnalazioni")
@Tag(name = "Segnalazioni")
public class SegnalazioniController {

    @Autowired
    private SegnalazioniService service;


    @PostMapping("/inviaSegnalazione")
    public Response<Boolean> inviaSegnalazione(@RequestBody UploadSegnalazioneDto dto, HttpServletRequest req) {
        try {
            return service.inserisciSegnalazione(dto, req.getHeader("Authorization"));
        } catch (Exception e) {
            return ResponseFactory.createFromResult(false, false, e.getMessage());
        }
    }

    @GetMapping("/segnalazioniPoi/{poiId}")
    public Response<List<SegnalazioneDto>> visualizzaSegnalazioniPoi(@PathVariable UUID poiId) {
        try {
            return service.visualizzaSegnalazioniPoi(poiId);
        } catch (Exception e) {
            return ResponseFactory.createFromResult(new ArrayList<>(), false, e.getMessage());
        }
    }

    @GetMapping("/segnalazioniContenuti/{contenutoId}")
    public Response<List<SegnalazioneDto>> visualizzaSegnalazioniContenuti(@PathVariable UUID contenutoId) {
        try {
            return service.visualizzaSegnalazioniContenuto(contenutoId);
        } catch (Exception e) {
            return ResponseFactory.createFromResult(new ArrayList<>(), false, e.getMessage());
        }
    }
}
