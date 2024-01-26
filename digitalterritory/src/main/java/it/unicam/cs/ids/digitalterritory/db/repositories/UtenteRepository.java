package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import it.unicam.cs.ids.digitalterritory.db.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("SELECT u.comune from Utente u where u.id = :userId ")
    Optional<Comune> getComuneByUserId(@Param("userId") UUID userId);
}
