
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Test class to test NIO Input and Output
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 *
 */

public class NIOTest {
	static String inputFileName = "Test.txt";
	static String outputFileName = "TestOutput.txt";

	public static void main(String args[]) {
		try {
			NonBlockInputStream input = new NonBlockInputStream(
					new FileInputStream(inputFileName));
			String data[];
			NonBlockOutputStream output = new NonBlockOutputStream(
					new FileOutputStream(outputFileName));
			input.open(); output.open();

			// Read till input is full and
			do {
				data = input.read();
				output.write(data);
				
				/*
				 * If Input is over : notify to Output streams
				 */
				boolean inputEofOccured=false;
				synchronized (input) {
					while(!(input.inputReadEOC || input.isBufferFull)){
						try {
							input.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					inputEofOccured = input.inputReadEOC;
				}

				if (inputEofOccured) {
					synchronized (output) {
						output.inputStreamNull = true;
						output.notifyAll();
						break;
					}
				}
				
				
				// Wait till data clears it's buffer
				synchronized (output) {
					if (!output.doneWritingData) {
						try {
							System.out.println("Waiting for Output data to be done!!");
							output.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				
			}while (true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}