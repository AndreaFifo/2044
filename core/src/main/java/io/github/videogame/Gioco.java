package io.github.videogame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


//Classe principale del gioco, estende GAME classe che gestisce il ciclo di vita del gioco

public class Gioco extends Game {

    //Imposta la grandezza della finestra di apertura
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    //Oggetto SpriteBatch che disegna le texture su schermo
    SpriteBatch batch;


    //Metodo chiamato all'avvio del gioco
    @Override
    public void create(){

        //Creo l'oggetto batch per disegnare le texture
        batch = new SpriteBatch();

        //Creo un'istanza del menu principale e l'attivo con setScreen
        this.setScreen(new MainMenuScreen(this));
    }


    //Chiamato continuamente durante l'esecuzione del gioco
    @Override
    public void render() {
        //Chiamo la funzione render della classe padre per il ciclo di vita del gioco
        super.render();
    }

}
