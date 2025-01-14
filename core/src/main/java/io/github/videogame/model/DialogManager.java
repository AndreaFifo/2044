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
    private Label avatarLabel;
    private Image avatar;

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
        dialogLabel.setFontScale(0.65F);
        avatarLabel = new Label("Joseph", labelStyle);
        avatarLabel.setFontScale(0.7F);

        dialogBox.setBackground(dialogBoxBG);
        dialogBox.setSize(768, 222);
        dialogBox.setPosition((float) (Gdx.graphics.getWidth() / 2) - 384, 30);

        Utility.loadAsset("UI/joseph.png", Texture.class);
        Utility.loadAsset("UI/bryan.png", Texture.class);
        Utility.loadAsset("UI/capo-polizia.png", Texture.class);
        Utility.loadAsset("UI/polizia.png", Texture.class);
        Utility.loadAsset("UI/ryan.png", Texture.class);
        Utility.loadAsset("UI/aurora.png", Texture.class);
        Utility.loadAsset("UI/anastasia.png", Texture.class);
        avatar = new Image(Utility.getAsset("UI/joseph.png", Texture.class));
        avatar.setSize(128, 128);

        //dialogBox.setDebug(true);
        dialogBox.add(avatarLabel).center();
        dialogBox.row().padTop(20);
        dialogBox.add(avatar).size(128, 128).expand(true, false).left().padLeft(65);
        dialogBox.add(dialogLabel).width(460).height(120).top().center().padLeft(10).padRight(45);
        stage.addActor(dialogBox);
    }

    public void setDialog(String text) {
        if(text.isEmpty()) {
            dialogLabel.setText("");
            return;
        }

        if(text.contains(":")) {
            String[] finalText = text.split(": ");
            dialogLabel.setText(finalText[1]);

            setAvatarTexture(finalText[0]);
            return;
        }

        dialogLabel.setText(text);
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

    public void setAvatarTexture(String text){
        String texturePath = "UI/joseph.png";
        String labelText = "Joseph";

        if(text.toLowerCase().contains("joseph forrest")) {
            texturePath = "UI/joseph.png";
            labelText = "Joseph";
        }
        else if(text.toLowerCase().contains("ryan pierce")){
            texturePath = "UI/ryan.png";
            labelText = "Ryan";
        }
        else if(text.toLowerCase().contains("bryan cooper")){
            texturePath = "UI/bryan.png";
            labelText = "Bryan";
        }
        else if(text.toLowerCase().contains("police chief")){
            texturePath = "UI/capo-polizia.png";
            labelText = "Chief";
        }
        else if(text.toLowerCase().contains("officer")){
            texturePath = "UI/polizia.png";
            labelText = "Officer";
        } else if(text.toLowerCase().contains("aurora")){
            texturePath = "UI/aurora.png";
            labelText = "Aurora";
        } else if(text.toLowerCase().contains("anastasia")){
            texturePath = "UI/anastasia.png";
            labelText = "Anastasia";
        }

        avatar.setDrawable(new TextureRegionDrawable(Utility.getAsset(texturePath, Texture.class)));
        avatarLabel.setText(labelText);
    }

    public Stage getStage() {
        return stage;
    }
}
