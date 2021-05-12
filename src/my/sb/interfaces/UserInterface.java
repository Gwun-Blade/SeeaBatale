package my.sb.interfaces;

import my.sb.game.fields.BattleField;

import java.io.IOException;

public interface UserInterface {
    public void showField(BattleField bf);

    public void sendMessage(String message);

    public String getMessage() throws IOException;
}

