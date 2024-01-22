package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.ComuneRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.OsmResponse;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.utenti.AbilitaComuneCuratoreDto;
import it.unicam.cs.ids.digitalterritory.utils.UserTypeCheck;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "Utente con email %s non trovato";
    private UtenteRepository utenteRepository;
    private ComuneRepository comuneRepository;
    private OsmService osmService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UtenteRepository utenteRepository, ComuneRepository comuneRepository, OsmService osmService,PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.comuneRepository = comuneRepository;
        this.osmService = osmService;
        this.passwordEncoder = passwordEncoder;
    }

    public Response<Boolean> abilitaComuneCuratore(AbilitaComuneCuratoreDto request) {
        try {
            var comuneOsm = osmService.getComuneByNomeRegione(request.nomeComune(), request.nomeRegione());
            var alreadyExistUser = utenteRepository.existsByEmail(request.curatore().email());
            if(alreadyExistUser) {
                return new Response<>(false, false, "Esiste già un utente con quella mail");
            }
            var utente = new Utente();
            utente.setNome(request.curatore().nome());
            utente.setCognome(request.curatore().cognome());
            utente.setTipoUtente(TipoUtente.Curatore);
            utente.setPassword(passwordEncoder.encode(request.curatore().password()));
            utente.setEmail(request.curatore().email());
            // salvo l'utente
            var saved = utenteRepository.save(utente);
            Comune comune = new Comune();
            comune.setCuratore(saved);
            comune.setNome(comuneOsm.getName());
            comune.setRegione(request.nomeRegione());
            comune.setPlaceOsmId(String.format("%s%d", comuneOsm.getOsmType().toUpperCase().charAt(0), comuneOsm.getPlaceId()));
            // salvo il comune
            var cSaved = comuneRepository.save(comune);
            saved.setComune(cSaved);
            utenteRepository.save(saved);
            return new Response<>(true, true, "");
        } catch (Exception e) {
            return new Response<>(false, false, e.getMessage());
        }
    }
}
