package snake;

import processing.core.PApplet;
import processing.core.PVector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Snake extends Element {
    protected boolean isRight, isLeft, isUp, isDown = false;
    private PVector vectorSnake;
    private int keyUp, keyDown, keyLeft, keyRight, speedY, enemysHit, indexOfEnemy, gegnerAuswahl, applesGone,
            difficulty, minDifficulty;
    public SnakeTail tail;
    private Float[][] distanzMapToApples;
    private ArrayList<Apple> toRemove;
    private PVector vectorApple;
    private String level;

    public Snake(PApplet p, int xPos, int yPos, int keyUp, int keyDown, int keyLeft, int KeyRight) {
        super(p, xPos, yPos);
        speedY = 20;
        this.keyDown = keyDown;
        this.keyLeft = keyLeft;
        this.keyRight = KeyRight;
        this.keyUp = keyUp;
        rad = 40;
        minDifficulty = 30;
        difficulty = 10;
        tail = new SnakeTail(p);
        distanzMapToApples = new Float[MainSnake.appleCount][2];
        toRemove = new ArrayList<>();
        applesGone = -1;
    }

    /**
     * Spieler bewegen
     */
    public void move() {
        if (this.isLeft) {
            xPos -= speedY;
        } else if (this.isRight) {
            xPos += speedY;
        }
        if (this.isUp) {
            yPos -= speedY;
        } else if (this.isDown) {
            yPos += speedY;
        }
        wrapPlayfield();
    }

    private void wrapPlayfield() {
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

    public void display() {
        p.fill(255);
        p.ellipse(xPos, yPos, rad, rad);
        tail.drawSnake();
    }

    private boolean isValid() {
        return level.equals("1") || level.equals("2") || level.equals("3");
    }

    /**
     * Kollisionen zwischen Spieler und Apples erkennen
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
        tail.manage(vectorSnake);
        tail.hitTail(vectorSnake);
    }

    public int points() {
        return tail.size();
    }

//	Player methods

    /**
     * Richtungsangabe f�r Spieler bei Tastendruck
     */
    public void direction() {
        if (p.keyCode == keyLeft && !isRight) {
            isLeft = true;
            isDown = false;
            isUp = false;
        }
        if (p.keyCode == keyRight && !isLeft) {
            isRight = true;
            isDown = false;
            isUp = false;
        }
        if (p.keyCode == keyUp && !isDown) {
            isUp = true;
            isLeft = false;
            isRight = false;
        }
        if (p.keyCode == keyDown && !isUp) {
            isDown = true;
            isLeft = false;
            isRight = false;
        }
    }

    //	Bot methods
    public void setLevel() {
        if (level == null) {
            do {
                level = JOptionPane.showInputDialog("level 1,2,3?");
            } while (!isValid() && level != null);
            difficulty = Integer.parseInt(level);
        }
    }

    /**
     * Mit dieser Methode spielt sich die Schlange selbst.
     */
    public void autoMove(AppleCollection apples, MainSnake main) {
        for (Apple a : apples) {
            for (int j = 0; j < 2; j++) {
                vectorSnake = new PVector(xPos, yPos);
                vectorApple = new PVector(a.xPos, a.yPos);
                if (j == 0) {
                    distanzMapToApples[apples.indexOf(a)][j] = vectorSnake.dist(vectorApple);
                } else {
                    distanzMapToApples[apples.indexOf(a)][j] = (float) apples.indexOf(a);
                }
            }
        }
        Arrays.sort(distanzMapToApples, (a, b) -> Float.compare(a[0], b[0]));
        if (applesGone < tail.size()) {
            gegnerAuswahl = (int) p.random(distanzMapToApples.length * difficulty) / minDifficulty;
            indexOfEnemy = Math.round(distanzMapToApples[gegnerAuswahl][1]);
            applesGone++;
        }
        vectorApple = new PVector(apples.get(indexOfEnemy).xPos, apples.get(indexOfEnemy).yPos);
        botDirection();
    }

    private void botDirection() {
        if (vectorApple.x < vectorSnake.x && !isRight) {
            isLeft = true;
            isDown = false;
            isUp = false;
        } else if (vectorApple.x > vectorSnake.x && !isLeft) {
            isRight = true;
            isDown = false;
            isUp = false;
        } else if (vectorApple.y < vectorSnake.y && !isDown) {
            isUp = true;
            isLeft = false;
            isRight = false;
        } else if (vectorApple.y > vectorSnake.y && !isUp) {
            isDown = true;
            isLeft = false;
            isRight = false;
        }
    }
}
