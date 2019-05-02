package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.core.PVector;
import snake.SnakeTail;

class SnakeTailTest extends PApplet{

	@Test
	public void testGrow() {
		SnakeTail tail = new SnakeTail(this);
		PVector vector = new PVector(100,100);
		tail.grow(vector);
		assertEquals(1, tail.size());
	}
	
	@Test
	public void testHitTail() {
		SnakeTail tail = new SnakeTail(this);
		PVector vector1 = new PVector(100,100);
		PVector vector2 = new PVector(120,100);
		PVector vector3 = new PVector(140,100);
		PVector vector4 = new PVector(160,100);
		tail.grow(vector1);
		tail.grow(vector2);
		tail.grow(vector3);
		tail.grow(vector4);
		tail.hitTail(vector2);
		assertEquals(3, tail.size());
	}
	
	@Test
	public void testTailGotHit() {
		SnakeTail tail = new SnakeTail(this);
		PVector vector1 = new PVector(100,100);
		PVector vector2 = new PVector(120,100);
		PVector vector3 = new PVector(140,100);
		PVector vector4 = new PVector(160,100);
		tail.grow(vector1);
		tail.grow(vector2);
		tail.grow(vector3);
		tail.grow(vector4);
		tail.tailGotHit(vector2);
		assertEquals(3, tail.size());
	}

}
