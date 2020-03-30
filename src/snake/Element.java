package snake;

import processing.core.PApplet;

public class Element {
    protected PApplet p;
    protected int xPos;
    protected int yPos;
    protected int rad;
    protected int fill;

    public Element(PApplet p, int xPos, int yPos, int rad, int fill) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.p = p;
        this.rad = rad;
        this.fill = fill;
    }

    public Element(PApplet p, int xPos, int yPos) {
        this.p = p;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
