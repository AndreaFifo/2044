package io.github.videogame.model;

import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

import java.util.ArrayList;
import java.util.List;

public abstract class NpcCreator implements Observable{

    private String NpcName;
    private Texture texture;
    private String[] dialogue;
    private int dialogIndex;
    private List<Observer> observers = new ArrayList<>();

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    private DialogManager dialogManager;
    private final float spawn_x;
    private final float spawn_y;
    private MovementController movementControllerPlayer;


    public NpcCreator(float spawn_x, float spawn_y, MovementController movementControllerPlayer){
        this.spawn_x = spawn_x;
        this.spawn_y = spawn_y;
        this.dialogIndex = 0;
        this.dialogue = new String[200];
        this.movementControllerPlayer = movementControllerPlayer;
        this.dialogManager = new DialogManager();
    }

    public abstract void drawDialogue();

    public abstract boolean canBeInteracted();

    public abstract String[] InitDialog(String[] dialogue);

    public void setNpcName(String npcName) {
        NpcName = npcName;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }


    public String getNpcName(){
        return NpcName;
    }

    public float getSpawn_x() {
        return spawn_x;
    }

    public float getSpawn_y() {
        return spawn_y;
    }

    public String getName() {
        return NpcName;
    }

    public Texture getTexture() {
        return texture;
    }

    public String[] getDialogue() {
        return dialogue;
    }

    public void setDialogue(String[] dialogue) {
        this.dialogue = dialogue;
    }

    public int getDialogIndex() {
        return dialogIndex;
    }

    public void setDialogIndex(int dialogIndex) {
        this.dialogIndex = dialogIndex;
    }

    public MovementController getMovementControllerPlayer() {
        return movementControllerPlayer;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
