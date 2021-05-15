package my.sb.game.fields;

import my.sb.game.Game;
import my.sb.game.InvalidShotException;
import my.sb.game.ShotToken;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Скороход Радхараман
 * @version 1.0.0
 * 0 - пустое поле, что угодно
 * 1 - поле с целым кораблем
 * -1 - поле с подбитым или убитым кораблем
 * 2 - поле в которое стреляли с результатом "промах"
* */

public class BattleField {
    private ArrayList<ArrayList<Integer>> bField;
    private HashMap<Integer, Character> formatTable;

    BattleField() {
        bField = new ArrayList<>(10);
        for (int i = 0; i <= Game.maxX; i++) {
            bField.add(new ArrayList<>(10));
            for (int j = 0; j <= Game.maxY; j++) {
                bField.get(i).add(j, 0);
            }
        }
    }
    public static boolean isOut(int x, int y) {
        return x < 0 || x > Game.maxX || y < 0 || y > Game.maxY;
    }

    public ShotToken getResultMove(int x, int y) throws InvalidShotException {
        if (y < 0 || y  > Game.maxX || x < 0 || x > Game.maxY) {
            throw new InvalidShotException("Координаты за гранью размеров поля\n", x, y, this);
        }
        ShotToken token = new ShotToken();
        int countShipFields = 0;
        int result = getCellsValue(x, y);
        if (result == 0) {
            token.miss(x, y);
        } else if (result == 1) {
            if (Math.abs(getCellsValue(x, y + 1)) == 1 ||  //вертикальная ветка
                    Math.abs(getCellsValue(x, y - 1)) == 1) {
                int newY = y;
                while (Math.abs(getCellsValue(x, newY - 1)) == 1) { //дошли до верха
                    newY--;
                }
                int beginsY;
                if (isOut(x,newY - 1)) {
                    beginsY = newY;
                }
                else {
                    beginsY = newY + 1;
                }
                while (getCellsValue(x, newY) == -1) { //Идем вниз, пока не найдем первое не простреленое поле
                    newY++;
                    countShipFields++;
                }
                if (getCellsValue(x, newY) == 1) { //Если это поле коробля - тот не потоплен
                    token.hit(x, y);
                } else { //Иначе, мы прошли весь корабль и убедились, что он потоплен
                    token.kill(x, beginsY, countShipFields, false);
                }
            } else if (Math.abs(getCellsValue(x + 1, y)) == 1 ||  //горизонтальная ветка
                    Math.abs(getCellsValue(x - 1, y)) == 1) {
                int newX = x;
                while (Math.abs(getCellsValue(newX - 1, y)) == 1) { //дошли до левого края
                    newX--;
                }
                int beginsX;
                if (isOut(newX - 1, y)) {
                    beginsX = newX;
                }
                else {
                    beginsX = newX + 1;
                }
                while (getCellsValue(newX, y) == -1) { //Идем вправо, пока не найдем первое поле, не -1
                    newX++;
                    countShipFields++;
                }
                if (getCellsValue(newX, y) == 1) { //Если это поле коробля - тот не потоплен
                    token.hit(x, y);
                } else { //Иначе, мы прошли весь корабль и убедились, что он потоплен
                    token.kill(beginsX, y, countShipFields, true);
                }
            } else { //однопалубный случай
                token.kill(x, y, 1, true);
            }
        } else {
            throw new InvalidShotException("Неверное значение поля или в него уже стреляли\n", x, y, this);
        }
        return token;
    }
    /**Получаем от противника координаты, выдаем результат хода
     * 0 - мимо
     * 1 - попал
     * 2 - убил
     * 3 - вы уже стреляли в это поле
     * -1 - невозможные координаты
     * */

    public void executeMove (ShotToken token) {
        //может быть стоит сделать валидацию токена
        switch (token.shotResult) {
            case MISS -> setCellsValue(token.x, token.y, 2);
            case HIT -> setCellsValue(token.x, token.y, -1);
            case KILL -> {
                if (token.horOrt) {
                    setCellsValue(token.x - 1, token.y, 2); //сзади
                    setCellsValue(token.x + token.countShipFields, token.y, 2); //спереди
                    for (int i = -1; i < token.countShipFields + 1; i++) {
                        setCellsValue(token.x + i, token.y + 1, 2); //снизу
                        setCellsValue(token.x + i, token.y - 1, 2); //сверху
                    }
                    for (int i = 0; i < token.countShipFields; i++) {
                        setCellsValue(token.x + i, token.y, -1); //сам корабль
                    }
                }
                else {
                    setCellsValue(token.x, token.y - 1, 2); //свеху
                    setCellsValue(token.x, token.y + token.countShipFields, 2); //снизу
                    for (int i = -1; i < token.countShipFields + 1; i++) {
                        setCellsValue(token.x + 1, token.y + i, 2); //справа
                        setCellsValue(token.x - 1, token.y + i, 2); //слева
                    }
                    for (int i = 0; i < token.countShipFields; i++) {
                        setCellsValue(token.x, token.y + i, -1); //сам корабль
                    }
                }
            }
        }
    }

    int getCellsValue(int x, int y) {
        if (isOut(x, y))
            return 0;
        else
            return bField.get(y).get(x);
    }

    void setCellsValue(int x, int y, int val) {
        if (!isOut(x, y))
            bField.get(y).set(x, val);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(". |0|1|2|3|4|5|6|7|8|9\n");
        for (int i = 0; i < 10; i++) {
            builder.append(i);
            builder.append(" |");
            for (int j = 0; j < 10; j++) {
                builder.append(formatSymbol(getCellsValue(i, j)));
                builder.append(' ');
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private char formatSymbol(int input) {
        if (formatTable == null) {
            formatTable = new HashMap<>(4);
            formatTable.put(0, '.');
            formatTable.put(-1, 'X');
            formatTable.put(1, '+');
            formatTable.put(2, '-');
        }
        return formatTable.get(input);
    }
}
