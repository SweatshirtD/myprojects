package spaceinvaders;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class SpaceAdapter extends KeyAdapter {

    public void keyReleased(KeyEvent e) {
        Gameplay.getSpaceship().KeyReleased(e);
    }

    public void keyPressed(KeyEvent e) {

        Gameplay.getSpaceship().KeyPressed(e);

        int x = Gameplay.getSpaceship().getX();
        int y = Gameplay.getSpaceship().getY();


        if (Gameplay.getPlaying()) {
            try {
                Robot robot = new Robot();
                if (e.isAltDown()) {
                    if (!Gameplay.getBlast().isVsb()) {
                        Gameplay.setBlast(x,y);
                        robot.keyRelease(KeyEvent.VK_A);
                    }
                }
                robot.keyRelease(KeyEvent.VK_A);
            }  catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }


}
