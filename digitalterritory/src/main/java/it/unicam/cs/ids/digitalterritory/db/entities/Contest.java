package it.unicam.cs.ids.digitalterritory.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "contest")
@Data
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;
    @Column
    private Date data;
    @Column
    private String obiettivo;
    @Column
    private boolean isClosed;
    @Column
    private boolean isAInviti;
    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = true)
    private Comune comune;
    @OneToMany(mappedBy = "contest")
    private List<PuntoInteresse> puntiInteresse;
    @ManyToMany
    private List<Utente> invitati;
}
