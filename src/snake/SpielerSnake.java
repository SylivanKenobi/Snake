package snake;

import java.util.*;
import javax.swing.JOptionPane;
import processing.core.*;

/**
 * 
 * @author Sylvain Gilgen
 *
 */
public class SpielerSnake extends Element {
	boolean isRight, isLeft, isUp, isDown = false;
	private PVector player;
	private int keyUp, keyDown, keyLeft, keyRight, speedy, enemysHit, indexVonGegner, gegnerAuswahl, enemyGone,
			schwierigkeit, minSchwierigkeit;
	public SnakeTail tail;
	private Float[][] distanzMap;
	private ArrayList<Apple> toRemoveG;
	private PVector enemy;
	private String level = "";

	public SpielerSnake(PApplet p, int xPos, int yPos, int keyUp, int keyDown, int keyLeft, int KeyRight) {
		super(p, xPos, yPos);
		speedy = 20;
		this.keyDown = keyDown;
		this.keyLeft = keyLeft;
		this.keyRight = KeyRight;
		this.keyUp = keyUp;
		rad = 50;
		minSchwierigkeit = 30;
		schwierigkeit = 10;
		tail = new SnakeTail(p);
		distanzMap = new Float[MainSnake.appleCount][2];
		toRemoveG = new ArrayList<Apple>();
		enemyGone = -1;
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
	 * Ki für den Spieler
	 * @param apples
	 */
	public void bot(AppleCollection apples) {
		if (level == "") {
			do {
				level = JOptionPane.showInputDialog("level? 1,2,3");
			} while (level == null);
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
				player = new PVector(xPos, yPos);
				enemy = new PVector(i.xPos, i.yPos);
				if (j == 0) {
					distanzMap[apples.indexOf(i)][j] = player.dist(enemy);
				} else {
					distanzMap[apples.indexOf(i)][j] = (float) apples.indexOf(i);
				}
			}
		}
		Arrays.sort(distanzMap, (a, b) -> Float.compare(a[0], b[0]));
		if (enemyGone < tail.size()) {
			gegnerAuswahl = (int) p.random(distanzMap.length * schwierigkeit) / minSchwierigkeit;
			indexVonGegner = Math.round(distanzMap[gegnerAuswahl][1]);
			enemyGone++;
		}
		enemy = new PVector(apples.get(indexVonGegner).xPos, apples.get(indexVonGegner).yPos);
		if (enemy.x < player.x && isRight != true) {
			isLeft = true;
			isRight = false;
			isDown = false;
			isUp = false;
		} else if (enemy.x > player.x && isLeft != true) {
			isRight = true;
			isLeft = false;
			isDown = false;
			isUp = false;
		} else if (enemy.y < player.y && isDown != true) {
			isUp = true;
			isDown = false;
			isLeft = false;
			isRight = false;
		} else if (enemy.y > player.y && isUp != true) {
			isDown = true;
			isUp = false;
			isLeft = false;
			isRight = false;
		}
	}

	/**
	 * Kollisionen zwischen Spieler und Apples erkennen
	 * @param apples
	 * @param p
	 */
	public void collisionDetection(AppleCollection apples, PVector player) {
		enemysHit = 0;
		for (Apple e : apples) {
			float dist1 = PApplet.dist(e.xPos, e.yPos, xPos, yPos);
			if (dist1 < rad) {
				tail.grow(player);
				toRemoveG.add(e);
				enemysHit++;
			}
		}
		apples.newApple(enemysHit);
		apples.removeAll(toRemoveG);
		tail.snake(player);
		tail.hitTail(player);
	}

	/**
	 * Punkte des Spielers ausgeben
	 * @return punkte des Spielers
	 */
	public int points() {
		return tail.size();
	}
}
