package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Contest;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.*;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.contest.ContestDto;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContestService {
    private final ContenutoRepository contenutoRepository;
    private final PuntoInteresseRepository poiRepository;
    private final SegnalazionePoiRepository segnalazionePoiRepository;
    private final SegnalazioneContenutoRepository segnalazioneContenutoRepository;
    private final JwtGenerator jwt;
    private final UtenteRepository utenteRepository;
    private final ContestRepository contestRepository;
    private final ComuneRepository comuneRepository;

    @Autowired
    public ContestService(ContenutoRepository contenutoRepository, PuntoInteresseRepository poiRepository, SegnalazionePoiRepository segnalazionePoiRepository, SegnalazioneContenutoRepository segnalazioneContenutoRepository, JwtGenerator jwt, UtenteRepository utenteRepository, ContestRepository contestRepository, ComuneRepository comuneRepository) {
        this.contenutoRepository = contenutoRepository;
        this.poiRepository = poiRepository;
        this.segnalazionePoiRepository = segnalazionePoiRepository;
        this.segnalazioneContenutoRepository = segnalazioneContenutoRepository;
        this.jwt = jwt;
        this.utenteRepository = utenteRepository;
        this.contestRepository = contestRepository;
        this.comuneRepository = comuneRepository;
    }

    public Response<Boolean> creaContest(ContestDto dto, String token) {
        var utente = this.getUtenteFromToken(token);
        if(utente.getTipoUtente() != TipoUtente.Animator) {
            return ResponseFactory.createFromResult(false, false, "Non hai i permessi per caricare un contest");
        }
        var utenti = new ArrayList<Utente>();
        var contest = new Contest();
        for(var ute : dto.invitati()) {
            var u = utenteRepository.getById(ute);
            var contests = new ArrayList<>(u.getContest());
            contests.add(contest);
            u.setContest(contests);
            utenti.add(u);
        }
        var comune = comuneRepository.getById(dto.comune());
        var contestList = new ArrayList<>(comune.getContests());
        contestList.add(contest);
        comune.setContests(contestList);
        contest.setComune(comune);
        contest.setInvitati(utenti);
        contest.setNome(dto.nome());
        contest.setData(dto.data());
        contest.setObiettivo(dto.obiettivo());
        contest.setClosed(false);
        contest.setAInviti(!dto.invitati().isEmpty());
        contestRepository.save(contest);
        if(!utenti.isEmpty()) {
            utenteRepository.saveAll(utenti);
        }
        comuneRepository.save(comune);
        return ResponseFactory.createFromResult(true);
    }

    public Response<Boolean> chiudiContest(UUID contestId) {
        var contest = contestRepository.getById(contestId);
        contest.setClosed(true);
        contestRepository.save(contest);
        return ResponseFactory.createFromResult(true);
    }


    private Utente getUtenteFromToken(String token) {
        String email = jwt.getEmailFromJwt(token.substring(7));
        Optional<Utente> utente = utenteRepository.findByEmail(email);
        return utente.orElse(null);
    }
}
