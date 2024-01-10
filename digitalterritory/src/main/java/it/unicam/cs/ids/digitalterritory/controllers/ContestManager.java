package it.unicam.cs.ids.digitalterritory.controllers;

import it.unicam.cs.ids.digitalterritory.model.Comune;
import it.unicam.cs.ids.digitalterritory.model.Contest;
import it.unicam.cs.ids.digitalterritory.model.Contributor;
import it.unicam.cs.ids.digitalterritory.model.PuntoInteresse;

import java.util.List;

/**
la classe implementa Singleton
 */
public class ContestManager {

    private static ContestManager istanza;

    private List<Contest> activeContests;

    private ContestManager(){
        this.activeContests=null;
    }

    public static ContestManager getInstance() {

        if (istanza == null) {
            istanza = new ContestManager();
        }

        return istanza;

    }

    /**
     * crea un contest libero per un certo comune
     * @param comune
     * @param nome
     * @param data
     * @param obbiettivo
     */
    public void newFreeContest(Comune comune, String nome, String data, String obbiettivo) {
        Contest newcontest= new Contest(idGenerator(),nome,data,obbiettivo,comune);
        this.activeContests.add(newcontest);

        this.sendInviti(); //TODO a tutti quelli di comune
    }

    /**
     * crea un contest ad inviti
     * @param nome
     * @param data
     * @param obbiettivo
     * @param partecipanti
     */
    public void newClosedContest(String nome, String data,String obbiettivo, List<Contributor> partecipanti) {
        Contest newcontest= new Contest(idGenerator(),nome,data,obbiettivo,partecipanti);
        this.activeContests.add(newcontest);

        this.sendInvitiPrivati(newcontest.getPartecipanti(),newcontest.getObbiettivo());
    }

    /**
     * invia gli inviti ad una lista di Contributo specificata
     * @param listaInviti
     * @param obbiettivo
     */
    private void sendInvitiPrivati(List<Contributor> listaInviti,String obbiettivo){
        listaInviti.forEach((contributor -> contributor.invita(obbiettivo)));
    }

    /**
     * invia gli inviti a tutti i Contributor di un Comune
     */

    private void sendInviti(){
       //TODO get list contributor di un certo comune
    }

    /**
     * chiude un contest settando il parametro is_Closed a true
     * @param nome
     */
    public void closeContest(String nome){
        this.activeContests.forEach(contest -> {
            if(contest.getNome().equals(nome)){contest.setClosed();}
        });
    }

    /**
     * ritorna la lista dei puntiInteresse caricati dai Contributor per un certo Contest
     * @param nome
     * @return
     */
    public List<PuntoInteresse> getContestList(String nome){
        List<PuntoInteresse> tempList = null;
        for (Contest contest : activeContests){
            if(contest.getNome().equals(nome)){tempList=  contest.getContributi();}
        }
    return tempList;}

    /**
     * rimuove un puntoInteresse dalla lista di un Contest
     */
    public void deleteContent(){
        //TODO rimuove un PuntoInteresse dalla Lista di un Contest
    }

    /**
     * chiamato quando la lista dei puntiInteresse di un Contest è vuota,
     * inserisce i punti interesse della lista nel DB
     */
    private void insertList(){
        //TODO fa l'insert nel DB
    }

    /**
     * genera un id casuale
     * //TODO da spostare il utils
     * @return  num
     */
    private int idGenerator(){
        int num=0;
        for(int i=0;i<5;i++){
            int n= (int) (Math.random()*10);
            num=num+n;
        }
        return num;
    }
}

