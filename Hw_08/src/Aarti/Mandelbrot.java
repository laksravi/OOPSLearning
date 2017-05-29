// original from: http://rosettacode.org/wiki/Mandelbrot_set#Java
// modified for color

//**********************

import java.awt.Color;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

class pixelThread implements Runnable {
	int x;
	int y;
	Mandelbrot m;

	public pixelThread(Mandelbrot m, int x, int y) {
		this.x = x;
		this.y = y;
		this.m = m;
	}

	public void run() {
		m.calculatePixel(m, x, y);
	}
}

public class Mandelbrot extends JFrame {

	private final int MAX = 5000;
	private final int LENGTH = 800;
	private final double ZOOM = 1000;
	private BufferedImage theImage;
	private int[] colors = new int[MAX];
	private static int count=0;

	public Mandelbrot() {
		super("Mandelbrot Set");

		initColors();
		setBounds(100, 100, LENGTH, LENGTH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	void calculatePixel(Mandelbrot m, int x, int y) {
		synchronized (m) {
			double zx, zy, cX, cY;
			zx = zy = 0;
			cX = (x - LENGTH) / ZOOM;
			cY = (y - LENGTH) / ZOOM;
			int iter = 0;
			double tmp;

			while ((zx * zx + zy * zy < 10) && (iter < MAX - 1)) { 
				tmp = zx * zx - zy * zy + cX; 		
				zy = 2.0 * zx * zy + cY; 
				zx = tmp; 
				iter++; 
			} 
			if (iter > 0) 
				theImage.setRGB(x, y, colors[iter]); 								
			else
				theImage.setRGB(x, y, iter | (iter << 8)); 												
		}
	}

	public void createSet() {
		theImage = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if(Thread.activeCount()<=Runtime.getRuntime().availableProcessors()){
				Thread t = new Thread(new pixelThread(this, x, y));
				t.start();
				}
				else{
				try {
					do{
					Thread.sleep(1,1);
					}while(Thread.activeCount()>=Runtime.getRuntime().availableProcessors());
					Thread t = new Thread(new pixelThread(this, x, y));
					t.start();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
					
				}
			}
		}

		while (Thread.activeCount() > 2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
				e.printStackTrace();
			}
		}
		
		repaint();
	}

	public void initColors() {
		for (int index = 0; index < MAX; index++) {
			colors[index] = Color.HSBtoRGB(index / 256f, 1, index
					/ (index + 8f));
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(theImage, 0, 0, this);
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Mandelbrot aMandelbrot = new Mandelbrot();
		aMandelbrot.setVisible(true);
		aMandelbrot.createSet();
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\nTotalTime taken: "+(totalTime/1000)+"seconds");	
		
	}
}