package io.github.videogame.model;

import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

import java.util.ArrayList;
import java.util.List;

public abstract class NpcCreator implements Observable{

    private String NpcName;
    private Texture texture;
    private String[] dialogueAct1;
    private int dialogIndexAct1;
    private List<Observer> observers = new ArrayList<>();
    private DialogManager dialogManager;
    private final float spawn_x;
    private final float spawn_y;
    private MovementController movementControllerPlayer;


    public NpcCreator(float spawn_x, float spawn_y, MovementController movementControllerPlayer){
        this.spawn_x = spawn_x;
        this.spawn_y = spawn_y;
        this.dialogIndexAct1 = 0;
        this.dialogueAct1 = new String[50];
        this.movementControllerPlayer = movementControllerPlayer;
        this.dialogManager = new DialogManager();
    }



    //Metodo abstract che dovranno implementare i singoli NPC
    public abstract void drawDialogueAct1();

    public abstract boolean canBeInteracted();

    public abstract String[] InitDialogAct1(String[] dialogue);




    //Altro

    public void setNpcName(String npcName) {
        NpcName = npcName;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
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

    public String[] getDialogueAct1() {
        return dialogueAct1;
    }

    public void setDialogueAct1(String[] dialogueAct1) {
        this.dialogueAct1 = dialogueAct1;
    }

    public int getDialogIndexAct1() {
        return dialogIndexAct1;
    }

    public void setDialogIndexAct1(int dialogIndexAct1) {
        this.dialogIndexAct1 = dialogIndexAct1;
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
