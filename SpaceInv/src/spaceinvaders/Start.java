package spaceinvaders;

import javax.swing.*;
import java.awt.*;



public class Start {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Gameframe game = new Gameframe();
                game.setVisible(true);
                game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            }
        });
    }
    }