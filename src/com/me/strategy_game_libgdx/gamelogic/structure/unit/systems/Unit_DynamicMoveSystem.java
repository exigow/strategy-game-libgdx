package com.me.strategy_game_libgdx.gamelogic.structure.unit.systems;


import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

public class Unit_DynamicMoveSystem extends Unit {
    private float flySpeed, flyDirection;
    private float
        flySpeedMax = 4f,
        flyAcceleration = 8f;

    public void moveUpdate(float deltaTime) {
        float directionToTarget = MathUtilities.pointDirection(goTo.getPosition(), getActual().getPosition());
        float distanceToTarget = MathUtilities.pointDistance(goTo.getPosition(), getActual().getPosition());
        float flySpeedTarget = Math.min(distanceToTarget * .025f, flySpeedMax);
        flySpeed += (flySpeedTarget - flySpeed) * deltaTime * flyAcceleration;
        float angdiff = MathUtilities.angdiff(directionToTarget, flyDirection) * 2f;
        flyDirection += angdiff * deltaTime;
        float fixPositionPercent = (float)Math.pow(1 - Math.min(MathUtilities.pointDistance(getActual().getPosition(), getTarget().getPosition()), 128f) / 128f, 16f);
        flyDirection -= MathUtilities.angdiff(flyDirection, getTarget().getDirection()) * fixPositionPercent;
        float vx = MathUtilities.lerp(MathUtilities.ldx(flySpeed, flyDirection), (getTarget().getPosition().x - getActual().getPosition().x) * deltaTime * 4f, fixPositionPercent);
        float vy = MathUtilities.lerp(MathUtilities.ldy(flySpeed, flyDirection), (getTarget().getPosition().y - getActual().getPosition().y) * deltaTime * 4f, fixPositionPercent);

        getActual().set(getActual().getPosition().x + vx, getActual().getPosition().y + vy, flyDirection);
    }

    public void moveUpdateConfig(float flySpeedMax, float flyAcceleration) {
        this.flySpeedMax = flySpeedMax;
        this.flyAcceleration = flyAcceleration;
    }
}
