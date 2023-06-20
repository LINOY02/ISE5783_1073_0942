package lighting;

import primitives.Color;

/**
* The Light class represents a light source in a 3D scene.
* It is an abstract class that provides basic functionality for a light source.
*/
abstract class Light 
{
	/**
	 * Represents the intensity of a light source.
	 */
	private Color intensity;
	private double radius = 0;//the radius of the light
	

	
	/**
     * Constructs a new Light object with the specified intensity.
     * @param intensity The color intensity of the light.
     */
	protected Light(Color intensity, double radius)
	{
		this.intensity = intensity;
		this.radius = radius;
	}
	
	protected Light(Color intensity)
	{
		this.intensity = intensity;
	}

	
	
	
	/**
     * @return The intensity of the light.
     */
	public Color getIntensity() {
		return intensity;
	}

	/**
     * @return The radius of the light.
     */
	public double getRadius()
	{
		return radius;
	}
	
}
