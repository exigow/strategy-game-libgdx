package com.me.strategy_game_libgdx.gamelogic.systems.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.strategy_game_libgdx.gamelogic.systems.Camera;
import com.me.strategy_game_libgdx.gamelogic.InterfaceColor;
import com.me.strategy_game_libgdx.gamelogic.Order;
import com.me.strategy_game_libgdx.gamelogic.stepper.Stepper;
import com.me.strategy_game_libgdx.gamelogic.structure.group.Group;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.Unit;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.systems.Collector;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

public class UserInterface {
    private Collector collector;
    private Controller controller;
    private Camera camera;
    private Stepper stepperAction, stepperSelect;

    public UserInterface(Controller controller, Collector collector, Camera camera, Stepper stepperAction, Stepper stepperSelect) {
        this.collector = collector;
        this.controller = controller;
        this.camera = camera;
        this.stepperAction = stepperAction;
        this.stepperSelect = stepperSelect;
    }

    public void draw() {
        Draw.clear(Color.BLACK);
        Draw.setFont(Assets.fOrbitron);
        Draw.setColor(Color.WHITE);

        // Mock.
        //batch.setProjectionMatrix(cameraScreen.combined);
        //Draw.textureRegion(Assets.testMockupRegion, 0, 0, WINDOW_SIZE_W, WINDOW_SIZE_H);
        //batch.setProjectionMatrix(cameraGame.combined);
        //Draw.setColor(Color.BLUE);
        //Draw.line(mousePosition.world2.x, mousePosition.world2.y, 0f, 0f, 4f);

        for (Group group: collector.getList()) {
            if (group.faction == controller.controlledFaction) {
                Draw.setColor(InterfaceColor.priorityHigh);
            } else {
                Draw.setColor(InterfaceColor.danger);
            }
            //Draw.sprite(Assets.sCross, group.getCenter(), 1f, 1f, 0f);
            /*Draw.text(
                    "[order: " + Order.getOrderString(group.getOrder()) + "]" + " [selected: " + group.isSelected() + "]" + " [marked: " + group.isMarked() + "]",
                    group.getCenter());*/

            if (group.isSelected()) {
                for (Unit i: group.list) {
                    Draw.sprite(Assets.sUnitSelect, i.getActual().getPosition(), 1f, 1f, i.getActual().getDirection() + 90f);
                }
            }
            if (group.isMarked()) {
                for (Unit i: group.list) {
                    Draw.sprite(Assets.sPick, i.getActual().getPosition(), 1f, 1f, 0f);
                }
            }
        }


        if (controller.pickGroupFounded) {
            if (controller.pickGroup.faction == controller.controlledFaction) {
                Draw.setColor(InterfaceColor.priorityHigh);
            } else {
                Draw.setColor(InterfaceColor.danger);
            }
            for (Unit unit: controller.pickGroup.list) {
                Draw.sprite(Assets.sPick, unit.getActual().getPosition(), 1f, 1f, 0f);
            }
        }

        // Wyliczanie wartosci do rysowania elementow tla.
        float px = 512f, dx;
        float py = 512f, dy;
        float ww = camera.w * 2f;
        float wh = camera.h * 2f;
        float minW = -camera.actual.x % px + camera.actual.x - ww;
        float minH = -camera.actual.y % py + camera.actual.y - wh;
        float maxW = camera.actual.x + ww;
        float maxH = camera.actual.y + wh;

        // Krzyzyki.
        Draw.setBlendMode(Draw.BlendMode.ADD);
        Draw.setColor(InterfaceColor.priorityLow);
        for (float x = minW; x <= maxW; x += px) {
            for (float y = minH; y <= maxH; y += py) {
                dx = x + ((camera.actual.x - x) * -1 * (1 / camera.zoom));
                dy = y + ((camera.actual.y - y) * -1* (1 / camera.zoom));
                for (float z = 0; z < 1f; z += .334f) {
                    Draw.setAlpha((1 - z) * .5f);
                    Draw.sprite(Assets.sCross, MathUtilities.lerp(x, dx, z), MathUtilities.lerp(y, dy, z), 1 + z, 1 + z, 0f);
                }
            }
        }
        Draw.setBlendMode(Draw.BlendMode.NORMAL);

        // Dots.
        Draw.setAlpha(.5f);
        for (float x = minW; x <= maxW; x += px * .5f) {
            for (float y = minH; y <= maxH; y += py * .5f) {
                Draw.sprite(Assets.sDot, x, y, 1f, 1f, 0f);
            }
        }

        Draw.setAlpha(.125f);
        for (float x = minW; x <= maxW; x += px) {
            Draw.line(x, minH, x, maxH, 2f);
        }
        for (float y = minH; y <= maxH; y += py) {
            Draw.line(minW, y, maxW, y, 2f);
        }

        // Ramka.
        Draw.setAlpha(1f);
        Draw.setColor(InterfaceColor.priorityMedium);
        if (stepperSelect.stepIs(Stepper.Step.hold) && controller.isSelecting) {
            // HACK na rysowanie rectangle.
            // TODO Cos z tym zrobic.
            Draw.getBatch().end();
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            Draw.getShape().begin(ShapeRenderer.ShapeType.Filled);
            Draw.getShape().setColor(InterfaceColor.priorityLow.r, InterfaceColor.priorityLow.g, InterfaceColor.priorityLow.b, .125f);
            Draw.getShape().rect(controller.selectStart.x, controller.selectStart.y, controller.selectEnd.x - controller.selectStart.x, controller.selectEnd.y - controller.selectStart.y);
            Draw.getShape().end();
            Gdx.gl.glDisable(GL10.GL_BLEND);
            Draw.getShape().begin(ShapeRenderer.ShapeType.Line);
            Draw.getShape().setColor(InterfaceColor.priorityMedium);
            Draw.getShape().rect(controller.selectStart.x, controller.selectStart.y, controller.selectEnd.x - controller.selectStart.x, controller.selectEnd.y - controller.selectStart.y);
            Draw.getShape().end();
            Draw.getBatch().begin();

            float scale = camera.zoom;
            Draw.sprite(Assets.sSelectRectangleBorder, controller.selectStart.x, controller.selectEnd.y, scale, scale, 0f); // 0, 0
            Draw.sprite(Assets.sSelectRectangleBorder, controller.selectEnd.x, controller.selectStart.y, scale, scale, 180f); // 1, 1
            Draw.sprite(Assets.sSelectRectangleBorder, controller.selectStart.x, controller.selectStart.y, scale, scale, 90f); // 0, 1
            Draw.sprite(Assets.sSelectRectangleBorder, controller.selectEnd.x, controller.selectEnd.y, scale, scale, 270f); // 1, 0
        }



        Draw.setBlendMode(Draw.BlendMode.ADD);
        for (Group group: collector.getList()) {
            for (Unit i: group.list) {
                /*Draw.setColor(Color.RED);
                Draw.line(i.getActual().getPosition(), i.getTarget().getPosition(), 1f);
                Draw.setColor(Color.GREEN);
                Draw.line(i.getActual().getPosition(), i.goTo.getPosition(), 1f);*/
            }

            if (group.getOrder() == Order.OrderType.ATTACK) {
                //Draw.line(group.getCenter(), group.getOrderTarget().getCenter(), 4f);
            } else {
                //Draw.line(group.getCenter(), group.getTarget(), 4f);
            }
            /*if (group.getOrder() == Order.OrderType.ATTACK) {
                //Draw.line(group.getCenter(), group.getOrderTarget().getCenter(), 4f);
                for (Unit i: group.list) {
                    Draw.line(i.getPosition(), i.orderTarget.getPosition(), 4f);
                }
            }*/
        }
        Draw.setBlendMode(Draw.BlendMode.NORMAL);
    }

}
