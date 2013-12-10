package com.me.strategy_game_libgdx.gamelogic.star;

import com.badlogic.gdx.graphics.Color;
import com.me.strategy_game_libgdx.gamelogic.systems.Camera;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;

import java.util.ArrayList;

public class StarManager {
    public static ArrayList<Star> listBelow;
    public static ArrayList<Star> listAbove;
    public static enum Level {ABOVE, BELOW}

    public static void create() {
        listBelow = new ArrayList<Star>();
        listAbove = new ArrayList<Star>();

        for (int i = 0; i < 512; i++) {
            addStar(-4096 + (float)Math.random()* 8192, -4096 + (float)Math.random()* 8192);
        }
    }

    public static void addStar(float x, float y) {
        Star star = new Star();
        star.position.set(x, y);
        if (star.depth < 0) {
            listAbove.add(star);
        } else {
            listBelow.add(star);
        }
    }

    public static void update(float deltaTime, Camera cam) {
        for (Star star: listBelow) {
            star.update(cam, deltaTime);
        }
        for (Star star: listAbove) {
            star.update(cam, deltaTime);
        }
    }

    public static void drawStars(Level level) {
        ArrayList<Star> whatList;
        switch (level) {
            case ABOVE: {
                whatList = listAbove;
                break;
            }
            case BELOW: {
                whatList = listBelow;
                break;
            }
            default: { whatList = listAbove; }
        }

        Draw.setBlendMode(Draw.BlendMode.ADD);
        Draw.getBatch().begin();
        for (Star star: whatList) {
            Draw.setColor(star.color);
            Draw.setAlpha(1 - Math.abs(star.depth * 2));
            Draw.sprite(Assets.sStar, star.drawPosition, (1f - star.depth), (1f - star.depth), 0f);
        }
        Draw.getBatch().end();
        Draw.setBlendMode(Draw.BlendMode.NORMAL);

        // Fix color/alpha po rysowaniu.
        Draw.setColor(Color.WHITE);
        Draw.setAlpha(1f);
    }
}
