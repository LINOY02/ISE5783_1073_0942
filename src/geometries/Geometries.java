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
public class Geometries {
	private List<Intersectable> intersectable;
	
	public Geometries(){
		intersectable = new LinkedList<Intersectable>();
	}
	public Geometries(Intersectable... geometries) {
		intersectable = new LinkedList<Intersectable>();
		Collections.addAll(intersectable, geometries);
	}
	public void add(Intersectable... geometries) {
		Collections.addAll(intersectable, geometries);
	}
	

    public List<Point> findIntersections(Ray ray)
    {
    return null;
    }
}
