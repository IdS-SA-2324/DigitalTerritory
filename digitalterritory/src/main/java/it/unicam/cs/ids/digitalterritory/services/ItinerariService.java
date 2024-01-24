package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Itinerario;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.repositories.*;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.itinerari.ItinerarioDto;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItinerariService {

    private final ContenutoRepository contenutoRepository;
    private final PuntoInteresseRepository poiRepository;
    private final JwtGenerator jwt;
    private final UtenteRepository utenteRepository;
    private final ComuneRepository comuneRepository;
    private final ItinerarioRepository itinerarioRepository;

    @Autowired
    public ItinerariService(ContenutoRepository contenutoRepository, PuntoInteresseRepository poiRepository, JwtGenerator jwt, UtenteRepository utenteRepository, ComuneRepository comuneRepository, ItinerarioRepository itinerarioRepository) {
        this.contenutoRepository = contenutoRepository;
        this.poiRepository = poiRepository;
        this.jwt = jwt;
        this.utenteRepository = utenteRepository;
        this.comuneRepository = comuneRepository;
        this.itinerarioRepository = itinerarioRepository;
    }

    public Response<List<ItinerarioDto>> visualizzaItinerariPrivati(String token) {
        var utente = this.getUtenteFromToken(token);
        var itinerari = itinerarioRepository.findAll()
                .stream().filter(x -> x.isPrivato() && x.getCreatore() == utente)
                .map(x -> new ItinerarioDto(x.getNome(), x.getPuntiInteresse().stream().map(PuntoInteresse::getId).toList(), x.isPrivato(), x.getCreatore().getEmail()))
                .toList();
        return ResponseFactory.createFromResult(itinerari);
    }

    public Response<List<ItinerarioDto>> visualizzaItinerariOfComune(String comune) {
        var c = comuneRepository.getComuneByNomeIgnoreCase(comune);
        if(c.isEmpty()) {
            return ResponseFactory.createFromResult(new ArrayList<>(), false, "Il comune non è presente nel sistema");
        }
        var itinerari = itinerarioRepository.findAll()
                .stream().filter(x -> !x.isPrivato() && x.getComune() == c.get())
                .map(x -> new ItinerarioDto(x.getNome(), x.getPuntiInteresse().stream().map(PuntoInteresse::getId).toList(), x.isPrivato(), x.getCreatore().getEmail()))
                .toList();
        return ResponseFactory.createFromResult(itinerari);
    }

    public Response<Boolean> caricaItinerario(ItinerarioDto dto, String token) {
        var utente = this.getUtenteFromToken(token);
        var poiList = new ArrayList<PuntoInteresse>();
        var itinerario = new Itinerario();
        for(var poiId : dto.poiList()) {
            var poi = poiRepository.getById(poiId);
            if(poi != null) {
                poiList.add(poi);
                var itinerari = new ArrayList<>(poi.getItinerari());
                itinerari.add(itinerario);
                poi.setItinerari(itinerari);
            }
        }
        itinerario.setCreatore(utente);
        itinerario.setPrivato(dto.isPrivato());
        itinerario.setNome(dto.nome());
        itinerario.setPuntiInteresse(poiList);
        var comune = utente.getComune();
        itinerario.setComune(comune);
        var itinerari = new ArrayList<>(comune.getItinerari());
        itinerari.add(itinerario);
        comune.setItinerari(itinerari);
        itinerarioRepository.save(itinerario);
        comuneRepository.save(comune);
        poiRepository.saveAll(poiList);
        return ResponseFactory.createFromResult(true);
    }

    private Utente getUtenteFromToken(String token) {
        String email = jwt.getEmailFromJwt(token.substring(7));
        Optional<Utente> utente = utenteRepository.findByEmail(email);
        return utente.orElse(null);
    }
}
