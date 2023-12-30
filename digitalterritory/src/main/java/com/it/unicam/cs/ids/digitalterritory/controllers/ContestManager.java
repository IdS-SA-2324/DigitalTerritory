package com.it.unicam.cs.ids.digitalterritory.controllers;

import com.it.unicam.cs.ids.digitalterritory.model.Comune;
import com.it.unicam.cs.ids.digitalterritory.model.Contest;
import com.it.unicam.cs.ids.digitalterritory.model.Contributor;
import com.it.unicam.cs.ids.digitalterritory.model.PuntoInteresse;

import java.util.*;

import java.util.List;

public class ContestManager {

    private List<Contest> activeContests= new LinkedList<>();

    public void newFreeContest(Comune comune, String nome, String data, String obbiettivo) {
        Contest newcontest= new Contest(idGenerator(),nome,data,obbiettivo);
        this.activeContests.add(newcontest);

        this.sendInviti(); //a tutti quelli di comune
    }

    public void newClosedContest(String nome, String data,String obbiettivo, List<Contributor> partecipanti) {
        Contest newcontest= new Contest(idGenerator(),nome,data,obbiettivo,partecipanti);
        this.activeContests.add(newcontest);

        this.sendInvitiPrivati();
    }

    private void sendInvitiPrivati(){
        List<Contributor> toBeInvited = this.activeContests.getLast().getPartecipanti();
        toBeInvited.forEach((partecipante) -> partecipante.invita(this.activeContests.getLast().getObbiettivo()));
    }

    private void sendInviti(){

    }

    public void closeContest(String nome){
        this.activeContests.forEach(contest -> {
            if(contest.getNome().equals(nome)){contest.setClosed();}
        });
    }

    public List<PuntoInteresse> getContestList(String nome){
        List<PuntoInteresse> tempList = null;
        for (Contest contest : activeContests){
            if(contest.getNome().equals(nome)){tempList=  contest.getContributi();}
        }
    return tempList;}

    public void deleteContent(){
        //rimuove un PuntoInteresse dalla Lista di un Contest
    }

    private void insertList(){
        //fa l'insert nel DB
    }

    private int idGenerator(){
        int num=0;
        for(int i=0;i<5;i++){
            int n= (int) (Math.random()*10);
            num=num+n;
        }
        return num;
    }
}

