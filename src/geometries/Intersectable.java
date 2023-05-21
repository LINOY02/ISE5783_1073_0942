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


	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		return findGeoIntersectionsHelper(ray);
	}
	
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
	
	public static class GeoPoint {
		
		 public Geometry geometry;
		 public Point point;
		 
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

