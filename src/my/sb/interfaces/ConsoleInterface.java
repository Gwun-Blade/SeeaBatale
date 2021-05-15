package my.sb.interfaces;

import my.sb.game.Game;
import my.sb.game.Pair;
import my.sb.game.fields.BattleField;
import my.sb.game.fields.MyField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface implements UserInterface {
    @Override
    public void costil(String message) {
        sendMessage(message);
        for (int i = 0; i < 25; i++) {
            sendMessage("");
        }
        getMessage();
    }

    public Pair getMove() {
        while (true) {
            try {
                String message = getMessage();
                int first = Integer.parseInt(message.substring(0, 1));
                int second = Integer.parseInt(message.substring(2, 3));
                if (MyField.isOut(first, second)) {
                    sendMessage("Ваши координаты выходят за границу поля");
                    sendMessage("Введите координаты в рамках 0 - " + Game.maxX + ", 0 - " + Game.maxY);
                } else {
                    return new Pair(first, second);
                }
            } catch (NumberFormatException e) {
                sendMessage("Вы ввели не число. Убедитесь, что ввод имеет следующий формат: x y");
            }
        }
    }

    public void arrangeShips(MyField bf) {
        boolean vertOrt = false;
        sendMessage("1 - авторасстановка");
        sendMessage("2 - ручная расстоновка");
        char command;
        while (true) {
             String message = getMessage();
             command = message.charAt(0);
             if (command == '1' || command == '2')
                 break;
             else
                 sendMessage("Вы не ввели ни одно из предложеных чисел");
        }
        switch (command) {
            case '1' -> {
                bf.randomPlacement();
                showField(bf);
            }
            case '2' -> {
                sendMessage("Введите: х, у, длинну корабля через пробел");
                sendMessage("Веедите: v для вертикальной ориентации или g для горизонтальной");
                sendMessage("Веедите: s если хотите взглянуть на поле");
                sendMessage("");
                while (bf.isNotReady()) {
                    sendMessage("Расположите очереденой корабль");
                    String message = getMessage();
                    if (message.charAt(0) == 'g') {
                        vertOrt = false;
                        sendMessage("Ориентация кораблей изменена на горизонтальную");
                    } else if (message.charAt(0) == 'v') {
                        vertOrt = true;
                        sendMessage("Ориентация кораблей изменена на вертикальную");
                    } else if (message.charAt(0) == 's') {
                        showField(bf);
                        sendMessage("Осталось: ");
                        sendMessage(4 - bf.getShipsCount(1) + " однопалубных");
                        sendMessage(3 - bf.getShipsCount(2) + " двупалубных");
                        sendMessage(2 - bf.getShipsCount(3) + " трехпалубных");
                        sendMessage(1 - bf.getShipsCount(4) + " четырехпалубных");
                    } else {
                        String[] splited = message.split(" ");
                        int x = Integer.parseInt(splited[0]);
                        int y = Integer.parseInt(splited[1]);
                        int countFields = Integer.parseInt(splited[2]);
                        if (bf.locateShip(x, y, countFields, vertOrt)) {
                            sendMessage("Корабль успешно расположен!");
                            showField(bf);
                        } else {
                            sendMessage("Невозможно расположить корабль так");
                        }
                    }
                }
                sendMessage("Все корабли размещены!");
            }
        }
    }

    @Override
    public String getUserName() {
        sendMessage("Дававйте знакомится, я - Игра, а вы?");
        String name = getMessage();
        sendMessage("Привет " + name + "!");
        return name;
    }

    @Override
    public void showField(BattleField bf) {
        System.out.println(bf.toString());
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                return reader.readLine();
            } catch (IOException e) {
                System.out.println("Что - то пошло не так, пропобуйте еще раз");
            }
        }
    }
}
