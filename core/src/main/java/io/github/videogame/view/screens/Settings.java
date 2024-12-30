package io.github.videogame.view.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.model.Gioco;
//import io.github.videogame.model.Gioco;

public class Settings implements Screen {
    private Gioco game;
    private Texture background;
    private Sound buttonClickSound; // Aggiungi il suono del click
    private Screen previousScreen;//flag utilizzato per capire da dove si sta accedendo alle impostazioni

    public Settings(Gioco game, Texture background, Sound buttonClickSound, Screen previousScreen) {
        this.game = game;
        this.background = background; // Usa lo stesso sfondo del menu principale
        this.buttonClickSound = buttonClickSound; // Passa il suono dal menu principale
        this.previousScreen = previousScreen; // Salva la schermata precedente
    }

    @Override
    public void show() {
        // Puoi inizializzare ulteriori risorse qui, se necessario
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, Gioco.WIDTH, Gioco.HEIGHT);
        game.getBatch().end();

        // Controlla se il tasto Esc è premuto
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            buttonClickSound.play(); // Riproduce il suono
            if (previousScreen != null) {
                game.setScreen(previousScreen); // Torna alla schermata precedente (es. MenuPausa)
            } else {
                game.setScreen(new MenuScreen(game)); // Torna al menu principale se non c'è schermata precedente
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Rilascia eventuali risorse create per questa schermata
    }

}
