package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcInnocent extends NpcCreator{
    public NpcInnocent(float spawn_x, float spawn_y){
        super(spawn_x, spawn_y);
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
        dialogue[15] = "";

        return dialogue;
    }

    //Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();

        if (storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT2")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                        storyState.setDialogueCompleted("NPC_INNOCENT_ACT1");
                        this.notifyObservers(6);
                    } else {
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.setDialogIndexAct1(0); // Resetta il dialogo
                    }
                }
            }
        }
    }
}
