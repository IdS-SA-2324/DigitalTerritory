package it.unicam.cs.ids.digitalterritory.utils;

import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import java.util.Optional;


public class UserTypeCheck {

   private JwtGenerator jwtGenerator;
   private UtenteRepository repository;
    private boolean isUserType(String token, TipoUtente tipoUtente) {
        if(token != null && token.startsWith("Bearer")){
            String jwtToken= token.substring(7);
            String email = this.jwtGenerator.getEmailFromJwt(jwtToken);
            Optional<Utente> utente = repository.findByEmail(email);
            if(utente.isPresent()){
                return utente.get().getTipoUtente() == tipoUtente;
            }
        }
        return false;
    }

    }








