package snake;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Dies ist die Hauptklasse hier kommen alle Klassen zusammen und formen das Spiel.
 *
 * @author Sylvain Gilgen
 * @version V 1.0
 */

public class MainSnake extends PApplet {
    public static final int appleCount = 30;
    public static int timer = 30;
    AppleCollection apples;
    Snake snakeOne;
    Snake snakeTwo;
    Modus modus;
    PVector vectorSnakeOne, vectorSnakeTwo;

    public static void main(String[] args) {
        PApplet.main("snake.MainSnake");
    }

    /**
     * Fenster instanziieren
     */
    public void settings() {
        size(displayWidth - 100, displayHeight - 100);
    }

    /**
     * Bestandteile des Spieles Instanziieren
     */
    public void setup() {
        modus = new Modus(this);
        snakeOne = new Snake(this, width - 20, height / 2, 38, 40, 37, 39);
        snakeTwo = new Snake(this, 20, height / 2, 87, 83, 65, 68);
        apples = new AppleCollection(this);
        apples.createApples(appleCount);
    }

    /**
     * Animantionen des Spieles
     */
    public void draw() {
        modus.pointsAusgabe(snakeOne.points(), snakeTwo.points());
        switch (modus.getGameState()) {
            case Modus.GAME_MENU:
                modus.menu();
                break;
            case Modus.GAME_RUNNING:
                playGame();
                break;
            case Modus.PAUSED:
                modus.pause();
                break;
            case Modus.GAME_OVER:
                gameOver();
                break;
            case Modus.GAME_RESTART:
                setup();
                break;
        }
    }

    /**
     * Methode zum kontrollieren ob eine Taste gedr�ckt wurde
     */
    public void keyPressed() {
        snakeOne.direction();
        snakeTwo.direction();
        if (keyCode == 80 && modus.getGameState() == Modus.GAME_RUNNING) {
            modus.setGameState(2);
        }
    }

    private void playGame() {
        vectorSnakeOne = new PVector(snakeOne.xPos, snakeOne.yPos);
        vectorSnakeTwo = new PVector(snakeTwo.xPos, snakeTwo.yPos);
        snakeOne.collisionDetection(apples, vectorSnakeOne);
        snakeTwo.collisionDetection(apples, vectorSnakeTwo);
        switch (modus.getSelectedMode()) {
            case Modus.MULTIPLAYER:
                twoPlayer();
                modus.twoPlayer();
                snakeTwo.display();
                break;
            case Modus.ARCADE:
                modus.arcade();
                break;
            case Modus.VS_PC:
                twoPlayer();
                modus.twoPlayer();
                snakeTwo.display();
                snakeTwo.setLevel();
                snakeTwo.autoMove(apples, this);
        }
        snakeOne.move();
        snakeOne.display();
        showApples();
    }

    private void showApples() {
        apples.forEach(a -> a.display());
    }

    private void gameOver() {
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
    }

    /**
     * Methode zum Zweispielermodus
     */
    private void twoPlayer() {
        snakeTwo.move();
        snakeTwo.direction();
        snakeOne.tail.tailGotHit(vectorSnakeTwo);
        snakeTwo.tail.tailGotHit(vectorSnakeOne);
        if (vectorSnakeOne.dist(vectorSnakeTwo) < snakeOne.rad) {
            textSize(100);
            text("Game Over", 500, 400);
            modus.setGameState(3);
        }
    }
}
