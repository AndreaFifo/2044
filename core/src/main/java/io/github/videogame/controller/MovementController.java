package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.*;
import io.github.videogame.model.GameState;
import io.github.videogame.model.Player;

import java.util.List;

public class MovementController {
    private static MovementController instance;
    private static final float SPEED = 150;
    private Player player;
    private int stateDirection;
    private boolean isMoving;
    private MapManager mapManager;

    private MovementController() {
        this.player = Player.getInstance();
        this.stateDirection = setStateDirection(0);
        this.isMoving = false;
        this.mapManager = MapManager.getInstance();
    }

    public static MovementController getInstance() {
        synchronized (MovementController.class) {
            if (instance == null) {
                instance = new MovementController();
            }
        }

        return instance;
    }

    public int setStateDirection(int stateDirection) {
        return this.stateDirection = stateDirection;
    }

    public void changeStateDirection(float deltaTime) {
        float velocityX = 0f;
        float velocityY = 0f;

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
        float newX = player.getX() + velocityX * deltaTime;
        float newY = player.getY() + velocityY * deltaTime;

        if(mapManager.isColliding(newX, player.getY()) && mapManager.isCollidingWithBryanDoor(newX, player.getY()))
            player.setX(newX);
        if(mapManager.isColliding(player.getX(), newY) && mapManager.isCollidingWithBryanDoor(player.getX(), newY))
            player.setY(newY);

        // Aggiorna lo stato di movimento
        isMoving = velocityX != 0 || velocityY != 0;

        updateGameState();
    }

    public boolean isPlayerMoving() {
        return isMoving;
    }

    public int getStateDirection() {
        return stateDirection;
    }

    private void updateGameState() {
        GameState.getInstance().setPlayerX(player.getX());
        GameState.getInstance().setPlayerY(player.getY());
    }
}
