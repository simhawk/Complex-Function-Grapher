import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


public class ComplexFunctionGrapher extends JPanel implements ActionListener {
	
	private final int DELAY = 10;
	private Timer timer;
	
	private final int PITCH = 50;
	private final int TICK_SIZE = 3;
	
	private final int WINDOW_THICKNESS = 22;
	private final int X_BOUND_MIN = 10;
	private final int Y_BOUND_MIN = 10;
	
	private final int X_BOUND_LENGTH = 900;
	private final int Y_BOUND_LENGTH = 900;

	private final double MAX_RAD = 4.0;
	private final double MAX_ITR = 130;
	
	private int xOffset = 0;
	private int yOffset = 0;
	
	private int xBoxCenter = (X_BOUND_MIN + X_BOUND_LENGTH) / 2;
	private int yBoxCenter = (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2;
	
	private int xOrigin = xOffset + xBoxCenter;
	private int yOrigin = -yOffset + yBoxCenter;
	
	private int canvasWidth;
	private int canvasHeight;
	private int t = 0;
	private boolean constantBrightness = true;
	
	public ComplexFunctionGrapher() {
		initTimer();
		canvasWidth = getWidth();
		canvasHeight = getHeight();
	}
	
	private void doDrawing(Graphics gr) {
		Graphics2D dc = (Graphics2D) gr;
		for(int i = X_BOUND_MIN; i < X_BOUND_MIN + X_BOUND_LENGTH; i++)  {
			for(int j = Y_BOUND_MIN; j < Y_BOUND_MIN + Y_BOUND_LENGTH; j++) {
				Complex z = getMappedNumber(i, j);
				double x = z.re;
				double y = z.im;
				Complex fz = new Complex(-Math.sin(x*y), Math.cos(x*y));
				
				double mod = fz.getModulus();
				double arg = Math.toRadians(fz.getArgument());
				
				Color q = getColor(mod, arg);
				
				dc.setColor(q);
				dc.drawLine(i, j, i, j);		
			}	
		}
		//this.setupConsole(dc);
		
	}
	
	private Color getColor(double mod, double arg) {
		float hue = (float)(arg / (2 * Math.PI));
		float saturation = (float)(1 - 2 / Math.PI * Math.atan(mod)); //saturation
		float brightness = 1.0f;//(float)(2 / Math.PI * Math.atan(mod)); //brightness
		if(constantBrightness ) {
			saturation = 1.0f;
		}

		Color myRGBColor = Color.getHSBColor(hue, saturation, brightness);
		return myRGBColor;
	}
	
	private int limitColorValues(double col) {
		return (int)Math.min(255, Math.max(0, (int)col));
	}
	
	private void initTimer() {
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public Timer getTimer() {
		return this.timer;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	private void drawComplexNum(Graphics2D dc, Complex c) {
		int radius = 4;
		
		int xOrigin = xOffset + (X_BOUND_MIN + X_BOUND_LENGTH) / 2;
		int yOrigin = -yOffset + (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2;
		int xEndPoint = (int)(c.re() * PITCH + xOffset + (X_BOUND_MIN + X_BOUND_LENGTH) / 2);
		int yEndPoint = (int)(-c.im() * PITCH - yOffset + (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2);
		
		dc.setColor(Color.BLACK);
		dc.drawLine(xOrigin, yOrigin, xEndPoint, yEndPoint);
		
		dc.setColor(Color.RED);
		dc.fillOval(xEndPoint - radius / 2, yEndPoint - radius / 2, radius, radius);
		
		dc.setColor(Color.BLACK);
	}
	
	private void setupConsole(Graphics2D dc) {
		int xTickCount = X_BOUND_LENGTH / PITCH;
		int yTickCount = Y_BOUND_LENGTH / PITCH;
		
		//drawSecondaryLines
		dc.setColor(Color.BLACK);
		for(int i = 1-xTickCount / 2; i <= xTickCount / 2; i++) {
			int xTickLocation = PITCH * i + xOrigin;
			dc.drawLine(xTickLocation, Y_BOUND_MIN, xTickLocation, Y_BOUND_LENGTH+Y_BOUND_MIN);
		}
		for(int i = 1-yTickCount / 2; i <= yTickCount / 2; i++) {
			int yTickLocation = PITCH * i + yOrigin;
			dc.drawLine(X_BOUND_MIN, yTickLocation,X_BOUND_LENGTH + X_BOUND_MIN, yTickLocation);
		}
		dc.setColor(Color.BLACK);
				
		//draw ticks
		for(int i = 1-xTickCount / 2; i <= xTickCount / 2; i++) {
			int xTickLocation = PITCH * i + xOrigin;
			dc.drawLine(xTickLocation, yOrigin, xTickLocation, yOrigin - TICK_SIZE);
		}
		for(int i = 1-yTickCount / 2; i <= yTickCount / 2; i++) {
			int yTickLocation = PITCH * i + yOrigin;
			dc.drawLine(xOrigin, yTickLocation, xOrigin + TICK_SIZE, yTickLocation);
		}

		//draw axes
		dc.setColor(Color.BLACK);
		dc.drawRect(xOrigin-1, Y_BOUND_MIN, 2,Y_BOUND_LENGTH);
		dc.drawRect(X_BOUND_MIN, yOrigin-1, X_BOUND_LENGTH, 2);
		
		//draw bounding box
		dc.drawRect(X_BOUND_MIN, Y_BOUND_MIN, X_BOUND_LENGTH, Y_BOUND_LENGTH);

		
	}
	
	private Complex getMappedNumber(int xPixel, int yPixel) {
		int xOrigin = xOffset + (X_BOUND_MIN + X_BOUND_LENGTH) / 2;
		int yOrigin = -yOffset + (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2;
		
		double real = (double)(xPixel - xOrigin) / PITCH;
		double imaginary = (double)(yPixel - yOrigin) / PITCH;
		return new Complex(real, imaginary);
	}
	
	private int checkColorVal(int val) {
		return Math.max(0,Math.min(255, val));
	}
	
}
