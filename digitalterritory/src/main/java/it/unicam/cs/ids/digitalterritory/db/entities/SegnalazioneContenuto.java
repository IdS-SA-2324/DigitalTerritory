package it.unicam.cs.ids.digitalterritory.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "segnalazioni_contenuti")
@Data
public class SegnalazioneContenuto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String descrizione;
    @ManyToOne
    @JoinColumn(name = "contenuto_id", nullable = false)
    private Contenuto contenuto;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Utente utente;
}
