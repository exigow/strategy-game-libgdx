package com.me.strategy_game_libgdx.gamelogic.systems.controller;

import com.me.strategy_game_libgdx.gamelogic.systems.Camera;
import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.Order;
import com.me.strategy_game_libgdx.gamelogic.structure.Faction;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.structure.group.Group;
import com.me.strategy_game_libgdx.gamelogic.systems.Former;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;
import com.me.strategy_game_libgdx.gamelogic.structure.Movable;

import java.util.*;

public class Controller {
    public List<Group> markedList, selectedList;
    public Vector2 pickStart, pickEnd, selectStart, selectEnd;
    public float length;
    private Camera camera;

    public Faction controlledFaction;

    public Group pickGroup;
    public boolean pickGroupFounded;

    public Order.OrderType orderType;
    public Group orderTarget;

    public Vector2 mouseWorld;

    public boolean isSelecting, doSelection;

    public Controller(Camera camera, Faction controlledFaction) {
        this.camera = camera;

        orderType = Order.OrderType.NULL;
        orderTarget = null;

        pickStart = new Vector2();
        pickEnd = new Vector2();
        selectStart = new Vector2();
        selectEnd = new Vector2();
        mouseWorld = new Vector2();

        length = 0f;
        pickGroup = null;
        pickGroupFounded = false;

        markedList = new ArrayList<Group>();
        selectedList = new ArrayList<Group>();
        this.controlledFaction = controlledFaction;
    }

    public void compute() {
        selectStart.set(pickStart);
        selectEnd.set(pickEnd);

        // Zamiana wymiarow (ostatecznie).
        if (pickStart.x > pickEnd.x) {
            selectStart.x = pickEnd.x;
            selectEnd.x = pickStart.x;
        }
        if (pickStart.y > pickEnd.y) {
            selectStart.y = pickEnd.y;
            selectEnd.y = pickStart.y;
        }

        addSelectBorder((camera.zoom) * 4f);

        length = MathUtilities.pointDistance(selectStart.x, selectStart.y, selectEnd.x, selectEnd.y);

        if (length > 32f) {
            doSelection = true;
        }

        if (doSelection) {
            isSelecting = true;
            pickGroup = null;
            pickGroupFounded = false;
        } else {
            isSelecting = false;
        }
    }

    public void updateMousePosition(Vector2 mouseWorld) {
        this.mouseWorld.set(mouseWorld);
    }

    private void addSelectBorder(float size) {
        selectStart.add(-size, -size);
        selectEnd.add(size, size);
    }

    public void findCandidates(List<Group> sourceList) {
        markedList.clear();
        //boolean oneOf;
        Unit ref;
        float dist;
        for (Group iter: sourceList) {
            //oneOf = false;
            iter.setAsMarked(false);
            for (int i = 0; i < iter.list.size(); i++) {
                ref = iter.list.get(i);
                dist = MathUtilities.pointRectDistance(ref.getActual().getPosition(), selectStart, selectEnd);
                if (dist < ref.getSize()) {
                    if (iter.faction != controlledFaction) {
                    //oneOf = true;
                        break;
                    }
                    iter.setAsMarked(true);
                }
            }
            if (iter.isMarked()) {
                markedList.add(iter);
            }
        }
    }

    public void findPick(List<Group> sourceList, Vector2 pos) {
        pickGroupFounded = false;
        Unit unit;
        for (Group iter: sourceList) {
            for (int i = 0; i < iter.list.size(); i++) {
                unit = iter.list.get(i);
                if (MathUtilities.pointDistance(pos.x, pos.y, unit.getActual().getPosition().x, unit.getActual().getPosition().y) < unit.getSize() * 2f) {
                    pickGroup = unit.getGroup();
                    pickGroupFounded = true;
                    break;
                }
            }
            if (pickGroupFounded) {
                break;
            }
        }
    }

    public void castToSelected() {
        for (Group g: selectedList) {
            g.setAsSelected(false);
        }
        selectedList.clear();
        if (pickGroupFounded && !isSelecting) {
            selectedList.add(pickGroup);
        } else {
            selectedList.addAll(markedList);
        }
        for (Group g: markedList) {
            g.setAsMarked(false);
        }
        markedList.clear();

        for (Group g: selectedList) {
            g.setAsSelected(true);
        }
    }

    public void doAction() {
        switch (orderType) {
            case MOVE: {
                orderTarget = null;

                // Liczenie sredniej pozycji zaznaczonych grup.
                Vector2 center = new Vector2(0, 0);
                int count = 0;
                for (Group iter: selectedList) {
                    center.add(iter.getActual().getPosition());
                    count++;
                }
                center.div(count);

                // Ustalanie kierunku do celu (na podstawie obliczonej sredniej pozycji)
                float direction = MathUtilities.pointDirection(mouseWorld.x, mouseWorld.y, center.x, center.y);

                // Formowanie.
                Former.form(mouseWorld.x, mouseWorld.y, direction, Former.Formation.ECHELON, (ArrayList<Movable>) ((ArrayList<?>) selectedList));
                for (Group group: selectedList) {
                    Former.form(group.getTarget().getPosition().x, group.getTarget().getPosition().y, direction, Former.Formation.ECHELON, (ArrayList<Movable>) ((ArrayList<?>) group.list));
                }
                break;
            }
            case ATTACK: {
                orderTarget = pickGroup;
                for (Group group: selectedList) {
                    group.setOrderTarget(orderTarget);
                    group.sendOrderToUnits();
                }
            }
        }
        for (Group g: selectedList) {
            g.setOrder(orderType);
            g.setOrderTarget(orderTarget);
        }
    }
}
