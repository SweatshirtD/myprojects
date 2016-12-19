package spaceinvaders;

import java.awt.*;


public class Model {
    private boolean vsb;
    private Image img;
    protected int x;
    protected int y;
    protected boolean rip;
    protected int xDir;

    public Model() {
        vsb = true;
    }

    public void die() {
        vsb = false;
    }

    public boolean isVsb() {
        return vsb;
    }

    protected void setVsb(boolean vsb) {
        this.vsb = vsb;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Image getImg() {
        return img;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setRip(boolean rip) {
        this.rip = rip;
    }

    public boolean isRip() {
        return this.rip;
    }
}
