package io.github.videogame.model;

import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

import java.util.ArrayList;
import java.util.List;

public abstract class NpcCreator implements Observable,NpcCreatorInterface{
    private String NpcName;
    private Texture texture;
    private String[] dialogueAct1;
    private int dialogIndexAct1;
    private List<Observer> observers = new ArrayList<>();
    private DialogManager dialogManager;
    private final float spawn_x;
    private final float spawn_y;

    public NpcCreator(float spawn_x, float spawn_y, int numDiag){
        this.spawn_x = spawn_x;
        this.spawn_y = spawn_y;
        this.dialogIndexAct1 = 0;
        this.dialogueAct1 = new String[numDiag];
        this.dialogManager = new DialogManager();
    }

    //IMPLEMENTAZIONE DELL'INTERFACCIA NpcCreatorInterface

    //Fare l'Override nelle sotto classi poiché ogni personaggio ha la sua GUI di dialogo
    public abstract void drawDialogueAct1();

    //Fare l'Override nelle sotto classi poiché ogni ATTO I fra gli NPC è diverso
    public abstract String[] InitDialogAct1(String[] dialogue);

    //Override fatto già adesso poiché è comune a tutti gli NPC
    @Override
    public boolean canBeInteracted() {
        Player player = Player.getInstance();

        return player.getX() <= this.getSpawn_x() + 30 &&
            player.getX() >= this.getSpawn_x() - 30 &&
            player.getY() <= this.getSpawn_y() + 30 &&
            player.getY() >= this.getSpawn_y() - 30;
    }

    //Override fatto già adesso poichè è comune a tutti gli NPC
    @Override
    public void setDialogueAct1(String[] dialogueAct1) {
        this.dialogueAct1 = dialogueAct1;
    }

    //IMPLEMENTAZIONE DELL'INTERFACCIA Observable
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int id) {
        for (Observer observer : observers) {
            observer.update(id);
        }
    }

    //METODI GET E SET MOLTO STANDARD
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

    public int getDialogIndexAct1() {
        return dialogIndexAct1;
    }

    public void setDialogIndexAct1(int dialogIndexAct1) {
        this.dialogIndexAct1 = dialogIndexAct1;
    }

    public abstract void drawDialogue();

    public abstract String[] initDialogues(String[] dialogues);
}
