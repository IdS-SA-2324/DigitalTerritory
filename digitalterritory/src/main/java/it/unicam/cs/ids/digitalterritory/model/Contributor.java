package it.unicam.cs.ids.digitalterritory.model;

public class Contributor extends UtenteAutenticato{
    private boolean isAutorizzato;

    public boolean isAutorizzato() {
        return isAutorizzato;
    }

    public void setAutorizzato(boolean autorizzato) {
        isAutorizzato = autorizzato;
    }

    public void invita(String message){
        System.out.println("Sei stato invitato a partecipare al contest che ha l'obbiettivo di: ");
        System.out.println(message);
    }

    public void creaContenutoPerContest(Contest contest){
        PuntoInteresse puntoInteresse=new PuntoInteresse(); //crea un punto di interesse e lo aggiunge
        contest.addContenuto(puntoInteresse);
    }
}
