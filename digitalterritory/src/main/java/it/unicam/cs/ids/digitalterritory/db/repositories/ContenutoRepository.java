package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.Contenuto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContenutoRepository extends JpaRepository<Contenuto, UUID> {
    List<Contenuto> getByCreatoreId(UUID id);
}
