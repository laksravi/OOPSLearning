

import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream which gets the input 
 * compresses the input and writes them to the file
 * @author laksh
 *
 */
public class StringZipOutputStream	{

	OutputStream myOutput;
	BinarySearchTree<String, Integer> myDictionary;
	byte[] buffer;
	Integer wordCount=0;
	
	// Creates a new output stream with a default buffer size.
	public StringZipOutputStream(OutputStream out)	{
		myDictionary = new BinarySearchTree<String, Integer>();
		myOutput=out;
	}
	
	/**
	 * compress the given input string using AZW compression technique
	 * @param inputString : which has to be compressed
	 * @return compressed input string
	 */
	public String compressInput(String inputString){
		inputString+="\n";
		String inputElements[] = inputString.split("\\W+");
		String compressedString = "";
		for(String inputElement : inputElements){
			//System.out.println("Reading Now!!"+inputElement);
			Integer reference= myDictionary.getReference(inputElement);
			if (reference != null){
				compressedString+= reference.toString()+" ";
			}
			else{
				myDictionary.addElement(inputElement, wordCount++);
				compressedString+= inputElement+" ";
			}
			}
		return compressedString;
	}
	
	/**
	 * Writes the compressed version of the string
	 * @param aString : to be compresses
	 */
	public void write(String aString) {

		buffer= compressInput(aString).getBytes();
		try {
			myOutput.write(buffer);
		} catch (IOException e) {
			System.out.println("Error while writing buffer data");;
		}
		
		
	}
	// Writes remaining data to the output stream and closes the underlying stream.
	public void close() {
		buffer = null;
		try {
			myOutput.close();
		} catch (IOException e) {
			System.out.println("Error while closing the Stream");
		}
	}
} 