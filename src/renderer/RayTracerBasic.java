package renderer;

import java.util.List;

import javax.script.AbstractScriptEngine;

import geometries.Intersectable.GeoPoint;

import primitives.*;
import lighting.*;
import static primitives.Util.alignZero;
import scene.Scene;
/**
 * The class inherits from RayTracerBase class
 * @author Tamar and Linoy
 *
 */
public class RayTracerBasic extends RayTracerBase
{
	
	private static final double DELTA = 0.1;
	
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
		return calcColor(closetPoint, ray);// call the function that return the color of the point
	}

	/**
	 * Calculates the color of a point in the scene based on the ambient light present.
	 * @param point the point in the scene for which to calculate the color
	 * @return the color of the point based on the ambient light present
	 */
	private Color calcColor(GeoPoint point, Ray ray)
	{
		return scene.ambientLight.getIntensity().add(calcLocalEffects(point,ray));
	}
	
	/**
	 * Calculates the local effects of the given geometric point and ray.
	 * @param gp The geometric point for which to calculate the local effects.
	 * @param ray The ray used for the calculation.
	 * @return The resulting color after applying the local effects.
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) 
	{
		// Get the emission color of the geometry at the geometric point
		Color color = gp.geometry.getEmission();
		// Calculate the direction vector of the ray
		Vector v = ray.getDir (); 
		// Calculate the surface normal at the geometric point
		Vector n = gp.geometry.getNormal(gp.point);
		// Calculate the dot product of the normal and the direction vector
		double nv = alignZero(n.dotProduct(v)); 
		// If the dot product is close to zero, return the emission color
		if (nv == 0) 
			return color;
		Material material = gp.geometry.getMaterial();
		// Iterate over all light sources in the scene
		for (LightSource lightSource : scene.lights) 
		{
		  // Get the direction vector from the geometric point to the light source
		   Vector l = lightSource.getL(gp.point);
		   double nl = alignZero(n.dotProduct(l));
		   if (nl * nv > 0)
		   {// sign(nl) == sing(nv)
		      if (unshaded(gp, l, n, nl, lightSource))
		         { 
		           Color iL = lightSource.getIntensity(gp.point);
		           // Calculate the diffuse component of the material
		           color = color.add(iL.scale(calcDiffusive(material, nl)),
		           // Calculate the specular component of the material
		           iL.scale(calcSpecular(material, n, l, nl, v)));
                  }
		         }
		   }
		return color;
	}
 
	/**
	 * Calculates the diffuse component of the material.
	 * @param mat The material for which to calculate the diffuse component.
	 * @param nl The dot product of the surface normal and the light direction.
	 * @return The calculated diffuse component as a Double3 vector.
	 */
	private Double3 calcDiffusive(Material mat, double nl)
	{
		// Scale the diffuse coefficient of the material by the absolute value of nl
		return mat.Kd.scale(Math.abs(nl));
	}
	
	/**
	 * Calculates the specular component of the material.
	 * @param mat The material for which to calculate the specular component.
	 * @param n The surface normal vector.
	 * @param l The direction vector from the geometric point to the light source.
	 * @param nl The dot product of the surface normal and the light direction.
	 * @param v The direction vector from the camera to the geometric point.
	 * @return The calculated specular component as a Double3 vector.
	 */
	private Double3 calcSpecular(Material mat,Vector n,Vector l,double nl,Vector v)
	{
		// Calculate the reflection vector using the surface normal, light direction, and dot product
		Vector r = l.subtract(n.scale(nl*2)).normalize();
		// Calculate the specular reflection coefficient of the material and scale by the specular coefficient of the material
		return mat.Ks.scale(Math.pow(Math.max(0, alignZero(v.scale(-1).dotProduct(r))), mat.nShininess));
	}
	
	
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, double  nl, LightSource light)
	{
		Vector lightDirection = l.scale(-1);
		Vector epsVector = n.scale(nl < 0 ? DELTA : -1*DELTA);
		Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return true;
		double distance = light.getDistance(point);
		for (GeoPoint geoPoint : intersections) 
		{
			if(light.getDistance(geoPoint.point) < distance)
				return false;
		}
		return true;
	}
}
