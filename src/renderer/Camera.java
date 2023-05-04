package renderer;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static primitives.Util.isZero;
/**
 * class that represent a camera
 * 
 * @author linoy
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
	
    public Vector getVto() {
        return Vto;
    }

    public Vector getVup() {
        return Vup;
    }

    public Vector getVright() {
        return Vright;
    }

    public Point getP0() {
        return p0;
    }
    
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
	 * 
	 * @param nX - row width
	 * @param nY - column height
	 * @param j - the column of the pixel
	 * @param i - The row of the pixel
	 * @return
	 */
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		Point centerPoint;
		if(isZero(distance))
			centerPoint = p0;
		else
			centerPoint = p0.add(Vto.scale(distance));
		double rX = width/nX;
		double rY = height/nY;
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

	@Override
	public String toString() {
		return "Camera [p0=" + p0 + ", Vup=" + Vup + ", Vto=" + Vto + ", Vright=" + Vright + ", height=" + height
				+ ", width=" + width + ", distance=" + distance + "]";
	}	

}
