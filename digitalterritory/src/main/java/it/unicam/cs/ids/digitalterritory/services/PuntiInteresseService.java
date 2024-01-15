package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.ComuneRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.osmdetails.OsmDetails;
import it.unicam.cs.ids.digitalterritory.dto.poi.PuntoInteresseDto;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.utils.PointContained;
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

    @Autowired
    public PuntiInteresseService(PuntoInteresseRepository repository, OsmService osmService, UserTypeCheck userTypeChecker, JwtGenerator jwt, UtenteRepository utenteRepository, ComuneRepository comuneRepository) {
        this.repository = repository;
        this.osmService = osmService;
        this.userTypeChecker = userTypeChecker;
        this.jwt = jwt;
        this.utenteRepository = utenteRepository;
        this.comuneRepository = comuneRepository;
    }


    public Response<Boolean> inserisciPuntoInteresse(PuntoInteresseDto dto, String jwtToken) throws Exception {
        if(!checkUserCanUploadPoi(jwtToken)) {
            return new Response<>(false, false, "Non hai le autorizzazioni per caricare un Punto di Interesse");
        }
        Utente user = this.getUtenteFromToken(jwtToken);
        Comune comune = this.getComuneOfUser(user);
        OsmDetails osm = this.getDetailsFromOsm(comune);
        PointContained pc = new PointContained();
        if(!pc.isInsideBoundingBox(dto.coordinate().latitude(), dto.coordinate().longitude(), osm.getGeometry().getCoordinates())) {
            return new Response<>(false, false, "Il punto selezionato è fuori dal tuo comune");
        }
        this.salvaPoi(comune, dto, user);
        return new Response<>(true, true, "");
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
}
