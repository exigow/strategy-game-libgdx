package com.me.strategy_game_libgdx.gamelogic.structure;

import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class Stand {
    private Vector2 position;
    private float direction;

    public Stand() {
        this(0, 0, 0);
    }
    /*public Stand(Stand from) {
        this.set(from);
    }*/
    public Stand(float x, float y, float dir) {
        position = new Vector2(x, y);
        direction = dir;
    }


    public void set(Stand from) {
        this.position.set(from.position);
        this.direction = from.direction;
    }
    public void set(Vector2 position, float direction) {
        this.position.set(position);
        this.direction = direction;
    }
    public void set(float x, float y, float direction) {
        position.x = x;
        position.y = y;
        this.direction = direction;
    }


    // Position.
    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }
    public Vector2 getPosition() {
        return position;
    }


    // Direction.
    public void setDirection(float direction) {
        this.direction = direction;
    }
    public float getDirection() {
        return direction;
    }
}
