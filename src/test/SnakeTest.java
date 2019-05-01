package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.core.PVector;
import snake.Apple;
import snake.AppleCollection;
import snake.SnakeTail;

class SnakeTest extends PApplet{
	
	@Test
	public void addApple() {
		AppleCollection apples = new AppleCollection(this);
		Apple apple = new Apple(this, 100, 100, 20, 255);
		apples.add(apple);
		assertEquals(1,apples.size());
	}
	
	@Test
	public void growTail() {
		SnakeTail tail = new SnakeTail(this);
		PVector vector = new PVector(100,100);
		tail.add(vector);
		assertEquals(1, tail.size());
	}
	
}