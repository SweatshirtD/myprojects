package spaceinvaders;

import javax.swing.*;


public class Gameframe extends JFrame {
    public Gameframe() {
        add(new Gameplay());
        setSize(Const.getGameWidth(), Const.getGameHeight());
        setLocationRelativeTo(null);
        setResizable(false);

    }
}
