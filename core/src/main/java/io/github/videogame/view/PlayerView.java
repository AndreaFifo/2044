package io.github.videogame.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.videogame.controller.MapManager;
import io.github.videogame.controller.MovementController;
import io.github.videogame.model.Player;

public class PlayerView {
    private Player player;
    private MovementController movementController;

    public PlayerView(Player player, MovementController movementController) {
        this.player = player;
        this.movementController = movementController;
    }

    public void render(SpriteBatch batch, float delta) {
        int direction = movementController.getStateDirection();
        boolean isMoving = movementController.isPlayerMoving();
        batch.draw(player.getCurrentFrame(direction, isMoving, delta), player.getX(), player.getY());
    }
}
