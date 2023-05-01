package primitives;

import java.util.Objects;

public class Point 
{
  /**
   * A field of type Double3 to specify a point
   */
  Double3 xyz;
  
  /**
   * point constructor with 3 parameters
   * @param x - X axis coordinate
   * @param y - Y axis coordinate
   * @param z - Z axis coordinate
   */
  public Point(double x, double y, double z) 
  {
	  xyz = new Double3(x,y,z);
  }
  
  /**
   * point constructor with 1 parameter
   * @param xyz - Of type Double3
   */
  Point(Double3 xyz) 
  {
	  if(xyz == Double3.ZERO)
		  throw new IllegalArgumentException("ERROR: zero vector is not valid");
	  this.xyz = xyz;
  }

@Override
public int hashCode() {
	return Objects.hash(xyz.hashCode());
}

@Override
public boolean equals(Object obj) 
{
	 if (this == obj) return true;
	 if (obj instanceof Point other)
	     return xyz.equals(other.xyz);
	 return false;
}

@Override
public String toString() {
	return "" + xyz;
}
  
/**
 * The function performs vector subtraction
 * @param p - the start point
 * @return new vector 
 */
public Vector subtract(Point p)
{
	if(xyz.equals(p.xyz))
		throw new IllegalArgumentException("ERROR: Subtraction of identical vectors gives the zero vector");
	return new Vector(xyz.subtract(p.xyz));
}

/**
 * The function adds a vector to a point
 * @param v - the vector
 * @return new point
 */
public Point add(Vector v)
{
	return new Point(xyz.add(v.xyz));
}

/**
 * the function get a point and calculate the distance
 * @param p - the point
 * @return the square distance between this point an the current object
 */
public double distanceSquared(Point p)
{
	double x = this.xyz.d1 - p.xyz.d1; 
	double y = this.xyz.d2 - p.xyz.d2;
	double z = this.xyz.d3 - p.xyz.d3;
	return x*x + y*y + z*z;
}

/**
 *  the function get a point and calculate the distance
 * @param p - the point
 * @return the distance between this point an the current objec
 */
public double distance(Point p)
{
  return Math.sqrt(this.distanceSquared(p));
}
  
public double getX()
{
	return this.xyz.d1;
}

public double getY()
{
	return this.xyz.d2;
}

public double getZ()
{
	return this.xyz.d3;
}

}

