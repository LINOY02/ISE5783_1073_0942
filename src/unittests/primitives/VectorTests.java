/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**
* Unit tests for primitives.Vector class
* @author Tamar and Linoy
*/
class VectorTests {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the sum of two vectors
		assertEquals(v1.add(v2), new Vector(-1, -2, -3), "ERROR: Vector + Vector does not work correctly");
		
		// =============== Boundary Values Tests ==================
		
		//TC11: test that check that in case of vector zero there is an exception
		assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)), "ERROR: Vector + -itself does not throw an exception");
		
		
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		
		/// ============ Equivalence Partitions Tests ==============
		
	    //TC01: test that check that multiplies the vector by a scalar is correct
		assertEquals(v1.scale(-2), v2, "ERROR: vector * scalar is not correct");
		
		// =============== Boundary Values Tests ==================
		
		//TC11: test that check that multiplies the vector by a zero throws an exception
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "ERROR: zero vector does not throw an exception");
		
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check if the result of the scalar multiplication is correct
		assertEquals(v1.dotProduct(v2), -28, "ERROR: dotProduct() wrong value");
		//TC02: test that check if the result of the scalar multiplication is zero
		assertEquals(v1.dotProduct(v3), 0, "ERROR: dotProduct() for orthogonal vectors is not zero");

		
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		
		Vector vr = v1.crossProduct(v3);
		double vec = v1.length() * v3.length();
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the correct of the result length
		assertEquals(vec, vr.length(), 0.00000001, "ERROR: crossProduct() wrong result length");
		//TC02: test that check the if the crossProduct() result is orthogonal to its operands
		assertEquals(vr.dotProduct(v1), 0, 0.00000001, "ERROR: crossProduct() result is not orthogonal to its operands");
		assertEquals(vr.dotProduct(v3), 0, 0.00000001, "ERROR: crossProduct() result is not orthogonal to its operands");
		
		// =============== Boundary Values Tests ==================
		
		//TC11: test that check the vector zero
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception");	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the length squared of the vector
		assertEquals(v1.lengthSquared(), 14, "ERROR: length() wrong value");
		
		// =============== Boundary Values Tests ==================
		
		//TC11: test that check the lengthSquared of normalized vector
		assertEquals(v1.normalize().lengthSquared(), 1, "ERROR: the normalized vector is not a unit vector");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check the length of the vector
		assertEquals(v1.length(), Math.sqrt(14), "ERROR: length() wrong value");
		
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		
		Vector v = new Vector(0, 3, 4);
	    Vector u = v.normalize();
	    
	    /// ============ Equivalence Partitions Tests ==============
	    
	    //TC01: test that check the direction of the normal
	    assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u), "ERROR: the normalized vector is opposite to the original one");
		//TC02: test that check the length of normalized vector
		assertEquals(v1.normalize().length(), 1, "ERROR: the normalized vector is not a unit vector");
		//TC03: test that check the correctness of the normal
		assertEquals(u, new Vector(0, 0.6, 0.8), "ERROR: the normalized vector is not parallel to the original one");
	    
	}

}
