package io.github.videogame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.videogame.controller.MapManager;
import io.github.videogame.model.Utility;
import jdk.jshell.execution.Util;

public class ElevatorDecisionBox {
    private Stage stage;
    private Label label;
    private boolean visible;
    BitmapFont font;
    Label.LabelStyle labelStyle;

    public ElevatorDecisionBox() {
       this.font = new BitmapFont(Gdx.files.internal("Font/font.fnt")); // Assicurati di avere un file di font
        this.labelStyle = new Label.LabelStyle(font, Color.WHITE);
        this.stage = new Stage(new ScreenViewport());
        this.label = new Label("Press E to go to the offices", labelStyle);
        this.label.setAlignment(Align.center);
        this.label.setVisible(false); // Inizialmente non visibile

        // Crea un tavolo per centrare il label
        Table table = new Table();
        table.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        //table.setFillParent(true);
        table.add(label).center();
        stage.addActor(table);
    }

    public void show() {
        label.setVisible(true);
        visible = true;
    }

    public void hide() {
        label.setVisible(false);
        visible = false;
    }

    public void render() {
        if (visible) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    public void handleInput() {
        if (visible) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                // Logica per salire
                MapManager.getInstance().loadNewMap();
                hide();
            }
        }
    }

    public Stage getStage() {
        return stage;
    }
}
