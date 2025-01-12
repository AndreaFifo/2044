package io.github.videogame.controller;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class CollisionDebugger {
    private ShapeRenderer shapeRenderer;

    public CollisionDebugger() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(List<Rectangle> rectangles, float x, float y, float width, float height) {
        shapeRenderer.setProjectionMatrix(MapManager.getInstance().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Disegna rettangoli di collisione
        shapeRenderer.setColor(Color.GRAY);
        for (Rectangle rect : rectangles) {
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }

        // Disegna l'hitbox del giocatore
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
