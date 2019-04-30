package snake;

import processing.core.PApplet;

/**
 * 
 * @author Sylvain Gilgen
 *
 */
public class Apple extends Element {

	/**
	 * Konstruktor zum Apple erstellen
	 * @param p
	 * @param xPos
	 * @param yPos
	 * @param rad
	 * @param fill
	 */
	public Apple(PApplet p, int xPos, int yPos, int rad, int fill) {
		super(p, xPos, yPos, rad, fill);

	}

	/**
	 * Apple darstellen
	 */
	public void display() {
		p.fill(fill, 0, 0);
		p.noStroke();
		p.ellipse(xPos, yPos, rad, rad);
	}

}
