package renderer;
import primitives.Point;

import java.util.*;
import primitives.Vector;
import renderer.PixelManager.Pixel;
import primitives.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static primitives.Util.*;

import java.util.MissingResourceException;
/**
 * class that represent a camera
 * 
 * @author Linoy and Tamar
 *
 */
public class Camera 
{
	private Point p0;
	private Vector Vup;
	private Vector Vto;
	private Vector Vright;
	private double height;
	private double width;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;
	private int threadsCount = 10;
	private double printInterval = 3;
	private PixelManager pixelManager;
	
    
	/**
	 * getter to the height of the view plane
	 * @return height of the view plane
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * getter to the width of the view plane
	 * @return width of the view plane
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * getter to the distance of the camera from the view plane
	 * @return the distance
	 */
		public double getDistance() {
		return distance;
	}
	/**
	 * Constructor with parameters
	 * @param p0
	 * @param Vup
	 * @param Vto
	 */
	public Camera(Point p0, Vector Vto, Vector Vup)
	{
		this.p0 = p0;
		if(!isZero(Vup.dotProduct(Vto)))
			throw new IllegalArgumentException("The vectors are not perpendicular");
		this.Vup = Vup.normalize();
		this.Vto = Vto.normalize();
		this.Vright = Vto.crossProduct(Vup).normalize();
		this.height = 0;
		this.width = 0;
		this.distance = 0;
	}
	
	/**
	 * the function set the parameters of the size of the view plane
	 * @param width
	 * @param height
	 * @return this camera
	 */
	public Camera setVPSize(double width, double height)
	{
		this.width = width;
		this.height = height;
		return this;
	}
	
	/**
	 * the function set the parameter of the distance between the camera and the view plane
	 * @param distance
	 * @return this camera
	 */
	public Camera setVPDistance(double distance)
	{
		this.distance = distance;
		return this;
	}
	
