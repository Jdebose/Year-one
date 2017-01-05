package edu.ycp.cs201.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Mandelbrot {
	public static final int HEIGHT = 600;

	public static final int WIDTH = 600;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Please enter coordinates of region to render:");
		System.out.print("  x1: ");
		double x1 = keyboard.nextDouble();
		System.out.print("  y1: ");
		double y1 = keyboard.nextDouble();
		System.out.print("  x2: ");
		double x2 = keyboard.nextDouble();
		System.out.print("  y2: ");
		double y2 = keyboard.nextDouble();

		System.out.print("Output filename: ");
		String fileName = keyboard.next();

		// TODO: create the rendering, save it to a file
		int[][] iterCounts = new int[WIDTH][HEIGHT];

		MandelbrotTask[] task = new MandelbrotTask[4];//splits the mandelbrot task into 4 
		for (int i = 0; i < 4; i++){
			task[i] = new MandelbrotTask(x1, y1, x2, y2, 0, 600, 150*i, 150*(i+1), iterCounts);	
		}
		Thread[] bread = new Thread[4];//puts the tasks into the threads
		for (int i = 0; i< 4; i++){
			bread[i] = new Thread(task[i]);
			bread[i].start();
		}
		for (int i = 0; i<4; i++){
			try{
				bread[i].join();

			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		for (int i = 0; i < WIDTH; i++){
			for (int j = 0; j<HEIGHT; j++){
				if (iterCounts[i][j]==1000){//points in the mandelbrot are black, points outside are different colors
					g.setColor(Color.BLACK);
					g.fillRect(i, j, 1, 1);
				}
				else if (iterCounts[i][j]<1000&&iterCounts[i][j]>=50){
					g.setColor(Color.RED);
					g.fillRect(i, j, 1, 1);
				}
				else if (iterCounts[i][j]<50&&iterCounts[i][j]>=40){
					g.setColor(Color.ORANGE);
					g.fillRect(i, j, 1, 1);
				}
				else if (iterCounts[i][j]<40&&iterCounts[i][j]>=30){
					g.setColor(Color.YELLOW);
					g.fillRect(i, j, 1, 1);
				}
				else if (iterCounts[i][j]<30&&iterCounts[i][j]>=20){
					g.setColor(Color.GREEN);
					g.fillRect(i, j, 1, 1);
				}
				else if (iterCounts[i][j]<20&&iterCounts[i][j]>=19){
					g.setColor(Color.CYAN);
					g.fillRect(i, j, 1, 1);
				}
				else if (iterCounts[i][j]<19){
					g.setColor(Color.BLUE);
					g.fillRect(i, j, 1, 1);
				}
				
		
				
				
				
			}
		}g.dispose();
		OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));//writes the png file and saves it
		try {
		    ImageIO.write(image, "PNG", os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
