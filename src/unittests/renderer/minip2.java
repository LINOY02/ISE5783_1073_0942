package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Geometries;
import geometries.Sphere;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.*;
class minip2 {

	@Test
	  void MiniProject2Test() {
		
		Scene scene = new Scene("Test scene") //
				.setCBR() //
		;

	    long startTime = System.currentTimeMillis();

	    // Test code

	    long endTime = System.currentTimeMillis();
	    long executionTime = endTime - startTime;

	    System.out.println("Test execution time: " + executionTime + " milliseconds");
	  }
	@Test
	void testManualBvh() {
		Color yellowColor = new Color(YELLOW);
		Material sphereMaterial = new Material().setKd(0.8).setKs(0.2).setShininess(200).setKr(0.1);

		Scene scene = new Scene("Test scene") //
				.setCBR() //
		;
		for (int col1 = -80; col1 < 80; col1 += 5 * 16) {
			var geos1 = new Geometries();
			for (int row1 = -40; row1 < 40; row1 += 5 * 8) {
				var geos2 = new Geometries();
				for (int col2 = col1; col2 < col1 + 5 * 16; col2 += 5 * 8) {
					var geos3 = new Geometries();
					for (int row2 = row1; row2 < row1 + 5 * 8; row2 += 5 * 4) {
						var geos4 = new Geometries();
						for (int col3 = col2; col3 < col2 + 5 * 8; col3 += 5 * 4) {
							var geos5 = new Geometries();
							for (int row3 = row2; row3 < row2 + 5 * 4; row3 += 5 * 2) {
								var geos6 = new Geometries();
								for (int col4 = col3; col4 < col3 + 5 * 4; col4 += 5 * 2) {
									var geos7 = new Geometries();
									for (int row4 = row3; row4 < row3 + 5 * 2; row4 += 5) {
										var geos8 = new Geometries();
										for (int col5 = col4; col5 < col4 + 5 * 2; col5 += 5)
											geos8.add(new Sphere(0.5, new Point(col5, row4, 800))
													.setEmission(yellowColor).setMaterial(sphereMaterial));
										geos7.add(geos8);
									}
									geos6.add(geos7);
								}
								geos5.add(geos6);
							}
							geos4.add(geos5);
						}
						geos3.add(geos4);
					}
					geos2.add(geos3);
				}
				geos1.add(geos2);
			}
			scene.geometries.add(geos1);
		}
		scene.lights.add(new DirectionalLight(new Color(YELLOW), 5.0, new Vector(0, 1, 0)));
		scene.lights
				.add(new SpotLight(new Color(WHITE), 5.0, new Point(100, 100, 1000), new Vector(1, -1, 1)).setKq(0.000001));

		new Camera(new Point(3, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(1000, 1000) //
				.setVPDistance(1000) //
				.setImageWriter(new ImageWriter("SpheresDofManualBvh", 2000, 2000)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage(); //
	}
}
