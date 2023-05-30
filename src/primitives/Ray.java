package primitives;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.isZero;
import static primitives.Util.*;
import java.lang.Object;
import java.util.List;



public class Ray 
{
  /**
  * the head of the ray
  */
  final Point p0;
  /**
   * the direction of the ray
   */
  final Vector dir;
  private static final double DELTA = 0.1;
  
  /**
   * Ray constructor with to parameters
   * @param p0 - point
   * @param dir - vector
   */
  public Ray(Point p0, Vector dir)
  {
	  this.p0 = p0;
	  this.dir = dir.normalize();
  }
  
  public Ray(Point p0, Vector dir, Vector n)
  {
	  this.dir = dir.normalize();
	  Vector epsVector = n.scale(n.dotProduct(this.dir) > 0 ? DELTA : -DELTA);
	  this.p0 = p0.add(epsVector);
  }
  
  
@Override
public boolean equals(Object obj) 
{
  if (this == obj)
	  return true;
  if (obj instanceof Ray other)
      return p0.equals(other.p0) && dir.equals(other.dir);
  return false;
  }

  /**
   * getter of the field p0
   * @return the head point of the ray
   */
public Point getP0() 
{
	return p0;
}

/**
 * getter of the filed dir
 * @return the direction vector of the ray
 */
public Vector getDir() 
{
	return dir;
}
/**
 * Calculation of a point on ray
 * @param t
 * @return
 */
public Point getPoint(double t)
{
	return p0.add(dir.scale(t));
}

/**
Finds the closest point to a given point within a list of points.
@param points The list of points to search for the closest point.
@return The closest point to the given point, or null if the list is empty.
*/
public GeoPoint findClosestGeoPoint(List<GeoPoint> points)
{
	GeoPoint closetPoint = null;
	double dis = Double.MAX_VALUE;
	double tempDis;
	
	if(points.isEmpty()|| points == null) //check if the list is empty
		return null;
	
	for (GeoPoint gPoint : points) //going through all the points in the list
	{
		tempDis = p0.distance(gPoint.point);
		if(tempDis < dis) //check if this point closer to the head of the ray
		{
			closetPoint = gPoint;
			dis = tempDis;
		}
	}
	return closetPoint;
}


public Point findClosestPoint(List<Point> points) {
	 return points == null || points.isEmpty() ? null
	 : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}



@Override
public String toString() {
	return "Ray [p0=" + p0 + ", dir=" + dir + "]";
}
	
}
