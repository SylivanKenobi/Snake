package snake;

import processing.core.*;

/**
 * 
 * @author Sylvain Gilgen
 * 
 * @version V 1.0
 *
 */

public class MainSnake extends PApplet {
	public static final int appleCount = 120;
	public static int timer = 120;
	AppleCollection apple;
	SpielerSnake player1;
	SpielerSnake player2;
	Modus modus;
	PVector p1, p2;

	public static void main(String[] args) {
		PApplet.main("snake.MainSnake");
	}

	public void settings() {
		size(displayWidth - 100, displayHeight - 100);
	}

	public void setup() {
		modus = new Modus(this);
		player1 = new SpielerSnake(this, width - 20, height / 2, 38, 40, 37, 39);
		player2 = new SpielerSnake(this, 20, height / 2, 87, 83, 65, 68);
		apple = new AppleCollection(this);
		apple.createApples(appleCount);

	}

	public void draw() {
		modus.pointsAusgabe(player1.points(), player2.points());
		switch (modus.getGameState()) {
		case Modus.GAME_MENU:
			modus.menu();
			break;
		case Modus.GAME_RUNNING:
			p1 = new PVector(player1.xPos, player1.yPos);
			p2 = new PVector(player2.xPos, player2.yPos);
			player1.collisionDetection(apple, p1);
			player2.collisionDetection(apple, p2);
			switch (modus.getSelectedMode()) {
			case Modus.MULTIPLAYER:
				twoPlayer();
				modus.twoPlayer();
				player2.move();
				player2.display();
				break;
			case Modus.ARCADE:
				modus.arcade();
				break;
			case Modus.VS_PC:
				twoPlayer();
				modus.twoPlayer();
				player2.move();
				player2.display();
				player2.bot(apple);
				player2.direction();
			}
			player1.move();
			player1.display();
			for (Apple i : apple) {
				i.display();
			}
			break;
		case Modus.PAUSED:
			modus.pause();
			break;
		case Modus.GAME_OVER:
			switch (modus.getSelectedMode()) {
			case Modus.MULTIPLAYER:
				modus.fertigTwoPlayer();
				break;
			case Modus.ARCADE:
				modus.fertigArcade();
				break;
			case Modus.VS_PC:
				modus.fertigTwoPlayer();
				break;
			}
			break;
		case Modus.GAME_RESTART:
			setup();
			break;
		}
	}

	public void keyPressed() {
		player1.direction();
		player2.direction();
		if (keyCode == 80) {
			modus.setGameState(2);
		}
	}

	public void twoPlayer() {
		player1.tail.tailGotHit(p2);
		player2.tail.tailGotHit(p1);
		if (p1.dist(p2) < player1.rad) {
			textSize(100);
			text("Game Over", 500, 400);
			modus.setGameState(3);
		}

	}
}
