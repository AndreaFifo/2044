package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcAurora extends NpcCreator{

    public NpcAurora(float spawn_x, float spawn_y, MovementController movementControllerPlayer){
        super(spawn_x, spawn_y, movementControllerPlayer);
        //Setto il nome del Npc
        this.setNpcName("Aurora");
        //Setto la texture del Npc
        this.setTexture(new Texture("Oggetti/nobackground.png"));
        //Setto il dialogo del Npc
        this.setDialogue(InitDialog(this.getDialogue()));
    }


    //Inizializza il dialogo del killer
    @Override
    public String[] InitDialog(String[] dialogue) {
        // ATTO I: SCOPERTA DEL MODO PER SCOPRIRE IL KILLER
        dialogue[0] = "Aurora:\n Ciao sono Aurora, l'intelligenza artificiale che assiste il dott. Caleb nelle sue ricerche, come posso esserle utile dott. Forest?";
        dialogue[1] = "Joseph:\n Caleb è stato assassinato, mi servirebbe una mano per scoprire il colpevole.";
        dialogue[2] = "Aurora:\n Questo è terribile. Ha già avvisato le autorità?";
        dialogue[3] = "Joseph:\n Sì, la polizia è già stata informata, ma ho bisogno di accedere ai dati e ai messaggi su cui Caleb stava lavorando.";
        dialogue[4] = "Aurora:\n Posso aiutarti, il dott. Caleb sembrerebbe aver terminato il suo algoritmo di decriptazione ";
        dialogue[5] = "Joseph:\n .... E dal 2039 che cerchiamo invano di creare un algoritmo adatto alla decifrazione dei messaggi...";
        dialogue[6] = "Joseph:\n Finalmente è riuscito nell'impresa......";
        dialogue[7] = "Joseph:\n Forza, mostrami il file dove è contenuto l'algoritmo devo subito condividerlo con le autorità!!!";
        dialogue[8] = "Aurora:\n Temo che non sia possibile dott.Forest... sembrerebbe che il file contenete l'algoritmo sia stato prelevato da un chiavetta questa notte";
        dialogue[9] = "Joseph:\n E terribile....questo significa che qualcuno del team è in realtà una spia Russo che ci ha ostacolato nelle ricerche tutto questo tempo";
        dialogue[10] = "Aurora:\n Non si preoccupi dott.Forest, il dott.Caleb era solito fare degli scherzi a chi copiava le proprie ricerche";
        dialogue[11] = "Aurora:\n Ecco dott.Forest...all'interno del computer vi è un software che inserisce un file all'interno dei dispositivi che copiano i dati del computer";
        dialogue[12] = "Aurora:\n Sarà sufficente trovare un dispositivo con all'interno il file e controllare se la data di creazione di quest ultimo coincide";
        dialogue[13] = "Joseph:\n Sei fantastica, vado a riferire agli agenti. Grazie mille";
        dialogue[14] = "Aurora:\n E sempre un piacere aiutarla dott.Forest. A presto";

        //ATTO II: INSERIMENTO CHIAVETTA INNOCENTE
        dialogue[15] = "Inserimento chiavetta del dott. Cooper nel compter";
        dialogue[16] = "Aurora:\nBuongiorno dott.Forest, di cosa ha bisogno?";
        dialogue[17] = "Joseph:\nAnalizza la chiavetta, dimmi se è quella incriminata contenente il file che stiamo cercando";
        dialogue[18] = "Aurora:\nAnalizzando la chiavetta...";
        dialogue[19] = "Aurora:\nSembrerebbe che questa non corrisponda. Vi sono diversi file del software interessato....";
        dialogue[20] = "Aurora:\nNessuno pero corrisponde alla data che stiamo cercando";
        dialogue[21] = "Joseph:\nVa bene, grazie Aurora";
        dialogue[22] = "Aurora:\nA presto dott.Forest";
        dialogue[24] = "De-inserimento della chiavetta";

        //ATTO III: INSERIMENTO CHIAVETTA KILLER
        dialogue[25] = "Inserimento chiavetta del dott. Pierce nel computer";
        dialogue[26] = "Aurora:\nBuongiorno dott.Forest, di cosa ha bisogno?";
        dialogue[27] = "Joseph:\\nAnalizza la chiavetta, dimmi se è quella incriminata contenente il file che stiamo cercando\"";
        dialogue[28] = "Aurora:\nAnalizzando la chiavetta...";
        dialogue[29] = "Aurora:\nMi spiace dott. Forest, sembra esserci una corrispondenza. Stimo che al 99.7% il dott. Pierce sia il killer che stava cercando";
        dialogue[30] = "Joseph:\nNon posso crederci! Dopo tutto questo tempo... Non mi sono accorto di chi avevo davanti";
        dialogue[31] = "De-inserimento della chiavetta";

        //ATTO IV: PUBBLICAZIONE ALGORITMO
        dialogue[32] = "Joseph:\nNon posso credere a quanto sia accaduto";
        dialogue[33] = "Aurora:\nCondoglianze per la sua perdita dott.Forest";
        dialogue[34] = "Joseph:\nGrazie, adesso facciamo in modo che la sua perdita non sia stata vana";
        dialogue[35] = "Aurora:\nPubblicare il file 30_e_lode contenente l'algoritmo richiesto sui server governativi?";
        dialogue[36] = "Joseph:\nSi";
        dialogue[37] = "Aurora:\nPubblicazione avvenuta con successo. E sempre un piacere lavorare con lei dott. & detective Forest ;) ";


        return dialogue;
    }


    //Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogue() {
        if (canBeInteracted()) {
            this.getDialogManager().draw();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (this.getDialogIndex() < this.getDialogue().length) {
                    this.getDialogManager().setDialog(this.getDialogue()[this.getDialogIndex()]); // Mostra la linea corrente
                    this.setDialogIndex(this.getDialogIndex() + 1);
                } else {
                    this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                    this.setDialogIndex(0); // Resetta il dialogo
                }
            }
        }
    }

    //Controllare se il player può interagire
    @Override
    public boolean canBeInteracted() {
        return getMovementControllerPlayer().getX() <= this.getSpawn_x() + 30 &&
            getMovementControllerPlayer().getX() >= this.getSpawn_x() - 30 &&
            getMovementControllerPlayer().getY() <= this.getSpawn_y() + 30 &&
            getMovementControllerPlayer().getY() >= this.getSpawn_y() - 30;
    }


}
