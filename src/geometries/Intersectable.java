package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**
 * An interface that represent find Intersections
 * @author Linoy and Tamar
 *
 */
public abstract class Intersectable {
	
	/***
	*
	* @param ray {@link Ray} pointing toward the object
	* @return list of intersections {@link Point}
	*/
	public List<Point> findIntersections(Ray ray) {
		 var geoList = findGeoIntersections(ray);
		 return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
		}

	/**
	 * Finds the geometric intersections of a ray with existing geometric objects.
	 * @param ray the geometric ray to find intersections with.
	 * @return a list of GeoPoint objects representing the intersection points.
     */
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		return findGeoIntersectionsHelper(ray);
	}
	
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
	
	/**
	 * Represents a geographic point.
	 */
	public static class GeoPoint {
		
		 public Geometry geometry;
		 public Point point;
		 
		 /**
	     * Constructs a new GeoPoint object with the specified geometry and point.
	     * @param geometry the geometry associated with the point.
	     * @param point the actual point in the coordinate system.
	     */
		 public GeoPoint(Geometry geometry, Point point)
		 {
			 this.geometry = geometry;
			 this.point = point;
		 }
		 
		 @Override
		 public boolean equals(Object obj) 
		 {
		 	 if (this == obj) return true;
		 	 if (obj instanceof GeoPoint other)
		 	     return Objects.equals(geometry,other.geometry) && Objects.equals(point, other.point);
		 	 return false;
		 }
		 
		 
		 @Override
		 public String toString() {
		 	return "geoPoint [geometry=" + geometry + ", point=" + point + "]";
		 }
		 
		}
}

