package io.github.videogame.view.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class MySlider extends Slider {
    public MySlider(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super(min, max, stepSize, vertical, skin);
    }

    @Override
    public float getPrefWidth() {
        return 150;
    }

    @Override
    public float getPrefHeight() {
        return 21;
    }
}
