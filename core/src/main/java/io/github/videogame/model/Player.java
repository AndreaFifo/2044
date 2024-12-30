package io.github.videogame.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// Classe che crea il Giocatore
public class Player {
    private static Player instance;
    private Texture spriteSheetPlayer;
    private Animation<TextureRegion>[] walkAnimationsPlayer;
    private float stateTime;
    private Inventory inventory;

    // Costruttore privato per impedire l'istanziazione diretta
    private Player(String spriteSheetPath) {
        this.spriteSheetPlayer = new Texture(spriteSheetPath);
        this.stateTime = 0f;
        TextureRegion[][] regions = TextureRegion.split(spriteSheetPlayer, 16, 32);
        walkAnimationsPlayer = new Animation[4];
        for (int i = 0; i < 4; i++) {
            walkAnimationsPlayer[i] = new Animation<>(0.1f, regions[i]);
        }
        this.inventory = Inventory.getInventoryInstance();

    }
    // Metodo statico per ottenere l'istanza del Singleton
    public static Player getInstance(String spriteSheetPath) {
        if (instance == null) {
            synchronized (Player.class) {
                if (instance == null) {
                    instance = new Player(spriteSheetPath);
                }
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

}
