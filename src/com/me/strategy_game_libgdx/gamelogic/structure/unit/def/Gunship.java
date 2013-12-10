package com.me.strategy_game_libgdx.gamelogic.structure.unit.def;

import com.me.strategy_game_libgdx.gamelogic.Order;
import com.me.strategy_game_libgdx.gamelogic.structure.device.Device;
import com.me.strategy_game_libgdx.gamelogic.structure.hitter.Hitter;
import com.me.strategy_game_libgdx.gamelogic.structure.unit.systems.Unit_DynamicMoveSystem;
import com.me.strategy_game_libgdx.gamelogic.systems.Assets;
import com.me.strategy_game_libgdx.gamelogic.utilities.Draw;
import com.me.strategy_game_libgdx.gamelogic.utilities.MathUtilities;

import java.util.ArrayList;

public class Gunship extends Unit_DynamicMoveSystem {
    private float shootTimer;
    private boolean shootAble;

    private ArrayList<Device> devices;
    public Gunship() {
        setSize(64f);
        shootTimer = 0f;
        shootAble = false;

        devices = new ArrayList<Device>();
        devices.add(new Device(this, -3, -20));
        devices.add(new Device(this, -20, 18));
    }

    public void draw() {
        Draw.sprite(Assets.sCorvette, getActual().getPosition(), 1f, 1f, getActual().getDirection());
        for (Device device: devices) {
            Draw.sprite(Assets.sGun, device.getWorldPosition(), 1f, 1f, device.getDirection());
            if (shootAble) {
                Draw.setBlendMode(Draw.BlendMode.ADD);
                Draw.sprite(Assets.sLine, device.getWorldPosition(), 256f, 1f, device.getDirection());
                Draw.setBlendMode(Draw.BlendMode.NORMAL);
            }
        }
    }

    public void update(float deltaTime) {
        // Okreslanie czy jest do czego strzelac oraz mechanika przemieszczania.
        shootAble = false;
        if (getGroup().getOrder() == Order.OrderType.ATTACK) {
            goTo.set(orderTarget.getActual().getPosition().x + 64, orderTarget.getActual().getPosition().y + 64, 0f);
            shootAble = true;
        } else {
            goTo.set(getTarget());
        }

        // Device update i ustalanie targetu.
        for (Device device: devices) {
            device.update(deltaTime);
            if (shootAble) {
                device.setTargetDirection(device.computeDirectionToUnit(orderTarget));
            }
        }

        // Strzelanie.
        shootTimer += deltaTime;
        if (shootTimer > .125f && shootAble) {
            shootTimer = 0f;
            for (Device device: devices) {
                Hitter a = new Hitter();
                a.postion.set(device.getWorldPosition(), device.getDirection());
                a.target = orderTarget;
                getCollectorLink().getHitterList().add(a);
            }
        }
    }
}
