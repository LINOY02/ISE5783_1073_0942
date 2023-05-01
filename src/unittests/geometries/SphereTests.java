/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;
import java.util.List;
import primitives.Ray;
/**
 * Unit tests for geometries.Sphere class
 * @author linoy and Tamar
 *
 */
class SphereTests {
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
	
		/// ============ Equivalence Partitions Tests ==============
		//TC01: test that check if there is a vector normal to point on the sphere
		Sphere sphere = new Sphere(1, new Point(0, 0, 0));
		Point point = new Point(1, 0, 0);
		assertEquals(sphere.getNormal(point), new Vector(1, 0, 0).normalize(), "ERROR: the normal is wrong value");	
	}
	
	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	 @Test
	 public void testFindIntersections() {
	 Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
	 List<Point> result;
	 Ray ray;
	 Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
	 Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
	 
	 // ============ Equivalence Partitions Tests ==============
	 
	 // TC01: Ray's line is outside the sphere (0 points)
	 assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))), "There are points of intersection with the sphere");
	 
	 // TC02: Ray starts before and crosses the sphere (2 points)
	 ray = new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0));
	 result = sphere.findIntersections(ray);
	 assertEquals(2, result.size(), "Wrong number of points");
	 if (result.get(0).getX() > result.get(1).getX())
		 result = List.of(result.get(1), result.get(0));
	 assertEquals(List.of(p1, p2 ), result, "Ray doesn't crosse sphere correctly");
	 
	 // TC03: Ray starts inside the sphere (1 point)
     result = sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0),new Vector(3, 1, 0)));
     assertEquals(1, result.size(), "Wrong number of points");
     assertEquals(result, List.of(p2), "There are no single intersection points when the ray starts inside the sphere");

	 // TC04: Ray starts after the sphere (0 points)
     ray = new Ray(new Point(3, 0, 0), new Vector(1, 1, 0));
     assertNull(sphere.findIntersections(ray), "There are points of intersection with the sphere");

     
	 // =============== Boundary Values Tests ==================
     
	 // **** Group: Ray's line crosses the sphere (but not the center)
 
	 // TC11: Ray starts at sphere and goes inside (1 points)
     ray = new Ray(p1, new Vector(3, 1, 0));
     result = sphere.findIntersections(ray);
     assertEquals(1, result.size(), "Wrong number of points");
     assertEquals(result, List.of(p2), "Ray starts at sphere and doesn't go inside");
     
	 // TC12: Ray starts at sphere and goes outside (0 points)
     ray = new Ray(new Point(2, 0, 0),new Vector(3, 1, 0));
     assertNull(sphere.findIntersections(ray), "Ray starts at sphere and doesn't go outside ");

	 // **** Group: Ray's line goes through the center
     
	 // TC13: Ray starts before the sphere (2 points)
	 ray = new Ray(new Point(1, -2, 0), new Vector(0, 4, 0));
	 result = sphere.findIntersections(ray);
	 assertEquals(2, result.size(), "Wrong number of points");
	 if (result.get(1).getX() > result.get(0).getX())
		 result = List.of(result.get(1), result.get(0));
	 assertEquals(List.of(new Point(1, -1, 0), new Point(1, 1, 0)), result, "Ray doesn't crosse sphere correctly");
    
	 // TC14: Ray starts at sphere and goes inside (1 points)
     ray = new Ray(new Point(1, -1, 0), new Vector(1, 1, 0));
     result = sphere.findIntersections(ray);
     assertEquals(1, result.size(), "Wrong number of points");
     assertEquals(result, List.of(new Point(2, 0, 0)), "ERROR: crossing point isn't correct");
     
	 // TC15: Ray starts inside (1 points)
     ray = new Ray(new Point(1, 0.5, 0), new Vector(0, 1, 0));
     result = sphere.findIntersections(ray);
     assertEquals(1, result.size(), "Wrong number of points");
     assertEquals(result, List.of(new Point(1, 1, 0)), "ERROR: crossing point isn't correct");
     
	 // TC16: Ray starts at the center (1 points)
     ray = new Ray(new Point(1, 0, 0), new Vector(2, 0, 0));
     result = sphere.findIntersections(ray);
     assertEquals(1, result.size(), "Wrong number of points");
     assertEquals(result, List.of(new Point(2, 0, 0)), "ERROR: crossing point isn't correct");
     
	 // TC17: Ray starts at sphere and goes outside (0 points)
     ray = new Ray(new Point(1, -1, 0), new Vector(-1, -1, 0));
     assertNull(sphere.findIntersections(ray), "There is point of intersection with the sphere");
     
	 // TC18: Ray starts after sphere (0 points)
     ray = new Ray(new Point(3, 0, 0), new Vector(4, 0, 0));
     assertNull(sphere.findIntersections(ray), "There is point of intersection with the sphere");
     
	 // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
     
	 // TC19: Ray starts before the tangent point
     ray = new Ray(new Point(0, 1, 0), new Vector(1, 0, 0));
     assertNull(sphere.findIntersections(ray), "Incorrect tangent point");
     
	 // TC20: Ray starts at the tangent point
     ray = new Ray(new Point(1, 1, 0), new Vector(0, 1, 0));
     assertNull(sphere.findIntersections(ray), "Incorrect tangent point");
    		 
	 // TC21: Ray starts after the tangent point
     ray = new Ray(new Point(2, 1, 0), new Vector(1, 0, 0));
     assertNull(sphere.findIntersections(ray), "Incorrect tangent point");
     
	 // **** Group: Special cases
     
	 // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
     ray = new Ray(new Point(3, 0, 0), new Vector(0, 1, 0));
     assertNull(sphere.findIntersections(ray), "Ray's line is outside isn't orthogonal to ray start to sphere's center line");
	 }

}
