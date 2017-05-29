import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream which gets the input compresses the input and writes them to
 * the file
 * 
 * @author Lakshmi Ravi
 * 
 */
public class NonBlockOutputStream extends Thread {

	OutputStream myOutput;
	String[] aString;
	byte[] buffer;
	Integer wordCount = 0;
	boolean inputStreamNull ;
	boolean doneWritingData ;
	boolean isThreadInitated ;
	boolean testNewInput ;

	// Creates a new output stream with a default buffer size.
	public NonBlockOutputStream(OutputStream out) {
		myOutput = out;
	}

	/**
	 * Opens the Output stream reader
	 */
	public void open(){
		 inputStreamNull = false;
		 doneWritingData = false;
		 isThreadInitated = false;
		 testNewInput = false;	
		 
	}
	
	/**
	 * Writes the String buffer to the Output stream specified Writes data in
	 * blocks if 1K
	 * 
	 * @param input
	 *            : buffer which contains data
	 */
	
	public void write(String[] input) {
		this.aString = input;
		doneWritingData = false;
		if (aString == null) {
			System.err.println("Error in Input!!");
			return;
		}
		if (!isThreadInitated) {
			this.start();
		} else {
			synchronized (this) {
				System.out.println("New Input");
				testNewInput = true;
				this.notifyAll();
			}
		}
	}

	public void run() {
		isThreadInitated = true;

		while (true) {
			testNewInput = false;
			//Get all the input from the buffers and process them
			for (int i = 0; i < 10; i++) {
				
				//On reading a null string check if Input is over 
				while (aString[i] == null) {
					synchronized (this) {
						//If input stream is null : then exit
						if (inputStreamNull) {
							System.out.println("Done with writing!!");
							doneWritingData = true;
							this.notifyAll();
							 return;
						}
					}
					//Wait for input data 
					synchronized (aString) {
						try {
							System.out.println("Waiting for Input data");
							aString.wait();
						} catch (InterruptedException e) {
							System.err
									.println("Writing Output interrupted while waiting for Buffer");
						}
					}
				}
				String inp;
				// Get the input from the buffer
				synchronized (aString) {
					inp = aString[i];
				}
				// Process the String for 20 milli seconds
				try {
					sleep(20);
				} catch (InterruptedException e1) {
					System.err.println("Interupted while processing data");
				}

				// Convert them into bytes and write it to the Output Stream
				buffer = inp.getBytes();
				System.out.println("\nWriting Data..............\n" + inp
						+ "\n");
				try {
					myOutput.write(buffer);
				} catch (IOException e) {
					System.err.println("Error while writing buffer data");
				}
			}
			//Notify after data in the buffer is written to specified output
			synchronized (this) {
				doneWritingData = true;
				this.notifyAll();
			}
			

			synchronized (this) {
				while (!testNewInput) {
					try {
						System.out
								.println("Waiting for new Buffer data to write");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

	// Writes remaining data to the output stream and closes the underlying
	// stream.
	public void close() {
		buffer = null;
		inputStreamNull = true;
		 doneWritingData = true;
		 isThreadInitated = true;
		 testNewInput = true;	
		try {
			myOutput.close();
		} catch (IOException e) {
			System.err.println("Error while closing the Stream");
		}
	}
}