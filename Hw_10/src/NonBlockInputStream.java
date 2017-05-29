import java.io.IOException;
import java.io.InputStream;

/**
 * Non block input stream that reads input and stores them in a buffer Input is
 * read 1k everytime Buffer can hold data upto 10k
 * 
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 */
public class NonBlockInputStream extends Thread {

	// Creates a new input stream with a default buffer size.
	InputStream thisStream;
	byte[] buffer;
	String[] input;
	Integer wordCount = 0;
	boolean inputReadEOC = false;
	boolean isBufferFull = false;
	boolean isReadInitiated = false;
	boolean isNewInput = false;

	// Constructor to create input stream
	public NonBlockInputStream(InputStream out) {
		thisStream = out;
	}

	public String[] accessInput() {
		while (input == null)
			;
		return input;
	}
	
	
	public void open(){
		 inputReadEOC = false;
		 isBufferFull = false;
		 isReadInitiated = false;
		 isNewInput = false;
	}

	/**
	 * reads the input in the stream Stores them in Input buffer
	 * 
	 * @return handle to input buffer
	 */
	public String[] read() {
		input = new String[10];
		if (!isReadInitiated) {
			this.start();
		} else {
			synchronized (this) {
				isNewInput = true;
				this.notify();
			}
		}
		return input;
	}

	public void run() {
		// Initiate the input string buffer
		inputReadEOC = false;
		isBufferFull = false;
		isReadInitiated = true;
		while (true) {
			// Fill-in the string buffer
			isNewInput = false;
			isBufferFull = false;
			for (int i = 0; i < 10; i++) {
				buffer = new byte[1024];
				try {
					// Check if EOF occured
					if (thisStream.read(buffer) != -1) {
						String inp = new String(buffer);
						 System.out.println("\n Reading....\n" + inp + "\n");
						synchronized (input) {
							input[i] = inp;
							input.notifyAll();
						}
					}// If EOF then set Input is all done
					else {
						synchronized (this) {
							inputReadEOC = true;
							inputReadEOC = true;
							this.notifyAll();
							return;
						}
					}

				} catch (IOException e) {
					System.out.println("Error while reading the input");
				}
			}
			// After filling up the string input buffer : set buffer full
			synchronized (this) {
				isBufferFull = true;
				this.notifyAll();
			}
			
			// Check if input is all read. If yes, wait for new Input
			synchronized (this) {
				while (!isNewInput) {
					try {
						System.out.println("Waiting for next Input Stream");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Releases the buffer and closes the stream
	 */
	public void close() {
		try {
			buffer = null;
			input = null;
			thisStream.close();
		} catch (IOException e) {
			System.out.println("Error while closing the Input Stream");
		}
	}
}