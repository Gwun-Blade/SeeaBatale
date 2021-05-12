package my.sb.game.fields;

import my.sb.game.ShotToken;

public class MyField extends BattleField {
    public int ships[]; //подумать над приватностью

    public MyField() {
        super();
        ships = new int[4];
    }
    /**Метод, размещающий корабль с колличесвом палуб countShipFilds
     * с началом в клетке (х, у) с нужной ориентацией
     * возвращаяет true и размещает корабль на заданую
     * позицию, если это можно сделать иначе возвращает false**/

    /**Делает случайную расстановку всех кораблей*/
    public void randomPlacement() {
        int length = 4;
        int randX;
        int randY;
        int j = 1;
        while (length > 0) {
            int i = 0;
            while (i < j) {
                randX = (int) (Math.random() * 10);
                randY = (int) (Math.random() * 10);
                if (locateShip(randX, randY, length, ((int) (Math.random() * 2)) == 0)) {
                    i++;
                }
            }
            length--;
            j++;
        }
    }


    /**Возвращяет готово ли поле к игре*/
    public boolean isReady() {
        return ships[0] == 4 && ships[1] == 3 && ships[2] == 2 && ships[3] == 1;
    }

    /**Вызывается, когда умирает корабль этого поля*/
    public void killShip(int countField) {
        ships[countField - 1]--;
    }

    /**Возвращяет, остались ли на поле живые корабли*/
    public boolean hesAlivedShips() {
        return ships[0] + ships[1] + ships[2] + ships[3] > 0;
    }

    @Override
    public void executeMove (ShotToken token) {
        super.executeMove(token);
        if (token.shotResult == ShotToken.ShotResult.KILL) {
            killShip(token.countShipFields);
        }
    }
}
