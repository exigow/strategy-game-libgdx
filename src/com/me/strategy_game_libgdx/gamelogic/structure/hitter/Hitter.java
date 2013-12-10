package com.me.strategy_game_libgdx.gamelogic.structure.hitter;

import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.structure.Stand;
import com.me.strategy_game_libgdx.gamelogic.structure.group.Group;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

public class Hitter {
    public Stand postion;
    public float life;
    private boolean alive;
    public Unit target;


    public Vector2 moveVec;
    public float spd;

    public Hitter() {
        postion = new Stand();
        moveVec = new Vector2();
        life = 1f;
        alive = true;

        spd = 1280f + (float)Math.random() * 128f;
    }

    public void update(float deltaTime) {
        moveVec.x = MathUtilities.ldx(deltaTime * spd, postion.getDirection());
        moveVec.y = MathUtilities.ldy(deltaTime * spd, postion.getDirection());

        postion.getPosition().x += moveVec.x;
        postion.getPosition().y += moveVec.y;

        life -= 1f * deltaTime;
        if (life < 0f) {
            killMe();
        }

        float distance = MathUtilities.pointDistance(postion.getPosition(), target.getActual().getPosition());
        if (distance < 32f) {
            killMe();
        }
    }
    public void draw() {
        //Draw.sprite(Assets.sMissile, position, 1f, 1f, direction);
        Draw.setBlendMode(Draw.BlendMode.ADD);
        Draw.sprite(Assets.sBullet, postion.getPosition(), 1f, 1f, postion.getDirection());
        Draw.setBlendMode(Draw.BlendMode.NORMAL);
    }


    public void killMe() {
        alive = false;
    }
    public boolean isAlive() {
        return alive;
    }
}



