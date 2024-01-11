package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.ComuneRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.OsmResponse;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.utenti.AbilitaComuneCuratoreDto;
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
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ComuneRepository comuneRepository;
    @Autowired
    private OsmService osmService;

    public Response<Boolean> abilitaComuneCuratore(AbilitaComuneCuratoreDto request) {
        try {
            OsmResponse comuneOsm = osmService.getComuneByNomeRegione(request.nomeComune(), request.nomeRegione());
            Boolean alreadyExistUser = utenteRepository.existsByEmail(request.curatore().email());
            if(alreadyExistUser) {
                return new Response<>(false, false, "Esiste già un utente con quella mail");
            }
            Utente utente = new Utente();
            utente.setNome(request.curatore().nome());
            utente.setCognome(request.curatore().cognome());
            utente.setTipoUtente(TipoUtente.Curatore);
            utente.setPassword(request.curatore().password());
            utente.setEmail(request.curatore().email());
            // salvo l'utente
            Utente saved = utenteRepository.save(utente);
            Comune comune = new Comune();
            comune.setCuratore(saved);
            comune.setNome(comuneOsm.getName());
            comune.setRegione(request.nomeRegione());
            comune.setPlaceOsmId(comuneOsm.getPlaceId());
            // salvo il comune
            Comune cSaved = comuneRepository.save(comune);
            saved.setComune(cSaved);
            utenteRepository.save(saved);
            return new Response<>(true, true, "");
        } catch (Exception e) {
            return new Response<>(false, false, e.getMessage());
        }
    }
}
