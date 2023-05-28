package renderer;
import static primitives.Util.*;
import java.util.List;

import javax.script.AbstractScriptEngine;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

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
	private static final Double3 INITIAL_K = new Double3(1.0);
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
		
	
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
		GeoPoint closestPoint = findClosestIntersection(ray);
		return calcColor(closestPoint, ray);// call the function that return the color of the point
	}

	/**
	 * Calculates the color of a point in the scene based on the ambient light present.
	 * @param point the point in the scene for which to calculate the color
	 * @return the color of the point based on the ambient light present
	 */
	private Color calcColor(GeoPoint point, Ray ray, int level, Double3 k)
	{
		Color color = calcLocalEffects(point,ray);
	    return 1 == level ? color : color.add(calcGlobalEffects(point, ray, level, k));
	}
	
	private Color calcColor(GeoPoint gp, Ray ray) 
	{
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
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
		           color = color.add(iL.scale(calcDiffusive(material, nl)),iL.scale(calcSpecular(material, n, l, nl, v)));
		           // Calculate the specular component of the material 
                  }
		         }
		   }
		return color;
	}
	private GeoPoint findClosestIntersection(Ray ray)
	{
		List <GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
	    if (intersections == null)
	    	return null;
		return ray.findClosestGeoPoint(intersections);
	}
	
	private Ray constructReflectedRay(Vector normal, Point point, Vector v)
	{
		if (isZero(v.dotProduct(normal)))
            return new Ray(point, v);
		Vector r = v.subtract(normal.scale(v.dotProduct(normal)*2)).normalize();
		return new Ray(point, r, normal);
	}
	
	 private Ray constructRefractedRay(Vector normal, Point point, Vector vector) {
	        return new Ray(point, vector, normal);
	    }
	
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) 
	{
		Color color = Color.BLACK;
		Ray reflectedRay = constructReflectedRay(gp.geometry.getNormal(gp.point), gp.point, ray.getDir());
		GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
		Double3 kr = gp.geometry.getMaterial().Kr, kkr = kr.product(k);
		if (kkr.lowerThan(MIN_CALC_COLOR_K))
		{
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
		}
		Ray refractedRay = constructRefractedRay(gp.geometry.getNormal(gp.point), gp.point, ray.getDir());
		GeoPoint refractedPoint = findClosestIntersection(refractedRay);
		Double3 kt = gp.geometry.getMaterial().Kt, kkt = kt.product(k);
		if (kkt.lowerThan(MIN_CALC_COLOR_K)) 
		{
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
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
	
	/**
	 * Checks if the given geometric point is unshaded by the light source.
	 * @param gp        The geometric point to check.
	 * @param l         The direction of the light.
	 * @param n         The normal vector to the surface at the geometric point.
	 * @param nl        The dot product of the normal vector and the light direction.
	 * @param light     The light source.
	 * @return          {@code true} if the point is unshaded, {@code false} otherwise.
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light)
	{
		// Invert the light direction to point from the point to the light source
		Vector lightDirection = l.scale(-1);
		// Create a ray from the shifted point to the light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        // Find the intersections between the ray and the geometries in the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        // there is no intresction
		if (intersections == null)
			return true;
		// the distance between the light and the point
		double distance = light.getDistance(gp.point);
		// Check if any of the intersections are closer to the light source than the shifted point
		for (GeoPoint geoPoint : intersections) 
		{
			if(light.getDistance(geoPoint.point) < distance)
				return false;
		}
		return true;
	}

}
