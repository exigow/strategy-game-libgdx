package com.me.strategy_game_libgdx.gamelogic.structure.device;


import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

public class Device {
    private Vector2 localPosition, worldPosition;
    private float direction, targetDirection;
    private Unit owner;
    public float directionFixMultipler;

    public Device(Unit owner, float x, float y) {
        this.owner = owner;
        localPosition = new Vector2(x, y);
        worldPosition = new Vector2();
        direction = 0f;
        targetDirection = 0f;
        directionFixMultipler = 1f;
    }

    public void update(float deltaTime) {
        worldPosition.set(MathUtilities.positionPlusTranslated(owner.getActual().getPosition(), localPosition, owner.getActual().getDirection() - 90f));
        direction += MathUtilities.angdiff(targetDirection, direction) * deltaTime * directionFixMultipler;
    }

    public Vector2 getWorldPosition() {
        return worldPosition;
    }

    public void setTargetDirection(float direction) {
        targetDirection = direction;
    }

    public float getDirection() {
        return direction;
    }

    public float computeDirectionToUnit(Unit targetUnit) {
        return 180 + MathUtilities.pointDirection(getWorldPosition(), targetUnit.getActual().getPosition());
    }
}
