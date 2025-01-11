package io.github.videogame.model;

import java.util.Stack;

public class CareTaker {
    private static Gamestate savedState;

    // Metodo per salvare lo stato
    public static void saveMemento(Gamestate memento) {
        savedState = memento;
    }

    // Metodo per recuperare lo stato salvato
    public static Gamestate getMemento() {
        return savedState;
    }

    // Metodo per cancellare il salvataggio (opzionale)
    public void clearMemento() {
        this.savedState = null;
    }
}

