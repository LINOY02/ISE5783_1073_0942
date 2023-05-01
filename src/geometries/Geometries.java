package geometries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * A class for a set of geometries bodies
 * @author linoy and tamar
 *
 */
public class Geometries implements Intersectable{
	private List<Intersectable> intersections;
	
	public Geometries(){
		intersections = new LinkedList<Intersectable>();
	}
	public Geometries(Intersectable... geometries) {
		intersections = new LinkedList<Intersectable>();
		Collections.addAll(intersections, geometries);
	}
	public void add(Intersectable... geometries) {
		Collections.addAll(intersections, geometries);
	}
	
	
	private boolean checkIntersections(Ray ray)
	{
		for(Intersectable shape: intersections)
	     {
			if(shape.findIntersections(ray) != null)
				return true;
	     }
		return false;
	}

    public List<Point> findIntersections(Ray ray)
    {
       if(!checkIntersections(ray))
    	   return null;
       List<Point> points = new LinkedList<Point>();
       for(Intersectable shape: intersections)
         {
    	  List<Point> temPoints = shape.findIntersections(ray);
    	  if(temPoints != null)
    	  {
    		points.addAll(temPoints);
    	  }
          }
       return points;
    }
}
