package my.sb.game;

import my.sb.game.fields.BattleField;

public class InvalidShotException extends Exception {
    int x;
    int y;

    public InvalidShotException(String message, int x, int y, BattleField bf) {
        super(message);
        System.out.println("Invalid shot to (" + x + ", " + y + ") in\n" + bf);
    }
}
