package primitives;

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
public Point findClosestPoint(List<Point> points)
{
	Point closetPoint = null;
	double dis = Double.MAX_VALUE;
	double tempDis;
	
	if(points.isEmpty()) //check if the list is empty
		return null;
	
	for (Point point : points) //going through all the points in the list
	{
		tempDis = p0.distance(point);
		if(tempDis < dis) //check if this point closer to the head of the ray
		{
			closetPoint = point;
			dis = tempDis;
		}
	}
	return closetPoint;
}


@Override
public String toString() {
	return "Ray [p0=" + p0 + ", dir=" + dir + "]";
}
	
}
