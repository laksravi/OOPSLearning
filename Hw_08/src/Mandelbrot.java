// original from: http://rosettacode.org/wiki/Mandelbrot_set#Java
// modified for color

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JFrame;

/**
 * Pixel threads that calculates the iter value for each pixel
 * set Color of the pixel
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 *
 */
class PixelThread extends Thread {
	Stack<Integer> xIndex;
	Stack<Integer> yIndex;
	static int LENGTH;
	static double ZOOM;
	static int MAX;
	Mandelbrot mandelBrot;
	boolean inputOver;
	static Integer threadCount=0;
	public PixelThread() {
		xIndex = new Stack<Integer>();
		yIndex = new Stack<Integer>();
		inputOver = false;
		threadCount++;
	}
	public PixelThread(int x, int y, Mandelbrot m){
		xIndex = new Stack<Integer>();
		yIndex = new Stack<Integer>();
		inputOver = false;
		xIndex.add(x); yIndex.add(y);mandelBrot=m;
		threadCount++;
	}
	
	/**
	 * Set the Input is done
	 */
	public synchronized void setInputOver(){
		inputOver=true;
		notifyAll();
	}

	/**
	 * @param length of the given image
	 * @param zoom : zoom factor in the image
	 * @param max : max pixels
	 */
	public static void setCommonProperties(int length, double zoom, int max){
		LENGTH=length; ZOOM=zoom;MAX=max;
	}
	
	/**
	 * Add another pixel to this thread
	 * @param x : x index of pixel
	 * @param y :y index of pixel
	 */
	public synchronized void addIndex(int x, int y) {
		xIndex.add(x);
		yIndex.add(y);
		notifyAll();
	}

	public synchronized void run() {
		while (true) {
			/*
			 * If no pixel is set : wait for the pixel to be set
			 */
			if (xIndex.isEmpty()) {
				//If input is set to be over, reduce the thread count and return
				if (inputOver) {
					synchronized (mandelBrot) {
						threadCount--;
					}
					return;
				}
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			/*
			 * Iterate through and find the iter value (Value when series ends)
			 */
			else if (!xIndex.isEmpty()) {
				int x = xIndex.pop();
				int y = yIndex.pop();
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
				mandelBrot.setImageRGB(x, y, iter);
			}

		}
	}
}

/**
 * Symbolizes the Madel-brot image
 *
 */
public class Mandelbrot extends JFrame {

	private final int MAX = 5000;
	private final int LENGTH = 800;
	private final double ZOOM = 1000;
	private BufferedImage theImage;
	private int[] colors = new int[MAX];

	public Mandelbrot() {
		super("Mandelbrot Set");

		initColors();
		setBounds(100, 100, LENGTH, LENGTH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void setImageRGB(int x, int y, int iter) {
		if (iter > 0)
			theImage.setRGB(x, y, colors[iter]);
		else
			theImage.setRGB(x, y, iter | (iter << 8));
	}

	public void createSet() {
		theImage = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		int processorCount = Runtime.getRuntime().availableProcessors();
		long start=System.currentTimeMillis();
		//Create '4' (processor count ) pixel threads and initate them
		PixelThread pixelThreads[] = new PixelThread[processorCount];
		PixelThread.setCommonProperties(LENGTH, ZOOM, MAX);
		for(int index=0; index < processorCount; index++){
			pixelThreads[index] = new PixelThread(0,0,this);
			pixelThreads[index].start();
		}
		
		//for a given pair, (x,y) add it to one of the four pixel threads running
		double zx, zy, cX, cY;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				int xyVal = x * LENGTH + y;
				int threadIndex = xyVal % processorCount;
				pixelThreads[threadIndex].addIndex(x,y);
			}
		} 
		for(int index=0;index<processorCount;index++){
		pixelThreads[index].setInputOver();
		}
		System.out.println("Waiting for other threads");
		while(PixelThread.threadCount != 0){
		}
		repaint();
		long end = System.currentTimeMillis();
		System.out.println(end-start+"ms is the total time taken!!");
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
		Mandelbrot aMandelbrot = new Mandelbrot();
		aMandelbrot.setVisible(true);
		aMandelbrot.createSet();
	}
}