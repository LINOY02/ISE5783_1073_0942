package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *  A class that represents Cylinder space and inherits from a Tube class
 * @author Tamar
 *
 */
public class Cylinder extends Tube
{
	/**
	 * this field is the height of the cylinder
	 */
  final double height;
  
  /**
   * Cylinder constructor with 3 parameters
   * @param radius - double
   * @param axisRay - ray
   * @param height - double
   */
  Cylinder(double radius, Ray axisRay, double height)
  {
	  super(radius, axisRay);
	  this.height = height;
  }

@Override
public Vector getNormal(Point p) {
	// TODO Auto-generated method stub
	return null;
}
	
}
