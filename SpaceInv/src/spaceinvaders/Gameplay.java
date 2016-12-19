package spaceinvaders;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Gameplay extends JPanel implements Runnable {

    private Image image;
    private ArrayList enemies;
    private static Spaceship spcship;
    private static Blast blast;

    private String msg = "DEAD!";
    private static boolean playing = true;
    private int dir = -1;
    private int ripcounter = 0;
    private int enemyX = 150;
    private int enemyY = 5;



    private final String boom = "src\\pics\\boom.png";
    private final String enemypic = "src\\pics\\enemy.png";


    private Thread mjrThread;

    public Gameplay() {

        addKeyListener(new SpaceAdapter());
        setFocusable(true);
        image = new ImageIcon("src\\pics\\space.jpg").getImage();
        gameInit();
    }

    public void gameInit() {

        enemies = new ArrayList();
        ImageIcon icon = new ImageIcon(enemypic);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Enemy enemy = new Enemy(enemyX + 32 * j, enemyY + 24 * i);
                enemy.setImg(icon.getImage());
                enemies.add(enemy);
            }
        }

        spcship = new Spaceship();
        blast = new Blast();

        if (mjrThread == null || !playing) {
            mjrThread = new Thread(this);
            mjrThread.start();
        }
    }

    public void run() {

        long startTime, diff, sleep;

        startTime = System.currentTimeMillis();

        while (playing) {
            repaint();
            Cycle();

            diff = System.currentTimeMillis() - startTime;
            sleep = Const.getSPEED() - diff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            startTime = System.currentTimeMillis();
        }
        gameOver();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.green);
        if (playing) {

            g.drawLine(0, Const.getInvasionLine(), Const.getGameWidth(), Const.getInvasionLine());
            drawEnemies(g);
            drawSpaceship(g);
            drawBlast(g);
            drawBombing(g);
        }
    }

    public void drawEnemies(Graphics g) {
        Iterator counter = enemies.iterator();

        while (counter.hasNext()) {
            Enemy enemy = (Enemy) counter.next();

            if (enemy.isVsb()) {
                g.drawImage(enemy.getImg(), enemy.getX(), enemy.getY(), this);
            }

            if (enemy.isRip()) {
                enemy.die();
            }
        }
    }

    public void drawSpaceship(Graphics g) {

        if (spcship.isVsb()) {
            g.drawImage(spcship.getImg(), spcship.getX(), spcship.getY(), this);
        }

        if (spcship.isRip()) {
            spcship.die();
            playing = false;
        }
    }

    public void drawBlast(Graphics g) {
        if (blast.isVsb())
            g.drawImage(blast.getImg(), blast.getX(), blast.getY(), this);
    }

    public void drawBombing(Graphics g) {

        Iterator bombingcounter = enemies.iterator();

        while (bombingcounter.hasNext()) {
            Enemy e = (Enemy) bombingcounter.next();

            Bomb bomb = e.getBomb();

            if (!bomb.isWrecked()) {
                g.drawImage(bomb.getImg(), bomb.getX(), bomb.getY(), this);
            }
        }
    }



    public void Cycle() {

        if (ripcounter == Const.getEnemyNumber()) {
            playing = false;
            msg = "YOU WON!";
        }
        spcship.move();
        moveBlast();
        moveEnemies();
        bombing();
    }



    public void moveBlast() {
        if (blast.isVsb()) {
            Iterator enemcounter = enemies.iterator();
            int shotX = blast.getX();
            int shotY = blast.getY();

            while (enemcounter.hasNext()) {
                Enemy enemy = (Enemy) enemcounter.next();
                int enemyX = enemy.getX();
                int enemyY = enemy.getY();

                if (enemy.isVsb() && blast.isVsb()) {
                    if (shotX >= (enemyX) && shotX <= (enemyX + Const.getEnemyWidth()) && shotY >= (enemyY) && shotY <= (enemyY + Const.getEnemyWidth())) {
                        ImageIcon icon = new ImageIcon(boom);
                        enemy.setImg(icon.getImage());
                        enemy.setRip(true);
                        ripcounter++;
                        blast.die();
                    }
                }
            }

            int y = blast.getY();
            y -= 4;
            if (y < 0)
                blast.die();
            else blast.setY(y);
        }


    }

    public void moveEnemies() {
        Iterator enemcounter2 = enemies.iterator();

        while (enemcounter2.hasNext()) {
            Enemy en1 = (Enemy) enemcounter2.next();
            int x = en1.getX();

            if (x >= Const.getGameWidth() - Const.getRIGHTSIDE() && dir != -1) {
                dir = -1;
                Iterator i1 = enemies.iterator();
                while (i1.hasNext()) {
                    Enemy en2 = (Enemy) i1.next();
                    en2.setY(en2.getY() + Const.getDOWN());
                }
            }

            if (x <= Const.getLEFTSIDE() && dir != 1) {
                dir = 1;

                Iterator i2 = enemies.iterator();
                while (i2.hasNext()) {
                    Enemy en = (Enemy) i2.next();
                    en.setY(en.getY() + Const.getDOWN());
                }
            }
        }


        Iterator iterator = enemies.iterator();

        while (iterator.hasNext()) {
            Enemy enemy = (Enemy) iterator.next();
            if (enemy.isVsb()) {

                int y = enemy.getY();

                if (y > Const.getInvasionLine() - Const.getEnemyHeight()) {
                    playing = false;
                    msg = "Invasion!";
                }

                enemy.act(dir);
            }
        }

    }

    public void bombing() {
        Iterator enemcounter3 = enemies.iterator();
        Random generator = new Random();

        while (enemcounter3.hasNext()) {
            int fire = generator.nextInt(15);
            Enemy e = (Enemy) enemcounter3.next();
            Bomb b = e.getBomb();
            if (fire == Const.getCHANCE() && e.isVsb() && b.isWrecked()) {

                b.setWrecked(false);
                b.setX(e.getX());
                b.setY(e.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int shipX = spcship.getX();
            int shipY = spcship.getY();

            if (spcship.isVsb() && !b.isWrecked()) {
                if (bombX >= (shipX) &&
                        bombX <= (shipX + Const.getSpaceshipWidth()) &&
                        bombY >= (shipY) &&
                        bombY <= (shipY + Const.getSpaceshipHeight())) {
                    ImageIcon icon = new ImageIcon(boom);
                    spcship.setImg(icon.getImage());
                    spcship.setRip(true);
                    b.setWrecked(true);
                    ;
                }
            }

            if (!b.isWrecked()) {
                b.setY(b.getY() + 1);
                if (b.getY() >= Const.getInvasionLine() - Const.getBombSize()) {
                    b.setWrecked(true);
                }
            }
        }

    }



    public void gameOver() {

        Graphics g = this.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, Const.getGameWidth(), Const.getGameHeight());

        g.setColor(new Color(132, 172, 226));
        g.fillRect(50, Const.getGameWidth() / 2 - 30, Const.getGameWidth() - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Const.getGameWidth() / 2 - 30, Const.getGameWidth() - 100, 50);

        Font font = new Font("Times New Roman", Font.BOLD, 16);
        FontMetrics metrix = this.getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (Const.getGameWidth() - metrix.stringWidth(msg)) / 2,
                Const.getGameWidth() / 2);
    }


    public static Spaceship getSpaceship() {
        return spcship;
    }

    public static void setBlast(int x, int y) {
        blast = new Blast(x, y);
    }

    public static boolean getPlaying() {
        return playing;
    }

    public static Blast getBlast() {
        return blast;
    }


}