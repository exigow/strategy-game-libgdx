package com.me.strategy_game_libgdx.gamelogic.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Camera {
    public Vector2 target, actual, effect, vec;
    public float zoomTarget, zoom;
    public float distToTarget;
    private boolean fixedPosition = false;
    private String name = "<default>";
    public float w, h;

    public Camera(String name, float x, float y, float z, float w, float h) {
        this.name = name;
        target = new Vector2(x, y);
        actual = new Vector2(x, y);
        effect = new Vector2(x, y);
        vec = new Vector2();
        zoomTarget = z;
        zoom = z;

        this.w = w;
        this.h = h;
    }

    public void doStep(float delta) {
        vec.x = (target.x - actual.x) * delta;
        vec.y = (target.y - actual.y) * delta;
        actual.x += vec.x;
        actual.y += vec.y;
        zoomTarget = (float)Math.max(1f, zoomTarget);
        zoomTarget = (float)Math.min(4f, zoomTarget);
        zoom += (zoomTarget - zoom) * delta;
        distToTarget = actual.dst(target);

        // Minimalizacja efektu rozmycia kamery. Jesli roznica odl. jest mala, zaokraglanie pozycji.
        boolean close = (Math.round(distToTarget) < 1);
        if (!(close && fixedPosition)) {
            effect.set(actual);
        } else {
            effect.x = Math.round(actual.x);
            effect.y = Math.round(actual.y);
        }
    }

    public void applyOrthographicCamera(OrthographicCamera cam) {
        cam.position.x = effect.x;
        cam.position.y = effect.y;
        cam.zoom = zoom;
    }

    public String getInfo() {
        return "camera: " + name + " pos: (" +  effect.x + ", " + effect.y + ") zoom: " + zoom + "";
    }
}
