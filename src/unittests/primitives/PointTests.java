/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;

import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 * @author linoy and Tamar
 *
 */
class PointTests {

	Point p1 = new Point(1, 2, 3); 
	Point p2 = new Point(-1, -2, -3); 
	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {

		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the difference of positive point and positive vector
		assertEquals(p1.subtract(new Point(2, 3, 4)), new Vector(-1, -1, -1), "ERROR: Point - Point does not work correctly");
		//TC02: test that check the difference of positive point and negative vector
		assertEquals(p1.subtract(new Point(-2, -3, -4)), new Vector(3, 5, 7), "ERROR: Point - Point does not work correctly");
		
		//TC03: test that check the difference of negative point and positive vector
		assertEquals(p2.subtract(new Point(2, 3, 4)), new Vector(-3, -5, -7), "ERROR: Point - Point does not work correctly");
		//TC04: test that check the difference of negative point and negative vector
		assertEquals(p2.subtract(new Point(-2, -3, -4)), new Vector(1, 1, 1), "ERROR: Point - Point does not work correctly");
}
	

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the sum of positive point and positive vector
		assertEquals(p1.add(new Vector(2, 3, 4)), new Point(3, 5, 7), "ERROR: Point + Vector does not work correctly");
		//TC02: test that check the sum of positive point and negative vector
		assertEquals(p1.add(new Vector(-2, -3, -4)), new Point(-1, -1, -1), "ERROR: Point + Vector does not work correctly");
		
		//TC03: test that check the sum of negative point and positive vector
		assertEquals(p2.add(new Vector(2, 3, 4)), new Point(1, 1, 1), "ERROR: Point + Vector does not work correctly");
		//TC04: test that check the sum of negative point and negative vector
		assertEquals(p2.add(new Vector(-2, -3, -4)), new Point(-3, -5, -7), "ERROR: Point + Vector does not work correctly");
}
	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {

		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the distance Squared of positive point and positive point
		assertEquals(p1.distance(new Point(2, 3, 4)), Math.sqrt(3), "ERROR: The distance squared between two Points does not work correctly");
		//TC02: test that check the distance Squared of positive point and negative point
		assertEquals(p1.distance(new Point(-2, -3, -4)), Math.sqrt(83), "ERROR: The distance squared between two Points does not work correctly");
		
		//TC03: test that check the distance Squared of negative point and positive point
		assertEquals(p2.distance(new Point(2, 3, 4)), Math.sqrt(83), "ERROR: The distance squared between two Points does not work correctly");
		//TC04: test that check the distance Squared of negative point and negative point
		assertEquals(p2.distance(new Point(-2, -3, -4)), Math.sqrt(3), "ERROR: The distance squared between two Points does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the distance of positive point and positive point
		assertEquals(p1.distance(new Point(2, 3, 4)), Math.sqrt(3), "ERROR: The distance between two Points does not work correctly");
		//TC02: test that check the distance of positive point and negative point
		assertEquals(p1.distance(new Point(-2, -3, -4)), Math.sqrt(83), "ERROR: The distance between two Points does not work correctly");
		
		//TC03: test that check the distance of negative point and positive point
		assertEquals(p2.distance(new Point(2, 3, 4)), Math.sqrt(83), "ERROR: The distance between two Points does not work correctly");
		//TC04: test that check the distance of negative point and negative point
		assertEquals(p2.distance(new Point(-2, -3, -4)), Math.sqrt(3), "ERROR: The distance between two Points does not work correctly");
	}

}
