package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import primitives.Ray;

class RayTests {

	/**
	 * Test method for {@link primitives.Ray#FindClosestPoint(List<Point>)}.
	 */
	@Test
	void testFindClosestPoint() {
		
		
		List<Point> points = new LinkedList<>();
		Point p1 = new Point(2, 2, 2);
		Point p2 = new Point(3, 1, 1);
		Point p3 = new Point(5, 6, 7);
		Point p4 = new Point(7, 3, 1);
		Point p5 = new Point(8, 9, 2);
		Ray ray = new Ray(new Point(1, 1, 1), new Vector(3, 2, 1));
		
	      // ============ Equivalence Partitions Tests ==============
		
		//TC01: A point in the middle of the list is the one closest to the beginning of the foundation
		points.add(p2);
		points.add(p1);
		points.add(p4);
		points.add(p5);
		points.add(p3);
		assertEquals(ray.findClosestPoint(points), p1, "The point is not in the middle of the list");
		
		// =============== Boundary Values Tests ==================

		//TC11: if the list is empty
		points.clear();
		assertNull(ray.findClosestPoint(points), "there is no intersection points");
		
		//TC12: The first point is closest to the beginning of the ray
		points.clear();
		points.add(p1);
		points.add(p4);
		points.add(p5);
		points.add(p2);
		points.add(p3);
		assertEquals(ray.findClosestPoint(points), p1, "The first point isn't closest to the beginning");
		
		//TC13: The last point is closest to the beginning of the ray
		points.clear();
		points.add(p3);
		points.add(p2);
		points.add(p5);
		points.add(p4);
		points.add(p1);
		assertEquals(ray.findClosestPoint(points), p1, "The last point isn't closest to the beginning");
	}

}
