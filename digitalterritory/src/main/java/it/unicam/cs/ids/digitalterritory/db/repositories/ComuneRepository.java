package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {
    Optional<Comune> getComuneByNomeIgnoreCase(String nome);
}
