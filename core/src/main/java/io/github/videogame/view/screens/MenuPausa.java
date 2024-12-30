package io.github.videogame.view.screens;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;

public class MenuPausa implements Screen {
    private Stage stage;
    private Gioco game;
    private SpriteBatch batch;
    private MainGameScreen mainGameScreen; // Passa solo l'istanza del gioco
    private ShapeRenderer shapeRenderer;
    private Button resumeButton, saveButton, settingsButton,mainmenuButton;
    private Sound clickSound;
    private Table table;

    private Skin skin;

    public MenuPausa(Gioco game, MainGameScreen mainGameScreen) {
        this.game = game;
        this.mainGameScreen = mainGameScreen;
        this.batch= game.getBatch();

    }

    @Override
    public void show() {
        loadMenuAssets();
        this.table = new Table();
        this.stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);


        // Configura i pulsanti
        resumeButton = new Button(skin, "resume");
        saveButton = new Button(skin, "save");
        settingsButton = new Button(skin, "settings");
        mainmenuButton = new Button(skin, "main-menu");

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play(); // Riproduce il suono
                game.setScreen(mainGameScreen); // Torna al MainGameScreen senza alterarne lo stato
            }
        });
//        // Listener dei pulsanti
//        resumeButton.addListener(new InputListener() {
//            @Override
//            public boolean mouseMoved(InputEvent event, float x, float y) {
//                resumeButton.getStyle().imageUp = resumeActive;
//                return true;
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                resumeButton.getStyle().imageUp = resumeInactive;
//            }
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                clickSound.play(); // Riproduce il suono
//                game.setScreen(mainGameScreen); // Torna al MainGameScreen senza alterarne lo stato
//                return true;
//            }
//        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
//        saveButton.addListener(new InputListener() {
//            @Override
//            public boolean mouseMoved(InputEvent event, float x, float y) {
//                saveButton.getStyle().imageUp = saveActive;
//                return true;
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                saveButton.getStyle().imageUp = saveInactive;
//            }
//        });


        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play(); // Riproduce il suono
                game.setScreen(new SettingsScreen(game, mainGameScreen));// Passa alla schermata delle impostazioni
            }
        });
//        settingsButton.addListener(new InputListener() {
//            @Override
//            public boolean mouseMoved(InputEvent event, float x, float y) {
//                settingsButton.getStyle().imageUp = settingsActive;
//                return true;
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                settingsButton.getStyle().imageUp = settingsInactive;
//            }
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                clickSound.play(); // Riproduce il suono
//                game.setScreen(new SettingsScreen(game));// Passa alla schermata delle impostazioni
//                return true;
//            }
//        });

        mainmenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play(); // Riproduce il suono
                game.setScreen(new MenuScreen(game)); // Passa al menu principale
            }
        });
//        mainmenuButton.addListener(new InputListener() {
//            @Override
//            public boolean mouseMoved(InputEvent event, float x, float y) {
//                mainmenuButton.getStyle().imageUp = mainmenuActive;
//                return true;
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                mainmenuButton.getStyle().imageUp = mainmenuInactive;
//            }
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                clickSound.play(); // Riproduce il suono
//                game.setScreen(new MenuScreen(game)); // Passa al menu principale
//                return true;
//            }
//        });

        // Layout pulsanti
        table.setFillParent(true);
        table.center();
        table.add(resumeButton).width(200).height(50).pad(10);
        table.row();
        table.add(saveButton).width(200).height(50).pad(10);
        table.row();
        table.add(settingsButton).width(200).height(50).pad(10);
        table.row();
        table.add(mainmenuButton).width(200).height(50).pad(10);
        table.row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // Disegna una copia statica della schermata del gioco
        batch.begin();
        mainGameScreen.renderStaticState(batch);
        batch.end();

        // Oscura leggermente lo schermo
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.9f, 0, 0, 0.3f)); // Rosso scuro molto lieve
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Disegna il menu di pausa
        stage.act(delta);
        stage.draw();
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
        // Libera le risorse dello stage
        stage.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        if (clickSound != null) {
            clickSound.dispose(); // Libera il suono
        }
    }
    private void loadMenuAssets(){
        this.clickSound = Utility.getAsset("menu/button-click.mp3", Sound.class);
        this.skin = Utility.getAsset("menu/ui-skin.json", Skin.class);
    }
}
