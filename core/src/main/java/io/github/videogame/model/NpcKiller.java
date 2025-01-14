package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcKiller extends NpcCreator {

    private String[] dialogueAct2;

    public int getDialogIndexAct2() {
        return dialogIndexAct2;
    }

    public void setDialogIndexAct2(int dialogIndexAct2) {
        this.dialogIndexAct2 = dialogIndexAct2;
    }

    private int dialogIndexAct2 =0;

    public NpcKiller(float spawn_x, float spawn_y) {
        super(spawn_x, spawn_y, 13);
        // Setto il nome del Npc
        this.setNpcName("Nome_Killer");
        // Setto la texture del Npc
        this.setTexture(new Texture("NPC/ryan.png"));
        // Setto il dialogo del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I
        this.dialogueAct2 = InitDialogAct2(new String[10]); // ATTO II
    }

    // Inizializza il dialogo del killer
    @Override
    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Joseph Forrest: Ryan, can I talk to you for a moment?";
        dialogue[1] = "Ryan Pierce: Sure, Joseph. What is it about?";
        dialogue[2] = "Joseph Forrest: I'm investigating Caleb's murder and I would like to know where you were and what you were doing the night it happened.";
        dialogue[3] = "Ryan Pierce: I was in my office, I spent the whole night working on the computer on some overdue tasks. I didn't leave until the morning.";
        dialogue[4] = "Joseph Forrest: I see. So you didn't see anything suspicious or hear any strange noises during the night?";
        dialogue[5] = "Ryan Pierce: No, nothing like that. It was all quiet. I worked late, then I even fell asleep at my desk, hahahaha.";
        dialogue[6] = "Ryan Pierce: ...";
        dialogue[7] = "Joseph Forrest: Thank you, Ryan. This helps me understand better what happened.";
        dialogue[8] = "Ryan Pierce: I hope you can figure it all out. If you need anything, let me know.";
        dialogue[9] = "Joseph Forrest: AH!! One last thing... could you give me your flash drive?";
        dialogue[10] = "Ryan Pierce: It's in my office, but what do you need it for...? ";
        dialogue[11] = "Joseph Forrest: I just need to check something, don't worry, I won't delete anything.";
        dialogue[12] = "Ryan Pierce: Good luck then.";

        return dialogue;
    }

    @Override
    public void drawDialogue() {

    }

    @Override
    public String[] initDialogues(String[] dialogues) {
        return new String[0];
    }

    private String[] InitDialogAct2(String[] dialogue){
        dialogue[0] = "Joseph Forrest: It's you, Ryan. You were the one who killed Caleb.";
        dialogue[1] = "Ryan Pierce: Finally, you figured it out, Joseph. I must say, it took you longer than I expected.";
        dialogue[2] = "Joseph Forrest: How could you do this? Caleb considered you a friend. We were a team!";
        dialogue[3] = "Ryan Pierce: A team? Don't make me laugh. I was never on your side, Joseph. My mission was to stop Caleb at all costs.";
        dialogue[4] = "Joseph Forrest: A spy... You're a traitor! You've destroyed everything we've worked for. For what? For a war that doesn't belong to you?";
        dialogue[5] = "Ryan Pierce: You don't understand, do you? Your nation is no different from mine. Everyone seeks power, and I chose to side with the winning side.";
        dialogue[6] = "Joseph Forrest: The winning side? You sacrificed an innocent life! Caleb had found the key. We could have stopped all of this.";
        dialogue[7] = "Ryan Pierce: And that's why he had to die. If that key fell into the hands of the Americans, the war would have been lost for us. I couldn't allow that.";
        dialogue[8] = "Joseph Forrest: And now? Do you really think you'll get away with it? I have the evidence that incriminates you. You can't hide.";
        dialogue[9] = "Ryan Pierce: I don't need to hide, Joseph. My mission is over.";

        return dialogue;
    }


    // Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_KILLER_ACT1"))
            return;

        if (storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT2")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                    } else {
                        storyState.setDialogueCompleted("NPC_KILLER_ACT1");
                        this.notifyObservers(8);
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

        if(storyState.getDialogueState("NPC_KILLER_ACT2"))
            return;

        if (storyState.getDialogueState("NPC_AURORA_ACT3")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct2 < this.dialogueAct2.length) {
                        this.getDialogManager().setDialog(this.dialogueAct2[dialogIndexAct2]); // Mostra la linea corrente
                        this.dialogIndexAct2 = dialogIndexAct2 + 1;
                    } else {
                        storyState.setDialogueCompleted("NPC_KILLER_ACT2");
                        this.notifyObservers(10);
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct2 = 0; // Resetta il dialogo
                    }
                }
            }
        }
    }
}
