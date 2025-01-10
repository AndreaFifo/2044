package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import java.util.List;

public class MovementController {
    private static final float SPEED = 300;
    private float x, y;
    private float velocityX, velocityY;
    private int stateDirection;
    private boolean isMoving;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public MovementController(float spawnAscissa, float spawnOrdinata) {
        this.x = spawnAscissa;
        this.y = spawnOrdinata;
        this.velocityX = 0f;
        this.velocityY = 0f;
        this.stateDirection = setStateDirection(0);
        this.isMoving = false;
    }

    public int setStateDirection(int stateDirection) {
        return this.stateDirection = stateDirection;
    }

    public void changeStateDirection(float deltaTime, List<Rectangle> wallRectangles) {
        velocityX = 0f;
        velocityY = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocityY = SPEED;
            stateDirection = setStateDirection(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocityY = -SPEED;
            stateDirection = setStateDirection(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -SPEED;
            stateDirection = setStateDirection(2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = SPEED;
            stateDirection = setStateDirection(3);
        }

        // Calcola la nuova posizione
        float newX = x + velocityX * deltaTime;
        float newY = y + velocityY * deltaTime;

        // Verifica collisioni e aggiorna la posizione
        boolean xMoved = isColliding(newX, y, wallRectangles);
        boolean yMoved = isColliding(x, newY, wallRectangles);

        if (xMoved) x = newX;
        if (yMoved) y = newY;

        // Aggiorna lo stato di movimento
        isMoving = velocityX != 0 || velocityY != 0;
    }

     private boolean isColliding(float x, float y, List<Rectangle> wallRectangles) {
        Rectangle playerRect = new Rectangle(x, y, 16, 32); // Dimensioni del giocatore
        for (Rectangle wall : wallRectangles) {
            if (playerRect.overlaps(wall)) {
                return false;
            }
        }
        return true;
    }
    public boolean isColliding2(float x, float y, List<Rectangle> elevatorRectangles) {
        Rectangle playerRect = new Rectangle(x, y, 16, 32); // Dimensioni del giocatore
        for (Rectangle elevator : elevatorRectangles) {
            if (playerRect.overlaps(elevator)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerMoving() {
        return isMoving;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getStateDirection() {
        return stateDirection;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }
}
