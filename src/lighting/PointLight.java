package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.*;

/**
 * The PointLight class represents a point light source in a 3D scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource
{
  
	/**
	 * the position of the light
	 */
	private Point position;
	/**
	 * attenuation coefficients
	 */
	private double Kc = 1, Kl = 0, Kq = 0;
	
	/**
     * Constructs a new PointLight object with the specified intensity and position.
     * @param intensity The color intensity of the light.
     * @param position  The position of the light source.
     */
	public PointLight(Color intensity,Double radius, Point position) {
		super(intensity,radius);
		this.position = position;
	}

	
	
	
	@Override
	public Color getIntensity(Point p) {
		return getIntensity().scale(alignZero(1/(Kc + Kl*position.distance(p) + Kq*position.distanceSquared(p)))); // calculate by The light propagation model
	}

	@Override
	public Vector getL(Point p) {
		return p.subtract(position).normalize(); // return the vector L from pl point to p
	}

	/**
     * Sets the constant attenuation factor of the point light.
     * @param kC The constant attenuation factor to set.
     * @return The modified PointLight object.
     */
	public PointLight setKc(double kC) {
		this.Kc = kC;
		return this;
	}

	/**
     * Sets the linear attenuation factor of the point light.
     * @param kL The linear attenuation factor to set.
     * @return The modified PointLight object.
     */
	public PointLight setKl(double kL) {
		this.Kl = kL;
		return this;
	}

	/**
     * Sets the quadratic attenuation factor of the point light.
     * @param kQ The quadratic attenuation factor to set.
     * @return The modified PointLight object.
     */
	public PointLight setKq(double kQ) {
		this.Kq = kQ;
		return this;
	}

	@Override
	public double getDistance(Point point) {
		// TODO Auto-generated method stub
		return point.distance(position);
	}

}
