import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class LakshmTrialTest {
	final  int MAX = 100000;
    String inputFileName 	= "words.txt";
    String outputFile = "F3.txt";

	
	public void readAndWrite() throws IOException{
		StringZipInputStream aStringZipInputStream = new StringZipInputStream( new FileInputStream(inputFileName));
		StringZipOutputStream output = new StringZipOutputStream(new FileOutputStream(outputFile));
		String aWord=null;
		while (  ( aWord = aStringZipInputStream.read() )  != null ) {
			//System.out.println("write:	" + aWord);
			output.write(aWord);
		}

}
	
	public static void main(String[] args){
		try {
			new LakshmTrialTest().readAndWrite();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
