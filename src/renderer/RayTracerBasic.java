package renderer;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
	private static final Double3 INITIAL_K = Double3.ONE;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private int numOfRays = 1;
		
	
    /**
     * constructor with one param
     * @param scene
     */
	public RayTracerBasic(Scene scene, int numOfRays) {
		super(scene);// call the ctor of the father
		this.numOfRays = numOfRays;
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
		if (closestPoint == null)
			return scene.background;
		return calcColor(closestPoint, ray);// call the function that return the color of the point
	}
	
	private Color calcColor(GeoPoint gp, Ray ray) 
	{
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Calculates the color of a point in the scene based on the ambient light present.
	 * @param point the point in the scene for which to calculate the color
	 * @return the color of the point based on the ambient light present
	 */
	private Color calcColor(GeoPoint point, Ray ray, int level, Double3 k)
	{
		Color color = calcLocalEffects(point,ray, k);
	    return 1 == level ? color : color.add(calcGlobalEffects(point, ray, level, k));
	}
	

	/**
	 * Calculates the local effects of the given geometric point and ray.
	 * @param gp The geometric point for which to calculate the local effects.
	 * @param ray The ray used for the calculation.
	 * @return The resulting color after applying the local effects.
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) 
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
			  Double3 ktr = transparency(gp, lightSource, l, n);
		      if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K))
		         { 
		           Color iL = lightSource.getIntensity(gp.point).scale(ktr);
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
		double nv = alignZero(normal.dotProduct(v));
		if (isZero(nv))
            return null;
		Vector r = v.subtract(normal.scale(nv*2));
		return new Ray(point, r, normal);
	}
	
	 private Ray constructRefractedRay(Vector normal, Point point, Vector vector) {
	        return new Ray(point, vector, normal);
	    }
	

	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) 
	{ 
		Color color= Color.BLACK; 
	    Vector v = ray.getDir(); 
	    Vector n = gp.geometry.getNormal(gp.point); 
	    Material material= gp.geometry.getMaterial(); 
	    return calcGlobalEffect(constructReflectedRay(n, gp.point, v), level, k, material.Kr) 
	    		.add(calcGlobalEffect(constructRefractedRay(n, gp.point, v), level, k, material.Kt)); 
	} 
	private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) 
	{ 
		Double3 kkx= k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K)) 
			return Color.BLACK; 
		GeoPoint gp= findClosestIntersection(ray); 
		if (gp == null) 
			return scene.background.scale(kx); 
		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK: calcColor(gp, ray, level - 1, kkx).scale(kx); 
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
	/*private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light)
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
			if(alignZero(geoPoint.point.distance(gp.point)) <= distance && geoPoint.geometry.getMaterial().Kt.equals(Double3.ZERO))
				return false;
		}
		return true;
	}*/
	
	/*private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n)

	{
		// Invert the light direction to point from the point to the light source
		Vector lightDirection = l.scale(-1);
		// Create a ray from the shifted point to the light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        // Find the intersections between the ray and the geometries in the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        // there is no intresction
		if (intersections == null)
			return Double3.ONE;
		// the distance between the light and the point
		double distance = light.getDistance(gp.point);
		Double3 ktr = Double3.ONE;
		// Check if any of the intersections are closer to the light source than the shifted point
		for (GeoPoint geoPoint : intersections) 
		{
			if(alignZero(geoPoint.point.distance(gp.point)) <= distance)
				{
				ktr = ktr.product(geoPoint.geometry.getMaterial().Kt);
				if(ktr.lowerThan(MIN_CALC_COLOR_K))
					return Double3.ZERO;
				}
			
		}
		return ktr;
	}*/
	
	
	/**
	 * 
	 * function that creates Partial shading in case the body or bodies that block
	 * the light source from the point have transparency at some level or another
	 * 
	 * @param gp- the point in the geometry
	 * @param light
	 * @param l         -the vector from the light to the point
	 * @param n-        normal vector to the point at the geometry
	 * @return double number represent the shadow
	 */
	private Double3 transparency (GeoPoint gp, LightSource light, Vector l, Vector n) {
		Double3 sum = Double3.ZERO;// sum of ktr - Coefficients
		List<Ray> beams = constructRaysToLight(light, l, n, gp);// create numberOfRays rays
		for (Ray ray : beams) { // for each ray
			List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);// calculate Intersections
			if (intersections != null)// there are intersections
			{
				double distance = light.getDistance(gp.point);
				Double3 ktr = Double3.ONE;
				for (GeoPoint geoPoint : intersections) {
					if (alignZero(geoPoint.point.distance(gp.point)) <= distance) 
					{
						//negative when the intersection point is before(from the object view) the light source
						ktr = ktr.product(geoPoint.geometry.getMaterial().Kt);
						if (ktr.lowerThan(MIN_CALC_COLOR_K))// if we got to the minimum value of k- stop the recursion
						{
							ktr = Double3.ZERO;
							break;// stop the checking for current point
						}
					}
					// else -> the intersection point is after(from the object view) the light
					// source, there is no shadow
				}
				sum = sum.add(ktr);
			} 
			else// no intersections
			{
				sum = sum.add(Double3.ONE);
			}
		}
		return sum.reduce(beams.size());// Average of Coefficients
	}

	
	
	/**

	Constructs a list of rays from a given point towards a light source.

	@param light The light source
    @param l Vector representing the direction from the point to the light source
    @param n Normal vector at the integration point
    @param geopoint The integration point on the object's surface
    @return A list of rays from the integration point towards the light source
	*/
	private List<Ray> constructRaysToLight(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n);
		List<Ray> beam = new LinkedList<>();// create list of rays
		beam.add(lightRay);
		if (light.getRadius() == 0) //if the light is one point(no radius) send one ray
		    return beam;
		Point p0 = lightRay.getP0();// the start point of the ray (the integration point)
		Vector v = lightRay.getDir();
		Vector vx= v.OrthogonalVector();
		Vector vy = (v.crossProduct(vx)).normalize();
		double r = light.getRadius();
		double distance = light.getDistance(p0);
		Point pC;
		if(Util.isZero(distance))
			 pC =p0;
		else
		     pC = lightRay.getPoint(distance);// calculate the center point of the light
		for (int i = 0; i < numOfRays - 1; i++) 
		{
		// create random polar system coordinates of a point in circle of radius r
		double cosTeta = ThreadLocalRandom.current().nextDouble(-1, 1);
		double sinTeta = Math.sqrt(1 - cosTeta * cosTeta);
		double d = ThreadLocalRandom.current().nextDouble(0, r);
		// Convert polar coordinates to Cartesian ones
		double x = d * cosTeta;
		double y = d * sinTeta;
		// pC - center of the circle
		// p0 - start of central ray, v - its direction, distance - from p0 to pC
		Point point = pC;
		 //move the new point from the center to the random point
		if (!Util.isZero(x))
		     point = point.add(vx.scale(x));
		if (!Util.isZero(y))
		     point = point.add(vy.scale(y));
		if(!(Util.isZero(x)&& Util.isZero(y)))
		     beam.add(new Ray(p0, point.subtract(p0), n)); // normalized inside Ray ctor
		}
		return beam;

		}
	
	
}
