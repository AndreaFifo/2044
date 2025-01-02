package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.videogame.model.Gioco;

import java.util.Objects;

public class ElevatorMenu implements Screen {
    private Gioco game;
    private MainGameScreen previousScreen;
    private String targetMap;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    public ElevatorMenu(Gioco game, MainGameScreen previousScreen, String targetMap) {
        this.game = game;
        this.previousScreen = previousScreen;
        this.targetMap = targetMap;
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
        /*String nome = null;
        if(Objects.equals(targetMap, "Mappa2.tmx")){
            nome = "Testo";
        }*/
        batch.begin();
        font.draw(batch, "Elevator Menu", 400, 300);
        font.draw(batch, "Press ENTER to go to " + targetMap, 350, 250);
        font.draw(batch, "Press ESC to return", 350, 200);
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
            game.setScreen(previousScreen); // Torna alla schermata precedente
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
