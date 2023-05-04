package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;

/**
 * @author linot and tamar
 *
*/
class integrationTests {
/**
 * 
 * @param expected
 * @param cam
 * @param geo
 * @param nX
 * @param nY
 */
private void assertIntersections(int exp, Camera camera, Intersectable geometries, int nX, int nY) 
{

     List<Point> points = new LinkedList<>();
     camera.setVPSize(3, 3);
     camera.setVPDistance(1);
     for (int i = 0; i < nY; ++i) 
     {
    	 for (int j = 0; j < nX; ++j) 
    	 {
    		 var intersections = geometries.findIntersections(camera.constructRay(nX, nY, j, i));
              if (intersections != null) 
              {
            	  points.addAll(intersections);                 
	          }
    	 }
     }
	        assertEquals(exp, points.size(), "Wrong amount of intersections");
	    }
	    /**
	     * Integration tests of Camera Ray construction with Ray-Sphere intersections
	     */
@Test
public void SphereIntegration() {

	Camera cam1 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));

	//TC01: Small Sphere 2 points
	assertIntersections(2, cam1, new Sphere(1, new Point(0, 0, -3)), 3, 3);
    //TC02: Big Sphere 18 points
    assertIntersections(18, cam1, new Sphere(2.5, new Point(0, 0, -2.5)), 3, 3);
    //TC03: Medium Sphere 10 points
    assertIntersections(10, cam1, new Sphere(2.0, new Point(0, 0, -2)) , 3, 3);
    //TC04: Inside Sphere 9 points
    assertIntersections(9, cam1, new Sphere(4.0, new Point(0, 0, -1)), 3, 3);
    //TC05: Beyond Sphere 0 points
    assertIntersections(0, cam1, new Sphere(0.5, new Point(0, 0, 1)), 3, 3);
    }
	@Test
    public void planeIntegration() {

    Camera cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
    		
	 //TC01: Plane against camera 9 points
     assertIntersections(9, cam, new Plane(new Point(0, 0, -2), new Vector(0, 0, 1)), 3, 3);
     //TC02: Plane with small angle 9 points
     assertIntersections(9, cam, new Plane(new Point(0, 0, -1.5), new Vector(0, -0.5, 1)), 3, 3);
     //TC03: Plane parallel to lower rays 6 points
     assertIntersections(6, cam, new Plane(new Point(0, 0, -3), new Vector(0, -1, 1)), 3, 3);
	    }

	 @Test
      public void triangleIntegration() {

      Camera cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));

	  //TC01: Small triangle 1 point
      assertIntersections(1, cam, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3);
      //TC02: Medium triangle 2 points
      assertIntersections(2, cam, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3);
	    }

	}
	


