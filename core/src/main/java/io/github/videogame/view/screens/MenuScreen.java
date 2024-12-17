package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;
import io.github.videogame.controller.MenuController;

import java.util.Arrays;

public class MenuScreen implements Screen {
    private final Gioco game;
    private final Batch batch;
    private final Stage stage;
    private final Table table;
    private MenuController menuController;

    private Texture background;
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture loadButtonActive;
    private Texture loadButtonInactive;
    private Texture settingsButtonInactive;
    private Texture settingsButtonActive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture gameTitle;

    private Music menuMusic;
    private Sound buttonClickSound;

    private Button.ButtonStyle buttonStyle;
    private Button play;
    private Button load;
    private Button settings;
    private Button exit;
    private Slider slider;

    public MenuScreen(Gioco game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        this.table = new Table();
    }

    @Override
    public void show() {
        loadMenuAssets();

        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();

        Gdx.input.setInputProcessor(stage);

        setupUI();
        this.menuController = new MenuController(this.game, this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1920, 1080);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        menuMusic.pause();
    }

    @Override
    public void resume() {
        menuMusic.play();
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        playButtonInactive.dispose();
        playButtonActive.dispose();
        loadButtonInactive.dispose();
        loadButtonActive.dispose();
        settingsButtonInactive.dispose();
        settingsButtonActive.dispose();
        exitButtonInactive.dispose();
        exitButtonActive.dispose();
        gameTitle.dispose();
        background.dispose();
        buttonClickSound.dispose();
        menuMusic.stop();
        menuMusic.dispose();
        stage.dispose();
        batch.dispose();
    }

    public Button createButton(Texture inactive, Texture active){
        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(inactive));
        buttonStyle.over = new TextureRegionDrawable(new TextureRegion(active));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(active));

        return new Button(buttonStyle);
    }

    public Button getPlayBtn() {
        return play;
    }

    public Button getLoadBtn() {
        return load;
    }

    public Button getSettingsBtn() {
        return settings;
    }

    public Button getExitBtn() {
        return exit;
    }

    private void loadMenuAssets(){
        //Rifare con atlas o skin.json
        Utility.loadAssetsFromJSON("assets.json", "menu");
        this.playButtonInactive = Utility.getAsset("menu/play_button_inactive.png", Texture.class); //Mettere button play
        this.playButtonActive = Utility.getAsset("menu/play_button_active.png", Texture.class);
        this.loadButtonInactive = Utility.getAsset("menu/load_button_inactive.png", Texture.class);
        this.loadButtonActive = Utility.getAsset("menu/load_button_active.png", Texture.class);
        this.settingsButtonInactive = Utility.getAsset("menu/settings_button_inactive.png", Texture.class);
        this.settingsButtonActive = Utility.getAsset("menu/settings_button_active.png", Texture.class);
        this.exitButtonInactive = Utility.getAsset("menu/exit_button_inactive.png", Texture.class);
        this.exitButtonActive = Utility.getAsset("menu/exit_button_active.png", Texture.class);
        this.gameTitle = Utility.getAsset("menu/text-2044.png", Texture.class);
        this.background = Utility.getAsset("menu/bg-menu.png", Texture.class);
        this.menuMusic = Utility.getAsset("menu/main_menu_music_dbd.mp3", Music.class);
        this.buttonClickSound = Utility.getAsset("menu/button-click.mp3", Sound.class);
    }

    private void setupUI(){
        play = createButton(playButtonInactive, playButtonActive);
        load = createButton(loadButtonInactive, loadButtonActive);
        settings = createButton(settingsButtonInactive, settingsButtonActive);
        exit = createButton(exitButtonInactive, exitButtonActive);

        table.setPosition((float) -Gdx.graphics.getWidth() / 4, 0);
        table.setFillParent(true);
        table.add(new Image(gameTitle)).padBottom(60).size((float) (gameTitle.getWidth() * 1.30), (float) (gameTitle.getHeight() * 1.3));
        table.row();
        table.add(play).padBottom(10);
        table.row();
        table.add(load).padBottom(10);
        table.row();
        table.add(settings).padBottom(10);
        table.row();
        table.add(exit);
        table.row();
        stage.addActor(table);
    }
}