	/**
	 * the function set the parameter of the Image Writer
	 * @param imageWriter
	 * @return this camera
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}
	
	/**
	 * the function set the parameter of the rayTracer
	 * @param rayTracer
	 * @return this camera
	 */
	public Camera setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}	

	/**
    Constructs a ray from a camera position to a specific pixel in the view plane.
    @param nX The number of pixels in the x direction in the view plane.
    @param nY The number of pixels in the y direction in the view plane.
    @param j The x coordinate of the pixel in the view plane.
    @param i The y coordinate of the pixel in the view plane.
    @return A ray from the camera position to the specified pixel in the view plane.
	*/
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		// Calculate the center point of the view plane
		Point centerPoint;
		if(isZero(distance))
			centerPoint = p0;
		else
			centerPoint = p0.add(Vto.scale(distance));
		// Calculate the size of each pixel in the view plane
		double rX = width/nX;
		double rY = height/nY;
		// Calculate the position of the specified pixel on the view plane
		double yI = -(i-(nY-1)/2d)*rY;
		double xJ = (j-(nX-1)/2d)*rX;
		Point pij = centerPoint;
		if(!isZero(xJ))
			pij = pij.add(Vright.scale(xJ));
		if(!isZero(yI))
			pij = pij.add(Vup.scale(yI));
		if(pij.equals(centerPoint))
			return new Ray(p0, new Vector(pij.getX(), pij.getY(), pij.getZ()));
		return new Ray(p0, pij.subtract(p0));
	}
	
	/**
	 * Renders an image by casting rays through each pixel and calculating the color of the closest intersection.
	 * Throws a MissingResourceException if any required resources are missing.
	 * @return this object
	 */
	/**public Camera renderImage()
	{
		// check if some of the parameters are missing
		if(p0 == null)
			throw new MissingResourceException("point p0 is missing","Camera", "p0");
		if(Vup == null)
			throw new MissingResourceException("vector Vup is missing","Camera", "Vup");
		if(Vright == null)
			throw new MissingResourceException("vector Vright is missing","Camera", "Vright");
		if(Vto == null)
			throw new MissingResourceException("vector Vto is missing","Camera", "Vto");
		if(height == 0)
			throw new MissingResourceException("height is missing","Camera", "height");
		if(width == 0)
			throw new MissingResourceException("width is missing","Camera", "width");
		if(distance == 0)
			throw new MissingResourceException("distance is missing","Camera", "distance");
		if(imageWriter == null)
			throw new MissingResourceException("image writer is missing", "Camera", "imageWriter");
		if(rayTracer == null)
			throw new MissingResourceException("ray tracer is missing", "Camera", "rayTracer");
		
		// if all the parameters are not missing, paint each pixel
		for (int i = 0; i < imageWriter.getNx(); i++)
		{
			for (int j = 0; j < imageWriter.getNy(); j++)
			{
				    imageWriter.writePixel(i, j, castRay(imageWriter.getNx(), imageWriter.getNy(), j, i));
			}
		}
		return this;
	}*/

	
	/**
	   * build for each pixel a ray and get it's color
	   *
	   * @return this camera
	   */
	  public Camera renderImage() {
	    if (p0 == null || Vright == null || Vup == null || Vto == null || imageWriter == null || rayTracer == null)
	      throw new MissingResourceException("missing filed in camera", "", "");
	    int nx = imageWriter.getNx();
	    int ny = imageWriter.getNy();
	    pixelManager = new PixelManager(ny, nx, printInterval);
	    if (threadsCount == 0)
	      for (int x = 0; x < nx; x++)
	        for (int y = 0; y < ny; y++)
	          castRay(x, y, nx, ny);
	    else {
	      var threads = new LinkedList<Thread>(); // list of threads
	      while (threadsCount-- > 0) // add appropriate number of threads
	        threads.add(new Thread(() -> { // add a thread with its code
	          Pixel pixel; // current pixel(row,col)
	          // allocate pixel(row,col) in loop until there are no more pixels
	          while ((pixel = pixelManager.nextPixel()) != null)
	            // cast ray through pixel (and color it – inside castRay)
	            castRay(pixel.col(), pixel.row(), nx, ny);
	        }));
	      // start all the threads
	      for (var thread : threads)
	        thread.start();
	      // wait until all the threads have finished
	      try {
	        for (var thread : threads)
	          thread.join();
	      } catch (InterruptedException ignore) {
	      }
	    }
	    return this;
	  }
	  
	public Camera setMultiThreading(int threads) {
	    if (threads < 0)
	      throw new IllegalArgumentException("number of threads must not be negative");
	    threadsCount = threads;
	    return this;
	  }

	  public Camera setDebugPrint(double interval) {
	    if (interval < 0)
	      throw new IllegalArgumentException("print interval must not be negative");
	    printInterval = interval;
	    return this;
	  }
	
	/**
	 * Prints a grid of a given color onto the image. The grid is formed by coloring every nth horizontal and vertical pixel.
	 * Throws a MissingResourceException if the image writer is missing.
	 * @param interval The interval between two colored pixels in the grid.
	 * @param color    The color to use for the grid.
	 */
	public void printGrid(int interval , Color color )
	{
		// check that the image writer is not missing
		if(imageWriter == null)
			throw new MissingResourceException("image writer is missing", "Camera", "imageWriter");
		
		// paint all the pixels in the grid
		for (int i = 0; i < imageWriter.getNx(); i++)
		{
			for (int j = 0; j < imageWriter.getNy(); j++)
			{
				if (j % interval == 0 || i % interval == 0)
				    imageWriter.writePixel(i, j, color);
			}
		}
	}
	
	
	/**
	 * Writes the rendered image to a file using the image writer. 
	 * Throws a MissingResourceException if the image writer is missing.
	 */
	public void writeToImage()
	{
		if(imageWriter == null)
			throw new MissingResourceException("image writer is missing", "Camera", "imageWriter");
		imageWriter.writeToImage();//delegation
	}
	
	/**
	 * Casts a ray from the camera towards the given pixel coordinates (i,j) and returns the color of the object that the ray intersects with. 
	 * The function constructs a ray using the camera's parameters and the given pixel coordinates, and then traces the ray using the ray tracer. 
	 * @param nX The number of pixels in the x direction.
	 * @param nY The number of pixels in the y direction.
	 * @param i The x-coordinate of the pixel.
	 * @param j The y-coordinate of the pixel.
	 * @return The color of the object that the ray intersects with.
	 */
	private Color castRay(int nX, int nY, int i, int j)
	{
		// construct a ray through the pixel
		Ray ray = constructRay(nX, nY, j, i);
		// find the color of the pixel and return it
		return rayTracer.traceRay(ray);
	}

	@Override
	public String toString() {
		return "Camera [p0=" + p0 + ", Vup=" + Vup + ", Vto=" + Vto + ", Vright=" + Vright + ", height=" + height
				+ ", width=" + width + ", distance=" + distance + "]";
	}
	
	
}
