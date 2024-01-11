package it.unicam.cs.ids.digitalterritory.db.repositories;

import it.unicam.cs.ids.digitalterritory.db.entities.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContestRepository extends JpaRepository<Contest, UUID> {

}
