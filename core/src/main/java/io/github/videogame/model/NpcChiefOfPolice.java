package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcChiefOfPolice extends NpcCreator {
    private String[] dialogueAct2;
    private String[] dialogueAct3;
    private int dialogIndexAct2 = 0;
    private int dialogIndexAct3 = 0;

    public NpcChiefOfPolice(float spawn_x, float spawn_y) {
        super(spawn_x, spawn_y, 26);
        // Setto il nome del Npc
        this.setNpcName("Nome_Capo_Polizia");
        // Setto la texture del Npc
        this.setTexture(new Texture("NPC/capo-polizia.png"));
        // Setto i dialoghi
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I
        this.dialogueAct2 = InitDialogAct2(new String[6]); // ATTO II
        this.dialogueAct3 = InitDialogAct3(new String[5]); // ATTO III
    }

    public int getDialogIndexAct2() {
        return dialogIndexAct2;
    }

    public void setDialogIndexAct2(int dialogIndexAct2) {
        this.dialogIndexAct2 = dialogIndexAct2;
    }

    public int getDialogIndexAct3() {
        return dialogIndexAct3;
    }

    // Inizializza il dialogo del primo atto
    @Override
    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Police Chief: I have been informed of the situation. Can you explain to me what happened?";
        dialogue[1] = "Joseph Forest: I found a body... It's up there, I don't know what happened.";
        dialogue[2] = "Police Chief: Did you touch anything? Did you see anyone suspicious around?";
        dialogue[3] = "Joseph Forest: No, I didn't touch anything. I didn't see anyone; it was like this when I arrived.";
        dialogue[4] = "Police Chief: Good, you did the right thing by calling me. Now let us handle it.";
        dialogue[5] = "Police Chief: Stay nearby; we might need to ask you some questions.";
        dialogue[6] = "Joseph Forest: I want to help you solve the case; Caleb, besides being my colleague, was also my friend.";
        dialogue[7] = "Police Chief: Don't make it so easy; you could be the killer...";
        dialogue[8] = "Police Chief: Where were you last night? When Caleb was killed?";
        dialogue[9] = "Joseph Forest: Last night I was in the office working with Caleb; I clocked out around 6:30 PM.";
        dialogue[10] = "Police Chief: And after midnight? What did you do?";
        dialogue[11] = "Joseph Forest: I went back to my apartment. The doorman can confirm that he saw me arrive around 12:30 AM. ";
        dialogue[12] = "Joseph Forest: Also, the building's security system records when I used my key card to enter.";
        dialogue[13] = "Police Chief: Good, those are verifiable proofs. But how do you know the murder happened after midnight?";
        dialogue[14] = "Joseph Forest: Caleb sent me a message at 1:15 AM, with a hint about a decryption key.";
        dialogue[15] = "Joseph Forest: The murder must have happened right after, because otherwise, he would have answered my call at 1:30 AM.";
        dialogue[16] = "Police Chief: Interesting. This alibi holds up. But this means the killer is someone from the team.";
        dialogue[17] = "Joseph Forest: Exactly. There are only four of us working here, and Caleb was working on something huge, something that could change the course of the war.";
        dialogue[18] = "Joseph Forest: Whoever killed him wanted to stop him from completing his work and steal the code he was working on.";
        dialogue[19] = "Police Chief: I can't deny that there's some sense in what you're saying.";
        dialogue[20] = "Police Chief: But listen, Forest, these are official investigations. I can't let you act alone.";
        dialogue[21] = "Joseph Forest: I understand, officer. But Caleb was my best friend, and I can't just sit back.";
        dialogue[22] = "Joseph Forest: I want to find out who the mole is and recover that code. I owe it to him.";
        dialogue[23] = "Police Chief: If you find anything, come to me immediately. Don't act without notifying me, understood?";
        dialogue[24] = "Joseph Forest: I will, but I can't promise to stay still. Caleb deserved justice, and I won't stop until I get it.";
        dialogue[25] = "Police Chief: Good luck, Forest. You'll need it.";
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
        dialogue[0] = "Joseph Forrest: Officer, I have discovered something that could solve the murder.";
        dialogue[1] = "Police Chief: Tell me, Dr. Forest.";
        dialogue[2] = "Joseph Forrest: The motive for the murder is the discovery of the algorithm capable of decrypting Russian secret messages.";
        dialogue[3] = "Police Chief: So you suspect that the killer is somehow an enemy spy?";
        dialogue[4] = "Joseph Forrest: It's hard to admit, but probably one of my esteemed colleagues is actually a traitor.";
        dialogue[5] = "Police Chief: Thank you for the information; I will look into this potential lead in my investigation.";
        return dialogue;
    }

    public String[] InitDialogAct3(String[] dialogue) {
        dialogue[0] = "Joseph Forrest: Officer, as you may have heard, Dr. Pierce is not who he claims to be.";
        dialogue[1] = "Police Chief: Great job, Dr. Forest...";
        dialogue[2] = "Police Chief: Now hand over the evidence you've collected.";
        dialogue[3] = "Joseph Forrest: Hold on, I need to do something with the flash drive first.";
        dialogue[4] = "Police Chief: Alright, but make it quick.";
        return dialogue;
    }

    // Disegna il primo atto
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();
        Inventory inventory = Inventory.getInventoryInstance();

        if(storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT1"))
            return;

        if (storyState.getDialogueState("NPC_DEADBODY_ACT1") & inventory.getItemInventory().contains("JosephPhone")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]);
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                    } else {
                        storyState.setDialogueCompleted("NPC_CHIEF_OF_POLICE_ACT1");
                        this.notifyObservers(3);
                        this.getDialogManager().setDialog("");
                        this.setDialogIndexAct1(0);
                    }
                }
            }
        }
    }

    // Disegna il secondo atto
    public void drawDialogueAct2() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT2"))
            return;

        if (storyState.getDialogueState("NPC_AURORA_ACT1")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct2 < this.dialogueAct2.length) {
                        this.getDialogManager().setDialog(this.dialogueAct2[dialogIndexAct2]);
                        this.dialogIndexAct2++;
                    } else {
                        storyState.setDialogueCompleted("NPC_CHIEF_OF_POLICE_ACT2");
                        this.notifyObservers(5);
                        this.getDialogManager().setDialog("");
                        this.dialogIndexAct2 = 0;
                    }
                }
            }
        }
    }


    // Disegna il terzo atto
    public void drawDialogueAct3() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT3"))
            return;

        if (storyState.getDialogueState("NPC_KILLER_ACT2")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct3 < this.dialogueAct3.length) {
                        this.getDialogManager().setDialog(this.dialogueAct3[dialogIndexAct3]);
                        this.dialogIndexAct3++;
                    } else {
                        storyState.setDialogueCompleted("NPC_CHIEF_OF_POLICE_ACT3");
                        this.notifyObservers(11);
                        this.getDialogManager().setDialog("");
                        this.dialogIndexAct3 = 0;
                    }
                }
            }
        }
    }
}
