
package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

      scene.geometries.add( //
                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }
   
   
   @Test
   public void NS() {
	      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
	         .setVPSize(150, 150).setVPDistance(1000);

	      scene.geometries.add( //
	                           new Sphere(22d, new Point(40, 35, -50)).setEmission(new Color(BLUE)) //
	                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
	                           new Sphere(10d, new Point(-50, 0, -40)).setEmission(new Color(222, 170, 222)) //
	                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
	                           new Sphere(14d, new Point(-25, 50, -90)).setEmission(new Color(10, 10, 10)) //
                                  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                               new Sphere(12d, new Point(0, 0, -70)).setEmission(new Color(40, 70, 170)) //
                                  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                               new Sphere(20d ,new Point(35, -35, -11)) //
	                              .setEmission(new Color(160, 26, 156)) //
	                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
	                           new Sphere(16d ,new Point(-20, -40, -11)) //
	                              .setEmission(new Color(10, 150, 200)) //
	                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(20)), //
	                           new Sphere(8d ,new Point(-60, 40, -11)) //
	                              .setEmission(new Color(190, 100, 200)) //
	                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(20)), //
	                           new Sphere(6d ,new Point(-60, -55, -11)) //
	                              .setEmission(new Color(240, 10, 80)) //
	                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(20)),//
	                           new Sphere(1d ,new Point(-20, 0, -11)) //
	                              .setEmission(new Color(WHITE)) //
	                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6))//
          );
	      scene.lights.add( //
	                       new DirectionalLight(new Color(1000, 600, 0), new Vector(-1, -1, -2))); //
	                          //.setKl(0.0004).setKq(0.0000006));

	      camera.setImageWriter(new ImageWriter("NS", 500, 500)) //
	         .setRayTracer(new RayTracerBasic(scene)) //
	         .renderImage() //
	         .writeToImage();
	   }

@Test
   public void newS() {
	      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(0, 200, 255));
	      Camera camera= new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))//
	    	      .setVPSize(200, 200).setVPDistance(1000) //
	    	      .setRayTracer(new RayTracerBasic(scene));
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
	                       new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
	                          .setKl(4E-4).setKq(2E-5));
	     /*** scene.lights.add( //
                  new DirectionalLight(new Color(100, 600, 0), new Vector(-1, -1, -2))); //**/

	      camera.setImageWriter(new ImageWriter("newSone", 600, 600)) //
	         .renderImage() //
	         .writeToImage();
	   }
  /**
   public void newShape() {
	      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(0, 200, 255));
	      Camera camera= new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))//
	    	      .setVPSize(200, 200).setVPDistance(1000) //
	    	      .setRayTracer(new RayTracerBasic(scene));
	      
	      scene.geometries.add( //
	                        
	                           new Triangle(new Point(-150, -20, -85), new Point(150, -20, -85), new Point(-75, -635, -85)).setEmission(new Color(BLUE)) //
	                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
	                           new Sphere(15d ,new Point(100, 100, -200)) //
	                              .setEmission(new Color(YELLOW)) //
	                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.8)), //
	                           new Triangle(new Point(-70, -10, -70), new Point(70, -5, -70), new Point(-30, -60, -70)).setEmission(new Color(GREEN)) //
	                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
	                           new Triangle(new Point(-45, -50, -50), new Point(-10, -50, -50), new Point(-30, -60, -50)).setEmission(new Color(BLUE)) //
	                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
	                           new Triangle(new Point(-30, 50, -80), new Point(-32, 50, -80), new Point(-30, -15, -80)).setEmission(new Color(BLACK)) //
	                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
	                           new Triangle(new Point(-32, -15, -80), new Point(-32, 50, -80), new Point(-30, -15, -80)).setEmission(new Color(BLACK)) //
	                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
	                           new Triangle(new Point(-29, 47, -80), new Point(-29, 33, -80), new Point(-10, 40, -80)).setEmission(new Color(RED)) //
	                              .setMaterial(new Material().setKs(0.8).setShininess(60))
	      );
	      scene.lights.add( //
	                       new SpotLight(new Color(200, 200, 200), new Point(60, 60, -200), new Vector(90, 10, -120)) //
	                          .setKl(4E-4).setKq(2E-5));
	      
	      ImageWriter imageWriter = new ImageWriter("newShape", 600, 600);
	      camera.setImageWriter(imageWriter) //
	         .setRayTracer(new RayTracerBasic(scene)) //
	         .renderImage() //
	         .writeToImage();
	   }*/
   
   
   /**
   @Test
   public void Spheres() {
	      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
	    	         .setVPSize(200, 200).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(12d, new Point(50, 65, 60)).setEmission(new Color(GREEN)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.4).setShininess(80).setKt(0.6)),
                           new Sphere(12d, new Point(60, 50, 60)).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.4).setShininess(80).setKt(0.6)),
                           new Sphere(12d, new Point(40, 50, 65)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.4).setShininess(80).setKt(0.6)),
                           new Triangle(new Point(40, 50, 60), new Point(70, 50, 60), new Point(50, -5, 60)).setEmission(new Color(255, 0, 255)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.4).setShininess(80).setKt(0.6)), //
      new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
              new Point(75, 75, -150)) //
    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
 new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))); //
 
      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
    	         .setKl(4E-5).setKq(2E-7));

      camera.setImageWriter(new ImageWriter("refractionSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }*/
}

