// original from: http://rosettacode.org/wiki/Mandelbrot_set#Java
// modified for color

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MandelbrotOld extends JFrame {

	private final int MAX = 5000;
	private final int LENGTH = 800;
	private final double ZOOM = 1000;
	private BufferedImage theImage;
	private int[] colors = new int[MAX];

	public MandelbrotOld() {
		super("Mandelbrot Set : HP Version");
		initColors();
		setBounds(100, 100, LENGTH, LENGTH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void createSet() {
		long start = System.currentTimeMillis();
		theImage = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		double zx, zy, cX, cY;

		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				zx = zy = 0;
				cX = (x - LENGTH) / ZOOM;
				cY = (y - LENGTH) / ZOOM;
				int iter = 0;
				double tmp;
				long starttime = System.currentTimeMillis();
				while ((zx * zx + zy * zy < 10) && (iter < MAX - 1)) {
					tmp = zx * zx - zy * zy + cX;
					zy = 2.0 * zx * zy + cY;
					zx = tmp;
					iter++;
				}
				
				if (iter > 0)
					theImage.setRGB(x, y, colors[iter]);
				else
					// this is the part for the parallel part
					theImage.setRGB(x, y, iter | (iter << 8)); // this is the
																// part for the
				long endTime= System.currentTimeMillis();
				System.out.println(endTime-starttime);												// parallel part
			}
		}
		repaint();
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-start);
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
		MandelbrotOld aMandelbrot = new MandelbrotOld();
		aMandelbrot.setVisible(true);
		aMandelbrot.createSet();
	}
}