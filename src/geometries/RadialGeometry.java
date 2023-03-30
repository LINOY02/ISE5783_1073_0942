package geometries;

 /**
  * An abstract class that represents 3D shapes with a radius that implements the Geometry interface
  * @author Tamar and Linoy
  *
  */
public abstract class RadialGeometry implements Geometry
{
	/**
	 * the field represent radius in a shape
	 */
  protected double radius;
  
  /**
   * constructor that get radius
   * @param radius - double
   */
  RadialGeometry(double radius)
  {
	  this.radius = radius;
  }
}
