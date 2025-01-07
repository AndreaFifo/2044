package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.videogame.model.Gioco;
import io.github.videogame.view.screens.MainGameScreen;
import java.util.Objects;

public class ElevatorMenu implements Screen {
    private Gioco game;
    private MainGameScreen previousScreen;
    private String targetMap,targetMap2;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    public ElevatorMenu(Gioco game, MainGameScreen previousScreen, String targetMap,String targetMap2) {
        this.game = game;
        this.previousScreen = previousScreen;
        this.targetMap = targetMap;
        this.targetMap2 = targetMap2;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        String nome = null;
        String nome2 = null;
        if(Objects.equals(targetMap, "sopra.tmx")){
            nome = "Piano Superiore";
        } else if (Objects.equals(targetMap,"ingresso.tmx")) {
            nome = "Piano Terra";
        } else if (Objects.equals(targetMap,"garage.tmx")) {
            nome = "Garage";
        }
        if(Objects.equals(targetMap2, "sopra.tmx")){
            nome2 = "Piano Superiore";
        } else if (Objects.equals(targetMap2,"ingresso.tmx")) {
            nome2 = "Piano Terra";
        } else if (Objects.equals(targetMap2,"garage.tmx")) {
            nome2 = "Garage";
        }
        batch.begin();
        font.draw(batch, "ASCENSORE", 400, 300);
        font.draw(batch, "Premi ENTER per raggiungere il " + nome, 350, 250);
        font.draw(batch, "Premi R per raggiungere il " + nome2, 350, 200);
        font.draw(batch, "Premi ESC per restare sul piano attuale", 350, 150);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (targetMap != null && !targetMap.isEmpty()) {
                if (previousScreen != null) {
                    targetMap = "Mappe/"+targetMap;
                    System.out.println(targetMap);
                    MainGameScreen mainScreen = previousScreen;
                    mainScreen.updateMap(targetMap); // Aggiorna la mappa
                    game.setScreen(mainScreen); // Torna alla schermata principale
                }
            } else {
                System.out.println("Errore: targetMap non valida.");
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (previousScreen != null) {
                MainGameScreen mainScreen = (MainGameScreen) previousScreen;
             //   mainScreen.savePlayerState();
                game.setScreen(mainScreen);
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            if (targetMap2 != null && !targetMap2.isEmpty()) {
                if (previousScreen != null) {
                    targetMap2 = "Mappe/"+targetMap2;
                    System.out.println(targetMap2);
                    MainGameScreen mainScreen = previousScreen;
                    mainScreen.updateMap(targetMap2); // Aggiorna la mappa
                    game.setScreen(mainScreen); // Torna alla schermata principale
                }
            } else {
                System.out.println("Errore: targetMap non valida.");
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
