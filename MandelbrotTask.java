package edu.ycp.cs201.mandelbrot;
public class MandelbrotTask implements Runnable {
    private double x1, y1, x2, y2;
    private int startCol, endCol, startRow, endRow;
    private int[][] iterCounts;

    public MandelbrotTask(double x1, double y1, double x2, double y2,
                          int startCol, int endCol, int startRow, int endRow,
                          int[][] iterCounts) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.startCol = startCol;
        this.endCol = endCol;
        this.startRow = startRow;
        this.endRow = endRow;
        this.iterCounts = iterCounts;
    }

    public void run() {
        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                Complex c = getComplex(i, j);
                int iterCount = computeIterCount(c);
                iterCounts[i][j] = iterCount;
            }
        }
    }

    public Complex getComplex(int real, int imag){//takes the points in the col and row and makes a complex number
    	double row = (x2 - x1)/600;
    	double col = (y2 - y1)/600;
    	double x = row*real + x1;
    	double y = col*imag + y1;
    	return new Complex(x, y); // TODO: implement getComplex and computeIterCount methods
    }
    public int computeIterCount(Complex c){//computes the itercount for the mandelbrot task
    	int iterCount = 0;
    	Complex z = new Complex(0, 0);
    	while(z.getMagnitude() < 2.0 && iterCount < 1000){
    		z = z.multiply(z).add(c);
    		iterCount++;
    	}
    	return iterCount;
    }
}