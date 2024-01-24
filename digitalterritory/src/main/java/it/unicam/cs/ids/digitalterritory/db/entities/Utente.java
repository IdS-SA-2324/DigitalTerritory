package it.unicam.cs.ids.digitalterritory.db.entities;

import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column
    private String nome;
    @Column
    private String cognome;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoUtente tipoUtente;
    @ManyToOne
    @JoinColumn(name = "comune_id")
    private Comune comune;
    @OneToMany(mappedBy = "utente")
    private List<SegnalazionePoi> segnalazioniPoi;
    @OneToMany(mappedBy = "utente")
    private List<SegnalazioneContenuto> segnalazioniContenuti;
    @OneToMany(mappedBy = "creatore")
    private List<Itinerario> itinerari;
    @OneToMany(mappedBy = "creatore")
    private List<Contenuto> contenuti;
    @OneToMany(mappedBy = "creatore")
    private List<PuntoInteresse> puntiInteresse;
}
