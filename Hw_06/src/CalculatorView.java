import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CalculatorView implements Observer{
	private String[] expression;
	private String userInput;
	public CalculatorView() {
		
	}

	public String[] getInputExpressions(){
		return expression;
	}
	/**
	 * Prints the result of the expression to the console
	 */
	public void printOutput(String expression) {
		System.out.println("The result of the Expression  "+userInput+" is  " + expression);
	}
	
	public void printErrorMessage(String error){
		System.err.println(error);
	}
	
	public void getUserInput(Observable arg0){
		System.out.println("Enter your expresion .....  ");
		Scanner myScanner = new Scanner(System.in);
		if(myScanner.hasNextLine()){
			userInput = myScanner.nextLine();
			if(userInput.equals("EXIT")){
				System.out.println("Done with Calculating!! Bye :)");
				System.exit(0);
			}
			expression = this.userInput.split(" ");
			((CalculatorControl)arg0).setExpression(expression);
			if (expression.length < 0) {
				System.err.println("No Input");
			}
		}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Boolean){
			getUserInput(arg0);
		}
		else if(arg1 instanceof Double){
			printOutput(arg1.toString());
		}
		else if (arg1 instanceof String){
			printErrorMessage(arg1.toString());
		}
	}

}
