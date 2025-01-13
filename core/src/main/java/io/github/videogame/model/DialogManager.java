package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DialogManager {
    private Stage stage;
    private Label dialogLabel;

    public DialogManager() {
        stage = new Stage();

        //Spostare i dialoghi in un file
        // Carica il font e crea uno stile per il testo
        BitmapFont font = new BitmapFont(Gdx.files.internal("Font/font.fnt")); // Assicurati di avere un file di font
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        Table dialogBox = new Table();
        TextureRegionDrawable dialogBoxBG = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("UI/dialog-box.png"))));
        dialogLabel = new Label("Press [SPACE] to start the dialog", labelStyle);
        dialogLabel.setWrap(true);
        dialogLabel.setFontScale(0.7F);
        Label avatarLabel = new Label("Joseph", labelStyle);
        avatarLabel.setFontScale(0.7F);

        dialogBox.setBackground(dialogBoxBG);
        dialogBox.setSize(768, 222);
        dialogBox.setPosition((float) (Gdx.graphics.getWidth() / 2) - 384, 30);

        Image avatar = new Image(new Texture(Gdx.files.internal("UI/joseph.png")));
        avatar.setSize(128, 128);

        //dialogBox.setDebug(true);
        dialogBox.add(avatarLabel).center();
        dialogBox.row().padTop(20);
        dialogBox.add(avatar).size(128, 128).expand(true, false).left().padLeft(65);
        dialogBox.add(dialogLabel).width(460).height(120).top().center().padLeft(10).padRight(45);
        stage.addActor(dialogBox);
    }

    public void setDialog(String text) {
        dialogLabel.setText(text);
    }

    public void setDialog(String[] dialogues) {
        StringBuilder dialogText = new StringBuilder();
        for (String line : dialogues) {
            dialogText.append(line).append("\n"); // Aggiungi ogni linea con un salto di riga
        }
        dialogLabel.setText(dialogText.toString()); // Imposta il testo completo
    }

    public void draw() {
        // Aggiorna lo stato degli attori
        stage.act();
        // Disegna gli attori
        stage.draw();
    }

    public void hide() {
        dialogLabel.setText(""); // Svuota il testo per nascondere il dialogo
    }

    public Stage getStage() {
        return stage;
    }
}
