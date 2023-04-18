package primitives;
import static primitives.Util.isZero;
public class Vector extends Point
{
    /**
     * vector constructor with 3 parameters
     * @param x - X axis coordinate
     * @param y - Y axis coordinate
     * @param z - Z axis coordinate
     */
	public Vector(double x, double y, double z)
    {
        super(x ,y ,z);
        if(x==0 && y==0 && z == 0)
  		  throw new IllegalArgumentException("ERROR: zero vector is not valid");
    }
    
	/**
	 *  vector constructor with 1 parameter
     * @param xyz - Of type Double3
	 */
    Vector(Double3 xyz)
    {
      super(xyz);
    }

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	 public boolean equals(Object obj) {
		 if (this == obj) return true;
		 if (obj instanceof Vector other)
			 return super.equals(other);
		 return false;
	}

	@Override
	public String toString() {
		return "->" + super.toString();
	}
    
	
	@Override
	public Vector add(Vector other)
	{
		if(this.equals(other.scale(-1)))
			throw new IllegalArgumentException("ERROR: Adding opposite vectors gives the zero vector");
		return new Vector(this.xyz.add(other.xyz));
	}
	
	/**
	 * The function multiplies the vector by a scalar
	 * @param scalar
	 * @return new vector
	 */
	public Vector scale(double scalar)
	{
		if(isZero(scalar))
			throw new IllegalArgumentException("ERROR: Scalar can't be zero");
		return new Vector(this.xyz.scale(scalar));
	}
	
	
	/**
	 * The function performs a scalar multiplication
	 * @param other
	 * @return the result of the multiplication
	 */
	public double dotProduct(Vector other)
	{
		return this.xyz.d1*other.xyz.d1 + this.xyz.d2* other.xyz.d2 + this.xyz.d3*other.xyz.d3;
	}
	
	
	/**
	 * The function performs vector multiplication
	 * @param other
	 * @return A vector perpendicular to both vectors
	 */
	public Vector crossProduct(Vector other)
	{
		double x = this.xyz.d2*other.xyz.d3 - this.xyz.d3*other.xyz.d2;
		double y = this.xyz.d3*other.xyz.d1 - this.xyz.d1*other.xyz.d3;
		double z = this.xyz.d1*other.xyz.d2 - this.xyz.d2*other.xyz.d1;
		return new Vector(x, y, z);
	}
    
	/**
	 * A function that calculates the squared length of the vector
	 * @return the length of the vector squared
	 */
	public double lengthSquared()
	{
		return this.dotProduct(this);
	}
	
	/**
	 * A function that calculates the length of the vector
	 * @return the length of the vector 
	 */	
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	 /**
	  * the function normalize the vector
	  * @return A unit vector in the same direction as the original vector
	  */
	public Vector normalize()
	{
		Vector newVector = new Vector(this.xyz);
		return newVector.scale(1/this.length());
	}
}
