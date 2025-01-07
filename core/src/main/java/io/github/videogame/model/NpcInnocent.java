package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcInnocent extends NpcCreator{

    public NpcInnocent(float spawn_x, float spawn_y, MovementController movementControllerPlayer){
        super(spawn_x, spawn_y, movementControllerPlayer);
        //Setto il nome del Npc
        this.setNpcName("Nome_Innocente");
        //Setto la texture del Npc
        this.setTexture(new Texture("NPC/innocent.png"));
        //Setto il dialogo del Npc
        this.setDialogue(InitDialog(this.getDialogue()));
    }


    //Inizializza il dialogo del killer
    @Override
    public String[] InitDialog(String[] dialogue) {

        dialogue[0] = "Bryan:\nEih Joseph, non posso credere a quello che sia successo......\n";
        dialogue[1]= "Joseph:\nDove ti trovavi questa notte?";
        dialogue[2]="Bryan:\n Non starai mica dubitando di me....? Lavoriamo assieme da anni...";
        dialogue[3] = "Joseph:\nNon è questione di dubitare di te, Bryan. Ma se uno di noi è la talpa, dobbiamo scoprirlo.";
        dialogue[4] = "Bryan:\nIo sono rimasto nel mio ufficio questa notte, stavo continuando il progetto riguardo decriptazione dei messaggi russi";
        dialogue[5] = "Joseph:\nHai sentito qualche cosa?";
        dialogue[6] = "Bryan:\nSe avessi sentito qualcosa non credi che l'avrei già detto alla polizia?";
        dialogue[7] = "Joseph:\n.......ok";
        dialogue[8] = "Joseph:\nPotresti darmi la tua chiavetta USB? ";
        dialogue[9] = "Bryan:\nA cosa ti serve.....? Comunque è nel mio ufficio...";
        dialogue[10] = "Bryan:\n............";
        dialogue[11] = "Bryan:\n......";
        dialogue[12] = "Bryan:\nNon trovo le chiavi del mio ufficio, devo averle dimenticate nella mensa con l'arrivo degli agenti...";
        dialogue[13] = "Joseph:\nCi penso io!";
        dialogue[14] = "Bryan:\nOk....a presto dopo allora....Non mettermi nei guai....";

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
