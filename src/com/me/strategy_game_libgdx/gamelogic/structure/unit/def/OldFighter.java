package com.me.strategy_game_libgdx.gamelogic.structure.unit.def;

import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;

public class OldFighter extends Unit {
    float flySpeed, flyDirection, flySpeedTarget;
    /*float directionToTarget, distanceToTarget;
    public float angdiff;
    private Rail lastRail;
    private float shootTimer;
    private boolean shootAble;*/

    public OldFighter() {
        setSize(24f);
        //setPriority(25);

        /*flySpeed = 0f;
        flyDirection = (float)Math.random() * 360f;

        shootTimer = (float)Math.random() * 1f;*/

        // Rail sys.
        /*Rail tempRail = new Rail(new Vector2(getPosition()), new Vector2(), null);
        tempRail.ref = tempRail; // Referencja do samego siebie.
        RailManager.list.add(tempRail);
        lastRail = new Rail(new Vector2(getPosition()), new Vector2(), tempRail);
        RailManager.list.add(lastRail);*/
    }

    /*public void update(float deltaTime) {
        // Rail sys.
        lastRail.position.set(getPosition());
        if (MathUtilities.pointDistance(lastRail.ref.position, getPosition()) > 48f) {
            Rail ref = new Rail(
                new Vector2(getPosition()),
                new Vector2(-.025f + (float)Math.random() * .05f, -.025f + (float)Math.random() * .05f), lastRail);
            RailManager.list.add(ref);
            lastRail = ref;
        }


        shootAble = false;
        if (getGroup().getOrder() == Order.OrderType.ATTACK) {
            //Group target = getGroup().getOrderTarget();
            if (Math.abs(MathUtilities.angdiff(getDirection(), MathUtilities.pointDirection(getPosition(), orderTarget.getPosition()) + 180)) < 5f) {
                shootAble = true;
            }
            setTarget(orderTarget.getPosition().x, orderTarget.getPosition().y);
        }

        shootTimer += deltaTime;
        if (shootTimer > .25f && shootAble) {
            shootTimer = 0f;
            //System.out.println("BANG!");

            Hitter a = new Hitter();
            a.position.set(getPosition());
            a.direction = getDirection() + (-1f + (float)Math.random() * 2f) * 5f;
            getCollectorLink().getHitterList().add(a);
        }
    }*/

    /*
    public void draw() {
        // Fighter.
        Draw.sprite(Assets.sFighter, getPosition(), 1f, 1f, getDirection());

        // Silnik.
        Draw.setBlendMode(Draw.BlendMode.ADD);
        Draw.setAlpha((float)Math.pow(Math.min(flySpeed, 1), 4));
        Draw.sprite(Assets.sFighterThruster, getPosition(), 1f, 1f, getDirection());
        Draw.setAlpha(1f);
        Draw.setBlendMode(Draw.BlendMode.NORMAL);
    }*/
/*
    public void moveUpdate(float deltaTime) {
        // FlockPosition local->world.
        setFlockWorldPosition(MathUtilities.positionPlusTranslated(getFlocked().getPosition(), getFlockLocalPosition(), getFlocked().getDirection()));

        // Ustalanie interpolacji flock-systemu.
        setFlockInterpolation(Math.min(MathUtilities.pointDistance(getPosition(), getTarget()), 256f) / 256f);
        MathUtilities.lerpVectors(getGoToPosition(), getTarget(), getFlockWorldPosition(), getFlockInterpolation());

        // Mechanika przemieszczania (cel: goToPosition)
        directionToTarget = MathUtilities.pointDirection(getGoToPosition(), getPosition());
        distanceToTarget = MathUtilities.pointDistance(getGoToPosition(), getPosition());

        flySpeedTarget = Math.min(distanceToTarget * .025f, 4f);
        flySpeed += (flySpeedTarget - flySpeed) * deltaTime * 8f;

        angdiff = MathUtilities.angdiff(directionToTarget, flyDirection) * 2f;

        flyDirection += angdiff * deltaTime;

        // Fix pozycji.
        fixPositionPercent = (float)Math.pow(1 - Math.min(MathUtilities.pointDistance(getPosition(), getTarget()), 128f) / 128f, 16f);
        flyDirection -= MathUtilities.angdiff(flyDirection, targetDirection) * fixPositionPercent;
        setMoveVector(
                MathUtilities.lerp(MathUtilities.ldx(flySpeed, flyDirection), (getTarget().x - getPosition().x) * deltaTime * 4f, fixPositionPercent),
                MathUtilities.lerp(MathUtilities.ldy(flySpeed, flyDirection), (getTarget().y - getPosition().y) * deltaTime * 4f, fixPositionPercent)
        );

        // Okreslanie stanu czy sie przemieszcza.
        if (getMoveVector().len() > .125f) {
            setMoving(true);
        } else {
            setMoving(false);
        }

        // Dodawanie wektora przemieszczenia.
        getPosition().add(getMoveVector());
        // Ustalenie direction.
        setDirection(flyDirection);
    }*/
}

