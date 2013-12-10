package com.me.strategy_game_libgdx.gamelogic.structure.unit.def;

import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

import java.util.ArrayList;

public class Cruiser extends Unit {
    public Cruiser() {
        setSize(48f);
    }
    public void draw() {
        Draw.sprite(Assets.sCruiser, getActual().getPosition(), 1f, 1f, getActual().getDirection());
    }
}
