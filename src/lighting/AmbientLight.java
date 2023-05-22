package lighting;

import primitives.Color;

import primitives.Double3;

/**
 * The AmbientLight class represents an ambient light source in a 3D scene.
 * It extends the Light class and provides functionality for ambient lighting.
 */
public class AmbientLight extends Light 
{
	
	/**
     * A constant representing no ambient light.
     */
	public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	
	/**
     * Constructs a new AmbientLight object with the specified ambient color and intensity.
     * @param IA The ambient color of the light.
     * @param kA The intensity factor of the ambient light.
     */
	public AmbientLight(Color IA, Double3 kA)
	{
		super(IA.scale(kA));
	}
	
	/**
     * Constructs a new AmbientLight object with the specified ambient color and intensity.
     * @param IA The ambient color of the light.
     * @param kA The intensity factor of the ambient light.
     */
	public AmbientLight(Color IA, double kA)
	{
		super(IA.scale(kA));	
	}
	
	
}
