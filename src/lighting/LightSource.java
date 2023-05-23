package lighting;
import primitives.*;

/**
 * The LightSource interface represents a light source in a 3D scene.
 * It defines methods for calculating the intensity and direction of the light at a given point.
 */
public interface LightSource 
{
	/**
     * Calculates the intensity of the light at the specified point.
     * @param p The point at which to calculate the intensity.
     * @return The intensity of the light at the specified point.
     */
	public Color getIntensity(Point p);
	
	
	/**
     * Calculates the direction from the light source to the specified point.
     * @param p The point for which to calculate the direction.
     * @return The direction from the light source to the specified point.
     */
	public Vector getL(Point p);
	
	
	double getDistance(Point point);

}
