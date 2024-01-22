package it.unicam.cs.ids.digitalterritory.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipologiaTipoInteresse;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
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
    @Enumerated(EnumType.STRING)
    private TipologiaTipoInteresse tipologia;
    @Column
    private String coordinate;
    @Column
    @Enumerated(EnumType.STRING)
    private StatoApprovazione statoApprovazione;
    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;

    @OneToMany(mappedBy = "poi")
    private List<Contenuto> contenuti;
    @OneToMany(mappedBy = "poi")
    private List<SegnalazionePoi> segnalazioni;
    @ManyToMany
    private List<Itinerario> itinerari;
    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = true)
    private Contest contest;

    public PuntoInteresse() {}
}
