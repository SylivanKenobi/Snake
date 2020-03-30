package snake;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * @author Sylvain Gilgen
 */
public class Apple extends Element {

    /**
     * Konstruktor zum Apple erstellen
     *
     * @param p    PApplet
     * @param xPos x-Position des Apfels
     * @param yPos y-Position des Apfels
     * @param rad  Radius des Apfels
     * @param fill Farbe des Apfels
     */
    public Apple(PApplet p, int xPos, int yPos, int rad, int fill) {
        super(p, xPos, yPos, rad, fill);
    }

    /**
     * Apple darstellen
     */
    public void display() {
        PImage apple = p.loadImage("assets/apple.png");
        p.image(apple, xPos, yPos, rad, rad);
    }

}
