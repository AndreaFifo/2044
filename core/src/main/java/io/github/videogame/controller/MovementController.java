package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


//Questa classe gestisce i movimenti del gioco

public class MovementController {

    private static final float SPEED = 150;
    private float x, y;
    private float velocityX, velocityY;
    //Stato di direzione (0 = giù, 1 = sinistra, 2 = destra, 3 = su)
    private int StateDirection;


    // Costruttore:
    public MovementController(float SpawnAscissa, float SpawnOrdinata, int stateDirection) {
        this.x = SpawnAscissa;
        this.y = SpawnOrdinata;
        this.velocityX = 0f;
        this.velocityY = 0f;
        this.StateDirection = setStateDirection(0); // Direzione iniziale (giù)
    }

    public int setStateDirection(int stateDirection){
        return this.StateDirection = stateDirection;
    }

    // Aggiorna il movimento
    public void changeStateDirection(float deltaTime) {

        // Ripristina la velocità per ogni frame
        velocityX = 0f;
        velocityY = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocityY = SPEED;  // Movimento positivo sull'asse Y (su)
            StateDirection = setStateDirection(1); // Su
        }
        // Movimento giù
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocityY = -SPEED; // Movimento negativo sull'asse Y (giù)
            StateDirection = setStateDirection(0); // Giù
        }
        // Movimento sinistra
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -SPEED; // Movimento negativo sull'asse X (sinistra)
            StateDirection = setStateDirection(2); // Sinistra
        }
        // Movimento destra
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = SPEED;  // Movimento positivo sull'asse X (destra)
            StateDirection = setStateDirection(3); // Destra
        }

        // Aggiornamento della posizione attuale della texture del personaggio
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
    }

    public boolean isPlayerMoving(){
        return (getVelocityX() != 0 || getVelocityY() != 0);
    }

    // Getter per le coordinate
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // Getter per la direzione corrente
    public int getStateDirection() {
        return StateDirection;
    }

    // Getter per la velocità orizzontale
    public float getVelocityX() {
        return velocityX;
    }

    // Getter per la velocità verticale
    public float getVelocityY() {
        return velocityY;
    }

}
