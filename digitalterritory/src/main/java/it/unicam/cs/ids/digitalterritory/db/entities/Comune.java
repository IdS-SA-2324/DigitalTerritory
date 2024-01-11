package it.unicam.cs.ids.digitalterritory.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "comuni")
@Data
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String nome;
    @Column
    private String regione;
    @Column
    private long placeOsmId;
    @OneToOne(mappedBy = "comune")
    private Utente curatore;

    @OneToMany(mappedBy = "comune")
    private Set<PuntoInteresse> puntiInteresse;

    public Comune() {}
}
