package com.me.strategy_game_libgdx.gamelogic.structure;

import com.badlogic.gdx.math.Vector2;

public class OldMovable {
    private Vector2 position, target;
    public float direction, targetDirection, size;
    private int priority;

    // Flocking.
    private OldMovable flockedTo;
    private boolean isFlockedBoolean;
    private Vector2 flockLocalPosition, flockWorldPosition;
    private Vector2 goToPosition, moveVector;
    private float flockInterpolation;
    public float fixPositionPercent;
    private boolean isMovingBoolean;

    // Konstruktor.
    public OldMovable() {
        isMovingBoolean = false; // Czy sie porusza. O update tego trzeba samemu zadbac.
        moveVector = new Vector2();
        position = new Vector2(); // Poz.
        target = new Vector2(); // Poz. w ktorej 'chce' byc (w efekcie koncowym).
        goToPosition = new Vector2(); // Miejsce do ktorego naprawde leci.
        direction = (float)Math.random() * 360f; // Kierunek zwrocenia.
        targetDirection = (float)Math.random() * 360f;
        size = 0f; // Wielkosc jednostki.
        priority = 0; // Priorytet jednostki/grupy. Brany pod uwage przy sortowaniu/formowaniu oddzialu.

        // Flocking.
        flockedTo = null; // Jednostki maja przypisane inne jednostki do ktorych chca sie dostac. Jedna jest wyjatkowa (head unit) ktora jako parent ma grupe)
        isFlockedBoolean = false; // Czy jest zflockowany.
        flockLocalPosition = new Vector2(); // Poz. lokalna.
        flockWorldPosition = new Vector2(); // Poz. world.
    }

    public void flockTo(OldMovable flockTarget, float localX, float localY) {
        this.isFlockedBoolean = true;
        this.flockedTo = flockTarget;
        this.flockLocalPosition.x = localX;
        this.flockLocalPosition.y = localY;
    }
    public boolean isFlocked() {
        return isFlockedBoolean;
    }
    public void setAsFlocked() {
        this.isFlockedBoolean = true;
    }
    public OldMovable getFlocked() {
        return flockedTo;
    }
    public Vector2 getFlockLocalPosition() {
        return flockLocalPosition;
    }
    public Vector2 getFlockWorldPosition() {
        return flockWorldPosition;
    }
    public void setFlockWorldPosition(Vector2 position) {
        flockWorldPosition.set(position);
    }
    public Vector2 getGoToPosition() {
        return goToPosition;
    }
    public void setGoToPosition(float x , float y) {
        goToPosition.set(x, y);
    }
    public void setFlockInterpolation(float value) {
        flockInterpolation = value;
    }
    public float getFlockInterpolation() {
        return flockInterpolation;
    }

    public boolean isMoving() {
        return isMovingBoolean;
    }
    public void setMoving(boolean yesOrNot) {
        isMovingBoolean = yesOrNot;
    }

    public Vector2 getMoveVector() {
        return moveVector;
    }
    public void setMoveVector(float x, float y) {
        moveVector.set(x, y);
    }



    // Get.
    // Position.
    public Vector2 getPosition() {
        return position;
    }
    // Target.
    public Vector2 getTarget() {
        return target;
    }
    // Direction.
    public float getDirection() {
        return direction;
    }
    // Target Direction.
    public float getTargetDirection() {
        return targetDirection;
    }
    // Size.
    public float getSize() {
        return size;
    }
    // Priority.
    public int getPriority() {
        return priority;
    }

    // Set.
    // Position.
    public void setPosition(Vector2 vec) {
        setPosition(vec.x, vec.y);
    }
    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }
    // Target.
    public void setTarget(Vector2 vec) {
        setTarget(vec.x, vec.y);
    }
    public void setTarget(float x, float y) {
        target.x = x;
        target.y = y;
    }
    // Direction.
    public void setDirection(float direction) {
        this.direction = direction;
    }
    // Target Direction.
    public void setTargetDirection(float targetDirection) {
        this.targetDirection = targetDirection;
    }
    // Size.
    public void setSize(float size) {
        this.size = size;
    }
    // Priority.
    public void setPriority(int val) {
        priority = val;
    }
}
