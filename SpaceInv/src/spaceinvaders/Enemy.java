package spaceinvaders;



public class Enemy extends Model {
    private Bomb bomb;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        bomb = new Bomb(x, y);
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void act(int dir) {
        this.x += dir;
    }




}
