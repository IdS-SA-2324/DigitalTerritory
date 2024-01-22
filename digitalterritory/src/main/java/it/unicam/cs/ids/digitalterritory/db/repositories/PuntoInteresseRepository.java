package it.unicam.cs.ids.digitalterritory.db.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import it.unicam.cs.ids.digitalterritory.db.entities.PuntoInteresse;

import java.util.UUID;

public interface PuntoInteresseRepository extends JpaRepository<PuntoInteresse, UUID> {
    PuntoInteresse getById(UUID id);
}
