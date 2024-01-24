package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.SegnalazionePoi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SegnalazionePoiRepository extends JpaRepository<SegnalazionePoi, UUID> {
}
