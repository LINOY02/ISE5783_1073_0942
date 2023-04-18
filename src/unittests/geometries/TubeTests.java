/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Tube;
import primitives.Ray;
/**
 * Unit tests for geometries.Tube class
 * @author linoy and Tamar
 *
 */
class TubeTests {

	Ray ray = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
	Tube tube = new Tube(1, ray);
	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01: test that check if there is a vector normal to point on the tube
		assertEquals(tube.getNormal(new Point(1, 0, 6)), new Vector(1, 0, 0), "ERROR: the normal is wrong value");
		
		// =============== Boundary Values Tests ==================
		
		//TC11: test that check if the point is in front of the ray's head
		assertEquals(tube.getNormal(new Point(0, 1, 0)), new Vector(0, 1, 0), "ERROR: the normal is wrong value");
	}

}
