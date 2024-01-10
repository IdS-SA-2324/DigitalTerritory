package it.unicam.cs.ids.digitalterritory.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "itinerari")
@Data
public class Itinerario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;
    @ManyToMany
    private Set<PuntoInteresse> puntiInteresse;
    @ManyToOne
    @JoinColumn(name = "creatore_id", nullable = false)
    private Utente creatore;

}
