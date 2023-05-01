/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.validator.PublicClassValidator;

import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * @author linoy and Tamar
 *
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		
		Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		
	      // ============ Equivalence Partitions Tests ==============
	      // TC01: There is a simple single test here - using a quad
	      Point[] pts =
	         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
	      // ensure there are no exceptions
	      assertDoesNotThrow(() -> triangle.getNormal(new Point(0, 0, 1)), "");
	      // generate the test result
	      Vector result = triangle.getNormal(new Point(0, 0, 1));
	      // ensure |result| = 1
	      assertEquals(1, result.length(), 0.00000001, "triangle's normal is not a unit vector");
	      // ensure the result is orthogonal to all the edges
	      for (int i = 0; i < 2; ++i)
	         assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
	                    "triangle's normal is not orthogonal to one of the edges");
	   }
	
	
	
	   /** Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}. */
		@Test
		public void testfindIntersections()
		{
			Triangle triangle = new Triangle(new Point(-1,0,0), new Point(1,0,0),new Point(0,0,2));
			Ray ray;
			// ============ Equivalence Partitions Tests ==============
			
			//TC01: point against  the rib outside the triangle
			ray = new Ray(new Point(-1, 1, 1), new Vector(0, -1, 0));
			assertNull(triangle.findIntersections(ray), "the ray crosses the triangle");
			
			//TC02: point against the vertex outside the triangle
			ray = new Ray(new Point(-2, 1, -1), new Vector(0, -1, 0));
			assertNull(triangle.findIntersections(ray), "the ray crosses the triangle");
			
			//TC03: point inside the triangle
			ray = new Ray(new Point(0, 1, 0), new Vector(0, -1, 1));
			assertEquals(1, triangle.findIntersections(ray).size(), "Wrong number of points");
			assertEquals(triangle.findIntersections(ray), List.of(new Point(0,0,1)), "intersection point isn't correct");

			
			// =============== Boundary Values Tests ==================
			
			//TC11: point that is on the continuation of the side
			ray = new Ray(new Point(-2, 1, -2), new Vector(0, -1, 0));
			assertNull(triangle.findIntersections(ray), "the ray isn't the continuation of the side");
			
			//TC12: point on the rib
			ray = new Ray(new Point(-0.5, 1, 1), new Vector(0, -1, 0));
			assertNull(triangle.findIntersections(ray), "the ray isn't on the rib");
			
			//TC13: point on the vertex
			ray = new Ray(new Point(-1, 0, 1), new Vector(3, 1, 0));
			assertNull(triangle.findIntersections(ray), "the ray isn't on the vertex");
			
		}
	}
