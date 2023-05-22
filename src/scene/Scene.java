package scene;

import primitives.*;
import lighting.*;

import java.util.LinkedList;
import java.util.List;

import geometries.*;

public class Scene {
	
	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();
	public List<LightSource> lights = new LinkedList<>();
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


public Scene setLights(List<LightSource> lights) {
	this.lights = lights;
	return this;
}
	
	
}
