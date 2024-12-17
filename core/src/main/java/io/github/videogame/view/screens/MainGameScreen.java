package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.videogame.model.Gioco;
import io.github.videogame.controller.MovementController;


//Lo scopo di questa classe è gestire la finestra della sessione di gioco

public class MainGameScreen implements Screen {
    private Gioco game;
    private io.github.videogame.model.Player Player;
    private int stateDirection;
    private MovementController movementController;
    private SpriteBatch batch;



    // Costruttore
    public MainGameScreen(Gioco game) {
        this.game = game;
        this.show();
    }


    @Override
    public void show() {
        this.batch = game.getBatch();
        this.stateDirection=0;
        this.Player = Player.getInstance("Orco_walk.png");  // Inizializza l'entità con la sprite sheet
        this.movementController = new MovementController(400, 105, stateDirection);
    }


    @Override
    public void render(float delta) {

        movementController.changeStateDirection(delta);  // Aggiorna la posizione in base all'input

        // Ottieni la direzione corrente
        int currentDirection = movementController.getStateDirection();

        // Verifica se il personaggio è in movimento
        boolean isMoving = movementController.isPlayerMoving();

        // Ottieni il fotogramma attuale in base alla direzione e allo stato di movimento
        TextureRegion currentFrame = Player.getCurrentFrame(currentDirection, isMoving, delta);

        // Pulisci lo schermo
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Disegna il personaggio
        batch.begin();
        batch.draw(currentFrame, movementController.getX(), movementController.getY());
        batch.end();
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
        if (Player != null) {
            Player.dispose();  // Libera le risorse dell'entità
        }
    }
}
