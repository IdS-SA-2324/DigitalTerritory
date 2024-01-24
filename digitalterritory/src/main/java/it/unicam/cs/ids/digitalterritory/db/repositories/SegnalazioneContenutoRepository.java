package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.SegnalazioneContenuto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SegnalazioneContenutoRepository extends JpaRepository<SegnalazioneContenuto, UUID> {
}
