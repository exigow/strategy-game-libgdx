package com.me.strategy_game_libgdx.gamelogic.star;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.systems.Camera;

public class Star {
    public Vector2 position, move, drawPosition;
    public float depth;
    public Color color;

    public Star() {
        position = new Vector2();
        move = new Vector2(
            -1f + (float)Math.random() * 2f,
            -1f + (float)Math.random() * 2f
        );
        drawPosition = new Vector2();
        depth = -.5f + (float)Math.random();
        color = new Color(1, 1, 1, 1);

        color.r = .75f + (float)Math.random() * .25f;
        color.g = .75f +(float)Math.random() * .25f;
        color.b = .75f +(float)Math.random() * .25f;
    }

    public void update(Camera cam, float deltaTime) {
        position.x += move.x * deltaTime * 1f;
        position.y += move.y * deltaTime * 1f;

        drawPosition.x = position.x + ((cam.actual.x - position.x) * depth * (1 / cam.zoom) * 1.25f);
        drawPosition.y = position.y + ((cam.actual.y - position.y) * depth * (1 / cam.zoom) * 1.25f);
    }
}
