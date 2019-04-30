package snake;

import processing.core.PApplet;

/**
 * 
 * @author Sylvain Gilgen
 *
 */
public class Element {
	protected PApplet p;
	protected int xPos;
	protected int yPos;
	protected int rad;
	protected int fill;

	/**
	 * Konstruktor für Element Klasse
	 * @param p
	 * @param xPos
	 * @param yPos
	 * @param rad
	 * @param fill
	 */
	public Element(PApplet p, int xPos, int yPos, int rad, int fill) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.p = p;
		this.rad = rad;
		this.fill = fill;
	}
	
	/**
	 * Konstruktor für Element Klasse
	 * @param p
	 * @param xPos
	 * @param yPos
	 */
	public Element(PApplet p, int xPos, int yPos) {
		this.p = p;
		this.xPos = xPos;
		this.yPos = yPos;
	}
}
