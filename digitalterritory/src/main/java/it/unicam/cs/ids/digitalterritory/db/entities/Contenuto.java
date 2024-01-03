package it.unicam.cs.ids.digitalterritory.db.entities;

import it.unicam.cs.ids.digitalterritory.db.enums.StatoApprovazione;
import it.unicam.cs.ids.digitalterritory.db.enums.TipoContenuto;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "contenuti")
public class Contenuto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @Enumerated(EnumType.STRING)
    private StatoApprovazione statoApprovazione;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoContenuto tipoContenuto;
    @Column
    private String textContent;
    @Lob
    private Blob fileContent;

    @ManyToOne
    @JoinColumn(name = "poi_id", nullable = false)
    private PuntoInteresse poi;

}
