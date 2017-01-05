package edu.ycp.cs201.disks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DisksPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;

	private Timer timer;
	private Random rng;//random number variable
	private int movex;//move x/ while moving is true
	private int movey;//move y/while moving is true
	private boolean moving = false;//determines when moving is true
	private int diskCount;//counts the number of disks
	private int d;//disk radius
	private ArrayList <Disk> diskList;//arraylist to put the disks in
	private String failure = "Game Over!";//Game Over screen
	private JLabel count;//visual count on the screen
	private int Time;//int variabel in charge of keeping time
	private boolean GameOver=false;//boolean which determines when the game is over
	// TODO: add any other fields you need to represent the state of the game




	public DisksPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.GRAY);
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				handleMouseClick(e);
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				handleMouseMove(e);
			}
		});

		// Schedule timer events to fire every 100 milliseconds (0.1 seconds)
		this.timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleTimerEvent(e);
			}
		});
		timer.start();
		rng = new Random();
		makeRandomCircle();
		diskList = new ArrayList <Disk> (); //these four methods start the game off, making the random variable, starting the timer and making the arraylist

		count = new JLabel("0");
		count.setFont(FONT);
		add(count);//these three start the count
	}

	// You can call this method to convert a DiskColor value into
	// a java.awt.Color object.
	public Color colorOf(DiskColor dc) {
		return new Color(dc.red(), dc.green(), dc.blue());
	}

	// This method is called whenever the mouse is moved
	protected void handleMouseMove(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();// getters for x and y

		if((x<WIDTH) && (y<HEIGHT) && (x>0) && (y>0)){
			movex = e.getX();
			movey = e.getY(); 
			moving = true;
			repaint();//attaches them to the move versions of x and y only when mouse is one the screen
		}else{
			moving = false;
		}

	}

	// This method is called whenever the mouse is clicked
	protected void handleMouseClick(MouseEvent e) {

		double x = e.getX();
		double y = e.getY();
		Disk distance = new Disk(x, y, d, DiskColor.random());//makes new disk of d size and random color

		if(!distance.isOutOfBounds(WIDTH, HEIGHT)){//out of bounds call for the area
			boolean inbounds = true;
			for(int i = 0; i<diskList.size(); i++){
				Disk disc = diskList.get(i);

				if(disc.overlaps(distance)){//counts for if disks overlaps as well
					inbounds = false;
					break;
				}
			}
			if (inbounds == true){//if circle is inbounds the necessary variables are updated
				diskCount++;
				diskList.add(distance);
				Time = 300;
			}else{//otherwise the game is over
				GameOver=true;
			}
		}else{//same with if the circle is out of bounds
			GameOver=true;
		}

		repaint();
		makeRandomCircle();

	}



	// This method is called whenever a timer event fires
	protected void handleTimerEvent(ActionEvent e) {
		Time -= (timer.getDelay()/10);//since the timer fires every 100 milliseconds or .1 seconds the delay is divided by 10 to update every second
		if(Time == 0){//if timer runs out the game is over
			GameOver=true;// TODO
		}
		repaint();
	}

	private static final Font FONT = new Font("Dialog", Font.BOLD, 24);

	// This method is called automatically whenever the contents of
	// the window need to be redrawned.
	@Override
	public void paintComponent(Graphics g) {
		// Paint the window background

		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(0, 280, Time, 20);//making the time bar based on how much time is left
		//oval follows mouse x and y
		if (moving ==true){
			g.setColor(Color.BLACK);
			g.drawOval(movex - d, movey - d, d*2, d*2);//making the disk outline
		}
		if (GameOver==false){
			for(int i = 0; i<diskList.size(); i++){
				Disk disc = diskList.get(i);
				g.setColor(colorOf(disc.getColor()));
				g.fillOval((int)(disc.getX() - disc.getRadius()), (int) ((int)disc.getY() - disc.getRadius()), (int)disc.getRadius()*2, (int)disc.getRadius()*2);
				count.setText(Integer.toString(diskCount));
			}//this for loop puts the disks in the array and displays them on the board. It also updates the counter for the disks
			//paints solid oval	
		}else{
			timer.stop();
			JLabel Done = new JLabel(failure);
			Done.setFont(FONT);
			Done.setBounds(new Rectangle(new Point(130, 125), Done.getPreferredSize()));
			add(Done);
		}//if the game is over then everything stops running. New JLabel is made using failure the String that is used for our gsme over.
		//It is placed by setBounds in the appropriate spot.


		// TODO: draw everything that needs to be drawn
	}
	public void makeRandomCircle(){//makes a random circle
		int randNum = rng.nextInt(54)+10;
		d = randNum/2;
	}
}
