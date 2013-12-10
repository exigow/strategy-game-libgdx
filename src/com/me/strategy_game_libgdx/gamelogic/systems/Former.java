package com.me.strategy_game_libgdx.gamelogic.systems;

import com.badlogic.gdx.math.Vector2;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;
import com.me.strategy_game_libgdx.gamelogic.structure.Movable;

import java.util.ArrayList;

public class Former {
    public enum Formation {ECHELON, LINE, CIRCLE, RANDOM, SIMPLE}
    static public void form(float x, float y, float direction, Formation formation, ArrayList<Movable> list) {
        Movable unit; // Referencja do unit.
        int listSize = list.size(); // Rozmiar grupy w jednostkach.

        switch (formation) {
            case ECHELON: {
                formSymmetry(x, y, 1f, -.75f, direction, list);
                break;
            }
            case LINE: {
                formSymmetry(x, y, 1f, 0, direction, list);
                break;
            }
            case CIRCLE: {
                for (int i = 0; i < listSize; i++) {
                    unit = list.get(i);
                    float
                        len = (2f * 3.14f * listSize),
                        dir = (1f / (float)listSize) * i * 360f;
                    unit.getTarget().set(x + MathUtilities.ldx(len, dir), y + MathUtilities.ldy(len, dir), 0f);
                }
                break;
            }
            case RANDOM: {
                for (int i = 0; i < listSize; i++) {
                    unit = list.get(i);
                    float randomScale = 64f;
                    unit.getTarget().set(x - randomScale + (float)Math.random() * randomScale * 2,
                    y - randomScale + (float)Math.random() * randomScale * 2, (float)Math.random() * 360);
                }
                break;
            }
            case SIMPLE: {
                for (int i = 0; i < listSize; i++) {
                    unit = list.get(i);
                    unit.getTarget().set(x, y, direction);
                }
                break;
            }
        }
    }

    // Prywatna funkcja formujaca liniowo symetryczne formacje (linia, grot)
    private static void formSymmetry(float baseX, float baseY, float vecX, float vecY, float direction, ArrayList<Movable> list) {
        int size = list.size(), i, jmp;
        Movable unit;
        float pair = ((size + 1) % 2) / 2f;
        Vector2 vec = new Vector2();
        int k = 0, a;

        for (i = 0; i < size; i++) {
            unit = list.get(i);
            vec.set(vecX * unit.getSize() * 2, vecY * unit.getSize() * 2);

            jmp = (i % 2 == 0) ? 1 : -1;
            if ((i + 1)%2 == 0) {
                k += 1;
            }
            a = jmp * k;

            unit.getTarget().set(
                baseX + MathUtilities.ldx(1, direction + 90) * (a + pair) * vec.x + MathUtilities.ldx(1, direction) * k * vec.y,
                baseY + MathUtilities.ldy(1, direction + 90) * (a + pair) * vec.x + MathUtilities.ldy(1, direction) * k * vec.y,
                direction);
        }
    }
}