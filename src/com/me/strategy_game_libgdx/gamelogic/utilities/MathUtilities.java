package com.me.strategy_game_libgdx.gamelogic.utilities;

import com.badlogic.gdx.math.Vector2;
import static java.lang.Math.*;

public class MathUtilities {

    // point -> point direction
    public static float pointDirection(Vector2 a, Vector2 b) {
        return pointDirection(a.x, a.y, b.x, b.y);
    }
    public static float pointDirection(float ax, float ay, float bx, float by) {
        return (float)(toDegrees(atan2(ay - by, ax - bx)));
    }

    // point -> point distance
    public static float pointDistance(Vector2 a, Vector2 b) {
        return pointDistance(a.x, a.y, b.x, b.y);
    }
    public static float pointDistance(float ax, float ay, float bx, float by) {
        return (float)sqrt(Math.pow((bx - ax), 2) + pow((by - ay), 2));
    }

    // point -> rectangle distance
    public static float pointRectDistance(Vector2 point, Vector2 start, Vector2 end) {
        return pointRectDistance(point.x, point.y, start.x, start.y, end.x, end.y);
    }
    public static float pointRectDistance(float px, float py, float ex, float ey, float sx, float sy) {
        float cx = max(Math.min(px, sx), ex);
        float cy = max(Math.min(py, sy), ey);
        return (float)sqrt((px - cx) * (px - cx) + (py - cy) * (py - cy));
    }

    // sin/cos(kierunek) razy odleglosc.
    public static float ldx(float len, float dir) {
        return (float)(cos(toRadians(dir))) * len;
    }
    public static float ldy(float len, float dir) {
        return (float)(sin(toRadians(dir))) * len;
    }

    // Obraca lokalna pozycje wokol bazowej pozycji na podstawie directiom i zwraca 'world' pozycje.
    public static Vector2 positionPlusTranslated(Vector2 basePosition, Vector2 localPosition, float direction) {
        return new Vector2(
                basePosition.x
                        + MathUtilities.ldx(localPosition.x, direction + 90)
                        + MathUtilities.ldx(localPosition.y, direction),
                basePosition.y
                        + MathUtilities.ldy(localPosition.x, direction + 90)
                        + MathUtilities.ldy(localPosition.y, direction));
    }

    // Roznica dwoch katow (w stopniach!)
    public static float angdiff(float angle0, float angle1) {
        return ((((angle0 - angle1) % 360) + 540) % 360) - 180;
    }

    // Interpolacja mniedzy dwoma wartosciami.
    public static float lerp(float from, float to, float percent) {
        //return (from + percent * (to - from));
        return from * (1 - percent) + to * percent;
    }

    // Interpolacja miedzy pozycjami dwoch wektorow.
    public static void lerpVectors(Vector2 sendTo, Vector2 vecA, Vector2 vecB, float percent) {
        sendTo.set(
            lerp(vecA.x, vecB.x, percent),
            lerp(vecA.y, vecB.y, percent)
        );
    }
}
