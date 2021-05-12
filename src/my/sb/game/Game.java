package my.sb.game;

import my.sb.interfaces.ConsoleInterface;

import java.io.IOException;

//очистка

public class Game {
    private Player currentPlayer;
    private Player currentOpponent;

    public void startTheGame() throws IOException {
        currentPlayer = new Player(new ConsoleInterface());
        currentOpponent = new Player(new ConsoleInterface());
    }

    public void mainGame() throws IOException {
        while (true) {
            currentPlayer.getUI().sendMessage(currentPlayer.getName() + " ваш ход!");
            currentPlayer.getUI().sendMessage("Ваше поле");
            currentPlayer.getUI().showField(currentPlayer.getMyField());
            currentPlayer.getUI().sendMessage("Поле противника");
            currentPlayer.getUI().showField(currentPlayer.getEnemyField());
            try {
                Pair pair = currentPlayer.doMove();
                ShotToken token = currentOpponent.getMyField().getResultTerne(pair.getFerst(), pair.getSecond());
                currentOpponent.getMyField().executeMove(token);
                currentPlayer.getEnemyField().executeMove(token);
                currentPlayer.getUI().sendMessage(token.shotResult.toString());


                if (token.shotResult == ShotToken.ShotResult.MISS) {
                    Player bufPlayer = currentPlayer;
                    currentPlayer = currentOpponent;
                    currentOpponent = bufPlayer;
                }
            } catch (InvalidShotException e) {
                System.out.println(e.getMessage());
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Вы ввели что-то не то");
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Вы ввели что-то не то");
                System.out.println(e.getMessage());
            }
            if (!currentPlayer.getMyField().hesAlivedShips()) {
                currentOpponent.win();
                currentPlayer.loos();
                break;
            } else if (!currentOpponent.getMyField().hesAlivedShips()) {
                currentPlayer.win();
                currentOpponent.loos();
                break;
            }
        }
        currentOpponent.getUI().sendMessage("Спасибо за игру, " + currentOpponent.getName() + "!");
        currentPlayer.getUI().sendMessage("Спасибо за игру, " + currentPlayer.getName() + "!");
    }
}

