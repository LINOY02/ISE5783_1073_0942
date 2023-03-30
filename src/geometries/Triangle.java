package geometries;

import primitives.Point;

/**
 * A class that represents a triangle in the plane and inherits from class Polygon
 * @author Tamar and Linoy
 *
 */
public class Triangle extends Polygon
{
  /**
   * Triangle constructor that get 3 points
   * @param p1 - point
   * @param p2 - point
   * @param p3 - point
   */
  Triangle(Point p1, Point p2, Point p3)
  {
	  super(p1, p2, p3);
  }
}
