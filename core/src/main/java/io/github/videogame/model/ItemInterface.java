package io.github.videogame.model;

public interface ItemInterface {
    //Chiamato per raccogliere l'oggetto
    public void pickUp();

    //Chiamato per verificare che l'oggetto possa essere preso
    public boolean canBePickedUp();

    //Chiamato per disegnare il dialogo di raccolta
    public void drawDialogue();

    //Chiamato per emettere il suono di raccolta
    public void emitSound();

    //Chiamato per verificare se l'oggetto è stato preso o meno
    public boolean isTaken();

}
