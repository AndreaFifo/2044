package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.view.screens.VideoOutroScreen;

public class NpcAurora extends NpcCreator {

    //Ulteriori dialoghi
    private String[] dialogueAct2;
    private String[] dialogueAct3;
    private String[] dialogueAct4;

    public int getDialogIndexAct2() {
        return dialogIndexAct2;
    }

    public void setDialogIndexAct2(int dialogIndexAct2) {
        this.dialogIndexAct2 = dialogIndexAct2;
    }

    private int dialogIndexAct2 =0;

    public void setDialogIndexAct3(int dialogIndexAct3) {
        this.dialogIndexAct3 = dialogIndexAct3;
    }

    public int getDialogIndexAct3() {
        return dialogIndexAct3;
    }

    private int dialogIndexAct3 =0;

    public void setDialogIndexAct4(int dialogIndexAct4) {
        this.dialogIndexAct4 = dialogIndexAct4;
    }

    public int getDialogIndexAct4() {
        return dialogIndexAct4;
    }

    private int dialogIndexAct4 =0;

    public NpcAurora(float spawn_x, float spawn_y) {
        super(spawn_x, spawn_y, 15);
        // Setto il nome del Npc
        this.setNpcName("Aurora");
        // Setto la texture del Npc
        this.setTexture(new Texture("Oggetti/nobackground.png"));
        // Setto i dialoghi del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I
        this.dialogueAct2 = InitDialogAct2(new String[9]); // ATTO II
        this.dialogueAct3 = InitDialogAct3(new String[8]); // ATTO III
        this.dialogueAct4 = InitDialogAct4(new String[6]); // ATTO IV
    }

    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Aurora: Hi, I'm Aurora, the artificial intelligence assisting Dr. Caleb in his research. How can I help you, Dr. Forest?";
        dialogue[1] = "Joseph: Caleb has been murdered; I need help to find out who the culprit is.";
        dialogue[2] = "Aurora: That's terrible. Have you already notified the authorities?";
        dialogue[3] = "Joseph: Yes, the police have already been informed, but I need access to the data and messages Caleb was working on.";
        dialogue[4] = "Aurora: I can help you; Dr. Caleb seems to have completed his decryption algorithm.";
        dialogue[5] = "Joseph: ... We've been trying in vain since 2039 to create a suitable algorithm for decrypting messages...";
        dialogue[6] = "Joseph: He finally succeeded in the endeavor...";
        dialogue[7] = "Joseph: Come on, show me the file containing the algorithm; I need to share it with the authorities immediately!";
        dialogue[8] = "Aurora: I'm afraid that's not possible, Dr. Forest... it seems that the file containing the algorithm was taken from a flash drive last night.";
        dialogue[9] = "Joseph: This is terrible... this means that someone on the team is actually a Russian spy who has been hindering our research all this time.";
        dialogue[10] = "Aurora: Don't worry, Dr. Forest; Dr. Caleb used to play tricks on those who copied his research.";
        dialogue[11] = "Aurora: Here, Dr. Forest... there is software on the computer that inserts a file into devices that copy the computer's data.";
        dialogue[12] = "Aurora: It will be enough to find a device with the file inside and check if the creation date of that file matches.";
        dialogue[13] = "Joseph: You're amazing; I'm going to report this to the agents. Thank you very much.";
        dialogue[14] = "Aurora: It's always a pleasure to help you, Dr. Forest. See you soon.";
        return dialogue;
    }

    @Override
    public void drawDialogue() {

    }

    @Override
    public String[] initDialogues(String[] dialogues) {
        return new String[0];
    }

    public String[] InitDialogAct2(String[] dialogue) {
        dialogue[0] = "Aurora: Inserting Dr. Cooper's flash drive into the computer.";
        dialogue[1] = "Aurora: Good morning, Dr. Forest, what do you need?";
        dialogue[2] = "Joseph Forrest: Analyze the flash drive, tell me if it's the incriminating one containing the file we're looking for.";
        dialogue[3] = "Aurora: Analyzing the flash drive...";
        dialogue[4] = "Aurora: It seems that this does not match. There are several files of the software in question...";
        dialogue[5] = "Aurora: However, none correspond to the date we are looking for.";
        dialogue[6] = "Joseph Forrest: Alright, thank you, Aurora.";
        dialogue[7] = "Aurora: See you soon, Dr. Forest.";
        dialogue[8] = "Aurora: Removing the flash drive.";
        return dialogue;
    }

    public String[] InitDialogAct3(String[] dialogue) {
        dialogue[0] = "Aurora: Inserting Dr. Pierce's flash drive into the computer.";
        dialogue[1] = "Aurora: Good morning, Dr. Forest, what do you need?";
        dialogue[2] = "Joseph Forrest: Analyze the flash drive, tell me if it's the incriminating one containing the file we're looking for.";
        dialogue[3] = "Aurora: Analyzing the flash drive...";
        dialogue[4] = "Aurora: I'm sorry, Dr. Forest, there seems to be a match.";
        dialogue[5] = "I estimate that there is a 99.7% chance that Dr. Pierce is the killer we were looking for.";
        dialogue[6] = "Joseph Forrest: I can't believe it! After all this time... I didn't realize who I had in front of me.";
        dialogue[7] = "Aurora: Removing the flash drive.";
        return dialogue;
    }

    public String[] InitDialogAct4(String[] dialogue) {
        dialogue[0] = "Joseph Forrest: I can't believe what has happened.";
        dialogue[1] = "Aurora: My condolences for your loss, Dr. Forest.";
        dialogue[2] = "Joseph Forrest: Thank you, now let's make sure that his loss was not in vain.";
        dialogue[3] = "Aurora: Shall I publish the file 30_e_lode.java containing the requested algorithm on the government servers?";
        dialogue[4] = "Joseph Forrest: Yes.";
        dialogue[5] = "Aurora: Publication successful. It's always a pleasure working with you, Dr. & Detective Forest ;)";
        return dialogue;
    }

    // Restante codice per il drawDialogue() e canBeInteracted() rimane invariato

    //Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_AURORA_ACT1"))
            return;

        if(storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT1")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                    } else {
                        storyState.setDialogueCompleted("NPC_AURORA_ACT1");
                        this.notifyObservers(4);
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.setDialogIndexAct1(0); // Resetta il dialogo
                    }
                }
            }
        }
    }

    //Disegna il secondo atto
    public void drawDialogueAct2() {
        StoryState storyState = StoryState.getInstance();
        Inventory inventory = Inventory.getInventoryInstance();

        if(storyState.getDialogueState("NPC_AURORA_ACT2"))
            return;

        if (storyState.getDialogueState("NPC_INNOCENT_ACT1") &
            inventory.getItemInventory().contains("MagneticKey") &
            inventory.getItemInventory().contains("FlashDriveInnocente")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct2 < this.dialogueAct2.length) {
                        this.getDialogManager().setDialog(this.dialogueAct2[dialogIndexAct2]); // Mostra la linea corrente
                        this.dialogIndexAct2 = dialogIndexAct2 + 1;
                    } else {
                        storyState.setDialogueCompleted("NPC_AURORA_ACT2");
                        this.notifyObservers(7);
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct2 = 0; // Resetta il dialogo
                    }
                }
            }
        }
    }

    public void drawDialogueAct3() {
        StoryState storyState = StoryState.getInstance();
        Inventory inventory = Inventory.getInventoryInstance();

        if(storyState.getDialogueState("NPC_AURORA_ACT3"))
            return;

        if( storyState.getDialogueState("NPC_KILLER_ACT1") &
            inventory.getItemInventory().contains("FlashDriveKiller")) {

            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct3 < this.dialogueAct3.length) {
                        this.getDialogManager().setDialog(this.dialogueAct3[dialogIndexAct3]); // Mostra la linea corrente
                        this.dialogIndexAct3 = dialogIndexAct3 + 1;
                    } else {
                        storyState.setDialogueCompleted("NPC_AURORA_ACT3");
                        this.notifyObservers(9);
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct3 = 0; // Resetta il dialogo
                    }
                }
            }
        }
    }


    public void drawDialogueAct4() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_AURORA_ACT4"))
            return;

        if (storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT3")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct4 < this.dialogueAct4.length) {
                        this.getDialogManager().setDialog(this.dialogueAct4[dialogIndexAct4]); // Mostra la linea corrente
                        this.dialogIndexAct4 = dialogIndexAct4 + 1;
                    } else {
                        storyState.setDialogueCompleted("NPC_AURORA_ACT4");
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct4 = 0; // Resetta il dialogo
                        ScreenManager.getInstance().showScreen(ScreenManager.ScreenType.OUTRO);
                    }
                }
            }
        }
    }
}
