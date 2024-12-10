package io.github.videogame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


// Classe che si occupa delle ANIMAZIONI DELLE ENTITA


public class Entity {

    //Variabile conterà lo spriteSheetPlayer ovvero l'immagine dove sono contenute le animazioni
    private Texture spriteSheetPlayer;
    //Vettore che contiene le animazioni di movimento (walk)
    private Animation<TextureRegion>[] walkAnimationsPlayer;
    /*Tiene traccia del tempo trascorso dall'inizio dell'animazione, serve per determinare quale fotogramma
    dell'animazione visualizzare */
    private float stateTime;



    // Costruttore
    public Entity(String spriteSheetPath) {

        this.spriteSheetPlayer = new Texture(spriteSheetPath);
        this.stateTime = 0f;
        //Matrice che mapperà lo spriteSheetPlayer dividendo le animazioni in una matrice
        TextureRegion[][] regions = TextureRegion.split(spriteSheetPlayer, 64, 64);
        /*Assegno le animazioni di movimento ad un vettore sfruttando la precedente matrice, ogni elemento è l'animazione
        lungo una direzione */
        walkAnimationsPlayer = new Animation[4];
        for (int i = 0; i < 4; i++)
            walkAnimationsPlayer[i] = new Animation<>(0.1f, regions[i]);
    }




    /* Metodo per aggiornare lo stateTime:
       -La variabile delta rappresenta il tempo trascorso dall'ultimo frame.
       -Il metoodo verrà chiamato in render per potare avanti l'animazione*/
    public void updateStateTime(float delta) {
        this.stateTime += delta;
    }



    // Metodo per ottenere il frame corrente dell'animazione
    public TextureRegion getCurrentFrame(int direction, boolean isMoving, float delta) {
        if (isMoving) {
            updateStateTime(delta);
            return walkAnimationsPlayer[direction].getKeyFrame(stateTime, true);
        }

        return walkAnimationsPlayer[direction].getKeyFrame(0f);
    }





    // Metodo per liberare le risorse
    public void dispose() {
        if (spriteSheetPlayer != null) {
            spriteSheetPlayer.dispose();
        }
    }
}
