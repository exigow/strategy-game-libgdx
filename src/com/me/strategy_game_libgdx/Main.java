package com.me.strategy_game_libgdx;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.me.strategy_game_libgdx.gamelogic.systems.Camera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.strategy_game_libgdx.gamelogic.InterfaceColor;
import com.me.strategy_game_libgdx.gamelogic.Order;
import com.me.strategy_game_libgdx.gamelogic.rail.RailManager;
import com.me.strategy_game_libgdx.gamelogic.star.StarManager;
import com.me.strategy_game_libgdx.gamelogic.stepper.Stepper;
import com.me.strategy_game_libgdx.gamelogic.structure.Faction;
import com.me.strategy_game_libgdx.gamelogic.systems.*;
import com.me.strategy_game_libgdx.gamelogic.systems.controller.Controller;
import com.me.strategy_game_libgdx.gamelogic.systems.controller.UserInterface;
import com.me.strategy_game_libgdx.gamelogic.utilities.*;

import java.io.*;


// UI PIORITY COLORS:
// High: .75f, 1f, 1f
// Medium: .375f, .5f, .5f
// Low: .25f, .375f, .375f
// Danger: 1f, .375f, .25f

public class Main implements ApplicationListener {
    public static final int
        WINDOW_SIZE_W = 800, WINDOW_SIZE_H = 600;
    public float deltaTime, time;
    Camera camera;
    SpriteBatch batch;
    ShapeRenderer shape;

    public static OrthographicCamera cameraScreen, cameraGame;

    FrameBuffer fboWorld, fboInterface;
    TextureRegion fboInterfaceRegion, fboWorldRegion;

    Collector globalCollector;

    Faction factionPlayer, factionEnemy;

    // Steppery i selecter.
    Controller userController;
    Stepper selectStepper, actionStepper;

    UserInterface userInterface;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = null;

