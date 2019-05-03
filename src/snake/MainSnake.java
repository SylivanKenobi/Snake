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
	public static final int appleCount = 50;
	public static int timer = 20;
	AppleCollection apple;
	SpielerSnake snakeOne;
	SpielerSnake snakeTwo;
	Modus modus;
	PVector vectorSnakeOne, vectorSnakeTwo;

	public static void main(String[] args) {
		PApplet.main("snake.MainSnake");
	}

	public void settings() {
		size(displayWidth - 100, displayHeight - 100);
	}

	public void setup() {
		modus = new Modus(this);
		snakeOne = new SpielerSnake(this, width - 20, height / 2, 38, 40, 37, 39);
		snakeTwo = new SpielerSnake(this, 20, height / 2, 87, 83, 65, 68);
		apple = new AppleCollection(this);
		apple.createApples(appleCount);
	}

	public void draw() {
		modus.pointsAusgabe(snakeOne.points(), snakeTwo.points());
		switch (modus.getGameState()) {
		case Modus.GAME_MENU:
			modus.menu();
			break;
		case Modus.GAME_RUNNING:
			vectorSnakeOne = new PVector(snakeOne.xPos, snakeOne.yPos);
			vectorSnakeTwo = new PVector(snakeTwo.xPos, snakeTwo.yPos);
			snakeOne.collisionDetection(apple, vectorSnakeOne);
			snakeTwo.collisionDetection(apple, vectorSnakeTwo);
			switch (modus.getSelectedMode()) {
			case Modus.MULTIPLAYER:
				twoPlayer();
				modus.twoPlayer();
				snakeTwo.move();
				snakeTwo.display();
				break;
			case Modus.ARCADE:
				modus.arcade();
				break;
			case Modus.VS_PC:
				twoPlayer();
				modus.twoPlayer();
				snakeTwo.move();
				snakeTwo.display();
				snakeTwo.autoMove(apple, this);
				snakeTwo.direction();
			}
			snakeOne.move();
			snakeOne.display();
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
		snakeOne.direction();
		snakeTwo.direction();
		if (keyCode == 80) {
			modus.setGameState(2);
		}
	}

	public void twoPlayer() {
		snakeOne.tail.tailGotHit(vectorSnakeOne);
		snakeTwo.tail.tailGotHit(vectorSnakeOne);
		if (vectorSnakeOne.dist(vectorSnakeTwo) < snakeOne.rad) {
			textSize(100);
			text("Game Over", 500, 400);
			modus.setGameState(3);
		}

	}
}
