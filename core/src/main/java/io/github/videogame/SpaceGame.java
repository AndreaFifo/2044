package io.github.videogame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jdk.tools.jmod.Main;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
//Ricordarsi che il Main implemente Game
public class SpaceGame extends Game {

    //Grandezza di apertura del menu principale
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    SpriteBatch batch;

    @Override
    public void create(){
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this)); //Apro il menu principale
    }

    @Override
    public void render() {
        super.render();
    }

}
