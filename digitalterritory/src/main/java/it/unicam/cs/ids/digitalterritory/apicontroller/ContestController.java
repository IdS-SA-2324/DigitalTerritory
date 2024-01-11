package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.db.entities.Contest;
import it.unicam.cs.ids.digitalterritory.db.repositories.ContestRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.contest.ContestLiberoDto;
import it.unicam.cs.ids.digitalterritory.model.Comune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/contest")
@Tag(name = "Contest")
public class ContestController {

    @Autowired
    private ContestRepository repository;
    @Autowired
    private UtenteRepository utenteRepository;

    @PostMapping("CreaContestLibero")
    public ResponseEntity<Response<Boolean>> creaContestLibero(@RequestBody ContestLiberoDto dto) {
        Contest c = new Contest();
        c.setData(dto.data());
        c.setNome(dto.nome());
//        c.se
        repository.save(c);
        // invia inviti
//        c.getContributori().stream().map(x -> x.getId());

        return null;
    }
}
