package snake;

import java.util.*;
import processing.core.*;

/*
 * Klasse des Schwanzes der Schlange
 * @author Sylvain Gilgen
 *
 */
public class SnakeTail extends ArrayList<PVector> {
	private PApplet p;
	private int rad;

	/**
	 * Konstruktor zum erstellen eines Schwanzes
	 * @param p PApplet
	 */
	public SnakeTail(PApplet p) {
		this.p = p;
		rad = 40;
	}

	/*
	 * Schwanz der Schlange verwalten
	 * 
	 * @param v
	 */
	public void snake(PVector player) {
		if (this.size() > 1) {
			for (int i = this.size() - 1; i > 0; i--) {
				this.set(i, this.get(i - 1));
			}
		}
		if (this.size() > 0) {
			this.set(0, player);
		}

	}

	/*
	 * Schwanz der Sclange anzeigen
	 */
	public void drawSnake() {
		for (PVector i : this) {
			p.noStroke();
			p.fill((this.indexOf(i) * 4), this.indexOf(i) * 2, this.indexOf(i) * 3);
			p.ellipse(i.x, i.y, rad, rad);
		}
	}

	/*
	 * Schwanz der Schlange vergrössern
	 * 
	 * @param player: PVector des Spielers
	 */
	public void grow(PVector snake) {
		this.add(snake);
	}

	/*
	 * Kontrollieren ob der Spieler seinen eigenen Schwanz getroffen hat
	 * 
	 * @param player: PVectoer des spielers
	 */
	public void hitTail(PVector snake) {
		boolean gotHit = false;
		for (int i = 1; i < this.size(); i++) {
			if (snake.dist(this.get(i)) < 2) {
				gotHit = true;
			}
		}
		if (gotHit == true) {
			this.remove(this.size() - 1);
		}

	}

	/*
	 * Kontrollieren ob der Gegner den Schwanz getroffen hat
	 * 
	 * @param player: PVector des Gegners
	 */
	public void tailGotHit(PVector snake) {
		boolean gotHit = false;
		for (int i = 1; i < this.size(); i++) {
			if (snake.dist(this.get(i)) < 20) {
				gotHit = true;
			}
		}
		if (gotHit == true) {
			this.remove(this.size() - 1);
		}
	}

}
