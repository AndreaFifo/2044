package io.github.videogame.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//interfaccia che definisce il contratto che ogni stato di un NPC deve implementare.
public interface NPCState {
    void update(NpcCreator npcCreator, float delta);  // Aggiorna lo stato dell'NPC (se necessario)
    void render(SpriteBatch batch, NpcCreator npcCreator);  // Disegna l'NPC nello stato attuale
    void enter(NpcCreator npcCreator);  // Azioni da eseguire quando si entra in questo stato
}
