package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * A class that represents a Sphere in space and inherits from RadialGeometry class
 * @author Tamar and Linoy
 *
 */
public class Sphere extends RadialGeometry
{
	/**
	 * the point is the center of the sphere
	 */
    final Point center;
    
    /**
     * Sphere constructor with 2 parameters
     * @param radius - double
     * @param center - point
     */
	Sphere(double radius, Point center) 
	{
		super(radius);
		this.center = center;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
