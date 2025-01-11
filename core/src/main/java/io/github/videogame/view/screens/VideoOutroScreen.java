package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import io.github.videogame.model.Gioco;

import java.io.FileNotFoundException;

public class VideoOutroScreen implements Screen {
    private final Gioco game;
    private SpriteBatch batch;
    private VideoPlayer videoPlayer;

    public VideoOutroScreen(Gioco game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Creazione del VideoPlayer
        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        // Listener per quando il video finisce
        videoPlayer.setOnCompletionListener(new VideoPlayer.CompletionListener() {
            @Override
            public void onCompletionListener(FileHandle file) {
                goToMenuScreen(); // Torna alla schermata MenuScreen
            }
        });

        // Avvio del video
        try {
            videoPlayer.play(Gdx.files.internal("video/videoutro.webm"));
        } catch (FileNotFoundException e) {
            Gdx.app.error("VideoScreen", "Video file not found: " + e.getMessage());
            goToMenuScreen(); // Se il video non è trovato, ritorna alla schermata principale
        }
    }

    private void goToMenuScreen() {
        videoPlayer.stop(); // Interrompe il video se è ancora in riproduzione
        game.setScreen(new MenuScreen(game)); // Passa alla schermata principale
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        videoPlayer.update();

        batch.begin();

        Texture frame = videoPlayer.getTexture();
        if (frame != null)
            batch.draw(frame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Gestione del resize se necessario
    }

    @Override
    public void pause() {
        // Eventuali azioni per la pausa
    }

    @Override
    public void resume() {
        // Eventuali azioni per la ripresa
    }

    @Override
    public void hide() {
        // Rilascia il video player quando la schermata è nascosta
        if (videoPlayer != null) {
            videoPlayer.dispose();
        }
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (videoPlayer != null) {
            videoPlayer.dispose();
        }
    }
}
