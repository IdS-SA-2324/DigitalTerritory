package com.it.unicam.cs.ids.digitalterritory.model;

import java.util.List;

public class Animatore extends UtenteAutenticato{
    private List<String> activeContests;
    public void creaContestLibero(String nome,String obbiettivo, Comune comune) {

    }
    public void creaContestChiuso(String nome, String obbiettivo, List<Contributor> listaContributor){

    }

    public void chiudiContest(String nome){};

    public void visualizzaContenuti(String nome){};

    public void approva(){};

    public void disapprova(){};
}
