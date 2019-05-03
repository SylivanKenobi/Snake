package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import snake.AppleCollection;
import snake.SpielerSnake;

/**
 * Applecollection testen
 * @author Sylvain Gilgen
 *
 */
class AppleCollectionTest extends PApplet {

	/**
	 * Testen ob die Methode newApple() funktioniert
	 */
	@Test
	public void testAddApple() {
		AppleCollection apples = new AppleCollection(this);
		apples.newApple(1);
		System.out.println(apples.size());
		assertEquals(1, apples.size());
	}

	/**
	 * Testen ob Methode createApples funktioniert
	 */
	@Test
	public void testCreateApples() {
		AppleCollection apples = new AppleCollection(this);
		apples.createApples(2);
		System.out.println(apples.size());
		assertEquals(2, apples.size());
	}

}
