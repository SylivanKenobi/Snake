package snake;

import java.util.*;
import javax.swing.JOptionPane;
import processing.core.*;

/**
 * Diese Klasse ist die Schlange
 * @author Sylvain Gilgen
 *
 */

public class SpielerSnake extends Element {
	protected boolean isRight, isLeft, isUp, isDown = false;
	private PVector vectorSnake;
	private int keyUp, keyDown, keyLeft, keyRight, speedy, enemysHit, indexVonGegner, gegnerAuswahl, applesGone,
			schwierigkeit, minSchwierigkeit;
	public SnakeTail tail;
	private Float[][] distanzMapToApples;
	private ArrayList<Apple> toRemove;
	private PVector vectorApple;
	private String level;

	/**
	 * Kunstruktor zum erstellen einer neuen Schlange
	 * @param p PApplet
	 * @param xPos x-Position der Schlange
	 * @param yPos y-Position der Schlange
	 * @param keyUp keyCode der Taste zum nach Oben bewegen
	 * @param keyDown keyCode der Taste zum nach Unten bewegen
	 * @param keyLeft keyCode der Taste zum nach Links bewegen
	 * @param KeyRight keyCode der Taste zum nach Rechts bewegen
	 */
	public SpielerSnake(PApplet p, int xPos, int yPos, int keyUp, int keyDown, int keyLeft, int KeyRight) {
		super(p, xPos, yPos);
		speedy = 20;
		this.keyDown = keyDown;
		this.keyLeft = keyLeft;
		this.keyRight = KeyRight;
		this.keyUp = keyUp;
		rad = 40;
		minSchwierigkeit = 30;
		schwierigkeit = 10;
		tail = new SnakeTail(p);
		distanzMapToApples = new Float[MainSnake.appleCount][2];
		toRemove = new ArrayList<Apple>();
		applesGone = -1;
	}

	/**
	 * Spieler bewegen
	 */
	public void move() {
		if (this.isLeft) {
			xPos -= speedy;
		} else if (this.isRight) {
			xPos += speedy;
		}
		if (this.isUp) {
			yPos -= speedy;
		} else if (this.isDown) {
			yPos += speedy;
		}
		if (xPos < 0) {
			xPos = p.width;
		}
		if (xPos > p.width) {
			xPos = 0;
		}
		if (yPos < 210) {
			yPos = p.height;
		}
		if (yPos > p.height) {
			yPos = 200;
		}
	}

	/**
	 * Richtungsangabe für Spieler bei Tastendruck
	 */
	public void direction() {
		if (p.keyCode == keyLeft && isRight != true) {
			isLeft = true;
			isRight = false;
			isDown = false;
			isUp = false;
		}
		if (p.keyCode == keyRight && isLeft != true) {
			isRight = true;
			isLeft = false;
			isDown = false;
			isUp = false;
		}
		if (p.keyCode == keyUp && isDown != true) {
			isUp = true;
			isDown = false;
			isLeft = false;
			isRight = false;
		}
		if (p.keyCode == keyDown && isUp != true) {
			isDown = true;
			isUp = false;
			isLeft = false;
			isRight = false;
		}
	}

	/**
	 * Spieler darstellen
	 */
	public void display() {
		p.fill(255);
		p.ellipse(xPos, yPos, rad, rad);
		tail.drawSnake();
	}

	/**
	 * Mit dieser Methode spielt sich die Schlange selbst.
	 * 
	 * @param apples
	 */
	public void autoMove(AppleCollection apples, MainSnake main) {
		if (level == null) {
			do {
				level = JOptionPane.showInputDialog("level 1,2,3?");
				if (level == null) {
					main.setup();
					return;
				}
			} while (isValid() == false);
			switch (level) {
			case "1":
				schwierigkeit = 30;
				break;
			case "2":
				schwierigkeit = 20;
				break;
			case "3":
				schwierigkeit = 10;
				break;
			}
		}
		for (Apple i : apples) {
			for (int j = 0; j < 2; j++) {
				vectorSnake = new PVector(xPos, yPos);
				vectorApple = new PVector(i.xPos, i.yPos);
				if (j == 0) {
					distanzMapToApples[apples.indexOf(i)][j] = vectorSnake.dist(vectorApple);
				} else {
					distanzMapToApples[apples.indexOf(i)][j] = (float) apples.indexOf(i);
				}
			}
		}
		Arrays.sort(distanzMapToApples, (a, b) -> Float.compare(a[0], b[0]));
		if (applesGone < tail.size()) {
			gegnerAuswahl = (int) p.random(distanzMapToApples.length * schwierigkeit) / minSchwierigkeit;
			indexVonGegner = Math.round(distanzMapToApples[gegnerAuswahl][1]);
			applesGone++;
		}
		vectorApple = new PVector(apples.get(indexVonGegner).xPos, apples.get(indexVonGegner).yPos);
		if (vectorApple.x < vectorSnake.x && isRight != true) {
			isLeft = true;
			isRight = false;
			isDown = false;
			isUp = false;
		} else if (vectorApple.x > vectorSnake.x && isLeft != true) {
			isRight = true;
			isLeft = false;
			isDown = false;
			isUp = false;
		} else if (vectorApple.y < vectorSnake.y && isDown != true) {
			isUp = true;
			isDown = false;
			isLeft = false;
			isRight = false;
		} else if (vectorApple.y > vectorSnake.y && isUp != true) {
			isDown = true;
			isUp = false;
			isLeft = false;
			isRight = false;
		}
	}
	/*
	 * Kontrollieren ob Eingabe korrekt ist
	 */
	private boolean isValid() {
		if (!level.equals("1") && !level.equals("2") && !level.equals("3")) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Kollisionen zwischen Spieler und Apples erkennen
	 * 
	 * @param apples
	 * @param p
	 */
	public void collisionDetection(AppleCollection apples, PVector vectorSnake) {
		enemysHit = 0;
		for (Apple e : apples) {
			float dist1 = PApplet.dist(e.xPos, e.yPos, xPos, yPos);
			if (dist1 < rad) {
				tail.grow(vectorSnake);
				toRemove.add(e);
				enemysHit++;
			}
		}
		apples.newApple(enemysHit);
		apples.removeAll(toRemove);
		tail.snake(vectorSnake);
		tail.hitTail(vectorSnake);
	}

	/**
	 * Punkte des Spielers ausgeben
	 * 
	 * @return punkte des Spielers
	 */
	public int points() {
		return tail.size();
	}

	/**
	 * Getter
	 * @return
	 */
	public boolean isLeft() {
		return isLeft;
	}
}
