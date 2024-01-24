package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.SegnalazioneContenuto;
import it.unicam.cs.ids.digitalterritory.db.entities.SegnalazionePoi;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.repositories.*;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.ContenutoDto;
import it.unicam.cs.ids.digitalterritory.dto.segnalazioni.SegnalazioneDto;
import it.unicam.cs.ids.digitalterritory.dto.segnalazioni.UploadSegnalazioneDto;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SegnalazioniService {
    private final ContenutoRepository contenutoRepository;
    private final PuntoInteresseRepository poiRepository;
    private final SegnalazionePoiRepository segnalazionePoiRepository;
    private final SegnalazioneContenutoRepository segnalazioneContenutoRepository;
    private final JwtGenerator jwt;
    private final UtenteRepository utenteRepository;

    @Autowired
    public SegnalazioniService(ContenutoRepository contenutoRepository, PuntoInteresseRepository repository, SegnalazionePoiRepository segnalazionePoiRepository, SegnalazioneContenutoRepository segnalazioneContenutoRepository, JwtGenerator jwt, UtenteRepository utenteRepository) {
        this.contenutoRepository = contenutoRepository;
        this.poiRepository = repository;
        this.segnalazionePoiRepository = segnalazionePoiRepository;
        this.segnalazioneContenutoRepository = segnalazioneContenutoRepository;
        this.jwt = jwt;
        this.utenteRepository = utenteRepository;
    }

    public Response<Boolean> inserisciSegnalazione(UploadSegnalazioneDto dto, String token) {
        return switch (dto.tipo()) {
            case POI -> inserisciSegnalazionePoi(dto, token);
            case CONTENUTO -> inserisciSegnalazioneContenuto(dto, token);
        };
    }

    public Response<List<SegnalazioneDto>> visualizzaSegnalazioniPoi(UUID poiId) {
        var poi = poiRepository.getById(poiId);
        if(poi == null) {
            return ResponseFactory.createFromResult(new ArrayList<>(), false, "Il punto di interesse indicato non esiste");
        }
        var segnalazioni = poi.getSegnalazioni()
                .stream().map(x -> new SegnalazioneDto(x.getDescrizione(), x.getUtente() != null ? x.getUtente().getEmail() : ""))
                .toList();
        return ResponseFactory.createFromResult(segnalazioni);
    }

    public Response<List<SegnalazioneDto>> visualizzaSegnalazioniContenuto(UUID contenutoId) {
        var contenuto = contenutoRepository.getById(contenutoId);
        if(contenuto == null) {
            return ResponseFactory.createFromResult(new ArrayList<>(), false, "Il punto di interesse indicato non esiste");
        }
        var segnalazioni = contenuto.getSegnalazioni()
                .stream().map(x -> new SegnalazioneDto(x.getDescrizione(), x.getUtente() != null ? x.getUtente().getEmail() : ""))
                .toList();
        return ResponseFactory.createFromResult(segnalazioni);
    }

    private Response<Boolean> inserisciSegnalazioneContenuto(UploadSegnalazioneDto dto, String token) {
        var segnalazione = new SegnalazioneContenuto();
        segnalazione.setDescrizione(dto.descrizione());
        var contenuto = contenutoRepository.getById(dto.infoId());
        // imposto il poi
        segnalazione.setContenuto(contenuto);
        var segnalazioni = new ArrayList<>(contenuto.getSegnalazioni());
        segnalazioni.add(segnalazione);
        contenuto.setSegnalazioni(segnalazioni);

        // imposto l'utente se non è anonimo
        if(!dto.isAnonimo()) {
            var utente = this.getUtenteFromToken(token);
            segnalazione.setUtente(utente);
            segnalazioni = new ArrayList<>(utente.getSegnalazioniContenuti());
            segnalazioni.add(segnalazione);
            utente.setSegnalazioniContenuti(segnalazioni);
            utenteRepository.save(utente);
        }
        segnalazioneContenutoRepository.save(segnalazione);
        contenutoRepository.save(contenuto);
        return ResponseFactory.createFromResult(true);
    }

    private Response<Boolean> inserisciSegnalazionePoi(UploadSegnalazioneDto dto, String token) {
        var segnalazione = new SegnalazionePoi();
        segnalazione.setDescrizione(dto.descrizione());
        var poi = poiRepository.getById(dto.infoId());
        // imposto il poi
        segnalazione.setPoi(poi);
        var segnalazioni = new ArrayList<>(poi.getSegnalazioni());
        segnalazioni.add(segnalazione);
        poi.setSegnalazioni(segnalazioni);

        // imposto l'utente se non è anonimo
        if(!dto.isAnonimo()) {
            var utente = this.getUtenteFromToken(token);
            segnalazione.setUtente(utente);
            segnalazioni = new ArrayList<>(utente.getSegnalazioniPoi());
            segnalazioni.add(segnalazione);
            utente.setSegnalazioniPoi(segnalazioni);
            utenteRepository.save(utente);
        }
        segnalazionePoiRepository.save(segnalazione);
        poiRepository.save(poi);
        return ResponseFactory.createFromResult(true);

    }

    private Utente getUtenteFromToken(String token) {
        String email = jwt.getEmailFromJwt(token.substring(7));
        Optional<Utente> utente = utenteRepository.findByEmail(email);
        return utente.orElse(null);
    }
}
