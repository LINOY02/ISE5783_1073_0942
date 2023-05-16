package lighting;

import primitives.Color;

import primitives.Double3;

public class AmbientLight {
	
	Color intensity;
	public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	
	public AmbientLight(Color IA, Double3 kA)
	{
		intensity = IA.scale(kA);	}
	
	public AmbientLight(Color IA, double ka)
	{
		intensity = IA.scale(ka);	}
	
	public Color getIntensity()
	{
		return this.intensity;
	}

}
