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
        super(spawn_x, spawn_y);
        // Setto il nome del Npc
        this.setNpcName("Nome_Capo_Polizia");
        // Setto la texture del Npc
        this.setTexture(new Texture("NPC/capo-polizia.png"));
        // Setto i dialoghi
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I
        this.dialogueAct2 = InitDialogAct2(new String[30]); // ATTO II
        this.dialogueAct3 = InitDialogAct3(new String[30]); // ATTO III
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
        dialogue[0] = "Capo della Polizia: Sono stato informato della situazione. Puoi spiegarmi cosa è successo?";
        dialogue[1] = "Joseph Forest: Ho trovato un corpo... È lì, non so cosa sia successo.";
        dialogue[2] = "Capo della Polizia: Hai toccato qualcosa? Hai visto qualcuno sospetto nei paraggi?";
        dialogue[3] = "Joseph Forest: No, non ho toccato niente. Non ho visto nessuno, era già così quando sono arrivato.";
        dialogue[4] = "Capo della Polizia: Bene, hai fatto la cosa giusta chiamandomi. Ora lascia fare a noi.";
        dialogue[5] = "Capo della Polizia: Resta nei paraggi, potremmo avere bisogno di farti qualche domanda.";
        dialogue[6] = "Joseph Forest: Voglio aiutarvi a risolvere il caso, Caleb oltre che essere mio collega era anche un mio amico.";
        dialogue[7] = "Capo della Polizia: Non farla così facile, potresti essere tu il killer....";
        dialogue[8] = "Capo della Polizia: Dove ti trovavi questa notte? Quando Caleb è stato ucciso?";
        dialogue[9] = "Joseph Forest: Ieri sera ero in ufficio e stavo lavorando con Caleb, ho timbrato l'uscita verso le 18:30.";
        dialogue[10] = "Capo della Polizia: E dopo mezzanotte? Cosa hai fatto?";
        dialogue[11] = "Joseph Forest: Sono tornato al mio appartamento. Il portiere del condominio può confermare che mi ha visto arrivare intorno alla 00:30. Inoltre, il sistema di sicurezza del palazzo registra quando ho usato la tessera magnetica per entrare.";
        dialogue[12] = "Capo della Polizia: Bene, queste sono prove verificabili. Ma come fai a sapere che l'omicidio è avvenuto dopo mezzanotte?";
        dialogue[13] = "Joseph Forest: Caleb mi ha inviato un messaggio alle 01:15, con un indizio su una chiave di decifrazione. L'omicidio deve essere avvenuto subito dopo, perché altrimenti avrebbe risposto alla mia chiamata delle 01:30.";
        dialogue[14] = "Capo della Polizia: Interessante. Questo alibi regge. Ma questo significa che il killer è qualcuno del team.";
        dialogue[15] = "Joseph Forest: Esatto. Siamo solo in quattro a lavorare qui, e Caleb stava lavorando su qualcosa di enorme, qualcosa che poteva cambiare il corso della guerra. Chiunque l'abbia ucciso voleva impedirgli di completare il suo lavoro e rubare il codice su cui stava lavorando.";
        dialogue[16] = "Capo della Polizia: Non posso negare che ci sia del senso in quello che dici. Ma ascolta, Forest, queste sono indagini ufficiali. Non posso lasciarti agire da solo.";
        dialogue[17] = "Joseph Forest: Capisco, agente. Ma Caleb era il mio migliore amico, e non posso restare con le mani in mano. Voglio scoprire chi è la talpa e recuperare quel codice. Lo devo a lui.";
        dialogue[18] = "Capo della Polizia: Se trovi qualcosa, vieni subito da me. Non agire senza avvisarmi, capito?";
        dialogue[19] = "Joseph Forest: Lo farò, ma non posso promettere di stare fermo. Caleb meritava giustizia, e non mi fermerò finché non la otterrò.";
        dialogue[20] = "Capo della Polizia: Buona fortuna, Forest. Ne avrai bisogno.";
        dialogue[21] = "";
        return dialogue;
    }

    public String[] InitDialogAct2(String[] dialogue) {
        dialogue[0] = "Joseph:\nAgente ho scoperto qualcosa che può risolvere l'omicidio.";
        dialogue[1] = "Capo della Polizia:\nMi dica dott. Forest.";
        dialogue[2] = "Joseph:\nIl movente dell'omicidio è la scoperta dell'algoritmo in grado di decifrare i messaggi segreti russi.";
        dialogue[3] = "Capo della Polizia:\nDunque supponi che il killer sia in qualche modo una spia nemica?";
        dialogue[4] = "Joseph:\nÈ difficile da ammettere, ma probabilmente uno dei miei stimati colleghi è in realtà un traditore.";
        dialogue[5] = "Capo della Polizia:\nGrazie per l'informazione, vedrò di seguire questa eventuale pista nelle mie indagini.";
        dialogue[6] = "";
        return dialogue;
    }

    public String[] InitDialogAct3(String[] dialogue) {
        dialogue[0] = "Joseph:\nAgente, come ha potuto sentire, il dott. Pierce non è chi vuol far credere.";
        dialogue[1] = "Capo della Polizia:\nOttimo lavoro, dott. Forest...";
        dialogue[2] = "Capo della Polizia:\nOra mi ceda le prove raccolte.";
        dialogue[3] = "Joseph:\nUn attimo, devo fare prima una cosa con la chiavetta.";
        dialogue[4] = "Capo della Polizia:\nD'accordo, ma faccia presto.";
        dialogue[5] = "";
        return dialogue;
    }

    // Disegna il primo atto
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();
        Inventory inventory = Inventory.getInventoryInstance();

        if (storyState.getDialogueState("NPC_DEADBODY_ACT1") & inventory.getItemInventory().contains("JosephPhone")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]);
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                        storyState.setDialogueCompleted("NPC_CHIEF_OF_POLICE_ACT1");
                        this.notifyObservers(3);
                    } else {
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

        if (storyState.getDialogueState("NPC_AURORA_ACT1")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct2 < this.dialogueAct2.length) {
                        this.getDialogManager().setDialog(this.dialogueAct2[dialogIndexAct2]);
                        this.dialogIndexAct2++;
                        storyState.setDialogueCompleted("NPC_CHIEF_OF_POLICE_ACT2");
                        this.notifyObservers(5);
                    } else {
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

        if (storyState.getDialogueState("NPC_KILLER_ACT2")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct3 < this.dialogueAct3.length) {
                        this.getDialogManager().setDialog(this.dialogueAct3[dialogIndexAct3]);
                        this.dialogIndexAct3++;
                        storyState.setDialogueCompleted("NPC_CHIEF_OF_POLICE_ACT3");
                        this.notifyObservers(11);
                    } else {
                        this.getDialogManager().setDialog("");
                        this.dialogIndexAct3 = 0;
                    }
                }
            }
        }
    }
}
