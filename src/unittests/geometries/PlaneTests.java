/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;
import geometries.Plane;
import geometries.Polygon;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Plane class
 * @author linoy and Tamar
 *
 */
class PlaneTests {
      void testConstructor()
      {
    	  //TC01: The first and second points are connected
    	  assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(0, 1, 0)));
    	  //TC02: The points are on the same line
    	  assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0,  0)));
      }

	
	
	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		
		/// ============ Equivalence Partitions Tests ==============
        
	      Point[] pts =
	          { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
	       Plane plan = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
	       // ensure there are no exceptions
	       assertDoesNotThrow(() -> plan.getNormal(new Point(0, 0, 1)), "");
	       // generate the test result
	       Vector result = plan.getNormal(new Point(0, 0, 1));
	       // ensure |result| = 1
	       assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
	       // ensure the result is orthogonal to all the edges
	       for (int i = 0; i < 2; ++i)
	          assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
	                     "Plane's normal is not orthogonal to one of the edges");
		
	}

}
