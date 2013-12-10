package com.me.strategy_game_libgdx.gamelogic.rail;

import com.badlogic.gdx.math.Vector2;

public class Rail {
    public Rail ref;
    public Vector2 position, step, vec;
    public float value, direction;
    public Rail(Vector2 position, Vector2 bziuuu, Rail ref) {
        this.position = position;
        this.step = bziuuu;
        this.ref = ref;
        this.value = 1f;
        this.vec = new Vector2();
    }
}
