package my.sb.game;

import my.sb.game.fields.EnemyField;
import my.sb.game.fields.MyField;
import my.sb.interfaces.UserInterface;

/**
* 0 - пустое поле, что угодно
* 1 - поле с целым кораблем
* -1 - поле с подбитым или убитым кораблем
* 2 - поле где точно нет корабля противника
* */

class Player {
    private String name;
    private MyField myField;
    private EnemyField enemyField;
    private UserInterface UI;


    Player(UserInterface UI) {
        this.UI = UI;
        this.name = UI.getUserName();
        myField = new MyField();
        enemyField = new EnemyField();
        UI.arrangeShips(myField);
    }

    void win() {
        UI.sendMessage(name + " вы вйграли!(");
    }
    void loos() {
        UI.sendMessage(name + " вы проиграли!)");
    }

    UserInterface getUI() {
        return UI;
    }

    String getName() {
        return name;
    }

    MyField getMyField() {
        return myField;
    }

    EnemyField getEnemyField() {
        return enemyField;
    }
}
