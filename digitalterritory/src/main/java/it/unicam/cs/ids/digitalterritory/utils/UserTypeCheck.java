package it.unicam.cs.ids.digitalterritory.utils;

import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserTypeCheck {

    private JwtGenerator jwtGenerator;
    private UtenteRepository repository;

    @Autowired
    public UserTypeCheck(JwtGenerator jwtGenerator, UtenteRepository repository) {
        this.jwtGenerator = jwtGenerator;
        this.repository = repository;
    }

    public boolean isUserType(String token, TipoUtente tipoUtente) {
        if (token != null && token.startsWith("Bearer")) {
            String jwtToken = token.substring(7);
            String email = this.jwtGenerator.getEmailFromJwt(jwtToken);
            Optional<Utente> utente = repository.findByEmail(email);
            if (utente.isPresent()) {
                return utente.get().getTipoUtente() == tipoUtente;
            }
        }
        return false;
    }

}








