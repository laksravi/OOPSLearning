

import java.io.IOException;
import java.io.OutputStream;

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
		for(Integer index=0; index<inputElements.length;index++){
			Integer reference= myDictionary.getReference(inputElements[index]);
			if (reference != null){
				compressedString+= reference.toString()+" ";
			}
			else{
				myDictionary.addElement(inputElements[index], wordCount++);
				compressedString+= inputElements[index]+" ";
			}
			}
		return compressedString;
	}
	
	/**
	 * Writes the compressed version of the string
	 * @param aString
	 */
	public void write(String aString) {

		byte[] myByte= compressInput(aString).getBytes();
		try {
			myOutput.write(myByte);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	// Writes remaining data to the output stream and closes the underlying stream.
	public void close() {
	}
} 