package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcInnocent extends NpcCreator{
    public NpcInnocent(float spawn_x, float spawn_y){
        super(spawn_x, spawn_y, 15);
        //Setto il nome del Npc
        this.setNpcName("Nome_Innocente");
        //Setto la texture del Npc
        this.setTexture(new Texture("NPC/bryan.png"));
        //Setto il dialogo del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1()));
    }

    //Inizializza il dialogo del iNNOCENTE
    @Override
    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Bryan Cooper: Hey Joseph, I can't believe what happened...";
        dialogue[1] = "Joseph Forrest: Where were you last night?";
        dialogue[2] = "Bryan Cooper: You’re not doubting me, are you...? We've been working together for years...";
        dialogue[3] = "Joseph Forrest: It's not about doubting you, Bryan. But if one of us is the mole, we need to find out.";
        dialogue[4] = "Bryan Cooper: I stayed in my office last night, I was continuing the project on decrypting Russian messages.";
        dialogue[5] = "Joseph Forrest: Did you hear anything?";
        dialogue[6] = "Bryan Cooper: If I had heard something, don't you think I would have already told the police?";
        dialogue[7] = "Joseph Forrest: ...ok";
        dialogue[8] = "Joseph Forrest: Could you give me your USB flash drive?";
        dialogue[9] = "Bryan Cooper: What do you need it for.....? Anyway, it's in my office...";
        dialogue[10] = "Bryan Cooper: ...";
        dialogue[11] = "Bryan Cooper: ...";
        dialogue[12] = "Bryan Cooper: I can't find the keys to my office, I must have left them in the cafeteria when the agents arrived...";
        dialogue[13] = "Joseph Forrest: I'll take care of it!";
        dialogue[14] = "Bryan Cooper: Ok... see you later then. Don't get me in trouble.";

        return dialogue;
    }

    @Override
    public void drawDialogue() {

    }

    @Override
    public String[] initDialogues(String[] dialogues) {
        return new String[0];
    }

    //Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_INNOCENT_ACT1"))
            return;

        if (storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT2")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                    } else {
                        storyState.setDialogueCompleted("NPC_INNOCENT_ACT1");
                        this.notifyObservers(6);
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.setDialogIndexAct1(0); // Resetta il dialogo
                    }
                }
            }
        }
    }
}
