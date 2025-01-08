package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DialogManager {
    private Stage stage;
    private Label dialogLabel;

    public DialogManager() {
        stage = new Stage(new ScreenViewport());

        // Carica il font e crea uno stile per il testo
        BitmapFont font = new BitmapFont(Gdx.files.internal("Font/font.fnt")); // Assicurati di avere un file di font
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLUE);

        // Crea il Label per il dialogo
        dialogLabel = new Label("", labelStyle);
        dialogLabel.setWrap(true); // Permette di andare a capo se il testo è lungo

        // Configura il layout
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().pad(20); // Posiziona il dialogo in basso
        table.add(dialogLabel).width(Gdx.graphics.getWidth() * 0.8f); // Limita la larghezza del testo

        stage.addActor(table);
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
