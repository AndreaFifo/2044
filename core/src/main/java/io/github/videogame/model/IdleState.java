package io.github.videogame.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//classe che rappresenta lo stato in cui l'NPC è fermo
public class IdleState implements NPCState{
    @Override
    public void update(NpcCreator npcCreator, float delta) {
        // L'NPC fermo non ha azioni particolari da eseguire qui
    }

    @Override
    public void render(SpriteBatch batch, NpcCreator npcCreator) {
        // Disegna l'NPC nella posizione attuale
        batch.draw(npcCreator.getTexture(), npcCreator.getSpawn_x(), npcCreator.getSpawn_y(), 32, 32);
    }

    @Override
    public void enter(NpcCreator npcCreator) {
        // Azioni opzionali quando si entra nello stato Idle
        System.out.println("NPC è nello stato Idle.");
    }
}
