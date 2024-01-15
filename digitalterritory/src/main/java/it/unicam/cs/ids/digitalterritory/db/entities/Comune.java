package it.unicam.cs.ids.digitalterritory.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
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
    private String placeOsmId;
    @OneToOne(mappedBy = "comune")
    private Utente curatore;

    @OneToMany(mappedBy = "comune",fetch = FetchType.EAGER)
    private List<PuntoInteresse> puntiInteresse;

    public Comune() {}
}
