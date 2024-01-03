package it.unicam.cs.ids.digitalterritory.db.entities;

import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "punti_interesse")
public class PuntoInteresse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String nome;
    @Column
    private int tipologia;
    @Column
    private String coordinate;
    @Column
    @Enumerated(EnumType.STRING)
    private StatoApprovazione statoApprovazione;
    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;

    @OneToMany(mappedBy = "poi")
    private Set<Contenuto> contenuti;
    @OneToMany(mappedBy = "poi")
    private Set<SegnalazionePoi> segnalazioni;

    public PuntoInteresse() {}
}
