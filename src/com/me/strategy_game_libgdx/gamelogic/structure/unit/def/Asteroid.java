package com.me.strategy_game_libgdx.gamelogic.structure.unit.def;

import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;

public class Asteroid extends Unit {
    public Asteroid() {
        setSize(64f);
    }

    public void draw() {
        Draw.sprite(Assets.sAsteroidBig, getActual().getPosition(), 1f, 1f, getActual().getDirection());
    }
}
