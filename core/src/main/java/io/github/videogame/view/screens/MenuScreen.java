package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.github.videogame.controller.AudioController;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;
import io.github.videogame.controller.MenuController;

public class MenuScreen implements Screen {
    private final Gioco game;
    private final Batch batch;
    private final Stage stage;
    private final Table table;
    private final MenuController menuController;
    private AudioController audioController;

    private Texture background;
    private Texture gameTitle;

    private Music menuMusic;
    private Sound buttonClickSound;

    private Button play;
    private Button load;
    private Button settings;
    private Button exit;

    private Skin skin;

    public MenuScreen(Gioco game) {
        this.game = game;
        this.batch = game.getBatch();
        this.stage = new Stage();
        this.table = new Table();
        this.menuController = new MenuController(this.game, this);
        this.audioController = AudioController.getInstance();

        setupUI();
        menuController.setup();
    }

    @Override
    public void show() {
        menuMusic.play();
        batch.setProjectionMatrix(stage.getCamera().combined);

        Gdx.input.setInputProcessor(stage);
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
        stage.getViewport().update(width, height, true);
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
    }

    @Override
    public void dispose() {
        gameTitle.dispose();
        background.dispose();
        buttonClickSound.dispose();
        menuMusic.stop();
        menuMusic.dispose();
        stage.dispose();
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

    public Music getMusic(){
        return menuMusic;
    }

    private void loadMenuAssets(){
        Utility.loadAssetsFromJSON("assets.json", "menu");

        this.skin = Utility.getAsset("menu/ui-skin.json", Skin.class);
        this.gameTitle = Utility.getAsset("menu/text-2044.png", Texture.class);
        this.background = Utility.getAsset("menu/bg-menu.png", Texture.class);
        this.menuMusic = Utility.getAsset("menu/main-menu-music.mp3", Music.class);
        this.buttonClickSound = Utility.getAsset("menu/button-click.mp3", Sound.class);

        audioController.addNewMusic("menu/main-menu-music.mp3", this.menuMusic);
        audioController.setMusicVolume(audioController.getMusicsVolume());
    }

    private void setupUI(){
        loadMenuAssets();

        menuMusic.setLooping(true);
        menuMusic.play();

        this.play = new Button(skin, "play");
        this.load = new Button(skin, "load");
        this.settings = new Button(skin, "settings");
        this.exit = new Button(skin, "exit");

        table.setPosition((float) -Gdx.graphics.getWidth() / 4, 0);
        table.setFillParent(true);
        table.add(new Image(gameTitle)).padBottom(60).size((float) (gameTitle.getWidth() * 1.30), (float) (gameTitle.getHeight() * 1.3));
        table.row();
        table.add(play).padBottom(20);
        table.row();
        table.add(load).padBottom(20);
        table.row();
        table.add(settings).padBottom(20);
        table.row();
        table.add(exit);
        table.row();
        stage.addActor(table);
    }
}
