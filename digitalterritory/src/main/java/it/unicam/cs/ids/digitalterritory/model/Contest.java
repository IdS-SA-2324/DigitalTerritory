package it.unicam.cs.ids.digitalterritory.model;

import java.util.List;

public class Contest {
    private int id;
    private String nome;

    private String data;

    private String obbiettivo;

    private boolean isClosed;

    private List<PuntoInteresse> contributi;

    private List<Contributor> partecipantiInvitati;

    public Contest(int id, String nome, String data, String obbiettivo,Comune comune){
        this.id=id;
        this.nome=nome;
        this.data=data;
        this.isClosed=false;
        this.obbiettivo=obbiettivo;
    }

    public Contest(int id, String nome, String data, String obbiettivo,List<Contributor> partecipantiInvitati){
        this.id=id;
        this.nome=nome;
        this.data=data;
        this.isClosed=false;
        this.partecipantiInvitati=partecipantiInvitati;
        this.obbiettivo=obbiettivo;
    }

    public int getId() {
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

    public boolean getIfClosed(){
        return this.isClosed;
    }

    public void setClosed(){
        this.isClosed=true;
    }

    public List<Contributor> getPartecipanti(){
        return this.partecipantiInvitati;
    }

    public String getObbiettivo(){
        return this.obbiettivo;
    }

    public List<PuntoInteresse> getContributi(){
        return this.contributi;
    }

    public void addContenuto(PuntoInteresse puntoInteresse){
        this.contributi.add(puntoInteresse);
    }
}
