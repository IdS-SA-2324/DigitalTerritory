package it.unicam.cs.ids.digitalterritory.db.entities;

import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "punti_interesse")
@Data
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
    @ManyToMany
    private Set<Itinerario> itinerari;
    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = true)
    private Contest contest;

    public PuntoInteresse() {}
}
