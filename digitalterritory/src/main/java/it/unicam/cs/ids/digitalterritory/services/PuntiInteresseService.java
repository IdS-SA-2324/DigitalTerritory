package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.Contest;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.*;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.ContenutoDto;
import it.unicam.cs.ids.digitalterritory.dto.osmdetails.OsmDetails;
import it.unicam.cs.ids.digitalterritory.dto.poi.InfoDaApprovareDto;
import it.unicam.cs.ids.digitalterritory.dto.poi.PuntoInteresseDto;
import it.unicam.cs.ids.digitalterritory.dto.poi.TipoInformazione;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.utils.Coordinate;
import it.unicam.cs.ids.digitalterritory.utils.PointContained;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import it.unicam.cs.ids.digitalterritory.utils.UserTypeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PuntiInteresseService {
    private final PuntoInteresseRepository repository;
    private final OsmService osmService;
    private final UserTypeCheck userTypeChecker;
    private final JwtGenerator jwt;
    private final UtenteRepository utenteRepository;
    private final ComuneRepository comuneRepository;
    private final ContenutoRepository contenutoRepository;
    private final ContestRepository contestRepository;

    @Autowired
    public PuntiInteresseService(PuntoInteresseRepository repository, OsmService osmService, UserTypeCheck userTypeChecker, JwtGenerator jwt, UtenteRepository utenteRepository, ComuneRepository comuneRepository, ContenutoRepository contenutoRepository, ContestRepository contestRepository) {
        this.repository = repository;
        this.osmService = osmService;
        this.userTypeChecker = userTypeChecker;
        this.jwt = jwt;
        this.utenteRepository = utenteRepository;
        this.comuneRepository = comuneRepository;
        this.contenutoRepository = contenutoRepository;
        this.contestRepository = contestRepository;
    }


    public Response<Boolean> inserisciPuntoInteresse(PuntoInteresseDto dto, String jwtToken) throws Exception {
        if(!checkUserCanUploadPoi(jwtToken)) {
            return ResponseFactory.createFromResult(false, false, "Non hai le autorizzazioni per caricare un Punto di Interesse");
        }
        Utente user = this.getUtenteFromToken(jwtToken);
        Comune comune = this.getComuneOfUser(user);
        OsmDetails osm = this.getDetailsFromOsm(comune);
        PointContained pc = new PointContained();
        if(!pc.isInsideBoundingBox(dto.coordinate().latitude(), dto.coordinate().longitude(), osm.getGeometry().getCoordinates())) {
            return ResponseFactory.createFromResult(false, false, "Il punto selezionato è fuori dal tuo comune");
        }
        this.salvaPoi(comune, dto, user);
        return ResponseFactory.createFromResult(true);
    }

    private void addPoiToComune(Comune comune, PuntoInteresse poi) {
        List<PuntoInteresse> pois = new ArrayList<>(comune.getPuntiInteresse());
        pois.add(poi);
        comune.setPuntiInteresse(pois);
        comuneRepository.save(comune);
    }

    private void salvaPoi(Comune comune, PuntoInteresseDto dto, Utente user) throws Exception {
        PuntoInteresse poi = new PuntoInteresse();
        poi.setComune(comune);
        poi.setNome(dto.nome());
        poi.setCoordinate(dto.coordinate().toString());
        poi.setTipologia(dto.tipologia());
        // setto lo stato in base al tipo utente
        poi.setStatoApprovazione(user.getTipoUtente() == TipoUtente.Contributor ? StatoApprovazione.DaApprovare : StatoApprovazione.Approvato);
        if(dto.contest() != UUID.fromString("00000000-0000-0000-0000-000000000000")) {
            var contest = contestRepository.getById(dto.contest());
            if(contest.isClosed()) {
                throw new Exception("Il contest è chiuso");
            }
            if(contest.isAInviti() && contest.getInvitati().stream().anyMatch(x -> x.getId() == user.getId())) {
                throw new Exception("Non fai parte degli invitati al contest");
            }
            // se fa parte del contest per forza devo metterlo da approvare
            poi.setStatoApprovazione(StatoApprovazione.DaApprovare);
            var pois = new ArrayList<>(contest.getPuntiInteresse());
            pois.add(poi);
            contest.setPuntiInteresse(pois);
            var contestList = new ArrayList<Contest>();
            contestList.add(contest);
            poi.setContest(contest);
            this.addPoiToComune(comune, poi);
            repository.save(poi);
            contestRepository.save(contest);
        } else {
            this.addPoiToComune(comune, poi);
            repository.save(poi);
        }
    }

    private OsmDetails getDetailsFromOsm(Comune comune) throws Exception {
        return osmService.getComuneById(comune.getPlaceOsmId());
    }

    private Comune getComuneOfUser(Utente user) {
        Optional<Comune> comune = utenteRepository.getComuneByUserId(user.getId());
        return comune.orElse(null);
    }

    private Utente getUtenteFromToken(String token) {
        String email = jwt.getEmailFromJwt(token.substring(7));
        Optional<Utente> utente = utenteRepository.findByEmail(email);
        return utente.orElse(null);
    }

    private boolean checkUserCanUploadPoi(String token) {
        return userTypeChecker.isUserType(token, TipoUtente.Curatore) || userTypeChecker.isUserType(token, TipoUtente.Contributor)
                || userTypeChecker.isUserType(token, TipoUtente.ContributorAutorizzato);
    }

    public Response<List<PuntoInteresseDto>> getAllPoiOfComune(String comune) {
        var comuneEntity = comuneRepository.getComuneByNomeIgnoreCase(comune);
        if(comuneEntity.isEmpty()) {
            return new Response<>(new ArrayList<>(), false, "");
        }
        var pois = comuneEntity.get()
                .getPuntiInteresse()
                .stream()
                .map(x -> {
                    // prendo solo i contenuti approvati
                    var contenuti = x.getContenuti().stream()
                            .filter(y -> y.getStatoApprovazione() == StatoApprovazione.Approvato && !y.isPrivato())
                            .map(y -> new ContenutoDto(y.getStatoApprovazione(), y.getTipoContenuto(), y.getTextContent()))
                            .toList();
                    return new PuntoInteresseDto(x.getNome(), x.getTipologia(), Coordinate.fromString(x.getCoordinate()), contenuti, UUID.fromString("00000000-0000-0000-0000-000000000000"));
                })
                .toList();
        return ResponseFactory.createFromResult(pois);
    }

    public Response<List<InfoDaApprovareDto>> visualizzaInfoDaApprovare(String token) {
        var utente = this.getUtenteFromToken(token);
        if(utente == null || utente.getTipoUtente() != TipoUtente.Curatore) {
            return new Response<>(new ArrayList<>(), false, "Non disponi delle autorizzazioni necessarie.");
        }
        var result = this.getInfoDaApprovareDto(utente);
        result.sort(Comparator.comparing(InfoDaApprovareDto::tipo));
        return ResponseFactory.createFromResult(result);
    }

    public Response<List<InfoDaApprovareDto>> visualizzaInfoDaApprovareContest(String token, UUID contestId) {
        var utente = this.getUtenteFromToken(token);
        if(utente == null || utente.getTipoUtente() != TipoUtente.Animator) {
            return new Response<>(new ArrayList<>(), false, "Non disponi delle autorizzazioni necessarie.");
        }
        var contest = contestRepository.getById(contestId);
        var result = this.getInfoDaApprovareOfContestDto(contest);
        result.sort(Comparator.comparing(InfoDaApprovareDto::tipo));
        return ResponseFactory.createFromResult(result);
    }

    public Response<Boolean> cambiaStatoApprovazione(UUID id, StatoApprovazione stato, TipoInformazione tipo){
        return switch (tipo) {
            case POI -> cambiaStatoApprovazionePoi(id, stato);
            case CONTENUTO -> cambiaStatoApprovazioneContenuto(id, stato);
        };
    }

    private Response<Boolean> cambiaStatoApprovazionePoi(UUID id, StatoApprovazione stato) {
        var poi = repository.getById(id);
        poi.setStatoApprovazione(stato);
        repository.save(poi);
        return ResponseFactory.createFromResult(true);
    }

    private Response<Boolean> cambiaStatoApprovazioneContenuto(UUID id, StatoApprovazione stato) {
        var contenuto = contenutoRepository.getById(id);
        contenuto.setStatoApprovazione(stato);
        contenutoRepository.save(contenuto);
        return ResponseFactory.createFromResult(true);
    }

    private ArrayList<InfoDaApprovareDto> getInfoDaApprovareOfContestDto(Contest contest) {
        var result = new ArrayList<InfoDaApprovareDto>();
        // mi prendo i poi che fanno parte di un contest, in quanto quelli vengono approvati dall'animator
        var pois = contest.getPuntiInteresse();
        return getInfoDaApprovareDtos(result, pois);
    }

    private ArrayList<InfoDaApprovareDto> getInfoDaApprovareDtos(ArrayList<InfoDaApprovareDto> result, List<PuntoInteresse> pois) {
        for(var poi : pois) {
            if(poi.getStatoApprovazione() == StatoApprovazione.DaApprovare) {
                result.add(new InfoDaApprovareDto(poi.getNome(), poi.getId(), poi.getCreatore() != null ? poi.getCreatore().getEmail() : "", TipoInformazione.POI));
            }
            for(var contenuto : poi.getContenuti().stream().filter(x -> x.getStatoApprovazione() == StatoApprovazione.DaApprovare).toList()) {
                result.add(new InfoDaApprovareDto("", contenuto.getId(), contenuto.getCreatore().getEmail(), TipoInformazione.CONTENUTO));
            }
        }
        return result;
    }

    private ArrayList<InfoDaApprovareDto> getInfoDaApprovareDto(Utente utente) {
        var result = new ArrayList<InfoDaApprovareDto>();
        // mi prendo tutti i poi di quel comune
        var comune = utente.getComune();
        // mi prendo i poi che non fanno parte di un contest, in quanto quelli vengono approvati dall'animator
        var pois = comune.getPuntiInteresse()
                .stream().filter(x -> x.getContest() == null)
                .toList();
        return getInfoDaApprovareDtos(result, pois);
    }


}
