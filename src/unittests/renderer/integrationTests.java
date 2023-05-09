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
    This method asserts that the expected number of intersections between a given camera and a collection of intersectable geometries
	is equal to the actual number of intersections found.
	@param exp - the expected number of intersections.
	@param camera - the camera from which rays are shot to find intersections.
	@param geometries - the collection of intersectable geometries to test for intersections.
	@param nX - the number of columns in the viewplane.
	@param nY - the number of rows in the viewplane.
	@throws AssertionError if the expected number of intersections is not equal to the actual number of intersections found.
	*/
private void assertIntersections(int exp, Camera camera, Intersectable geometries, int nX, int nY) 
{
	int counter = 0;
    List<Point> points = null;
    // set the size and distance of the view plane
    camera.setVPSize(3, 3);
    camera.setVPDistance(1);
    // Go through each pixel in the view plane
    for (int i = 0; i < nY; ++i) 
    {
    	for (int j = 0; j < nX; ++j) 
           {
             var intersections = geometries.findIntersections(camera.constructRay(nX, nY, j, i));
                 if (intersections != null) // in case there are intersections
                   {
                      if (points == null) // check that the list of points has been initialized
                          points = new LinkedList<>();
                      points.addAll(intersections); // add all the new points to the list               
                   }
           }
    }
    // the amount of points in the intersections
    if(points == null )
    	counter = 0;
    else 
		counter = points.size();
    assertEquals(exp, counter, "Wrong amount of intersections");// run the test
}
/**
 * * Integration tests of Camera Ray construction with Ray-Sphere intersections
 * */
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

/**

 * Integration tests of Camera Ray construction with Ray-Plane intersections

 */
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

	/**

     * Integration tests of Camera Ray construction with Ray-Triangle intersections

     */
	 @Test
      public void triangleIntegration() {

      Camera cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));

	  //TC01: Small triangle 1 point
      assertIntersections(1, cam, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3);
      //TC02: Medium triangle 2 points
      assertIntersections(2, cam, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 3, 3);
	    }

	}
	


