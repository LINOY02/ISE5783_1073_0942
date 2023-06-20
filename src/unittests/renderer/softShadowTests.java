package unittests.renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

class softShadowTests {
	  private Scene scene = new Scene("Test scene");

	@Test
	   public void beforeSoftShadow() 
	{
		      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(0, 200, 255));
		      Camera camera= new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))//
		    	      .setVPSize(200, 200).setVPDistance(1000) //
		    	      .setRayTracer(new RayTracerBasic(scene).setNumOfRadius(1));
		      scene.geometries.add( //
		                           new Triangle(new Point(-170, -170, -115), new Point(140, -170, -135), new Point(90, 25, -150)) //
		                              .setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(140, 70, 20)), //
		                              
		                           new Triangle(new Point(-170, -170, -115), new Point(-80, 20, -140), new Point(90, 25, -150)) //
		                              .setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(140, 70, 20)),//
		                             
		                           new Sphere(25d ,new Point(70, 70, -11)) //
		                              .setEmission(new Color(100, 150, 80)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
		                              
		                              new Sphere(30d ,new Point(0, -14, 0)) //
		                              .setEmission(new Color(BLUE)) //
		                              .setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(30).setKt(0.6)), //
		                             
		                              
		                              new Sphere(17d ,new Point(-20, 24, 13)) //
		                              .setEmission(new Color(BLUE)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
		                              
		                              new Sphere(17d ,new Point(20, 24, 13)) //
		                              .setEmission(new Color(BLUE)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
		                              
		                              new Sphere(5d, new Point(0, -16, 50)).setEmission(new Color(20, 20, 20)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
		                              
		                             
		                              
		      );
		      scene.lights.add( //
		                       new SpotLight(new Color(700, 400, 400),5.0, new Point(40, 40, 115), new Vector(-1, -1, -4)) //
		                          .setKl(4E-4).setKq(2E-5));
		     /*** scene.lights.add( //
	                  new DirectionalLight(new Color(100, 600, 0), new Vector(-1, -1, -2))); //**/

		      camera.setImageWriter(new ImageWriter("beforeSoftShadow", 600, 600)) //
		         .renderImage() //
		         .writeToImage();
		   }
	
	@Test
	   public void afterSoftShadow() 
	{
		      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(0, 200, 255));
		      Camera camera= new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))//
		    	      .setVPSize(200, 200).setVPDistance(1000) //
		    	      .setRayTracer(new RayTracerBasic(scene).setNumOfRadius(30));
		      scene.geometries.add( //
		                           new Triangle(new Point(-170, -170, -115), new Point(140, -170, -135), new Point(90, 25, -150)) //
		                              .setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(140, 70, 20)), //
		                              
		                           new Triangle(new Point(-170, -170, -115), new Point(-80, 20, -140), new Point(90, 25, -150)) //
		                              .setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(140, 70, 20)),//
		                             
		                           new Sphere(25d ,new Point(70, 70, -11)) //
		                              .setEmission(new Color(100, 150, 80)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
		                              
		                              new Sphere(30d ,new Point(0, -14, 0)) //
		                              .setEmission(new Color(BLUE)) //
		                              .setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(30).setKt(0.6)), //
		                             
		                              
		                              new Sphere(17d ,new Point(-20, 24, 13)) //
		                              .setEmission(new Color(BLUE)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
		                              
		                              new Sphere(17d ,new Point(20, 24, 13)) //
		                              .setEmission(new Color(BLUE)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
		                              
		                              new Sphere(5d, new Point(0, -16, 50)).setEmission(new Color(20, 20, 20)) //
		                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
		                              
		                             
		                              
		      );
		      scene.lights.add( //
		                       new SpotLight(new Color(700, 400, 400),5.0, new Point(40, 40, 115), new Vector(-1, -1, -4)) //
		                          .setKl(4E-4).setKq(2E-5));
		     /*** scene.lights.add( //
	                  new DirectionalLight(new Color(100, 600, 0), new Vector(-1, -1, -2))); //**/

		      camera.setImageWriter(new ImageWriter("afterSoftShadow", 600, 600)) //
		         .renderImage() //
		         .writeToImage();
		   }

}
