package renderer;
import primitives.*;

import scene.*;

/**
An abstract class representing a basic ray tracer.
This class provides a constructor for the scene object and an abstract method for tracing a ray.
*/
public abstract class RayTracerBase 
{
	protected Scene scene;
	
	/**
    Constructs with one param.
	@param scene the scene to be traced
	*/
	public RayTracerBase(Scene scene)
	{
		this.scene = scene;
	}
	
	/**
    Traces a ray in the scene and returns the color of the closest intersection point.
	@param ray the ray to be traced
	@return the color of the closest intersection point
	*/
	public abstract Color traceRay(Ray ray);

}
