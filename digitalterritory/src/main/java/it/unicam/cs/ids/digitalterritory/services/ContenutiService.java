package it.unicam.cs.ids.digitalterritory.services;

import it.unicam.cs.ids.digitalterritory.db.entities.Contenuto;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import it.unicam.cs.ids.digitalterritory.db.repositories.ContenutoRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.PuntoInteresseRepository;
import it.unicam.cs.ids.digitalterritory.db.repositories.UtenteRepository;
import it.unicam.cs.ids.digitalterritory.dto.Response;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.ContenutoDto;
import it.unicam.cs.ids.digitalterritory.dto.contenuti.UploadContenutoDto;
import it.unicam.cs.ids.digitalterritory.security.JwtGenerator;
import it.unicam.cs.ids.digitalterritory.utils.ResponseFactory;
import it.unicam.cs.ids.digitalterritory.utils.UserTypeCheck;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ContenutiService {
    private final ContenutoRepository contenutoRepository;
    private final UserTypeCheck userTypeChecker;
    private final JwtGenerator jwt;
    private final UtenteRepository utenteRepository;
    private final PuntoInteresseRepository poiRepository;

    @Autowired
    public ContenutiService(ContenutoRepository contenutoRepository, UserTypeCheck userTypeChecker, JwtGenerator jwt, UtenteRepository utenteRepository, PuntoInteresseRepository poiRepository) {
        this.contenutoRepository = contenutoRepository;
        this.userTypeChecker = userTypeChecker;
        this.jwt = jwt;
        this.utenteRepository = utenteRepository;
        this.poiRepository = poiRepository;
    }

    public Response<Boolean> uploadContenuto(UploadContenutoDto dto, String token) throws IOException {
        if(!canUserUploadContenuto(token)) {
            return ResponseFactory
                    .createFromResult(false, false, "Non si dispone delle autorizzazioni necessarie per caricare un contenuto");
        }
        var utente = this.getUtenteFromToken(token);
        var poi = this.poiRepository.getById(dto.poiId());
        var contenuto = this.createContenutoFromDto(dto, utente, poi);
        var contenuti = new ArrayList<>(poi.getContenuti());
        contenuti.add(contenuto);
        poi.setContenuti(contenuti);
        var contenutiUtente = new ArrayList<>(utente.getContenuti());
        contenutiUtente.add(contenuto);
        utente.setContenuti(contenutiUtente);
        poiRepository.save(poi);
        utenteRepository.save(utente);
        contenutoRepository.save(contenuto);
        return ResponseFactory.createFromResult(true);
    }

    private Contenuto createContenutoFromDto(UploadContenutoDto dto, Utente utente, PuntoInteresse poi) throws IOException {
        var contenuto = new Contenuto();
        contenuto.setTipoContenuto(dto.tipoContenuto());
        contenuto.setCreatore(utente);
        contenuto.setPoi(poi);
        // se il contenuto è privato allora lo metto subito come approvato
        if((utente.getTipoUtente() == TipoUtente.ContributorAutorizzato || utente.getTipoUtente() == TipoUtente.Curatore) && !dto.isPrivato()) {
            contenuto.setStatoApprovazione(StatoApprovazione.Approvato);
        } else {
            contenuto.setStatoApprovazione(StatoApprovazione.DaApprovare);
        }
        if(dto.fileContent() == null && !dto.textContent().isEmpty()) {
            contenuto.setTextContent(dto.textContent());
        } else if(dto.fileContent() != null) {
            contenuto.setFileContent(dto.fileContent());
        }
        contenuto.setPrivato(dto.isPrivato());
        return contenuto;
    }

    private Utente getUtenteFromToken(String token) {
        String email = jwt.getEmailFromJwt(token.substring(7));
        Optional<Utente> utente = utenteRepository.findByEmail(email);
        return utente.orElse(null);
    }

    public Response<List<ContenutoDto>> visualizzaContenutiPrivati(String token) {
        var utente = this.getUtenteFromToken(token);
        var contenuti = contenutoRepository
                .getByCreatoreId(utente.getId())
                .stream()
                .filter(Contenuto::isPrivato)
                .map(x -> {
                    var poi = x.getPoi();
                    return new ContenutoDto(StatoApprovazione.Approvato, x.getTipoContenuto(), x.getTextContent(), poi.getNome());
                })
                .toList();
        return ResponseFactory.createFromResult(contenuti);
    }

    private boolean canUserUploadContenuto(String token) {
        return userTypeChecker.isUserType(token, TipoUtente.Curatore) || userTypeChecker.isUserType(token, TipoUtente.Contributor)
                || userTypeChecker.isUserType(token, TipoUtente.ContributorAutorizzato);
    }
}
