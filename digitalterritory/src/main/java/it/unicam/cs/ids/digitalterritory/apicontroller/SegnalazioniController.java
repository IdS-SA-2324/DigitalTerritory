package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.segnalazioni.UploadSegnalazioneDto;
import it.unicam.cs.ids.digitalterritory.services.SegnalazioniService;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
