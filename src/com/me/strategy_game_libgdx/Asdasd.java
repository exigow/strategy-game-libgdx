package com.me.strategy_game_libgdx;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 08.12.13
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class Asdasd {
}


/*
// Flockowanie jednostek do innych jednostek (oraz ustalanie local pos).
        refGroup.list.get(0).flockTo(refGroup, 0, 0); // Pierwsza jest wyjatkiem. Ma ona ustawiony flock na instancje grupy (tzw. "Head-unit").
        for (int i = 1; i < count; i++) {
            refGroup.list.get(i).flockTo(refGroup.list.get(Math.max(0, i - 2)),
                ((i%2 == 0)?(1):(-1)) * refGroup.list.get(i).getSize() * 2,
                -refGroup.list.get(i).getSize());
        }
 */
/*
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

// Mock.
batch.setProjectionMatrix(cameraScreen.combined);
//batch.draw(Assets.testMockupRegion, 0, 0, WINDOW_SIZE_W, WINDOW_SIZE_H);

// Crossy.
batch.setProjectionMatrix(cameraGame.combined);
float px = 512f, dx;
float py = 512f, dy;
float wh = WINDOW_SIZE_H * 2f;
float ww = WINDOW_SIZE_W * 2f;
float minW = -camera.actual.x % px + camera.actual.x - ww;
float minH = -camera.actual.y % py + camera.actual.y - wh;
float maxW = camera.actual.x + ww;
float maxH = camera.actual.y + wh;
batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
for (float x = minW; x <= maxW; x += px) {
        for (float y = minH; y <= maxH; y += py) {
        dx = x + ((camera.actual.x - x) * -1 * (1 / camera.zoom));
dy = y + ((camera.actual.y - y) * -1* (1 / camera.zoom));
for (float z = 0; z < 1f; z += .334f) {
        Assets.sCross.setColor(StandardColors.withAlpha(StandardColors.priorityLow, (1 - z) * .5f));
Draw.draw(Assets.sCross, MathUtilities.lerp(x, dx, z), MathUtilities.lerp(y, dy, z), 1 + z, 1 + z, 0f);
}
        }
        }
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

// Grid.

batch.end();
Gdx.gl.glEnable(GL10.GL_BLEND);
Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
DrawShapeUtilities.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
shapeRenderer.setColor(StandardColors.withAlpha(StandardColors.priorityLow, .125f));
for (float x = minW; x <= maxW; x += px) {
        shapeRenderer.line(x, minH, x, maxH);
}
        for (float y = minH; y <= maxH; y += py) {
        shapeRenderer.line(minW, y, maxW, y);
}
        DrawShapeUtilities.getShapeRenderer().end();
Gdx.gl.glDisable(GL10.GL_BLEND);
batch.begin();

// Dots.
for (float x = minW; x <= maxW; x += px * .5f) {
        for (float y = minH; y <= maxH; y += py * .5f) {
        Assets.sDot.setColor(StandardColors.withAlpha(StandardColors.priorityLow, .375f));
Draw.draw(Assets.sDot, x, y, 1f, 1f, 0f);
}
        }

        // Kursor.
        //batch.setProjectionMatrix(cameraScreen.combined);
        //Assets.drawFixed(Assets.sUnitBorder, mousePosition.screen.x, mousePosition.screen.y, 0f);



        // Per unit.
        batch.setProjectionMatrix(cameraGame.combined);

for (Group g: globalCollector.getList()) {
        //Draw.draw(Assets.sPick, g.getCenter().x, g.getCenter().y, 1f, 1f, 0f);
            for (Unit i: g.list) {
                //Draw.draw(Assets.sUnitSelect, i.getPosition().x, i.getPosition().y, 1f, 1f, i.getDirection() + 90f);
            }
        }

        //Assets.sUnitSelect.setColor(StandardColors.priorityLow);
        for (Group g: globalCollector.getList()) {
            for (Unit i: g.list) {
                //Draw.draw(Assets.sUnitSelect, i.getPosition().x, i.getPosition().y, 1f, 1f, i.getDirection() + 90f);
            }
        }
        for (Group g: globalCollector.getList()) {
            for (Unit i: g.list) {
                //Draw.draw(Assets.s, i.getPosition().x, i.getPosition().y, 1f, 1f, i.getDirection() + 90f);
            }
        }

        // Moving per unit.
        Vector2 pathStart = new Vector2();
        Vector2 pathEnd = new Vector2();
        Vector2 sampler = new Vector2();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (Group g: userController.selectedList) {
            for (Unit iter: g.list) {
                float dir = MathUtilities.pointDirection(iter.getPosition(), iter.getTarget());
                float distance = MathUtilities.pointDistance(iter.getPosition(), iter.getTarget());
                float len = 48f;
                float samplingLength = 512f;
                if (distance > len * 2f) {
                    pathStart.x = iter.getPosition().x + MathUtilities.ldx(-len, dir);
                    pathStart.y = iter.getPosition().y + MathUtilities.ldy(-len, dir);
                    pathEnd.x = iter.getTarget().x + MathUtilities.ldx(len, dir);
                    pathEnd.y = iter.getTarget().y + MathUtilities.ldy(len, dir);

                    Assets.sLine.setColor(StandardColors.withAlpha(StandardColors.priorityLow, .25f));
                    Draw.draw(Assets.sLine, pathStart.x, pathStart.y, ((distance - len * 2f)), 1.5f, dir + 180);

                    Assets.sDot.setColor(StandardColors.priorityLow);
                    Draw.draw(Assets.sDot, pathStart.x, pathStart.y, 2f, 2f, 45f);
                    Draw.draw(Assets.sDot, pathEnd.x, pathEnd.y, 2f, 2f, 45f);
                    Assets.sDot.setColor(StandardColors.withAlpha(StandardColors.priorityLow, .5f));
                    for (float i = (time * 384f)%samplingLength; i < distance; i += samplingLength) {
                        MathUtilities.lerpVectors(sampler, pathStart, pathEnd, i / distance);
                        Draw.draw(Assets.sDot, sampler.x, sampler.y, 1f, 1f, 45f);
                    }

                    Assets.sCircle.setColor(StandardColors.withAlpha(StandardColors.priorityLow, .25f));
                    Draw.draw(Assets.sCircle, iter.getTarget().x, iter.getTarget().y, 2f, 2f, 0f);
                }
            }
        }
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        // Pick.
        if (userController.pickGroupFounded) {
        Assets.sPick.setColor(StandardColors.priorityMedium);
Assets.sLine.setColor(StandardColors.priorityMedium);
Assets.sRingFilledBorder.setColor(StandardColors.priorityMedium);
for (Unit i: userController.pickGroup.list) {
        if (userController.pickGroup.faction == factionEnemy) {
        Assets.sPick.setColor(StandardColors.danger);
Assets.sLine.setColor(StandardColors.danger);
Assets.sRingFilledBorder.setColor(StandardColors.danger);
}
        Draw.setBlendMode(Draw.BlendMode.ADD);
Draw.drawLine(mousePosition.world2, i.getPosition(), 1.5f);
Draw.setBlendMode(Draw.BlendMode.NORMAL);

Draw.draw(Assets.sPick, i.getPosition().x, i.getPosition().y, 1f, 1f, 0f);
Draw.draw(Assets.sRingFilledBorder, i.getPosition().x, i.getPosition().y, 1f, 1f, 0f);
}
        Draw.draw(Assets.sRingFilledBorder, mousePosition.world2.x, mousePosition.world2.y, 1f, 1f, 0f);
}

        // Ramka.
        if (selectStepper.stepIs(Stepper.Step.hold) && userController.isSelecting) {
        batch.end();
Gdx.gl.glEnable(GL10.GL_BLEND);
Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
shapeRenderer.setColor(StandardColors.withAlpha(StandardColors.priorityLow, .25f));
shapeRenderer.rect(userController.selectStart.x, userController.selectStart.y, userController.selectEnd.x - userController.selectStart.x, userController.selectEnd.y - userController.selectStart.y);
shapeRenderer.end();
Gdx.gl.glDisable(GL10.GL_BLEND);
shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
shapeRenderer.setColor(StandardColors.priorityMedium);
shapeRenderer.rect(userController.selectStart.x, userController.selectStart.y, userController.selectEnd.x - userController.selectStart.x, userController.selectEnd.y - userController.selectStart.y);
shapeRenderer.end();
batch.begin();
batch.setProjectionMatrix(cameraGame.combined);
float scale = camera.zoom;
Assets.sSelectRectangleBorder.setColor(StandardColors.priorityHigh);
Draw.draw(Assets.sSelectRectangleBorder, userController.selectStart.x, userController.selectEnd.y, scale, scale, 0f); // 0, 0
Draw.draw(Assets.sSelectRectangleBorder, userController.selectEnd.x, userController.selectStart.y, scale, scale, 180f); // 1, 1
Draw.draw(Assets.sSelectRectangleBorder, userController.selectStart.x, userController.selectStart.y, scale, scale, 90f); // 0, 1
Draw.draw(Assets.sSelectRectangleBorder, userController.selectEnd.x, userController.selectEnd.y, scale, scale, 270f); // 1, 0
}
*/