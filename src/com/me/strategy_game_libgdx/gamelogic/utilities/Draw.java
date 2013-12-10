package com.me.strategy_game_libgdx.gamelogic.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;

//
// Klasa ze pelna statycznych metod zwiazanych z rysowaniem.
//

public class Draw {
    public static enum BlendMode {ADD, NORMAL}
    public static SpriteBatch batch;
    public static ShapeRenderer shape;
    private static float distance, direction;
    public static Color actualColor = new Color();
    public static BitmapFont actualFont;

    // Batch.
    public static void setBatch(SpriteBatch b) {
        batch = b;
    }
    public static SpriteBatch getBatch() {
        return batch;
    }

    // Shape.
    public static void setShape(ShapeRenderer s) {
        shape = s;
    }
    public static ShapeRenderer getShape() {
        return shape;
    }

    // Color.
    public static void setColor(Color color) {
        actualColor.r = color.r;
        actualColor.g = color.g;
        actualColor.b = color.b;
    }
    public static Color getColor() {
        return actualColor;
    }

    // Alpha.
    public static void setAlpha(float alpha) {
        actualColor.a = alpha;
    }
    public static float getAlpha() {
        return actualColor.a;
    }

    // Sprite.
    public static void sprite(Sprite spr, Vector2 position, float scalex, float scaley, float direction) {
        Draw.sprite(spr, position.x, position.y, scalex, scaley, direction);
    }
    public static void sprite(Sprite spr, float x, float y, float scalex, float scaley, float direction) {
        spr.setColor(actualColor);
        spr.setPosition(x, y);
        spr.setScale(scalex, scaley);
        spr.setRotation(direction);
        spr.translate(-spr.getOriginX(), -spr.getOriginY());
        spr.draw(batch);
    }

    // Texture Region.
    public static void textureRegion(TextureRegion region, float x, float y, float w, float h) {
        batch.draw(region, x, y, w, h);
    }

    // Font.
    public static void setFont(BitmapFont font) {
        actualFont = font;
    }
    // Text.
    public static void text(String string, Vector2 position) {
        Draw.text(string, position.x, position.y);
    }
    public static void text(String string, float x, float y) {
        actualFont.setColor(actualColor);
        actualFont.draw(batch, string, x, y);
    }

    // Clear.
    public static void clear(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Line.
    public static void line(Vector2 from, Vector2 to, float width) {
        Draw.line(from.x, from.y, to.x, to.y, width);
    }
    public static void line(float fromX, float fromY, float toX, float toY, float width) {
        distance = MathUtilities.pointDistance(fromX, fromY, toX, toY);
        direction = MathUtilities.pointDirection(fromX, fromY, toX, toY) + 180;
        Draw.sprite(Assets.sLine, fromX, fromY, distance, width, direction);
    }

    // Blend Mode.
    public static void setBlendMode(BlendMode mode) {
        switch (mode) {
            case ADD: {
                batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                break;
            }
            case NORMAL: {
                batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                break;
            }
        }

    }
}
