package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.db.entities.Contest;
import it.unicam.cs.ids.digitalterritory.db.repositories.ContestRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.contest.ContestDto;
import it.unicam.cs.ids.digitalterritory.services.ContestService;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contest")
@Tag(name = "Contest")
public class ContestController {

    @Autowired
    private ContestService service;

    @PostMapping("/creaContest")
    public Response<Boolean> creaContest(@RequestBody ContestDto dto, HttpServletRequest req) {
        try {
            return service.creaContest(dto, req.getHeader("Authorization"));
        } catch (Exception e) {
            return ResponseFactory.createFromResult(false, false, e.getMessage());
        }
    }

    @GetMapping("/chiudiContest/{contestId}")
    public Response<Boolean> chiudiContest(@PathVariable UUID contestId) {
        try {
            return service.chiudiContest(contestId);
        } catch (Exception e) {
            return ResponseFactory.createFromResult(false, false, e.getMessage());
        }
    }
}
