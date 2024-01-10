package it.unicam.cs.ids.digitalterritory.db.entities;

import it.unicam.cs.ids.digitalterritory.db.enums.TipoUtente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comune_id", referencedColumnName = "id")
    private Comune comune;
    @OneToMany(mappedBy = "utente")
    private Set<SegnalazionePoi> segnalazioniPoi;
    @OneToMany(mappedBy = "utente")
    private Set<SegnalazioneContenuto> segnalazioniContenuti;
    @OneToMany(mappedBy = "creatore")
    private Set<Itinerario> itinerari;
}
