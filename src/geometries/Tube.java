package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

import java.util.List;
/**
 * A class that represents Tube space and inherits from a RadialGeometry class
 * @author Tamar and Linoy
 *
 */
public class Tube extends RadialGeometry
{   
	/**
	 * the ray is the direction of the tube
	 */
    final Ray axisRay;
	
    /**
     * Tube constructor with 2 parameters
     * @param radius - double
     * @param axisRay - ray
     */
	public Tube(double radius, Ray axisRay) {
		super(radius);
		// TODO Auto-generated constructor stub
		this.axisRay = axisRay;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		double t = this.axisRay.getDir().dotProduct(p.subtract(this.axisRay.getP0()));
		if(isZero(t))
		{
			return p.subtract(this.axisRay.getP0()).normalize();
		}
		Point o = this.axisRay.getP0().add(this.axisRay.getDir().scale(t));
		return p.subtract(o).normalize();
	}
	
	  public List<Point> findIntersections(Ray ray)
	  {
		  return null;
	  }
}
 
