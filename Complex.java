package edu.ycp.cs201.mandelbrot;
import java.lang.Math;
public class Complex {
    // ...fields...
	private double real;
	private double imag;
	
   

    public Complex(double real, double imag) {
		this.real=real;
		this.imag=imag;// TODO Auto-generated constructor stub
    }//system failure cause me to remake this constructor.

    // add given complex number to this one, returning the Complex result
    public Complex add(Complex other) {
        
    	double realSum = real+other.real;
        double imagSum = imag+other.imag;
        return new Complex(realSum, imagSum);// ...
		
    }

	// multiply given complex number by this one, returning the Complex result
    public Complex multiply(Complex other) {
        double likeNums = (real*other.real)-(imag*other.imag);
        double hateNums = (real*other.imag)+(imag*other.real);
        return new Complex(likeNums, hateNums);// ...
    }

    // get the magnitude of this complex number
    public double getMagnitude() {
        double mag = Math.sqrt(Math.pow(real, 2)+Math.pow(imag, 2));
        return mag;// ...
    }
}