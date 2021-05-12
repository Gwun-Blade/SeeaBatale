package my.sb.game.fields;

public class EnemyField extends BattleField {
    public EnemyField() {
        super();
    }

    public void doTerne(int x, int y, int val) {
        super.setCellsValue(x, y, val);
    }

    public void killShip(int x, int y, int longed, boolean horOrt) {

    }
}
