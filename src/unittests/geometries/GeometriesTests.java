package unittests.geometries;
import geometries.Geometries;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Plane;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Unit tests for geometries.Geometries class
 * @author linoy and tamar
 *
 */
class GeometriesTests {

	@Test
	void testFindIntersections() {
		 // =============== Boundary Values Tests ==================
		// TC11: An empty geometries collection
		Geometries geometries = new Geometries();
		Ray ray1= new Ray(new Point(0,0,1), new Vector(1,1,1));
		assertNull(geometries.findIntersections(ray1), "The geometries group is not empty ");
		
		Sphere sphere = new Sphere(1, new Point(1,0,0));
		Triangle triangle = new Triangle(new Point(-4,0,0), new Point(0,0,5), new Point(0,-5,0));
		Plane plane = new Plane(new Point(0,0,1), new Point(1,0,0), new Point(4,0,2));
		geometries.add(sphere, triangle, plane);
		
		// TC12: No shape is cut
		Ray ray2= new Ray(new Point(0,-8,0), new Vector(-10,-1,0));
		assertNull(geometries.findIntersections(ray2), "There are intersections in the group ");
		
		// TC13: Only one shape is cut
	    Ray ray3 = new Ray(new Point(-0.8,-3,1), new Vector(3.4,3,1.57));
		assertEquals(geometries.findIntersections(ray3).size(),1, "There are more than one shape thath is cut ");
		
		// TC14: All the shapes are cut
	    Ray ray4 = new Ray(new Point(-4,-3,0), new Vector(6,3,0.5));
		assertEquals(geometries.findIntersections(ray4).size(),4, "Not all the shape are cut");
		
		
		 // ============ Equivalence Partitions Tests ==============
		// TC01: Some shapes (but not all) are cut
		Ray ray5 = new Ray(new Point(-4,-3,2), new Vector(9,5,-1));
		assertEquals(geometries.findIntersections(ray5).size(),2, "All the shape are cut or not cut");
		
		
	};

}
