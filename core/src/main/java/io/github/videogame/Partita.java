package io.github.videogame;


/*Model (Partita): La classe Partita si occupa solo della logica di gioco, senza preoccuparsi della grafica o
delle interazioni dell'utente */


public class Partita {

    private static Partita instance = null;

    private Partita(){}

    public static Partita getInstance(){
        if(instance == null)
            instance = new Partita();
        return instance;
    }

    public void avviaGioco() {
        // Logica per avviare il gioco
    }

    public void terminaGioco() {
        // Logica per terminare il gioco
    }




}
