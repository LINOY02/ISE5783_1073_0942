package lighting;

import java.security.NoSuchProviderException;

import org.junit.experimental.max.MaxCore;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The SpotLight class represents a spotlight in a 3D scene.
 * It extends the PointLight class and provides functionality for a directional light beam.
 */
public class SpotLight extends PointLight
{
    /**
     * the direction of the spot light
     */
	private Vector direction;
	
	/**
     * Constructs a new SpotLight object with the specified intensity, position, and direction.
     * @param intensity The color intensity of the light.
     * @param position  The position of the light source.
     * @param direction The direction of the spotlight beam.
     */
	public SpotLight(Color intensity,Double radius, Point position, Vector direction) {
		super(intensity,radius, position);
		this.direction = direction;
	}
	
	
	/**
     * Constructs a new SpotLight object with the specified intensity, position, and direction.
     * @param intensity The color intensity of the light.
     * @param position  The position of the light source.
     * @param direction The direction of the spotlight beam.
     */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction;
	}
	
	@Override
	public Color getIntensity(Point p) {
		double scalar = Math.max(0,direction.normalize().dotProduct(getL(p)));// calculate the scalar
		return super.getIntensity(p).scale(scalar);// mult the scalar with the result with the point light
	}

	@Override
	public double getDistance(Point point) {
		// TODO Auto-generated method stub
		return super.getDistance(point);
	}

}
