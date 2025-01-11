package io.github.videogame.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

// Classe che crea il Giocatore
public class Player {
    private static volatile Player instance;
    private Texture spriteSheetPlayer;
    private Animation<TextureRegion>[] walkAnimationsPlayer;
    private float stateTime;
    private Inventory inventory;
    private float x, y;

    // Costruttore privato per impedire l'istanziazione diretta
    private Player() {
        this.spriteSheetPlayer = new Texture("player/player.png");
        this.stateTime = 0f;
        TextureRegion[][] regions = TextureRegion.split(spriteSheetPlayer, 16, 32);
        walkAnimationsPlayer = new Animation[4];
        for (int i = 0; i < 4; i++) {
            walkAnimationsPlayer[i] = new Animation<>(0.15f, regions[i]);
        }

        this.inventory = Inventory.getInventoryInstance();

        this.x = 0;
        this.y = 0;
    }
    // Metodo statico per ottenere l'istanza del Singleton
    public static Player getInstance() {
        synchronized (Player.class) {
            if (instance == null) {
                instance = new Player();
            }
        }

        return instance;
    }

    // Metodo per aggiornare lo stateTime
    public void updateStateTime(float delta) {
        this.stateTime += delta;
    }

    // Metodo per ottenere il frame corrente dell'animazione
    public TextureRegion getCurrentFrame(int direction, boolean isMoving, float delta) {
        if (isMoving) {
            updateStateTime(delta);
            return walkAnimationsPlayer[direction].getKeyFrame(stateTime, true);
        }
        return walkAnimationsPlayer[direction].getKeyFrame(0f);
    }

    // Metodo per liberare le risorse
    public void dispose() {
        if (spriteSheetPlayer != null) {
            spriteSheetPlayer.dispose();
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory.setInventory(inventory);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setSpawn(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
