package io.github.videogame.view.screens;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.videogame.model.Gioco;

public class MenuPausa implements Screen {
    private Stage stage;
    private Gioco game;
    private MainGameScreen mainGameScreen; // Passa solo l'istanza del gioco
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private ImageButton resumeButton, saveButton, settingsButton,mainmenuButton;
    private Sound clickSound;

    public MenuPausa(Gioco game, MainGameScreen mainGameScreen) {
        this.game = game;
        this.mainGameScreen = mainGameScreen;
        this.batch=game.getBatch();
    }

    @Override
    public void show() {
        // Carica il suono del clic
        clickSound = Gdx.audio.newSound(Gdx.files.internal("menu/button-click.mp3"));
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);

        // Configurazione dei pulsanti con immagini per stato attivo e inattivo
        TextureRegionDrawable resumeInactive = new TextureRegionDrawable(new Texture("menu/resume.png"));
        TextureRegionDrawable resumeActive = new TextureRegionDrawable(new Texture("menu/resume-active.png"));
        TextureRegionDrawable saveInactive = new TextureRegionDrawable(new Texture("menu/save.png"));
        TextureRegionDrawable saveActive = new TextureRegionDrawable(new Texture("menu/save-active.png"));
        TextureRegionDrawable settingsInactive = new TextureRegionDrawable(new Texture("menu/settings_pausa_inactive.png"));
        TextureRegionDrawable settingsActive = new TextureRegionDrawable(new Texture("menu/settings_pausa_active.png"));
        TextureRegionDrawable mainmenuInactive= new TextureRegionDrawable(new Texture("menu/main-menu.png"));
        TextureRegionDrawable mainmenuActive = new TextureRegionDrawable(new Texture("menu/main-menu-active.png"));

        // Configura i pulsanti
        resumeButton = new ImageButton(resumeInactive, resumeActive);
        saveButton = new ImageButton(saveInactive, saveActive);
        settingsButton = new ImageButton(settingsInactive, settingsActive);

        mainmenuButton=new ImageButton(mainmenuInactive,mainmenuActive);

        // Listener dei pulsanti
        resumeButton.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                resumeButton.getStyle().imageUp = resumeActive;
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                resumeButton.getStyle().imageUp = resumeInactive;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play(); // Riproduce il suono
                game.setScreen(mainGameScreen); // Torna al MainGameScreen senza alterarne lo stato
                return true;
            }
        });

        saveButton.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                saveButton.getStyle().imageUp = saveActive;
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                saveButton.getStyle().imageUp = saveInactive;
            }
        });


        settingsButton.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                settingsButton.getStyle().imageUp = settingsActive;
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                settingsButton.getStyle().imageUp = settingsInactive;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play(); // Riproduce il suono
                // Passa alla schermata delle impostazioni
                game.setScreen(new Settings(game, new Texture("menu/bg-menu.png"), Gdx.audio.newSound(Gdx.files.internal("menu/button-click.mp3")),MenuPausa.this));
                return true;
            }
        });

        mainmenuButton.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                mainmenuButton.getStyle().imageUp = mainmenuActive;
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                mainmenuButton.getStyle().imageUp = mainmenuInactive;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play(); // Riproduce il suono
                game.setScreen(new MenuScreen(game)); // Passa al menu principale
                return true;
            }
        });


        // Layout pulsanti
        Table table = new Table();
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
}
