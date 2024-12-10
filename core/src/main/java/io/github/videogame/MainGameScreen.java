package io.github.videogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


//Lo scopo di questa classe è gestire la finestra della sessione di gioco


public class MainGameScreen implements Screen {


    private SpaceGame game;
    private Entity Player;
    private MovementController movementController;
    private SpriteBatch batch;




    // Costruttore
    public MainGameScreen(SpaceGame game) {
        this.game = game;
        this.batch = game.batch;
        this.Player = new Entity("Orco_walk.png");  // Inizializza l'entità con la sprite sheet
        this.movementController = new MovementController(40, 15);
    }




    @Override
    public void show() {}


    @Override
    public void render(float delta) {

        movementController.updatePosition(delta);  // Aggiorna la posizione in base all'input

        // Ottieni la direzione corrente
        int currentDirection = movementController.getCurrentDirection();

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
