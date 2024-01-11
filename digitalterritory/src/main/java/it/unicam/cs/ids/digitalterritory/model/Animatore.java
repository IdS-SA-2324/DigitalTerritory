package it.unicam.cs.ids.digitalterritory.model;

import it.unicam.cs.ids.digitalterritory.controllers.ContestManager;
import it.unicam.cs.ids.digitalterritory.utils.PuntoInteresse;

import java.util.List;

public class Animatore extends UtenteAutenticato{
    public void creaContestLibero(String nome,String obbiettivo, Comune comune,String data) {
        ContestManager.getInstance().newFreeContest(comune,nome,data,obbiettivo);
    }
    public void creaContestChiuso(String nome, String obbiettivo, List<Contributor> listaContributor,String data){
        ContestManager.getInstance().newClosedContest(nome,data,obbiettivo,listaContributor);
    }

    public void chiudiContest(String nome){
        ContestManager.getInstance().closeContest(nome);
    };

    public void visualizzaContenuti(String nome){
        ContestManager.getInstance().getContestList(nome);
    };

    public void approva(PuntoInteresse puntoInteresse){
        //TODO il puntoInteresse viene caricato nel DB
        //TODO se la lista dei puntiInteresse è vuota chiama insertList
    };

    public void disapprova(){
        //TODO il puntoInteresse viene eliminato
        //TODO se la lista dei puntiInteresse è vuota chiama insertList
    };
}
