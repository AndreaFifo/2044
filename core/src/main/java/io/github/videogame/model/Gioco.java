package io.github.videogame.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.view.screens.MenuScreen;


//Classe principale del gioco, estende GAME classe che gestisce il ciclo di vita del gioco

public class Gioco extends Game {
    //Imposta la grandezza della finestra di apertura
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    private ScreenManager screenManager;


    //Oggetto SpriteBatch che disegna le texture su schermo
    private SpriteBatch batch;

    //Metodo chiamato all'avvio del gioco
    @Override
    public void create(){
        //Creo l'oggetto batch per disegnare le texture
        batch = new SpriteBatch();

        this.screenManager = ScreenManager.getInstance();
        screenManager.init(this);

        screenManager.showScreen(ScreenManager.ScreenType.MAIN_MENU);
    }


    //Chiamato continuamente durante l'esecuzione del gioco
    @Override
    public void render() {
        //Chiamo la funzione render della classe padre per il ciclo di vita del gioco
        super.render();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

}
