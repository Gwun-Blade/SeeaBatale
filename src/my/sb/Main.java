package my.sb;

import my.sb.game.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        try {
            game.startTheGame();
            game.mainGame();
        } catch (IOException e) {
            System.out.println("Что-то пошло не так");
            e.printStackTrace();
        }
    }
}
