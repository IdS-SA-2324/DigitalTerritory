package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.ComuneRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.ContenutoRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
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

    @Autowired
    public PuntiInteresseService(PuntoInteresseRepository repository, OsmService osmService, UserTypeCheck userTypeChecker, JwtGenerator jwt, UtenteRepository utenteRepository, ComuneRepository comuneRepository, ContenutoRepository contenutoRepository) {
        this.repository = repository;
        this.osmService = osmService;
        this.userTypeChecker = userTypeChecker;
        this.jwt = jwt;
        this.utenteRepository = utenteRepository;
        this.comuneRepository = comuneRepository;
        this.contenutoRepository = contenutoRepository;
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

    private void salvaPoi(Comune comune, PuntoInteresseDto dto, Utente user) {
        PuntoInteresse poi = new PuntoInteresse();
        poi.setComune(comune);
        poi.setNome(dto.nome());
        poi.setCoordinate(dto.coordinate().toString());
        poi.setTipologia(dto.tipologia());
        // setto lo stato in base al tipo utente
        poi.setStatoApprovazione(user.getTipoUtente() == TipoUtente.Contributor ? StatoApprovazione.DaApprovare : StatoApprovazione.Approvato);
        this.addPoiToComune(comune, poi);
        repository.save(poi);
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
                    return new PuntoInteresseDto(x.getNome(), x.getTipologia(), Coordinate.fromString(x.getCoordinate()), contenuti);
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

    private ArrayList<InfoDaApprovareDto> getInfoDaApprovareDto(Utente utente) {
        var result = new ArrayList<InfoDaApprovareDto>();
        // mi prendo tutti i poi di quel comune
        var comune = utente.getComune();
        var pois = comune.getPuntiInteresse();
        for(var poi : pois) {
            if(poi.getStatoApprovazione() == StatoApprovazione.DaApprovare) {
                result.add(new InfoDaApprovareDto(poi.getNome(), poi.getId(), poi.getCreatore().getEmail(), TipoInformazione.POI));
            }
            for(var contenuto : poi.getContenuti().stream().filter(x -> x.getStatoApprovazione() == StatoApprovazione.DaApprovare).toList()) {
                result.add(new InfoDaApprovareDto("", contenuto.getId(), contenuto.getCreatore().getEmail(), TipoInformazione.CONTENUTO));
            }
        }
        return result;
    }


}
