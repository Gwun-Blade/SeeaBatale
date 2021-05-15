package my.sb.interfaces;

import my.sb.game.Pair;
import my.sb.game.fields.BattleField;
import my.sb.game.fields.MyField;

public interface UserInterface {
    void costil(String message);

    Pair getMove();

    void arrangeShips(MyField bf);

    String getUserName();

    void showField(BattleField bf);

    void sendMessage(String message);

    String getMessage();
}

