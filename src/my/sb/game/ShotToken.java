package my.sb.game;

public class ShotToken {
    public enum ShotResult {MISS, HIT, KILL}
    public ShotResult shotResult;
    public int x;
    public int y;
    public int countShipFields;
    public boolean horOrt;

    public void miss(int x, int y) {
        shotResult = ShotResult.MISS;
        this.x = x;
        this.y = y;
    }

    public void hit(int x, int y) {
        shotResult = ShotResult.HIT;
        this.x = x;
        this.y = y;
    }

    public void kill(int x, int y, int countShipFields, boolean horOrt) {
        shotResult = ShotResult.KILL;
        this.x = x;
        this.y = y;
        this.countShipFields = countShipFields;
        this.horOrt = horOrt;
    }
}
