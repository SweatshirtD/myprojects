package spaceinvaders;

import javax.swing.*;
import java.awt.event.KeyEvent;


public class Spaceship extends Model  {

    private final int startPosY = 350;
    private final int startPosX = 230;

    private final String spaceshipPic = "src\\pics\\spaceship.png";
    private int width;



    public Spaceship() {
        ImageIcon icon = new ImageIcon(spaceshipPic);
        width = icon.getImage().getWidth(null);
        setImg(icon.getImage());
        setX(startPosX);
        setY(startPosY);

    }

    public void move() {
        x += xDir;
        if (x <= 2)
            x = 2;
        if (x >= Const.getGameWidth() - 2 * width)
            x = Const.getGameWidth() - 2 * width;
    }




    public void KeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {

            xDir = -2;
        }
        if (key == KeyEvent.VK_RIGHT) {
            xDir = 2;
        }
    }

    public void KeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            xDir = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            xDir = 0;
        }
    }

}

