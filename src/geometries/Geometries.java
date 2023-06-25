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
	private final List<Intersectable> infinites = new LinkedList<Intersectable>();
	private final List<Intersectable> intersections = new LinkedList<Intersectable>();
	
	/**
	 * empty constructor
	 */
	public Geometries(){
	}
	
	/**
     Constructor with parameters 
	@param geometries An array of intersectable geometries.
	*/
	public Geometries(Intersectable... geometries) {
		
		Collections.addAll(intersections, geometries);
	}
	
	/**
	 * constructor that gets several intersectables and add them to the geometries
	 * list
	 * 
	 * @param geometries geometries to add to list
	 */
	public Geometries(List<Intersectable> geometries) {
		add(geometries);
	}
	
	/**
    Adds one or more intersectable geometries to this Geometries object.
	@param geometries One or more intersectable geometries to add to this Geometries object.
	*/

	public void add(Intersectable... geometries) 
	{
		if(geometries != null)
		    Collections.addAll(intersections, geometries);
	}
	
	/**
	 * adds geometries to the list
	 * 
	 * @param geometries the geomtries to add
	 */
	public void add(List<Intersectable> geometries) {
		if (!cbr) {
			this.intersections.addAll(geometries);
			return;
		}

		for (var g : geometries) {
			if (g.box == null)
				infinites.add(g);
			else {
				this.intersections.add(g);
				if (infinites.isEmpty()) {
					if (box == null)
						box = new Border();
					if (g.box.minX < box.minX)
						box.minX = g.box.minX;
					if (g.box.minY < box.minY)
						box.minY = g.box.minY;
					if (g.box.minZ < box.minZ)
						box.minZ = g.box.minZ;
					if (g.box.maxX > box.maxX)
						box.maxX = g.box.maxX;
					if (g.box.maxY > box.maxY)
						box.maxY = g.box.maxY;
					if (g.box.maxZ > box.maxZ)
						box.maxZ = g.box.maxZ;
				}
			}
		}
		// if there are inifinite objects
		if (!infinites.isEmpty())
			box = null;
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
       for(Intersectable shape: infinites)
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
    
	/**
	 * create the hierarchy and put into the right boxes
	 */
	public void setBVH() {
		if (!cbr)
			return;
		// min amount of geometries in a box is 2
		if (intersections.size() <= 4)
			return;

		if (box == null) {
			var finites = new Geometries(intersections);
			intersections.clear();
			intersections.add(finites);
			return;
		}

		double x = box.maxX - box.minX;
		double y = box.maxY - box.minY;
		double z = box.maxZ - box.minZ;
		// which axis we are reffering to
		final char axis = y > x && y > z ? 'y' : z > x && z > y ? 'z' : 'x';
//		Collections.sort(geometries, //
//				(i1, i2) -> Double.compare(average(i1, axis), average(i2, axis)));

		var l = new Geometries();
		var m = new Geometries();
		var r = new Geometries();
		double midX = (box.maxX + box.minX) / 2;
		double midY = (box.maxY + box.minY) / 2;
		double midZ = (box.maxZ + box.minZ) / 2;
		switch (axis) {
		case 'x':
			for (var g : intersections) {
				if (g.box.minX > midX)
					r.add(g);
				else if (g.box.maxX < midX)
					l.add(g);
				else
					m.add(g);
			}
			break;
		case 'y':
			for (var g : intersections) {
				if (g.box.minY > midY)
					r.add(g);
				else if (g.box.maxY < midY)
					l.add(g);
				else
					m.add(g);
			}
			break;
		case 'z':
			for (var g : intersections) {
				if (g.box.minZ > midZ)
					r.add(g);
				else if (g.box.maxZ < midZ)
					l.add(g);
				else
					m.add(g);
			}
			break;
		}

		// add geometries to the splitted boxes
//		int counter = 0;
//		int middle = geometries.size() / 2;
//		for (var g : geometries)
//			if (counter++ <= middle)
//				l.add(g);
//			else
//				r.add(g);

		intersections.clear();
		if (l.intersections.size() <= 2)
			intersections.addAll(l.intersections);
		else {
			l.setBVH();
			intersections.add(l);
		}

		if (m.intersections.size() <= 2)
			intersections.addAll(m.intersections);
		else
			intersections.add(m);
		
		if (r.intersections.size() <= 2)
			intersections.addAll(r.intersections);
		else {
			r.setBVH();
			intersections.add(r);
		}
	}
}
