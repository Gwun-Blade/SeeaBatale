package my.sb.game;

import my.sb.game.fields.BattleField;
import my.sb.game.fields.EnemyField;
import my.sb.game.fields.MyField;
import my.sb.interfaces.UserInterface;

import java.io.IOException;

/**
* 0 - пустое поле, что угодно
* 1 - поле с целым кораблем
* -1 - поле с подбитым или убитым кораблем
* 2 - поле где точно нет корабля противника
* */

public class Player {
    private String name;
    private MyField myField;
    private EnemyField enemyField;
    private UserInterface UI;
    private boolean horisontalOrt;

    public Player(UserInterface UI) throws IOException {
        this.UI = UI;
        UI.sendMessage("Представьтесь пожалуйста, как к вам обращятся?");
        this.name = UI.getMessage();
        myField = new MyField();
        enemyField = new EnemyField();
        horisontalOrt = false;
        UI.sendMessage("Привет " + name + "!");
        UI.sendMessage("Если хотите чтобы карабли расставили за вас - введите r, иначе, введите k");
        String input = UI.getMessage();
        if (input.equals("r")) {
            myField.randomPlacement();
            UI.showField(myField);
        } else if (input.equals("k")) {
            UI.sendMessage("Распологайте корабли вводя три числа - координату по вертикали, по горизонтали и длинну кобраля. \n" +
                    "Если вы заходите распологать коробли вертикально - отдельно введите v если горизонтально - g");
            try {
                while (!myField.isReady()) {
                    locateShips();
                }
                UI.sendMessage("Спасибо, " + name + " поле заполнено!");
            } catch (IOException e) {
                System.out.println("Ошибка ввода вывода при размещении кораблей у игрока " + name);
            }
        } else {
            throw new IOException();
        }
    }

    private void locateShips() throws IOException {
        UI.sendMessage("Расположите очереденой корабль \n" +
                "Осталось: \n" +
                (4 - myField.ships[0]) + " однопалубных\n" +
                (3 - myField.ships[1]) + " двупалубных\n" +
                (2 - myField.ships[2]) + " трехпалубных\n" +
                (1 - myField.ships[3]) + " четырехпалубных");
        String message = UI.getMessage();
        if (message.charAt(0) == 'g') {
            horisontalOrt = true;
            UI.sendMessage("Ориентация кораблей изменена на горизонтальную");
        } else if (message.charAt(0) == 'v') {
            horisontalOrt = false;
            UI.sendMessage("Ориентация кораблей изменена на вертикальную");
        } else if (message.charAt(0) == 's') {
            UI.showField(myField);
        } else {
            String splited[] = message.split(" ");
            int x = Integer.parseInt(splited[0]);
            int y = Integer.parseInt(splited[1]);
            int countFields = Integer.parseInt(splited[2]);
            if (verificate(x, y, countFields)) {
                if (myField.locateShip(x, y, countFields, horisontalOrt)) {
                    UI.sendMessage("Корабль успешно расположен!");
                    UI.showField(myField);
                } else {
                    UI.sendMessage("Невозможно расположить корабль так");
                }
            } else {
                UI.sendMessage("Корбль или его координаты не корректны");
            }
        }
    }

    private boolean verificate(int x, int y, int count) {
        boolean countFieldTrue ;
        if (count == 1) {
            countFieldTrue = myField.ships[0] < 4;
        } else if (count == 2) {
            countFieldTrue = myField.ships[1] < 3;
        } else if (count == 3) {
            countFieldTrue = myField.ships[2] < 2;
        } else if (count == 4) {
            countFieldTrue = myField.ships[3] < 1;
        } else {
            countFieldTrue = false;
        }
        return countFieldTrue && x >= 0 && x <= 9 && y >= 0 && y <= 9 && count >= 1 && count <= 4;
    }

    public Pair doMove() throws IOException {
        String message = UI.getMessage();
        return new Pair(Integer.parseInt(message.substring(0, 1)), Integer.parseInt(message.substring(2, 3)));
    }

    public void win() {
        UI.sendMessage(name + " вы вйграли!(");
    }
    public void loos() {
        UI.sendMessage(name + " вы проиграли!)");
    }

    public UserInterface getUI() {
        return UI;
    }

    public String getName() {
        return name;
    }

    public MyField getMyField() {
        return myField;
    }

    public EnemyField getEnemyField() {
        return enemyField;
    }
}
