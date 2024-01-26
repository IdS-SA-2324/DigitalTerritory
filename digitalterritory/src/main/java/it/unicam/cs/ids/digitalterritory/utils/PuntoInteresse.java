package it.unicam.cs.ids.digitalterritory.utils;

import it.unicam.cs.ids.digitalterritory.utils.Coordinate;

import java.util.List;
import java.util.UUID;

public class PuntoInteresse {
    private UUID id;
    private String nome;

    private int tipologia;

    private Coordinate coordinate;

    private List<Object> multimedia;


    public PuntoInteresse(String nome, int tipologia, Coordinate coordinate){
        this.nome=nome;
        this.tipologia= tipologia;
        this.coordinate=coordinate;
    }

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

    public void addMultimedia(Object object){
        this.multimedia.add(object);
    }
}
