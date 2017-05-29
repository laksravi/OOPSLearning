

import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream which compresses the Input data and sends the compresses string
 * It checks for repeated element and replaces them with their first occuring position
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 */
public class StringZipInputStream	{

	// Creates a new input stream with a default buffer size.
	InputStream thisStream;
	BinarySearchTree<Integer, String> myInverseDictionary;
	byte[] buffer;
	Integer wordCount=0;
	
	/**
	 * Checks if the input is a digits
	 * @param word
	 * @return digit or not 
	 */
	private boolean isDigit(String word){
		try{
			Integer.parseInt(word);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	//Constructor to create input stream
	public StringZipInputStream(InputStream out)	{
		myInverseDictionary = new BinarySearchTree<Integer, String>();
		thisStream=out;
	}
	
	/**
	 * Uncompress the string input by extracting the value from the index mentioned
	 * @param input : input that has to be uncompressed
	 * @return the uncompressed input
	 */
	public String unCompress(String input){
		String unCompressed="";
		String[] inputElements = input.split(" +");
		
		for(String inputString : inputElements){
			//System.out.println("Writing Now!!"+inputString);
			if (isDigit(inputString)){
				Integer digit=Integer.parseInt(inputString);
				String word = myInverseDictionary.getReference(digit);
				unCompressed+= word+"\n";
			}
			else{
			unCompressed+=inputString+"\n";
			myInverseDictionary.addElement(wordCount++, inputString);
			}
		}
		return unCompressed;
	}
	
	
	/**
	 * reads the input in the stream
	 * compresses the input 
	 * @return compressed string of the input
	 */
	public String read() {
		buffer= new byte[1024];
		try {
			if( thisStream.read(buffer) != -1){
				String input = new String(buffer);
				//System.out.println(unCompress(input));
				return unCompress(input);
			}
			
		} catch (IOException e) {
			System.out.println("Error while reading the input");
		}
		return null;
	}
		
	/**
	 * Releases the buffer and closes the stream
	 */
	public void close() {
		try {
			buffer = null;
			thisStream.close();
		} catch (IOException e) {
			System.out.println("Error while closing the Input Stream");
		}
	}
} 