package io.github.videogame.view.screens;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.videogame.controller.MenuPauseController;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;

public class MenuPause implements Screen {
    private Stage stage;
    private Gioco game;
    private SpriteBatch batch;
    private MenuPauseController controller;

    private ScreenManager screenManager; // Passa solo l'istanza del gioco

    private Button resumeButton, saveButton, settingsButton,mainmenuButton;
    private Sound clickSound;

    private Table table;
    private TextureRegionDrawable tableBG;

    private Skin skin;

    public MenuPause(Gioco game) {
        this.game = game;
        this.screenManager = ScreenManager.getInstance();
        this.batch= game.getBatch();
        this.controller = new MenuPauseController(game, this);

        this.table = new Table();
        this.stage = new Stage();

        loadMenuAssets();

        // Configura i pulsanti
        this.resumeButton = new Button(skin, "resume");
        this.saveButton = new Button(skin, "save");
        this.settingsButton = new Button(skin, "settings");
        this.mainmenuButton = new Button(skin, "main-menu");

        controller.setUpBtnsListeners();
        setUpTableLayout();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        screenManager.getGameScreen().renderStaticState(batch);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Disegna il menu di pausa
        stage.act(delta);
        stage.draw();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();

        if (clickSound != null) {
            clickSound.dispose();
        }

        if (skin != null) {
            skin.dispose();
        }

        if (tableBG != null && tableBG.getRegion() != null) {
            tableBG.getRegion().getTexture().dispose();
        }
    }

    private void loadMenuAssets(){
        this.clickSound = Utility.getAsset("menu/button-click.mp3", Sound.class);
        this.skin = Utility.getAsset("menu/ui-skin.json", Skin.class);

        Utility.loadAsset("UI/pause-ui.png", Texture.class);
        this.tableBG = new TextureRegionDrawable(Utility.getAsset("UI/pause-ui.png", Texture.class));
    }

    private void setUpTableLayout() {
        float MENU_WIDTH = 576;
        float MENU_HEIGHT = 714;

        table.setBackground(tableBG);
        table.setPosition(((float) Gdx.graphics.getWidth() / 2) - (MENU_WIDTH / 2), ((float) Gdx.graphics.getHeight() /2) - (MENU_HEIGHT / 2));
        table.setSize(MENU_WIDTH, MENU_HEIGHT);
        table.add(resumeButton).padTop(40);
        table.row();
        table.add(saveButton).padTop(40);
        table.row();
        table.add(settingsButton).padTop(40);
        table.row();
        table.add(mainmenuButton).padTop(40);
        table.row();

        stage.addActor(table);
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getMainmenuButton() {
        return mainmenuButton;
    }
}
