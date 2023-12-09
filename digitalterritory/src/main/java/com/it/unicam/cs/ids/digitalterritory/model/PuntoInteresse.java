package com.it.unicam.cs.ids.digitalterritory.model;

import com.it.unicam.cs.ids.digitalterritory.utils.Coordinate;

import java.util.UUID;

public class PuntoInteresse {
    private UUID id;
    private String nome;

    private int tipologia;

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

    public int getTipologia() {
        return tipologia;
    }

    public void setTipologia(int tipologia) {
        this.tipologia = tipologia;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
