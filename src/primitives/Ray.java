package primitives;

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
  Ray(Point p0, Vector dir)
  {
	  this.p0 = p0;
	  this.dir = dir.normalize();
  }
  
  @Override
  public boolean equals(Object obj) {
  if (this == obj) return true;
  return (obj instanceof Ray other)
  && this.p0.equals(other.p0)
  && this.dir.equals(other.dir);
  }

  /**
   * getter of the field p0
   * @return the head point of the ray
   */
public Point getP0() {
	return p0;
}

/**
 * getter of the filed dir
 * @return the direction vector of the ray
 */
public Vector getDir() {
	return dir;
}
}
