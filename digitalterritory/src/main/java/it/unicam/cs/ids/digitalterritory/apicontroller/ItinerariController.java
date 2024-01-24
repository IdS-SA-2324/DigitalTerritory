package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.itinerari.ItinerarioDto;
import it.unicam.cs.ids.digitalterritory.services.ItinerariService;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/itinerari")
@Tag(name = "Itinerari")
public class ItinerariController {
    @Autowired
    private ItinerariService itinerariService;


    @PostMapping("/caricaItinerario")
    public Response<Boolean> caricaItinerario(@RequestBody ItinerarioDto dto, HttpServletRequest req) {
        try {
            return itinerariService.caricaItinerario(dto, req.getHeader("Authorization"));
        } catch (Exception e) {
            return ResponseFactory.createFromResult(false, false, e.getMessage());
        }
    }

    @GetMapping("/visualizzaItinerariPrivati")
    public Response<List<ItinerarioDto>> visualizzaItinerariPrivati(HttpServletRequest req) {
        try {
            return itinerariService.visualizzaItinerariPrivati(req.getHeader("Authorization"));
        } catch (Exception e) {
            return ResponseFactory.createFromResult(new ArrayList<>(), false, e.getMessage());
        }
    }

    @GetMapping("/visualizzaItinerari/{comune}")
    public Response<List<ItinerarioDto>> visualizzaItinerariPrivati(@PathVariable String comune) {
        try {
            return itinerariService.visualizzaItinerariOfComune(comune);
        } catch (Exception e) {
            return ResponseFactory.createFromResult(new ArrayList<>(), false, e.getMessage());
        }
    }
}
