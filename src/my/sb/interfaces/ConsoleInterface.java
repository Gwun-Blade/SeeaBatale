package my.sb.interfaces;

import my.sb.game.fields.BattleField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface implements UserInterface {
    @Override
    public void showField(BattleField bf) {
        System.out.println(bf.toString());
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}
