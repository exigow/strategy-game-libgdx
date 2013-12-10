package com.me.strategy_game_libgdx.gamelogic.systems;


import com.me.strategy_game_libgdx.gamelogic.structure.Faction;
import com.me.strategy_game_libgdx.gamelogic.structure.hitter.Hitter;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.def.Asteroid;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.def.Gunship;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.def.Cruiser;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.def.Fighter;
import com.me.strategy_game_libgdx.gamelogic.structure.group.Group;
import com.me.strategy_game_libgdx.gamelogic.structure.Movable;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;

import java.util.ArrayList;
import java.util.List;

public class Collector {
    public enum GroupType {FIGHTERS, CRUISERS, ASTEROIDS, CORVETTES}

    private List<Group> groupGlobalList;
    private List<Hitter> hitterGlobalList;

    public Collector() {
        groupGlobalList = new ArrayList<Group>();
        hitterGlobalList = new ArrayList<Hitter>();
    }

    public List<Group> getList() {
        return groupGlobalList;
    }

    public List<Hitter> getHitterList() {
        return hitterGlobalList;
    }

    // Tworzy grupe danego typu (squad mysliwcow, squad cruiserow, etc.).
    public void createGroupOf(GroupType type, Faction faction, float x, float y) {
        // Tworzenie referencji.
        Unit refSelectable;
        Group refGroup = new Group();

        // Okreslanie ilosci instancji do utworzenia.
        int count = 1;
        switch (type) {
            case FIGHTERS: {
                count = 5;
                break;
            }
            case CRUISERS: {
                count = 2;
                break;
            }
            case ASTEROIDS: {
                count = 3;
                break;
            }
            case CORVETTES: {
                count = 2;
                break;
            }
        }

        // Tworzenie jednostki.
        // Class a = Fighter.class;
        // Fighter zz = a.newInstance();
        for (int i = 0; i < count; i++) {
            switch (type) {
                case FIGHTERS: {
                    refSelectable = new Fighter();
                    break;
                }
                case CRUISERS: {
                    refSelectable = new Cruiser();
                    break;
                }
                case ASTEROIDS: {
                    refSelectable = new Asteroid();
                    break;
                }
                case CORVETTES: {
                    refSelectable = new Gunship();
                    break;
                }
                default: {
                    refSelectable = new Unit();
                }
            }
            refSelectable.getActual().set(x, y, 0f);
            refSelectable.setCollectorLink(this);
            refGroup.addToGroup(refSelectable);
            refGroup.setFaction(faction);
        }

        // Formowanie grupy z utworzonymi jednostkami.
        Former.form(x, y, (float) Math.random() * 360f, Former.Formation.ECHELON, (ArrayList<Movable>) ((ArrayList<?>) refGroup.list));

        // Dodanie grupy do zbioru globalnego.
        getList().add(refGroup);
    }

    // Draw.
    public void drawAll() {
        Draw.getBatch().begin();
        // Group.
        for (Group group: groupGlobalList) {
            // Unit.
            for (Unit u: group.list) {
                u.draw();
            }
        }
        // Hitter.
        for (Hitter hitter: hitterGlobalList) {
            hitter.draw();
        }
        Draw.getBatch().end();
    }

    // Update.
    public void updateAll(float deltaTime) {
        // Group.
        for (Group g: groupGlobalList) {
            g.update(deltaTime);
        }
        // Hitter.
        Hitter href;
        for (int i = 0; i < hitterGlobalList.size(); i++) {
            href = hitterGlobalList.get(i);
            href.update(deltaTime);

            if (!href.isAlive()) {
                hitterGlobalList.remove(i);
                i--;
            }
        }
    }
}

