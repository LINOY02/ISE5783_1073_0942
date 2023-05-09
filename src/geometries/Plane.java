package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class that represents a plane in space by a point and a vector normal to the plane
 * @author Tamar and Linoy
 *
 */
public class Plane implements Geometry
{
	/**
	 * A point on the plane
	 */
    final Point p0;
    /**
     * A vector perpendicular to the plane
     */
    final Vector normal;
    
    /**
     * Plane constructor that get 3 point and calculate the normal to the plane
     * @param p1 - point
     * @param p2 - point
     * @param p3 - point
     */
    public Plane(Point p1, Point p2, Point p3)
    {
    	this.p0 = p1;
    	Vector v1 = p2.subtract(p1);
    	Vector v2 = p3.subtract(p1);
    	this.normal = v1.crossProduct(v2).normalize();
    }
	
    /**
     * Plane constructor with 2 parameters
     * @param p - point
     * @param v - vector
     */
    public Plane(Point p, Vector v)
    {
    	this.p0 = p;
    	this.normal = v.normalize();
    }
	
	@Override
	public Vector getNormal(Point p) {
		return this.normal;
	}


    /**
     * getter of the filed normal
     * @return the normal vector to the plane
     */
	public Vector getNormal() 
	{
		return normal;
	}
	
	/**
     Finds the intersection point of a given ray with this Plane.
     @param ray The ray to find the intersection point with.
     @return A list containing the intersection point, or null if there is no intersection.
	*/
	 public List<Point> findIntersections(Ray ray)
	 {
		 Point p = ray.getP0();
		 Vector v = ray.getDir();
		 Vector n = getNormal();
		 double nv = n.dotProduct(v);
		 if (isZero(nv) || p0.equals(p)) //Checking if the beam is parallel to the plane or if it starts at the same point as the plane
			 return null;
		// Calculate the distance from the ray's starting point to the intersection point with this plane
		 double tmp = n.dotProduct(p0.subtract(p));
		 double t = alignZero(tmp / nv);
		// If the intersection point is in front of the camera return it
         if (t > 0)
         {
        	 Point point = ray.getPoint(t);
        	 return List.of(point);
         }
      // If there is no intersection point
		  return null;
	  }

	@Override
	public String toString() {
		return "Plane [p0=" + p0 + ", normal=" + normal + "]";
	}

}
