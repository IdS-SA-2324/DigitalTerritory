package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import it.unicam.cs.ids.digitalterritory.model.PuntoInteresse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PuntoInteresseRepository extends JpaRepository<PuntoInteresse, UUID> {
    Optional<PuntoInteresse> findByID(String id);
}
