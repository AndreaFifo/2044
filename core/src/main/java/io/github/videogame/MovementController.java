package io.github.videogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


//Questa classe gestisce i movimenti del gioco

public class MovementController {

    //Velocità della texture del player
    private static final float SPEED = 150;
    //Posizione del personaggio
    private float x, y;
    //Valocità di movimento attuale
    private float velocityX, velocityY;
    //Direzione corrente (0 = giù, 1 = sinistra, 2 = destra, 3 = su)
    private int currentDirection;



    // Costruttore: VUOLE COME PARAMETRO LE COORDINATE DI SPAWN
    public MovementController(float SpawnAscissa, float SpawnOrdinata) {
        this.x = SpawnAscissa;
        this.y = SpawnOrdinata;
        this.velocityX = 0f;
        this.velocityY = 0f;
        this.currentDirection = 0; // Direzione iniziale (giù)
    }


    // Aggiorna il movimento
    public void updatePosition(float deltaTime) {

        // Ripristina la velocità per ogni frame
        velocityX = 0f;
        velocityY = 0f;

        /* I seguenti IF permetteranno il movimento e aggiorneranno currentDirection che verrà usata
         per sincronizzare la textura con la direzione di movimento in Entity
        */
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocityY = SPEED;  // Movimento positivo sull'asse Y (su)
            currentDirection = 1; // Su
        }
        // Movimento giù
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocityY = -SPEED; // Movimento negativo sull'asse Y (giù)
            currentDirection = 0; // Giù
        }
        // Movimento sinistra
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -SPEED; // Movimento negativo sull'asse X (sinistra)
            currentDirection = 2; // Sinistra
        }
        // Movimento destra
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = SPEED;  // Movimento positivo sull'asse X (destra)
            currentDirection = 3; // Destra
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
    public int getCurrentDirection() {
        return currentDirection;
    }

    // Getter per la velocità orizzontale
    public float getVelocityX() {
        return velocityX;
    }

    // Getter per la velocità verticale
    public float getVelocityY() {
        return velocityY;
    }

    // Setter opzionali per modificare direttamente le coordinate
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
