package com.me.strategy_game_libgdx.gamelogic.rail;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;

import java.util.ArrayList;

public class RailManager {
    // Lista zawierajaca wszystkie raile.
    public static ArrayList<Rail> list;

    // Region i batch - rysowanie raili przy uzyciu polygonow.
    private static PolygonRegion pr;
    private static PolygonSpriteBatch pbatch;

    // Referencja do tablicy z komponentami polygonow.
    private float [] vals;

    // Metoda tworzaca komponenty. Wymagane wywolanie przed uzyciem rysowania/update.
    public static void create() {
        // Tworzymy liste do trzymania raili.
        list = new ArrayList<Rail>();

        // Tworzenie polygona.
        TextureRegion textureRegion = new TextureRegion(Assets.sBeam.getTexture());
        pr = new PolygonRegion(textureRegion, new float[] {0, 0, 0, 0, 0, 0, 0, 0}, new short[] {0, 1, 2, 1, 2, 3});
        float[] coords = new float[] {0f, 0f, 1f, 0f, 0, 1f, 1f, 1f};
        for (int i = 0; i < 8; i++) {
            pr.getTextureCoords()[i] = coords[i];
        }

        // Tworznie batcha do rysowania polygonow.
        pbatch = new PolygonSpriteBatch();
    }

    public static void drawRails(OrthographicCamera camera) {
        float [] vals = pr.getVertices(); // Pobranie vertexow.

        pbatch.setProjectionMatrix(camera.combined); // Macierz do kamery.

        // Rysowanie.
        pbatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE); // Odpalenie blendingu "ADD" z alpha controlem.
        pbatch.begin();
        for (Rail i: list) {
            // Kolor. Alfa ustawiona na podstawie "zycia" raila.
            pbatch.setColor(1f, 1f, 1f, i.value);

            // Obliczanie pozycji vertexow.
            vals[0] = i.position.x + i.vec.x;
            vals[1] = i.position.y + i.vec.y;
            vals[2] = i.position.x - i.vec.x;
            vals[3] = i.position.y - i.vec.y;
            vals[4] = i.ref.position.x + i.ref.vec.x;
            vals[5] = i.ref.position.y + i.ref.vec.y;
            vals[6] = i.ref.position.x - i.ref.vec.x;
            vals[7] = i.ref.position.y - i.ref.vec.y;

            // Rysowanie PolygonRegion.
            pbatch.draw(pr, 0f, 0f);
        }
        pbatch.end();
        pbatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    // Metoda do aktualizacji raili w liscie.
    public static void updateRails(float deltaTime) {
        Rail ref;
        for (int i = 0; i < list.size(); i++) {
            ref = list.get(i);
            ref.value -= deltaTime * .25f; // Odejmowanie "zycia".
            ref.position.add(list.get(i).step); // Dodanie pozycji wektora przesuniecia ("step").

            // Kierunek raila do jego referencji.
            ref.direction = (float)Math.atan2(ref.position.y - ref.ref.position.y, ref.position.x - ref.ref.position.x) + 1.57f;

            // Obliczanie wektora szerokosci.
            ref.vec.set((float)Math.cos(ref.direction) * ref.value * 12f, (float)Math.sin(ref.direction) * ref.value * 12f);

            // Usuwanie "martwych" raili.
            if (ref.value < 0f) {
                list.remove(i);
                i--;
            }
        }
        //System.out.println("Rail List Size: " + list.size());
    }
}
