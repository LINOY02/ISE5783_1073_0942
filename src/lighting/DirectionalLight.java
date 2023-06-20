package lighting;

import javax.swing.text.Position;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in a 3D scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class DirectionalLight extends Light implements LightSource
{
	/**
	 * the direction of the light
	 */
	private Vector direction;

	/**
     * Constructs a new DirectionalLight object with the specified intensity and direction.
     * @param intensity The color intensity of the light.
     * @param direction The direction of the light.
     */
	public DirectionalLight(Color intensity,Double radius, Vector direction) {
		super(intensity, radius);
		this.direction = direction;
		// TODO Auto-generated constructor stub
	}
	
       
	@Override
	public Color getIntensity(Point p) {
		return getIntensity();
	}
    
	@Override
	public Vector getL(Point p) {
		// TODO Auto-generated method stub
		return direction.normalize();
	}

	@Override
	public double getDistance(Point point) {
		// TODO Auto-generated method stub
		return Double.POSITIVE_INFINITY;
	}

}
