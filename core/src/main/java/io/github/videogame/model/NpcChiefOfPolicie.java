package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcChiefOfPolicie extends NpcCreator{

    public NpcChiefOfPolicie(float spawn_x, float spawn_y, MovementController movementControllerPlayer){
        super(spawn_x, spawn_y, movementControllerPlayer);
        //Setto il nome del Npc
        this.setNpcName("Nome_Capo_Polizia");
        //Setto la texture del Npc
        this.setTexture(new Texture("NPC/PoliceOfficer.png"));
        //Setto il dialogo del Npc
        this.setDialogue(InitDialog(this.getDialogue()));
    }


    //Inizializza il dialogo del killer
    @Override
    public String[] InitDialog(String[] dialogue) {
        //Discussione iniziale post-chiamata polizia
        dialogue[0] = "Capo della Polizia: Sono stato informato della situazione. Puoi spiegarmi cosa è successo?";
        dialogue[1] = "Joseph Forest: Ho trovato un corpo... È lì, non so cosa sia successo.";
        dialogue[2] = "Capo della Polizia: Hai toccato qualcosa? Hai visto qualcuno sospetto nei paraggi?";
        dialogue[3] = "Joseph Forest: No, non ho toccato niente. Non ho visto nessuno, era già così quando sono arrivato.";
        dialogue[4] = "Capo della Polizia: Bene, hai fatto la cosa giusta chiamandomi. Ora lascia fare a noi.";
        dialogue[5] = "Capo della Polizia: Resta nei paraggi, potremmo avere bisogno di farti qualche domanda.";

        //Richiesta di volere aiutare nelle indagi
        dialogue[6] = "Joseph Forest: Voglio aiutarvi a risolvere il caso, Caleb oltre che essere mio collega era anche un mio amico";
        dialogue[7] = "Capo della Polizia: Non farla cosi facile, potresti essere tu il killer....";
        dialogue[8] = "Capo della Polizia: Dove ti trovavi questa notte? Quando Caleb è stato ucciso? ";
        // Discussione sull'alibi
        dialogue[9] = "Joseph Forest: Ieri sera ero in ufficio e stavo lavorando con Caleb, ho timbrato l'uscita verso le 18:30";
        dialogue[10] = "Capo della Polizia: E dopo mezzanotte? Cosa hai fatto?";
        dialogue[11] = "Joseph Forest: Sono tornato al mio appartamento. Il portiere del condominio può confermare che mi ha visto arrivare intorno alla 00:30. Inoltre, il sistema di sicurezza del palazzo registra quando ho usato la tessera magnetica per entrare.";
        dialogue[12] = "Capo della Polizia: Bene, queste sono prove verificabili. Ma come fai a sapere che l'omicidio è avvenuto dopo mezzanotte?";
        dialogue[13] = "Joseph Forest: Caleb mi ha inviato un messaggio alle 01:15, con un indizio su una chiave di decifrazione. L'omicidio deve essere avvenuto subito dopo, perché altrimenti avrebbe risposto alla mia chiamata delle 01:30.";
        dialogue[14] = "Capo della Polizia: Interessante. Questo alibi regge. Ma questo significa che il killer è qualcuno del team.";

        // Dichiarazione della volontà di scoprire la verità
        dialogue[15] = "Joseph Forest: Esatto. Siamo solo in quattro a lavorare qui, e Caleb stava lavorando su qualcosa di enorme, qualcosa che poteva cambiare il corso della guerra. Chiunque l'abbia ucciso voleva impedirgli di completare il suo lavoro e rubare il codice su cui stava lavorando.";
        dialogue[16] = "Capo della Polizia: Non posso negare che ci sia del senso in quello che dici. Ma ascolta, Forest, queste sono indagini ufficiali. Non posso lasciarti agire da solo.";
        dialogue[17] = "Joseph Forest: Capisco, agente. Ma Caleb era il mio migliore amico, e non posso restare con le mani in mano. Voglio scoprire chi è la talpa e recuperare quel codice. Lo devo a lui.";
        dialogue[18] = "Capo della Polizia: Se trovi qualcosa, vieni subito da me. Non agire senza avvisarmi, capito?";
        dialogue[19] = "Joseph Forest: Lo farò, ma non posso promettere di stare fermo. Caleb meritava giustizia, e non mi fermerò finché non la otterrò.";
        dialogue[20] = "Capo della Polizia: Buona fortuna, Forest. Ne avrai bisogno.";


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
