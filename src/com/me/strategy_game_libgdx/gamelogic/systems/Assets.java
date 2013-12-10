package com.me.strategy_game_libgdx.gamelogic.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Assets {
    public static Sprite sCorvette, sFighter, sCruiser, sAsteroidBig, sGun, sGradient, sTest, sBeam, sFighterThruster, sStar, sMissile, sBullet;
    public static Sprite sPick, sPickBorder, sCross, sCrossBorder, sCircle, sCircleBorder, sDot, sDotBorder, sRing, sRingBorder, sRingFilledBorder, sLine;
    public static Sprite sUnitSelectBorder, sUnitSelect, sSelectRectangle, sSelectRectangleBorder;
    public static TextureRegion backRegion, testMockupRegion;
    public static Texture back, testMockup;
    public static BitmapFont fOrbitron;

    public enum OriginType {CENTER, ZERO, LEFTTOP}

    // AssetManager.
    public static AssetManager assetManager;
    public static TextureLoader.TextureParameter textureParameter;

    // SpriteBatch.
    /*public static SpriteBatch spriteBatch;
    public static void setSpriteBatch(SpriteBatch batch) {
        spriteBatch = batch;
    }
    public static SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }*/

    //public static Gson gson;
    //public static JSONSpriteCollector jsonSpriteCollector;
    public static void create() {
        // Json parser.
        /*gson = new Gson();
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\strategy-game-libgdx\\assets\\sprites.json"));
            jsonSpriteCollector = gson.fromJson(br, JSONSpriteCollector.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    // Wczywanie assetow.
    public static void load() {
        assetManager = new AssetManager();

        textureParameter = new TextureLoader.TextureParameter();
        textureParameter.minFilter = Texture.TextureFilter.Linear;
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        textureParameter.genMipMaps = true;

        Sprite cache = addSprite("background.jpg", OriginType.ZERO);
        back = cache.getTexture();
        backRegion = new TextureRegion(back);
        Sprite cache2 = addSprite("mockup.jpg", OriginType.ZERO);
        testMockup = cache2.getTexture();
        testMockupRegion = new TextureRegion(testMockup);

        sFighter = addSprite("fighter.tga", OriginType.CENTER);
        sAsteroidBig = addSprite("asteroid.png", OriginType.CENTER);
        sCruiser = addSprite("cruiser.png", OriginType.CENTER);
        sGun = addSprite("gun.png", OriginType.CENTER);
        sGun.setOrigin(7, 7);
        sGradient = addSprite("gradient.png", OriginType.CENTER);
        sTest = addSprite("test.png", OriginType.CENTER);
        sBeam = addSprite("beam.png", OriginType.CENTER);
        sStar = addSprite("star.png", OriginType.CENTER);
        sFighterThruster = addSprite("fighter_thruster.png", OriginType.CENTER);
        sFighterThruster.setOrigin(sFighterThruster.getWidth() - 10f, sFighterThruster.getHeight() / 2);
        sMissile = addSprite("missile.tga", OriginType.CENTER);
        sBullet = addSprite("bullet.tga", OriginType.CENTER);
        sCorvette = addSprite("corvette.png", OriginType.CENTER);


        // Symbole.
        sCross = addSprite("symbols/cross.png", OriginType.CENTER);
        sCrossBorder = addSprite("symbols/cross_border.png", OriginType.CENTER);
        sCircle = addSprite("symbols/circle.png", OriginType.CENTER);
        sCircleBorder = addSprite("symbols/circle_border.png", OriginType.CENTER);
        sDot = addSprite("symbols/dot.png", OriginType.CENTER);
        sDotBorder = addSprite("symbols/dot_border.png", OriginType.CENTER);
        sRing = addSprite("symbols/ring.png", OriginType.CENTER);
        sRingBorder = addSprite("symbols/ring_border.png", OriginType.CENTER);
        sRingFilledBorder = addSprite("symbols/ring_filled_border.png", OriginType.CENTER);
        sUnitSelect = addSprite("symbols/unit_select.png", OriginType.CENTER);
        sUnitSelectBorder = addSprite("symbols/unit_select_border.png", OriginType.CENTER);
        sSelectRectangle = addSprite("symbols/select_rectangle.png", OriginType.LEFTTOP);
        sSelectRectangle.setOrigin(12, sSelectRectangle.getWidth() - 12);
        sSelectRectangleBorder = addSprite("symbols/select_rectangle_border.png", OriginType.LEFTTOP);
        sSelectRectangleBorder.setOrigin(12, sSelectRectangleBorder.getHeight() - 12);
        sLine = addSprite("symbols/line.png", OriginType.CENTER);
        sLine.setOrigin(0, sLine.getHeight() / 2);
        sPick = addSprite("symbols/pick.png", OriginType.CENTER);
        sPickBorder = addSprite("symbols/pick_border.png", OriginType.CENTER);

        fOrbitron = new BitmapFont(Gdx.files.internal("font/orbitron20aa.fnt"), Gdx.files.internal("font/orbitron20aa_0.png"), false);
    }

    // Funkcja rysujaca sprite.
    //draw_sprite_ext(sprite,subimg,x,y,xscale,yscale,rot,color,alpha );
    /*public static void draw(Sprite sprite, float x, float y, float scalex, float scaley, float direction) {
        sprite.setPosition(x, y);
        sprite.setScale(scalex, scaley);
        sprite.setRotation(direction);
        sprite.translate(-sprite.getOriginX(), -sprite.getOriginY());
        sprite.draw(spriteBatch);
    }*/

    /*public static void drawFixed(Sprite sprite, Vector2 position, float direction) {
        drawFixed(sprite, position.x, position.y, direction);

    }
    public static void drawFixedScaled(Sprite sprite, Vector2 position, float direction, float scale) {
        sprite.setScale(scale);
        drawFixed(sprite, position, direction);
    }
    public static void drawFixedScaled(Sprite sprite, float x, float y, float direction, float scale) {
        sprite.setScale(scale);
        drawFixed(sprite, x, y, direction);
    }*/

    // Dodawanie sprite.
    private static Sprite addSprite(String path, OriginType originType) {
        assetManager.load(path, Texture.class, textureParameter);
        assetManager.finishLoading();
        Sprite spr = new Sprite(assetManager.get(path, Texture.class));

        // Ustawianie origina.
        float ox = 0, oy = 0;
        switch (originType) {
            case CENTER: {
                ox = spr.getWidth() / 2f;
                oy = spr.getHeight() / 2f;
                break;
            }
            case ZERO: {
                ox = 0;
                oy = 0;
                break;
            }
            case LEFTTOP: {
                ox = 0;
                oy = spr.getWidth();
                break;
            }
        }
        spr.setOrigin(ox, oy);

        return spr;
    }
}
