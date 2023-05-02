/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Plane;

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
	void testGetNormalPoint() 
	{ 
		
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
	    	   assertEquals(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])),0,0.000001,
	                     "Plane's normal is not orthogonal to one of the edges");
		
	}
	
	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testfindIntersections()
	{
		/// ============ Equivalence Partitions Tests ==============
		
        Plane plane = new Plane(new Point(1, 0, 1), new Point(0, 1, 1), new Point(1, 1, 1));
        Ray ray;
		//TC01: Ray intersects the plane
        ray = new Ray(new Point(0, 0.5, 0),new Vector(1, 0, 1));
        List<Point> result = List.of(new Point(1, 0.5, 1));
        assertEquals(result, plane.findIntersections(ray), "ERROR: Ray doesn't intersects the plane");

		//TC02: Ray does not intersect the plane
        ray = new Ray(new Point(1, 0.5, 2), new Vector(1, 2, 5));
        assertNull(plane.findIntersections(ray), "ERROR: Ray intersect the plane");
        
        // =============== Boundary Values Tests ==================
        
        // **** Group: ray parallel to the plane
        
        //TC11: included in it
        ray = new Ray(new Point(1,2,1), new Vector(1,0,0));
        assertNull(plane.findIntersections(ray), "the ray isn't included in the plane");
        
        //TC12: not included in it
        ray = new Ray(new Point(1,2,2), new Vector(1,0,0));
        assertNull(plane.findIntersections(ray), "the ray included in the plane");
        
        // **** Group: ray is orthogonal to the plane
        
        //TC13: ray's head is inside the plane
        ray = new Ray(new Point(1, 2, 1), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray), "There is point of intersection with the plane");

        //TC14: ray's head is before of the plane
        ray = new Ray(new Point(1, 1, 0), new Vector(0, 0, 1));
        assertEquals(plane.findIntersections(ray), List.of(new Point(1, 1, 1)), "There isn't point of intersection with the plane");

        //TC15: ray's head is after the plane
        ray = new Ray(new Point(1, 2, 2), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray), "There is point of intersection with the plane");
        
        // **** Group: ray neither orthogonal nor parallel to the plane
        
        //TC16: ray begins in the same point which appears as reference point in the plane
        ray = new Ray(new Point(1,0,1), new Vector(2,3,5));
        assertNull(plane.findIntersections(ray), "There is point of intersection with the plane");
        //TC17: ray begins in reference point
        ray = new Ray(new Point(2,4,1), new Vector(2,3,5));
        assertNull(plane.findIntersections(ray), "There is point of intersection with the plane");
        
	}

}
