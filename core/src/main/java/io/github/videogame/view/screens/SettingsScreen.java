package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.controller.SettingsController;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;

public class SettingsScreen implements Screen {
    private Gioco game;
    private SpriteBatch batch;
    private Stage stage;
    private Table table;

    private Label settingsLabel;
    private Label videoLabel;
    private Label audioLabel;
    private Label vSyncLabel;
    private Label musicLabel;
    private Label soundLabel;

    private MySlider soundSlider;
    private MySlider musicSlider;
    private CheckBox vSyncCheckBox;
    private TextButton backButton;

    private SettingsController settingsController;

    private Skin skin;

    public SettingsScreen(Gioco game) {
        this.game = game;
        this.batch = game.getBatch();
        this.stage = new Stage();
        this.table = new Table();
        this.skin = Utility.getAsset("menu/ui-skin.json", Skin.class);

        setupUI();
        this.settingsController = new SettingsController(this.game, this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        settingsController.setupValues();
    }

    private void setupUI() {
        this.musicSlider = new MySlider(0f, 1f, 0.1f, false, skin);
        this.soundSlider = new MySlider(0f, 1f, 0.1f, false, skin);
        this.vSyncCheckBox = new CheckBox(null, skin);

        this.settingsLabel = new Label("Settings", skin, "general");
        this.videoLabel = new Label("Video", skin, "general");
        this.audioLabel = new Label("Audio", skin, "general");
        this.vSyncLabel = new Label("V-Sync", skin, "general");
        this.soundLabel = new Label("Sound", skin, "general");
        this.musicLabel = new Label("Music", skin, "general");
        this.backButton = new TextButton("Back", skin);

        //table.setDebug(true);
        table.setFillParent(true);
        table.add(settingsLabel).padBottom(30);
        table.row();
        table.add(musicLabel).uniform().padLeft(20);
        table.add(musicSlider).center().padBottom(20);
        table.row();
        table.add(soundLabel).uniform().padLeft(20);
        table.add(soundSlider).center().padBottom(20);
        table.row();
        table.add(vSyncLabel).uniform().padLeft(20);
        table.add(vSyncCheckBox).center();
        table.row();
        table.row();
        table.add(backButton);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public MySlider getSoundSlider() {
        return soundSlider;
    }

    public MySlider getMusicSlider() {
        return musicSlider;
    }

    public CheckBox getvSyncCheckBox() {
        return vSyncCheckBox;
    }

    public TextButton getBackButton() {
        return backButton;
    }
}
