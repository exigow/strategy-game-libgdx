package com.me.strategy_game_libgdx.gamelogic;

public class Order {
    private static String str;
    public static enum OrderType {ATTACK, MOVE, NULL}

    public static String getOrderString(OrderType orderType) {
        switch (orderType) {
            case ATTACK: {
                str = "ATTACK";
                break;
            }
            case MOVE: {
                str = "MOVE";
                break;
            }
            case NULL: {
                str = "NULL";
                break;
            }
        }
        return str;
    }
}
