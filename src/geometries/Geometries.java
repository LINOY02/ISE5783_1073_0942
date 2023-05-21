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
public class Geometries extends Intersectable
{
	private List<Intersectable> intersections;
	
	/**
	 * empty constructor
	 */
	public Geometries(){
		intersections = new LinkedList<Intersectable>();
	}
	
	/**
     Constructor with parameters 
	@param geometries An array of intersectable geometries.
	*/
	public Geometries(Intersectable... geometries) {
		intersections = new LinkedList<Intersectable>();
		Collections.addAll(intersections, geometries);
	}
	
	/**
    Adds one or more intersectable geometries to this Geometries object.
	@param geometries One or more intersectable geometries to add to this Geometries object.
	*/

	public void add(Intersectable... geometries) {
		Collections.addAll(intersections, geometries);
	}
	
	
	/**
      Finds the intersection points of a given ray with all the intersectable geometries in this Geometries object.
      @param ray The ray to find the intersection points with.
      @return A list of intersection points, or null if there are no intersections.
	*/
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
       // create list of points
       List<GeoPoint> points = null;
       //find intersections for each shape in geometries
       for(Intersectable shape: intersections)
         {
    	  List<GeoPoint> temPoints = shape.findGeoIntersections(ray);
    	   if(temPoints != null)
    	   {
    		   if(points == null)
    			   points = new LinkedList<>();
    		   points.addAll(temPoints);// add the new point to the list
    	   }
         }
       return points;
    }
}
