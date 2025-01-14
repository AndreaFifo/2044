package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.controller.SettingsController;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;

public class SettingsScreen implements Screen {
    private Gioco game;
    private SpriteBatch batch;
    private Stage stage;
    private Table table;

    private Label vSyncLabel;
    private Label musicLabel;
    private Label soundLabel;

    private MySlider soundSlider;
    private MySlider musicSlider;
    private CheckBox vSyncCheckBox;
    private TextButton backButton;
    private Texture background;
    private TextureRegionDrawable tableBG;

    private SettingsController settingsController;

    private ScreenManager screenManager;
    private ScreenManager.ScreenType previousScreen;

    private Skin skin;

    public SettingsScreen(Gioco game) {
        this.game = game;
        this.batch = game.getBatch();
        this.stage = new Stage();
        this.table = new Table();
        this.screenManager = ScreenManager.getInstance();
        this.background = Utility.getAsset("menu/bg-menu.png", Texture.class);
        Utility.loadAsset("UI/settings-ui.png", Texture.class);
        this.tableBG = new TextureRegionDrawable(Utility.getAsset("UI/settings-ui.png", Texture.class));
        this.skin = Utility.getAsset("menu/ui-skin.json", Skin.class);

        setupUI();
        this.settingsController = new SettingsController(this.game, this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        previousScreen = screenManager.getPreviousScreen();
        settingsController.setupValues();
    }

    private void setupUI() {
        this.musicSlider = new MySlider(0f, 1f, 0.1f, false, skin);
        this.soundSlider = new MySlider(0f, 1f, 0.1f, false, skin);
        this.vSyncCheckBox = new CheckBox(null, skin);

        this.vSyncLabel = new Label("V-Sync", skin, "general");
        this.soundLabel = new Label("Sound", skin, "general");
        this.musicLabel = new Label("Music", skin, "general");
        this.backButton = new TextButton("Back", skin);

        float MENU_WIDTH = 576;
        float MENU_HEIGHT = 714;

        table.setBackground(tableBG);
        table.setPosition(((float) Gdx.graphics.getWidth() / 2) - (MENU_WIDTH / 2), ((float) Gdx.graphics.getHeight() /2) - (MENU_HEIGHT / 2));
        table.setSize(MENU_WIDTH, MENU_HEIGHT);
        table.row();
        table.add(musicLabel).center().padLeft(50);
        table.add(musicSlider).center();
        table.row().padTop(30);
        table.add(soundLabel).center().padLeft(50);
        table.add(soundSlider).center();
        table.row().padTop(30);
        table.add(vSyncLabel).center().padLeft(50);
        table.add(vSyncCheckBox).center();
        table.row().padTop(30);
        table.row();
        table.add(backButton).padTop(50);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(previousScreen == ScreenManager.ScreenType.MAIN_MENU) {
            batch.begin();
            batch.draw(background, 0, 0, 1920, 1080);
            batch.end();
        }
        else
            screenManager.getGameScreen().renderStaticState(batch);


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
