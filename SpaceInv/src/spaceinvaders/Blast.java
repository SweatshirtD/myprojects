package spaceinvaders;

import javax.swing.*;

public class Blast extends Model {

    private String blast = "src\\pics\\blast.png";
    private final int horizSpace = 10;
    private final int vertSpace = 19;

    public Blast() {
    }

    public Blast(int x, int y) {
        ImageIcon icon = new ImageIcon(blast);
        setImg(icon.getImage());
        setX(x + horizSpace);
        setY(y - vertSpace);
    }
}
