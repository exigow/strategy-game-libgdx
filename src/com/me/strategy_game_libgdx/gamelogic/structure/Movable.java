package com.me.strategy_game_libgdx.gamelogic.structure;

import com.badlogic.gdx.math.Vector2;

public class Movable {
    private Stand actual, target;
    private float size;

    // Konstruktor.
    public Movable() {
        actual = new Stand();
        target = new Stand();
        size = 32f;
    }

    // Standy.
    public Stand getTarget() {
        return target;
    }
    public Stand getActual() {
        return actual;
    }

    // Size.
    public void setSize(float size) {
        this.size = size;
    }
    public float getSize() {
        return size;
    }
}
