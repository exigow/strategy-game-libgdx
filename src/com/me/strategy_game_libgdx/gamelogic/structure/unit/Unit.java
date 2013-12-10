package com.me.strategy_game_libgdx.gamelogic.structure.unit;

import com.me.strategy_game_libgdx.gamelogic.structure.Stand;
import com.me.strategy_game_libgdx.gamelogic.structure.group.Group;
import com.me.strategy_game_libgdx.gamelogic.structure.Movable;
import com.me.strategy_game_libgdx.gamelogic.systems.Collector;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

public class Unit extends Movable {
    private Group group; // Grupa do ktorej nalezy jednostka.
    private Collector collectorLink;
    public Unit orderTarget;
    public Stand goTo;

    // Konstruktor.
    public Unit() {
        super();
        group = null;
        collectorLink = null;
        goTo = new Stand();
    }

    // Get/Set.
    public void setGroup(Group group) {
        this.group = group;
    }
    public Group getGroup() {
        return group;
    }
    public void setCollectorLink(Collector collector) {
        collectorLink = collector;
    }
    public Collector getCollectorLink() {
        return collectorLink;
    }


    // OVERRIDE
    public void update(float deltaTime) {
        goTo.set(getTarget());
    }
    // OVERRIDE
    public void draw() {
    }
    // OVERRIDE
    // To jak sie przemieszcza.
    public void moveUpdate(float deltaTime) {
        // Default update (jesli nie dojdzie do override).
        getActual().getPosition().x += (goTo.getPosition().x - getActual().getPosition().x) * deltaTime;
        getActual().getPosition().y += (goTo.getPosition().y - getActual().getPosition().y) * deltaTime;
        getActual().setDirection(getActual().getDirection() - MathUtilities.angdiff(getActual().getDirection(), goTo.getDirection()) * deltaTime);
    }
}
