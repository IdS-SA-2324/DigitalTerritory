package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "Utente con email %s non trovato";
    private final UtenteRepository utenteRepository;
    @Autowired
    public UserService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }
}
