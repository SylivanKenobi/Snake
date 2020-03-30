package snake;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class SnakeTail extends ArrayList<PVector> {
    private PApplet p;
    private int rad;

    public SnakeTail(PApplet p) {
        this.p = p;
        rad = 40;
    }

    public void manage(PVector player) {
        if (this.size() > 1) {
            for (int i = this.size() - 1; i > 0; i--) {
                this.set(i, this.get(i - 1));
            }
        }
        if (this.size() > 0) {
            this.set(0, player);
        }

    }

    public void drawSnake() {
        for (PVector i : this) {
            p.noStroke();
            p.fill((this.indexOf(i) * 4), this.indexOf(i) * 2, this.indexOf(i) * 3);
            p.ellipse(i.x, i.y, rad, rad);
        }
    }

    public void grow(PVector snake) {
        this.add(snake);
    }

    public void hitTail(PVector snake) {
        boolean gotHit = false;
        for (int i = 1; i < this.size(); i++) {
            if (snake.dist(this.get(i)) < 2) {
                gotHit = true;
            }
        }
        if (gotHit) {
            this.remove(this.size() - 1);
        }

    }

    public void tailGotHit(PVector snake) {
        boolean gotHit = false;
        for (int i = 1; i < this.size(); i++) {
            if (snake.dist(this.get(i)) < 20) {
                gotHit = true;
            }
        }
        if (gotHit) {
            this.remove(this.size() - 1);
        }
    }

}
