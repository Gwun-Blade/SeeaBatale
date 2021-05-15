package my.sb.game.fields;

import my.sb.game.Game;
import my.sb.game.ShotToken;

public class MyField extends BattleField {
    private final int[] ships;

    public MyField() {
        super();
        ships = new int[5];
    }
    /**
     * Метод, размещающий корабль с колличесвом палуб countShipFields
     * с началом в клетке (х, у) с нужной ориентацией
     * возвращаяет true и размещает корабль на заданую
     * позицию, если это можно сделать иначе возвращает false**/
    public boolean locateShip(int x, int y, int countShipFields, boolean horOrt) {
        if  (verificate(x, y, countShipFields) &&
                notBorderInter(x , y, countShipFields, horOrt) &&
                    notShipInter(x, y, countShipFields, horOrt)) {
            for (int i = 0; i < countShipFields; i++)
                if (horOrt)
                    setCellsValue(x + i, y, 1);
                else
                    setCellsValue(x, y + i, 1);
            ships[countShipFields]++;
            return true;
        }
        else {
            return false;
        }
    }

    private boolean notBorderInter(int x, int y, int countShipFields, boolean horOrt) {
        for (int i = 0; i < countShipFields; i++) {
            if (horOrt) {
                if (isOut(x + i, y))
                    return false;
            }
            else {
                if(isOut(x, y + i))
                    return false;
            }
        }
        return true;
    }

    private boolean notShipInter(int x, int y, int countShipFields, boolean horOrt) {
        for (int i = -1; i < countShipFields + 1; i++) {
            if (horOrt) {
                if (hesShip(x + i, y - 1) || hesShip(x + i, y) || hesShip(x + i, y + 1))
                    return false;
            }
            else {
                if (hesShip(x - 1, y + i) || hesShip(x, y + i) || hesShip(x + 1, y + i))
                    return false;
            }
        }
        return true;
    }

    private boolean hesShip(int x, int y) {
        return isOut(x, y) || getCellsValue(x, y) == 1;
    }

    /**Делает случайную расстановку всех кораблей*/
    public void randomPlacement() {
        int length = Game.maxDesk.length - 1;
        int randX;
        int randY;
        int j = 1;
        while (length > 0) {
            int i = 0;
            while (i < j) {
                randX = (int) (Math.random() * Game.maxX + 1);
                randY = (int) (Math.random() * Game.maxY + 1);
                if (locateShip(randX, randY, length, ((int) (Math.random() * 2)) == 0)) {
                    i++;
                }
            }
            length--;
            j++;
        }
    }

    /**Возвращяет готово ли поле к игре*/
    public boolean isNotReady() {
        for (int i = 1; i <= Game.maxDesk.length; i++) {
            if (ships[i] != Game.maxDesk[i])
                return true;
        }
        return false;
    }

    private boolean verificate(int x, int y, int count) {
        boolean countFieldTrue = switch (count) {
            case 1 -> ships[1] < 4;
            case 2 -> ships[2] < 3;
            case 3 -> ships[3] < 2;
            case 4 -> ships[4] < 1;
            default -> false;
        };
        return countFieldTrue && x >= 0 && x <= Game.maxX && y >= 0 && y <= Game.maxY;
    }

    /**Возвращяет, остались ли на поле живые корабли*/
    public boolean allShipDead() {
        return ships[1] + ships[2] + ships[3] + ships[4] <= 0;
    }

    @Override
    public void executeMove (ShotToken token) {
        super.executeMove(token);
        if (token.shotResult == ShotToken.ShotResult.KILL) {
            ships[token.countShipFields]--;
        }
    }

    public int getShipsCount(int countDesk) {
        if (countDesk > 0 && countDesk < Game.maxDesk.length)
            return ships[countDesk];
        else
            return -1;
    }
}
