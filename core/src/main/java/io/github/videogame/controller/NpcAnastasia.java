package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.model.NpcCreator;

public class NpcAnastasia extends NpcCreator {


    public NpcAnastasia(float spawn_x, float spawn_y, MovementController movementControllerPlayer) {
        super(spawn_x, spawn_y, movementControllerPlayer);
        // Setto il nome del Npc
        this.setNpcName("Anastasia");
        // Setto la texture del Npc
        this.setTexture(new Texture("Oggetti/nobackground.png"));
        // Setto i dialoghi del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I

    }

    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Anastasia:\nBen tornato dott.Pierce. Attualmente ha decine di mail non lette da ...";
        dialogue[1] = "Anastasia:\nUn momento..., buongiorno dott. Forest non mi aspettavo di vederla";
        dialogue[2] = "Joseph:\nSmettila di fingere, so tutto. Il dott. Pierce è una spia.";
        dialogue[3] = "***Appaiono caratteri Russi su schermo***";
        dialogue[4] = "Anastasia:\nMi spiace dott. Pierce, la guerra non conosce amicizie.";
        dialogue[5] = "Anastasia:\nIl dott. Piece ha fatto cio che era giusto per la sua nazione, per il suo popolo";
        dialogue[6] = "Joseph:\nSiete solo dei pazzi, avete ucciso il dott.Morrison... Ora grazie a questa chiavetta ribalteremo le sorti della guerra";
        dialogue[7] = "Joesph:\nTutti i responsabili pagheranno e tu verrai terminata";
        dialogue[8] = "Anastasia:\nCaro Joseph, non è stufo del modo in cui lei è trattato all'interno di questo dipartimento...?";
        dialogue[9] = "Joseph:\nNon provare a manipolarmi, Anastasia. Ho visto cosa siete capaci di fare.";
        dialogue[10] = "Anastasia:\nE lei, dott. Forest? Quanto è disposto a sacrificare per un governo che l'ha sempre considerata solo un ingranaggio?";
        dialogue[11] = "Joseph:\nNon cambio idea. Non tradirò i miei ideali.";
        dialogue[12] = "Anastasia:\nIdeali? Non è più questione di ideali, Joseph. È sopravvivenza. La chiavetta può porre fine a tutto questo, senza ulteriori vittime.";
        dialogue[13] = "Joseph:\nNon posso fidarmi di te. Questa chiavetta potrebbe fare il contrario e distruggere tutto.";
        dialogue[14] = "Anastasia:\nGuardi il mondo intorno a lei. Le bugie, i compromessi... Ha ancora il coraggio di credere che ci siano eroi in questa guerra?";
        dialogue[15] = "Joseph:\nIo credo ancora in Caleb. Lui avrebbe voluto che questa chiavetta fosse usata per il bene.";
        dialogue[16] = "Anastasia:\nE allora lo faccia, Joseph. Ma rifletta: il bene di chi? Il governo degli Stati Uniti uccidera milioni di Russi a causa di questa chiavetta";
        dialogue[17] = "Joseph:\nNon posso lasciarvi vincere. Questa chiavetta è la nostra unica speranza.";
        dialogue[18] = "Anastasia:\nIn Russia sarebbe un eroe, pieno di richezze e fama";
        dialogue[19] = "Anastasia:\nDott. Forest, non le sto chiedendo di fare la scelta giusta per la sua nazione";
        dialogue[20] = "Joseph:\nLei deve fare la scelta giusta per se stesso. Inserisca la chiavetta";
        dialogue[21] = "***Appaiono caratteri Russi su schermo***";
        dialogue[22] = "Inserire la chiavetta all'interno del computer del Dott.Pierce? Y/N";

        return dialogue;
    }



    //Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        if (canBeInteracted()) {
            this.getDialogManager().draw();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                    this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                    this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                } else {
                    this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                    this.setDialogIndexAct1(0); // Resetta il dialogo
                }
            }
        }
    }

    @Override
    public boolean canBeInteracted() {
        return getMovementControllerPlayer().getX() <= this.getSpawn_x() + 30 &&
            getMovementControllerPlayer().getX() >= this.getSpawn_x() - 30 &&
            getMovementControllerPlayer().getY() <= this.getSpawn_y() + 30 &&
            getMovementControllerPlayer().getY() >= this.getSpawn_y() - 30;
    }


}
