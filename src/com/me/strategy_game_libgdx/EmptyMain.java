package com.me.strategy_game_libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class EmptyMain implements ApplicationListener {
    public void create() {
    }
    public void dispose() {
    }
    public void render() {
        Gdx.gl.glClearColor(1f, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
    public void resize(int width, int height) {
    }
    public void pause() {
    }
    public void resume() {
    }
}
