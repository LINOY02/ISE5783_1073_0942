package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * An interface that represent find Intersections
 * @author Linoy and Tamar
 *
 */
public interface Intersectable {
	
	/***
	*
	* @param ray {@link Ray} pointing toward the object
	* @return list of intersections {@link Point}
	*/
	List<Point> findIntersections(Ray ray);

}

