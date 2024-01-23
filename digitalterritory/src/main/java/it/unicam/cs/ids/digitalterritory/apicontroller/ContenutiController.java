package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.ContenutoDto;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.UploadContenutoDto;
import it.unicam.cs.ids.digitalterritory.dto.poi.PuntoInteresseDto;
import it.unicam.cs.ids.digitalterritory.services.ContenutiService;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenuti")
@Tag(name = "Contenuti")
public class ContenutiController {
    @Autowired
    private ContenutiService service;
    @PostMapping(value = "/CaricaContenuto")
    public Response<Boolean> caricaContenuto(@RequestBody() UploadContenutoDto dto, HttpServletRequest req) {
        try {
            return service.uploadContenuto(dto, req.getHeader("Authorization"));
        } catch (Exception e) {
            return ResponseFactory.createFromResult(false, false, e.getMessage());
        }
    }


    @GetMapping(value = "/VisualizzaContenutiPrivati")
    public Response<List<ContenutoDto>> visualizzaContenutiPrivati(HttpServletRequest req) {
        try {
            return service.visualizzaContenutiPrivati(req.getHeader("Authorization"));
        } catch (Exception e) {
            return ResponseFactory.createFromResult(null, false, "C'è stato un errore. Riprova");
        }
    }
}
