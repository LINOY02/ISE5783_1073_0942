package renderer;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Camera 
{
	private Point p0;
	private Vector Vup;
	private Vector Vto;
	private Vector Vright;
	private double height;
	private double width;
	private double distance;
	
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
	public Camera(Point p0, Vector Vup, Vector Vto)
	{
		this.p0 = p0;
		if(Vup.dotProduct(Vto) != 0)
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
	 * 
	 * @param nX - row width
	 * @param nY - column height
	 * @param j - the column of the pixel
	 * @param i - The row of the pixel
	 * @return
	 */
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		Point centerPoint =p0.add(Vto.scale(distance));
		double rX = width/nX;
		double rY = height/nY;
		double yI = -(i-(nY-1)/2)*rY;
		double xJ = (j-(nX-1)/2)*rX;
		Point pij = centerPoint;
		if(xJ != 0)
			pij = pij.add(Vright.scale(xJ));
		if(yI != 0)
			pij = pij.add(Vup.scale(yI));
		Ray ray = new Ray(p0, pij.subtract(p0));
		return ray;
	}
	

}
