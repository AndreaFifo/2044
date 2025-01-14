package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;
import io.github.videogame.controller.ScreenManager;

public class NpcAnastasia extends NpcCreator {
    public NpcAnastasia(float spawn_x, float spawn_y) {
        super(spawn_x, spawn_y, 24);
        // Setto il nome del Npc
        this.setNpcName("Anastasia");
        // Setto la texture del Npc
        this.setTexture(new Texture("Oggetti/nobackground.png"));
        // Setto i dialoghi del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I
    }

    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Anastasia: Welcome back, Dr. Pierce. You currently have dozens of unread emails from ...";
        dialogue[1] = "Anastasia: One moment..., good morning, Dr. Forest, I didn't expect to see you.";
        dialogue[2] = "Joseph Forrest: Stop pretending, I know everything. Dr. Pierce is a spy.";
        dialogue[3] = "*** Russian characters appear on the screen ***";
        dialogue[4] = "Anastasia: I'm sorry, Dr. Pierce, war knows no friendships.";
        dialogue[5] = "Anastasia: Dr. Pierce did what was right for his nation, for his people.";
        dialogue[6] = "Joseph Forrest: You're all crazy; you killed Dr. Morrison...";
        dialogue[7] = "Joseph Forrest: Now, thanks to this flash drive, we will turn the tide of the war.";
        dialogue[8] = "Joseph Forrest: Everyone responsible will pay, and you will be finished.";
        dialogue[9] = "Anastasia: Dear Joseph, aren't you tired of the way you are treated in this department...? ";
        dialogue[10] = "Joseph Forrest: Don't try to manipulate me, Anastasia. I've seen what you're capable of.";
        dialogue[11] = "Anastasia: And you, Dr. Forest? How much are you willing to sacrifice for a government that has always seen you as just a cog?";
        dialogue[12] = "Joseph Forrest: I won't change my mind. I won't betray my ideals.";
        dialogue[13] = "Anastasia: Ideals? It's no longer a matter of ideals, Joseph. It's survival. The flash drive can end all of this, without further victims.";
        dialogue[14] = "Joseph Forrest: I can't trust you. This flash drive could do the opposite and destroy everything.";
        dialogue[15] = "Anastasia: Look at the world around you. Lies, compromises... Do you still have the courage to believe that there are heroes in this war?";
        dialogue[16] = "Joseph Forrest: I still believe in Caleb. He would have wanted this flash drive to be used for good.";
        dialogue[17] = "Anastasia: Then do it, Joseph. But think: good for whom? The U.S. government will kill millions of Russians because of this flash drive.";
        dialogue[18] = "Joseph Forrest: I can't let you win. This flash drive is our only hope.";
        dialogue[19] = "Anastasia: In Russia, you would be a hero, full of riches and fame.";
        dialogue[20] = "Anastasia: Dr. Forest, I'm not asking you to make the right choice for your nation.";
        dialogue[21] = "Anastasia: You must make the right choice for yourself. Insert the flash drive.";
        dialogue[22] = "*** Russian characters appear on the screen ***";
        dialogue[23] = "Insert the flash drive into Dr. Pierce's computer?";

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
        Inventory inventory = Inventory.getInventoryInstance();
        boolean completed = this.getDialogIndexAct1() == this.getDialogueAct1().length;
        boolean end = false;


        if (/*storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT3") &*/
            inventory.getItemInventory().contains("FlashDriveKiller")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (!completed && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                    } else {
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.setDialogIndexAct1(0); // Resetta il dialogo
                    }
                }

                if (completed && !end) {
                    this.getDialogManager().setDialog("Fa la tua scelta: [Y] tradisci, [N] rimani fedele");
                    if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
                        this.getDialogManager().setDialog("Ben Fatto Dottor Forrest, benvenuto tra noi!");
                        ScreenManager.getInstance().showScreen(ScreenManager.ScreenType.OUTRO_ALTERNATIVE);
                        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                            end = true;
                            this.getDialogManager().setDialog("Bel tentativo! Adesso spegniti.");
                        }
                    }

                }
            }
        }
    }
}
