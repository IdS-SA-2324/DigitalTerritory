package com.it.unicam.cs.ids.digitalterritory.model;

import com.it.unicam.cs.ids.digitalterritory.utils.Coordinate;

import java.util.UUID;

public class Comune {
    private UUID id;
    private String nome;
    private String regione;
    private Coordinate coordinate;

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
