package primitives;

public class Material 
{
   public Double3 Kd = Double3.ZERO, Ks = Double3.ZERO;
   public int nShininess = 0;


public Material setKd(Double3 kD) {
	this.Kd = kD;
	return this;
}
public Material setKs(Double3 ks) {
	this.Ks = ks;
return this;
}
public Material setKd(double kD) {
	this.Kd = new Double3(kD);
	return this;
}
public Material setKs(double ks) {
	this.Ks = new Double3(ks);
return this;
}
public Material setShininess(int nShininess) {
	this.nShininess = nShininess;
	return this;
}
   
}