        // Config.
        Gson gson = new Gson();
        InputStream inputStream;
        try {
            // Odczyt pliku i parsing.
            inputStream = new FileInputStream("c:\\config.json");
            Reader data = new InputStreamReader(inputStream);
            cfg = gson.fromJson(data, LwjglApplicationConfiguration.class);
        } catch (FileNotFoundException e) {
            // Jesli zabraknie pliku, default.
            cfg = new LwjglApplicationConfiguration();
            cfg.fullscreen = false;
            cfg.width = 640;
            cfg.height = 480;
            cfg.useGL20 = true;
            cfg.resizable = false;
        }
        new LwjglApplication(new Main(), cfg);
    }

    public void create() {
        // Batch rysujacy.
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        globalCollector = new Collector();

        // Fbo od interface.
        fboWorld = new FrameBuffer(Pixmap.Format.RGB888, WINDOW_SIZE_W, WINDOW_SIZE_H, false);
        fboInterface = new FrameBuffer(Pixmap.Format.RGB888, WINDOW_SIZE_W, WINDOW_SIZE_H, false);
        fboWorldRegion = new TextureRegion(fboWorld.getColorBufferTexture());
        fboWorldRegion.flip(false, true);
        fboInterfaceRegion = new TextureRegion(fboInterface.getColorBufferTexture());
        fboInterfaceRegion.flip(false, true);

        // Assety.
        Assets.create();
        Assets.load();

        // Wyslanie batcha do statica Draw.
        Draw.setBatch(batch);
        Draw.setShape(shape);

        // Manager raila.
        RailManager.create();

        // Manager starow.
        StarManager.create();

        // Kolory standardowe.
        InterfaceColor.create();

        //factionPlayer, factionEnemy
        factionPlayer = new Faction("Player");
        factionEnemy = new Faction("Enemy");

        // Pozycje myszy (ekran/world).
        MousePosition.init();

        // Kamery libgdx.
        cameraScreen = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraScreen.setToOrtho(false);
        cameraGame = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraGame.setToOrtho(false);
        camera = new Camera("<game>", 0f, 0f, 1.5f, WINDOW_SIZE_W, WINDOW_SIZE_H); // Kamera operacyjna.

        // Kontroler.
        userController = new Controller(camera, factionPlayer);

        // Steppery.
        selectStepper = new Stepper();
        actionStepper = new Stepper();

        // User Interface.
        userInterface = new UserInterface(userController, globalCollector, camera, actionStepper, selectStepper);

        // Tworzenie grup i statkow.
        /*for (int i = 0; i < 1; i++) {
            globalCollector.createSquad(factionPlayer, GroupCollector.GroupType.FIGHTERS, -512f + -128f + (float)Math.random() * 256f, -128f + (float)Math.random() * 256f);
        }
        for (int i = 0; i < 1; i++) {
            globalCollector.createSquad(factionEnemy, GroupCollector.GroupType.FIGHTERS, 512f + -128f + (float)Math.random() * 256f, -128f + (float)Math.random() * 256f);
        }*/
        globalCollector.createGroupOf(Collector.GroupType.FIGHTERS, factionPlayer, -128, -128);
        globalCollector.createGroupOf(Collector.GroupType.CORVETTES, factionPlayer, -128, 128);
        globalCollector.createGroupOf(Collector.GroupType.FIGHTERS, factionEnemy, 256, 0f);

        //globalCollector.createGroupOf(Collector.GroupType.CRUISERS, factionPlayer, 0f, -256f);
        //globalCollector.createGroupOf(Collector.GroupType.ASTEROIDS, factionEnemy, 0f, 256f);
    }

    public void logic() {
        // Kamera i ogolne update.
        cameraMovement(); // Poruszanie kamera.
        camera.doStep(deltaTime * 16f);
        camera.applyOrthographicCamera(cameraGame);
        cameraGame.update();
        deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        MousePosition.update(cameraGame, Gdx.input.getX(), Gdx.input.getY(), WINDOW_SIZE_W, WINDOW_SIZE_H);
        userController.updateMousePosition(MousePosition.world2);

        // Update rails.
        RailManager.updateRails(deltaTime);

        // Update stars.
        StarManager.update(deltaTime, camera);

        // Akcje myszy. Wykonywanie steppera.
        selectStepper.pickStepFrom(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
        actionStepper.pickStepFrom(Gdx.input.isButtonPressed(Input.Buttons.RIGHT));

        // Stepper zaznaczania.
        switch (selectStepper.getStep()) {
            case wait: {
                // Ustalanie jaka grupa jest pod myszka.
                userController.findPick(globalCollector.getList(), MousePosition.world2);
                break;
            }
            case press: {
                userController.pickStart.set(MousePosition.world2);
                userController.doSelection = false;
                break;
            }
            case hold: {
                userController.pickEnd.set(MousePosition.world2);
                userController.compute();
                // Szukanie kandydatow do marked.
                if (userController.isSelecting) {
                    userController.findCandidates(globalCollector.getList());
                }
                break;
            }
            case release: {
                // Zrzut z marked to wlasciwej listy z zaznaczeniem.
                userController.castToSelected();
                break;
            }
        }

        // Stepper akcji.
        switch (actionStepper.getStep()) {
            case wait: {
                break;
            }
            case press: {
                userController.orderType = Order.OrderType.NULL;
                if (userController.selectedList.size() != 0) {
                    if (userController.pickGroupFounded) {
                        userController.orderType = Order.OrderType.ATTACK;
                    } else {
                        userController.orderType = Order.OrderType.MOVE;
                    }
                }
                break;
            }
            case hold: {
                break;
            }
            case release: {
                userController.doAction();
                break;
            }
        }

        // Update grup (->jednostek).
        /*for (Group iter: globalCollector.getList()) {
            iter.update(deltaTime);
        }*/
        globalCollector.updateAll(deltaTime);
    }

    public void render() {
        logic(); // Update logiki.

        // Rendering swiata.
        fboWorld.begin();
        renderWorld();
        fboWorld.end();

        // Rendering interface.
        fboInterface.begin();
        batch.begin();
        shape.setProjectionMatrix(cameraGame.combined);
        userInterface.draw();
        batch.end();
        fboInterface.end();

        // Showing.
        batch.setProjectionMatrix(cameraScreen.combined);
        batch.begin();
        batch.draw(fboWorldRegion, 0, 0);
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Draw.setBlendMode(Draw.BlendMode.ADD);
        }
        batch.draw(fboInterfaceRegion, 0, 0);
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Draw.setBlendMode(Draw.BlendMode.NORMAL);
        }
        batch.end();
    }
    public void resize(int w, int h) {
    }
    public void pause() {
    }
    public void resume() {
    }
    public void dispose() {
    }

    private void renderWorld() {
        Draw.clear(Color.BLACK);

        batch.setProjectionMatrix(cameraGame.combined); // Projekcja batch na world.

        // Background.
        batch.begin();
        batch.draw(Assets.backRegion,
                cameraGame.position.x - (WINDOW_SIZE_W / 2) * camera.zoom,
                cameraGame.position.y - (WINDOW_SIZE_H / 2) * camera.zoom,
                WINDOW_SIZE_W * camera.zoom, WINDOW_SIZE_H * camera.zoom);
        batch.end();

        // Railsy.
        RailManager.drawRails(cameraGame);
        // Starsy below.
        StarManager.drawStars(StarManager.Level.BELOW);
        // Rysowanie jednostek/hitterow - zawartosci kolektora.
        globalCollector.drawAll();
        // Starsy above.
        StarManager.drawStars(StarManager.Level.ABOVE);
    }

    private void cameraMovement() {
        float move_spd = 2048f * deltaTime, zoom_spd = 4f * deltaTime;
        int border = 0;
        // Przemieszczanie.
        if(Gdx.input.isKeyPressed(Input.Keys.W) || MousePosition.screen.y > WINDOW_SIZE_H - border) {
            camera.target.y += move_spd;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || MousePosition.screen.y < border) {
            camera.target.y -= move_spd;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || MousePosition.screen.x < border) {
            camera.target.x -= move_spd;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || MousePosition.screen.x > WINDOW_SIZE_W - border) {
            camera.target.x += move_spd;
        }

        // Zoom.
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoomTarget -= zoom_spd;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoomTarget += zoom_spd;
        }
    }
}