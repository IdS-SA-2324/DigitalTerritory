package com.it.unicam.cs.ids.digitalterritory.model;

import java.util.UUID;

public class Contest {
    private UUID id;
    private String nome;

    private String data;

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
