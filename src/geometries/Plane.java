package geometries;

import primitives.Point;
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
    Plane(Point p1, Point p2, Point p3)
    {
    	this.p0 = p1;
    	this.normal = null;
    }
	
    /**
     * Plane constructor with 2 parameters
     * @param p - point
     * @param v - vector
     */
    Plane(Point p, Vector v)
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
	public Vector getNormal() {
		return normal;
	}

}
