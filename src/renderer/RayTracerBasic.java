package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
/**
 * The class inherits from RayTracerBase class
 * @author Tamar and Linoy
 *
 */
public class RayTracerBasic extends RayTracerBase
{
    /**
     * constructor with one param
     * @param scene
     */
	public RayTracerBasic(Scene scene) {
		super(scene);// call the ctor of the father
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Traces a ray through the scene and calculates the color at the point where the ray intersects with an object.
	 * @param ray the ray to trace through the scene
	 * @return the color at the point where the ray intersects with an object, or the background color if no intersection is found
	 */
	@Override
	public Color traceRay(Ray ray) 
	{
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if(intersections == null)// check if there is no point in the intersection
			return scene.background;
		GeoPoint closetPoint = ray.findClosestGeoPoint(intersections);// find the closet point to the head of the ray
		return calcColor(closetPoint);// call the function that return the color of the point
	}

	/**
	 * Calculates the color of a point in the scene based on the ambient light present.
	 * @param point the point in the scene for which to calculate the color
	 * @return the color of the point based on the ambient light present
	 */
	private Color calcColor(GeoPoint point)
	{
		return scene.ambientLight.getIntensity().add(point.geometry.getEmission());
	}

	
}
