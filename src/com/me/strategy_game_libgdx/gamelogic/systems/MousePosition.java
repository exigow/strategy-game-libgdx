package com.me.strategy_game_libgdx.gamelogic.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MousePosition {
    public static Vector2 world2, screen;
    public static Vector3 world3;

    public static void init() {
        world2 = new Vector2();
        world3 = new Vector3();
        screen = new Vector2();
    }

    public static void update(OrthographicCamera cam, float x, float y, int windowW, int windowH) {
        screen.set(x, windowH - y);

        world3.x = x;
        world3.y = y;
        cam.unproject(world3, 0, 0, windowW, windowH);

        world2.x = world3.x;
        world2.y = world3.y;
    }
}
