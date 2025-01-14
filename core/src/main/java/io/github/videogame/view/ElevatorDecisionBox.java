package io.github.videogame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.videogame.controller.MapManager;
import io.github.videogame.model.Player;
import io.github.videogame.model.Utility;
import jdk.jshell.execution.Util;

public class ElevatorDecisionBox {
    private Stage stage;
    private Label label;
    private Table table;
    private boolean visible;
    BitmapFont font;
    Label.LabelStyle labelStyle;
    private String labelText;

    public ElevatorDecisionBox() {
       this.font = new BitmapFont(Gdx.files.internal("Font/font.fnt")); // Assicurati di avere un file di font
        this.labelStyle = new Label.LabelStyle(font, Color.WHITE);
        this.stage = new Stage(new ScreenViewport());
        this.label = new Label("", labelStyle);

        this.label.setAlignment(Align.center);
        this.label.setFontScale(0.5f);
        this.label.setWrap(true);
        this.label.setVisible(false); // Inizialmente non visibile
        this.table = new Table();

        Utility.loadAsset("UI/elevator-ui.png", Texture.class);
        table.setBackground(new TextureRegionDrawable(Utility.getAsset("UI/elevator-ui.png", Texture.class)));
        table.setSize(192, 62);
        table.add(label).size(170, 50).center();
        stage.addActor(table);
    }

    public void show() {
        String mapName = MapManager.getInstance().getCurrentMap().equals("Mappa-prova/atrio-mensa.tmx") ? "offices" : "lobby";
        label.setText("[E] Go to the " + mapName);
        table.setPosition(Player.getInstance().getX() + 550, Player.getInstance().getY() + 300);
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
                MapManager.getInstance().changeMap();
                hide();
            }
        }
    }

    public Stage getStage() {
        return stage;
    }
}
