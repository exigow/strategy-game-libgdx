package com.me.strategy_game_libgdx.gamelogic.structure.group;

import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.Order;
import com.me.strategy_game_libgdx.gamelogic.structure.Faction;
import com.me.strategy_game_libgdx.gamelogic.structure.Movable;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;

import java.util.ArrayList;

public class Group extends Movable {
    public ArrayList<Unit> list;

    private Order.OrderType order; // Rozkaz grupy.
    private Group orderTarget; // Obiekt zainteresowania zwiazany z rozkazem (np. atak, follow).

    private boolean marked, selected;
    public Faction faction;

    // Konstruktor.
    public Group() {
        super();
        list = new ArrayList<Unit>();
        setSize(128f); // Potrzebne Forerowi.

        selected = false;
        marked = false;
        faction = null;

        order = Order.OrderType.NULL;
        orderTarget = null;
    }

    // Ustala frakcje.
    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    // Dodaje do grupy jednostke.
    public void addToGroup(Unit selectable) {
        selectable.setGroup(this);
        list.add(selectable);
    }


    // Aktualizacja update grupy i wszystkich jednostek w grupie.
    public void update(float deltaTime) {
        // Pozycja.
        getActual().set(0, 0, 0);
        float size = list.size();
        for (Movable iter: list) {
            getActual().getPosition().add(iter.getActual().getPosition());
        }
        getActual().getPosition().div(size);

        // Update jednostek.
        for (Unit iter: list) {
            iter.moveUpdate(deltaTime);
            iter.update(deltaTime);
        }
    }

    // Order.
    public void setOrder(Order.OrderType order) {
        this.order = order;
    }
    public Order.OrderType getOrder() {
        return order;
    }

    // Marked.
    public void setAsSelected(boolean bool) {
        selected = bool;
    }
    public void setAsMarked(boolean bool) {
        marked = bool;
    }

    // Selected.
    public boolean isSelected() {
        return selected;
    }
    public boolean isMarked() {
        return marked;
    }

    // Order Target.
    public void setOrderTarget(Group orderTarget) {
        this.orderTarget = orderTarget;
    }
    public Group getOrderTarget() {
        return orderTarget;
    }

    public void sendOrderToUnits() {
        int i = 0;
        for (Unit unit: list) {
            unit.orderTarget = orderTarget.list.get(i);
            i += 1;
            i = i%orderTarget.list.size();
        }
    }
}
