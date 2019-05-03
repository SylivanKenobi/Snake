package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.core.PVector;
import snake.AppleCollection;
import snake.SpielerSnake;

class SpielerSnakeTest extends PApplet {

	@Test
	public void testDirection() {
		SpielerSnake snake = new SpielerSnake(this, 20, 120, 38, 40, 37, 39);
		keyCode = 37;
		snake.direction();
		assertEquals(true, snake.isLeft());
	}
}
