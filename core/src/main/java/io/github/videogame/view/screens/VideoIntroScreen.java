package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.model.Gioco;

import java.io.FileNotFoundException;

public class VideoIntroScreen implements Screen {
    private Gioco game;
    private SpriteBatch batch;
    private VideoPlayer videoPlayer;

    public VideoIntroScreen(Gioco game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        videoPlayer.setOnCompletionListener(new VideoPlayer.CompletionListener() {
            @Override
            public void onCompletionListener(FileHandle file) {
                game.setScreen(new MainGameScreen(game));
            }
        });

        try {
            videoPlayer.play(Gdx.files.internal("video/videointro.webm"));
        } catch (FileNotFoundException e) {
            Gdx.app.error("gdx-video", "Oh no!");
        }
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            goToMainScreen();
        }
    }

    private void goToMainScreen() {
        videoPlayer.stop(); // Interrompe il video se è ancora in riproduzione
        ScreenManager.getInstance().showScreen(ScreenManager.ScreenType.GAME); // Passa alla schermata principale
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
    public void dispose () {
        batch.dispose();
    }
}
