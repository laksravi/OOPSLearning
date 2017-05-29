

import java.io.IOException;
import java.io.InputStream;

public class StringZipInputStream	{

	// Creates a new input stream with a default buffer size.
	InputStream thisStream;
	BinarySearchTree<Integer, String> myInverseDictionary;
	byte[] buffer;
	Integer wordCount=0;
	
	private boolean isDigit(String word){
		try{
			Integer.parseInt(word);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	public StringZipInputStream(InputStream out)	{
		myInverseDictionary = new BinarySearchTree<Integer, String>();
		thisStream=out;
	}
	
	
	public String unCompress(String input){
		String unCompressed="";
		String[] inputElements = input.split(" +");
		
		for(Integer index=0;index<inputElements.length;index++){
			if (isDigit(inputElements[index])){
				Integer digit=Integer.parseInt(inputElements[index]);
				String word = myInverseDictionary.getReference(digit);
				unCompressed+= word+" ";
			}
			else{
			unCompressed+=inputElements[index]+" ";
			myInverseDictionary.addElement(wordCount++, inputElements[index]);
			}
		}
		return unCompressed;
	}
	
	
	
	// Reads data into a string. the method will block until some input can be read; otherwise, no bytes are read and null is returned.
	public String read() {
		buffer= new byte[1024];
		try {
			if( thisStream.read(buffer) != -1){
				String input = new String(buffer);
				return unCompress(input);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	// Closes this input stream and releases any system resources associated with the stream.
	public void close() {
		try {
			buffer = null;
			thisStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} 