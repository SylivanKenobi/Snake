package snake;

import java.util.*;
import processing.core.*;

/**
 * 
 * @author Sylvain Gilgen
 *
 */
public class AppleCollection extends ArrayList<Apple> {
	PApplet p;
	Apple apple;
	private int x, y;
	PVector newXY, oldXY;
	boolean isValid;

	public AppleCollection(PApplet p) {
		this.p = p;
	}

	/**
	 * Apples erstellen und in der Collection speichern
	 * @param anz
	 */
	public void createApples(int anz) {
		while (this.size() < anz) {
			isValid = true;
			x = ((int) p.random(p.width - 50)) / 20;
			y = ((int) p.random(p.height - 200)) / 20;
			x = (x * 20) + 60;
			y = (y * 20) + 220;
			newXY = new PVector(x, y);
			for (Apple i : this) {
				oldXY = new PVector(i.xPos, i.yPos);
				if (oldXY.dist(newXY) < i.rad + 5) {
					isValid = false;
				}
			}
			if (isValid == true) {
				this.add(apple = new Apple(p, x, y, 20, (int) p.random(255)));
			}
		}
	}
	
	/**
	 * Neuer Apple erstellen und in der Collection speichern
	 * @param anz
	 */
	public void newApple(int anz) {
		int counter = 0;
		while (counter < anz) {
			isValid = true;
			x = ((int) p.random(p.width - 50)) / 20;
			y = ((int) p.random(p.height - 200)) / 20;
			x = (x * 20) + 60;
			y = (y * 20) + 220;
			newXY = new PVector(x, y);
			for (Apple i : this) {
				oldXY = new PVector(i.xPos, i.yPos);
				if (oldXY.dist(newXY) < i.rad + 5) {
					isValid = false;
				}
			}
			if (isValid == true) {
				this.add(apple = new Apple(p, x, y, 20, (int) p.random(255)));
				counter++;
			}			
		}
	}
}
