package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.Itinerario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItinerarioRepository extends JpaRepository<Itinerario, UUID> {
}
