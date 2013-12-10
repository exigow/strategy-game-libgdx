package com.me.strategy_game_libgdx.gamelogic;

import com.badlogic.gdx.graphics.Color;

public class InterfaceColor {
    public static Color priorityLow, priorityMedium, priorityHigh, danger;
    private static Color outputColor;

    public static void create() {
        priorityLow = new Color(.25f, .375f, .375f, 1f);
        priorityMedium = new Color(.375f, .5f, .5f, 1f);
        priorityHigh = new Color(.75f, 1f, 1f, 1f);
        danger = new Color(1f, .375f, .25f, 1f);
        outputColor = new Color();
    }

    public static Color withAlpha(Color whatColor, float alphaValue) {
        outputColor.set(whatColor);
        outputColor.a = alphaValue;
        return outputColor;
    }
}
