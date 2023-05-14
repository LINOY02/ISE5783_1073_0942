package scene;

import primitives.*;
import lighting.*;
import geometries.*;

public class Scene {
	
	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();
	/**
	 * constructor with one param
	 * @param name
	 */
	public Scene(String name)
	{
		this.name = name;
	}

/**
 * Setter in the Builder development template
 * @param background
 * @return this
 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

/**
 * Setter in the Builder development template
 * @param ambientLight
 * @return this
 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

/**
 * Setter in the Builder development template
 * @param geometries
 * @return this
 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return  this;
	}
	
	
}
