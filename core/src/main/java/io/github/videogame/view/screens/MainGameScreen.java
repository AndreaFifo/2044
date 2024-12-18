package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.videogame.model.Gioco;
import io.github.videogame.controller.MovementController;
import io.github.videogame.model.Player;
import io.github.videogame.model.Utility;


//Lo scopo di questa classe è gestire la finestra della sessione di gioco

public class MainGameScreen implements Screen {
    private Gioco game;
    private Player player;
    private int stateDirection;
    private MovementController movementController;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    // Costruttore
    public MainGameScreen(Gioco game) {
        this.game = game;
    }


    @Override
    public void show() {
        this.batch = game.getBatch();
        this.stateDirection=0;
        this.player = Player.getInstance("player/player-spritesheet.png");  // Inizializza l'entità con la sprite sheet
        this.movementController = new MovementController(400, 105, stateDirection);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);
    }


    @Override
    public void render(float delta) {

        movementController.changeStateDirection(delta);  // Aggiorna la posizione in base all'input

        // Ottieni la direzione corrente
        int currentDirection = movementController.getStateDirection();

        // Verifica se il personaggio è in movimento
        boolean isMoving = movementController.isPlayerMoving();

        // Ottieni il fotogramma attuale in base alla direzione e allo stato di movimento
        TextureRegion currentFrame = player.getCurrentFrame(currentDirection, isMoving, delta);

        // Pulisci lo schermo
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(movementController.getX(), movementController.getY(), 0f);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        // Disegna il personaggio
        batch.begin();
        batch.draw(Utility.getAsset("menu/bg-menu.png", Texture.class), 0, 0, 1920, 1080);
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
        if (player != null) {
            player.dispose();  // Libera le risorse dell'entità
        }
    }
}
