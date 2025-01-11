package io.github.videogame.model;

public class CareTaker {
    private static GameState savedState;

    // Metodo per salvare lo stato
    public static void saveMemento(GameState memento) {
        savedState = memento;
    }

    // Metodo per recuperare lo stato salvato
    public static GameState getMemento() {
        return savedState;
    }

    // Metodo per cancellare il salvataggio (opzionale)
    public void clearMemento() {
        this.savedState = null;
    }
}

