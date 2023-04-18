/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Cylinder;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
/**
 * Unit tests for geometries.Cylinder class
 * @author linoy and Tamar
 *
 */
class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		
		Cylinder cylinder = new Cylinder(4, new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 3);
		
		/// ============ Equivalence Partitions Tests ==============
		
		//TC01:test that check if the point at the base of the cylinder
		//asserEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 2, 3)), "ERROR: the normal at the base is wrong value");
		//TC02:test that check if the point at the top of the cylinder
		//asserEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(1, 1, 0)), "ERROR: the normal at the top is wrong value");
		//TC03:test that check if the point on the side of the cylinder
		//asserEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(4, 0, 2)), "ERROR: the normal on the side is wrong value");
		
		// =============== Boundary Values Tests ==================
		
		
		//TC11: test that check if the point is the point center of the top
		//asserEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0, 0, 3)), "ERROR: the normal at the base is wrong value");
		//TC12: test that check if the point is the point center of the base
		//asserEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0, 0, 0)), "ERROR: the normal at the top is wrong value");
	}

}
