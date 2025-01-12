package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.videogame.model.CareTaker;
import io.github.videogame.model.GameState;
import io.github.videogame.model.Player;
import io.github.videogame.model.TaskModel;
import io.github.videogame.view.screens.MainGameScreen;

import java.util.ArrayList;

public class GameStateController {
    private static GameStateController instance;
    private GameState gameState;
    private Player player;
    private TaskModel taskModel;
    private MapManager mapManager;

    private GameStateController() {
        this.player = Player.getInstance();
        this.taskModel = TaskModel.getInstance();
        this.gameState = GameState.getInstance();
        this.mapManager = MapManager.getInstance();
    }

    public static GameStateController getInstance() {
        if (instance == null) {
            instance = new GameStateController();
        }
        return instance;
    }

    public void loadGameState(){
        try {
            FileHandle file = Gdx.files.internal("saves/game_save.json");
            String jsonString = file.readString();

            GameState tempGameState = new Json().fromJson(GameState.class, jsonString);
            gameState.init(
                tempGameState.getPlayerX(),
                tempGameState.getPlayerY(),
                tempGameState.getCurrentMap(),
                tempGameState.getInventory(),
                tempGameState.getIdCurrentTask()
            );

            restoreGameState();

            CareTaker.saveMemento(gameState);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore durante il caricamento dello stato del gioco.");
        }
    }

    public void restoreGameState(){
        player.setSpawn(gameState.getPlayerX(), gameState.getPlayerY());
        player.setInventory(gameState.getInventory());
        taskModel.setTask(gameState.getIdCurrentTask());
        mapManager.setCurrentMap(gameState.getCurrentMap());
    }

    public void saveGameState(){
        try {
            gameState.setPlayerX(player.getX());
            gameState.setPlayerY(player.getY());
            gameState.setItemInventory(player.getInventory().getItemInventory());
            gameState.setCurrentMap(mapManager.getCurrentMap());
            gameState.setIdCurrentTask(taskModel.getIdCurrentTask());

            CareTaker.saveMemento(gameState);

            Json json = new Json();
            String jsonString = json.toJson(gameState);

            FileHandle file = Gdx.files.local("saves/game_save.json");
            file.writeString(jsonString, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialGameState(){
        gameState.initialState();
    }


}
