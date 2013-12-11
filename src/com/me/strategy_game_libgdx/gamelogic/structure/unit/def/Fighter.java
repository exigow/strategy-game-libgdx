package com.me.strategy_game_libgdx.gamelogic.structure.unit.def;

import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.Order;
import com.me.strategy_game_libgdx.gamelogic.structure.Stand;
import com.me.strategy_game_libgdx.gamelogic.structure.device.Device;
import com.me.strategy_game_libgdx.gamelogic.structure.hitter.Hitter;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.systems.Unit_DynamicMoveSystem;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

public class Fighter extends Unit_DynamicMoveSystem {
    private float shootTimer;
    private boolean shootAble;

    private Stand maneuver;
    public Fighter() {
        setSize(24f);
        //maneuverPosition = new Vector2(this.getActual().getPosition());
        maneuver = new Stand();
        shootTimer = 0f;
    }

    public void draw() {
        Draw.sprite(Assets.sFighter, getActual().getPosition(), 1f, 1f, getActual().getDirection());
        Draw.sprite(Assets.sCross, maneuver.getPosition(), 1f, 1f, 0f);
    }

    public void update(float deltaTime) {
        shootTimer += deltaTime;
        if (getGroup().getOrder() == Order.OrderType.ATTACK) {
            computeNewManeuverPosition();
            goTo.set(maneuver.getPosition(), 0f);
            float dirto = MathUtilities.pointDirection(getActual().getPosition(), orderTarget.getActual().getPosition()) + 180;
            float sdiff = Math.abs(MathUtilities.angdiff(getActual().getDirection(), dirto));
            if (sdiff < 15f) {
                if (shootTimer > .125f) {
                    shootTimer = 0f;
                    Hitter a = new Hitter();
                    a.postion.set(getActual().getPosition(), getActual().getDirection());
                    a.target = orderTarget;
                    getCollectorLink().getHitterList().add(a);
                }
            }
        } else {
            goTo.set(getTarget());
        }
    }

    private void computeNewManeuverPosition() {
        float length = MathUtilities.pointDistance(getActual().getPosition(), orderTarget.getActual().getPosition());
        if (length > 256f) {
            // do obiektu
            maneuver.setDirection(MathUtilities.pointDirection(getActual().getPosition(), orderTarget.getActual().getPosition()) + 180);
            maneuver.setPosition(orderTarget.getActual().getPosition());
        } else {
            // do maneuver
            maneuver.setPosition(
                getActual().getPosition().x + MathUtilities.ldx(1024f, maneuver.getDirection()),
                getActual().getPosition().y + MathUtilities.ldy(1024f, maneuver.getDirection())
            );
        }
    }
}

