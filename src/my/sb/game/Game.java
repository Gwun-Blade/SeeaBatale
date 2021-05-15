package my.sb.game;

import my.sb.interfaces.ConsoleInterface;

//очистка
//везде где вижу horOrt менять на vertOrt
//разобратся с багом при авторасстановке1
public class Game {
    public final static int maxX = 9;
    public final static int maxY = 9;
    public static final int[] maxDesk = {0, 4, 3, 2, 1};

    private Player currentPlayer;
    private Player currentOpponent;

    public void startTheGame() {
        currentPlayer = new Player(new ConsoleInterface());
        currentOpponent = new Player(new ConsoleInterface());
    }

    public void mainGame() {
        while (true) {
            currentPlayer.getUI().sendMessage(currentPlayer.getName() + " ваш ход!");
            currentPlayer.getUI().sendMessage("Ваше поле");
            currentPlayer.getUI().showField(currentPlayer.getMyField());
            currentPlayer.getUI().sendMessage("Поле противника");
            currentPlayer.getUI().showField(currentPlayer.getEnemyField());
            try {
                Pair pair = currentPlayer.getUI().getMove();
                ShotToken token = currentOpponent.getMyField().getResultMove(pair.getFirst(), pair.getSecond());
                currentOpponent.getMyField().executeMove(token);
                currentPlayer.getEnemyField().executeMove(token);
                currentPlayer.getUI().sendMessage(token.shotResult.toString());


                if (token.shotResult == ShotToken.ShotResult.MISS) {
                    Player bufPlayer = currentPlayer;
                    currentPlayer = currentOpponent;
                    currentOpponent = bufPlayer;
                    currentOpponent.getUI().costil("Нажмите ENTER чтобы принять ход");
                }
            } catch (InvalidShotException e) {
                System.out.println(e.getMessage());
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Вы ввели что-то не то");
                System.out.println(e.getMessage());
            }
            if (currentPlayer.getMyField().allShipDead()) {
                currentOpponent.win();
                currentPlayer.loos();
                break;
            } else if (currentOpponent.getMyField().allShipDead()) {
                currentPlayer.win();
                currentOpponent.loos();
                break;
            }
        }
        currentOpponent.getUI().sendMessage("Спасибо за игру, " + currentOpponent.getName() + "!");
        currentPlayer.getUI().sendMessage("Спасибо за игру, " + currentPlayer.getName() + "!");
    }
}

