package it.unicam.cs.ids.digitalterritory.apicontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.utenti.AbilitaComuneCuratoreDto;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/utenti")
@Tag(name = "Utenti")
public class UtentiController {
    @Autowired
    private UserService service;
    @Autowired
    private JwtGenerator jwt;
    @Autowired
    private UtenteRepository utenteRepository;

    @PostMapping("abilitaComuneCuratore")
    @Operation(summary = "Metodo per abilitare un curatore e un comune")
    public ResponseEntity<Response<Boolean>> abilitaComuneCuratore(@RequestBody AbilitaComuneCuratoreDto dto, HttpServletRequest request) {
        if(isUserType(request.getHeader("Authorization"), TipoUtente.ADMIN)) {
            Response<Boolean> result = service.abilitaComuneCuratore(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response<>(false, false, "Devi essere un admin"), HttpStatus.OK);
    }


    private boolean isUserType(String token, TipoUtente tipoUtente){
        if(token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String email = jwt.getEmailFromJwt(jwtToken);
            Optional<Utente> utente = utenteRepository.findByEmail(email);
            if(utente.isPresent()) {
                return utente.get().getTipoUtente() == tipoUtente;
            }
        }
        return false;
    }

}
