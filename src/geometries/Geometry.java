package geometries;

import primitives.Point;
import primitives.Vector;
import java.util.List;
import primitives.*;
/**
 * 
 * @author linoy and Tamar
 *
 */
public abstract class Geometry extends Intersectable
{  
	protected Color emission = Color.BLACK;
	private Material material = new Material();
	
	public abstract Vector getNormal(Point p);
	
	public Color getEmission() 
	{
		return emission;
	}
	
	public Geometry setEmission(Color emission) 
	{
		
		this.emission = emission;
		return this;
	}

	public Material getMaterial() {
		return material;
	}

	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
}
