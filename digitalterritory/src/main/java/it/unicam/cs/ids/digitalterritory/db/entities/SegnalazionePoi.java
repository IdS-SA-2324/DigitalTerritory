package it.unicam.cs.ids.digitalterritory.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "segnalazioni_poi")
@Data
public class SegnalazionePoi {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String descrizione;
    @ManyToOne
    @JoinColumn(name = "poi_id", nullable = false)
    private PuntoInteresse poi;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Utente utente;

}
