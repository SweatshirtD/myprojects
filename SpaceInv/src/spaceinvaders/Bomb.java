package spaceinvaders;

import javax.swing.*;

public class Bomb extends Model {

    private boolean wrecked;
    private final String bomb = "src\\pics\\bomb.png";


    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        ImageIcon icon = new ImageIcon(bomb);
        setImg(icon.getImage());
        setWrecked(true);
    }

    public void setWrecked(boolean wrecked) {
        this.wrecked = wrecked;
    }

    public boolean isWrecked() {
        return wrecked;
    }


}