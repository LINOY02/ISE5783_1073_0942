package geometries;

import primitives.Point;
import primitives.Vector;
import java.util.List;

/**
 * 
 * @author linoy and Tamar
 *
 */
public interface Geometry extends Intersectable
{
  public Vector getNormal(Point p);
}
