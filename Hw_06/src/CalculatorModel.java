import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Observer;
import java.util.Stack;
import java.util.Observable;

public class CalculatorModel implements Observer {
	private static String OPERATOR_PRECEDENCE = "+-%*/^";
	private String OperatorNotfound="Error: Operator Not Found!";
	private String inValidExpression ="Error: Invalid Expression";
	private Double expressionValue;
	private String errorType;
	private Stack<Double> numericStack;
	private Stack<String> operatorStack;

	public CalculatorModel(){
		numericStack = new Stack<Double>();
		operatorStack = new Stack<String>();
		expressionValue = 0.0;
		errorType=null;
	}
	

	/**
	 * @param operator
	 *            whose precedence has to be found
	 * @return the precedence order of the operator
	 */
	public int getPrecedence(String operator) {
		// If unknown operator is present -call out and exit the program
		if (!OPERATOR_PRECEDENCE.contains(operator)) {
			errorType=OperatorNotfound;
			return -1;
		}
		return OPERATOR_PRECEDENCE.indexOf(operator);
	}
	
	/**
	 * Get if the expression evaluation had any error
	 * @return the error
	 */
	public String getError(){
		return errorType;
	}

	/**
	 * Process the given expression and stores them in a stack
	 * 
	 * @param args
	 *            - expression user input Sets the value of expression
	 * @exception - if the arguments neither matched the Operator and nor a
	 *            number
	 */
	public void processExpression(String[] args) {
		
		if ((args == null) || (args.length < 1)) {
			errorType = inValidExpression;
		}

		
		// Push the contents to Operator stack and Numeric stack
		for (int index = 0; index < args.length; index++) {
			String value = args[index];
			// check if the argument is operator using regex
			try {
				// Check if its a paranthesis
				if (value.matches("[(]")) {
					int endParanthesisIndex = index;
					int startParanthesisIndex = index;

					// Search for ")" index
					int openParanthesisCount = 0;
					for (int current = index; current < args.length; current++) {
						if (args[current].matches("[)]")
								&& (--openParanthesisCount == 0)) {
							endParanthesisIndex = current;
							break;
						} else if (args[current].matches("[(]")) {
							openParanthesisCount++;
						}
					}
					if(openParanthesisCount !=0){
						errorType = inValidExpression;
					}

					/*
					 * Create a new calculator for evaluating the
					 * sub-expressions
					 */
					CalculatorModel subcalcModel = new CalculatorModel();
					subcalcModel.processExpression(Arrays.copyOfRange(args,
							startParanthesisIndex + 1, endParanthesisIndex));
					index = endParanthesisIndex;
					if (subcalcModel.errorType!=null) {
						errorType = subcalcModel.errorType;	
					} 
					else {
						numericStack.push(subcalcModel.getExpressionValue());
					}
				}

				else if (value.matches("[+-//*/^%]")) {
					// Check if current precedence > existing Operator
					if (operatorStack.isEmpty()
							|| (getPrecedence(value) > getPrecedence(operatorStack
									.peek()))) {
						operatorStack.push(value);
					}
					// If precedence is lower than top element
					else {
						// Pop out the top values from stacks
						double rightOperand = numericStack.pop();
						double leftOperand = numericStack.pop();
						String operation = operatorStack.pop();
						double result = calculate(rightOperand, leftOperand,
								operation);
						// Push result
						numericStack.push(result);
						// Remain in the same index if Operand is not pushed.
						index--;
					}

				} else {
					numericStack.push(Double.parseDouble(value));
				}
			} catch (NumberFormatException ne) {
				errorType=inValidExpression;
				return;
				//System.exit(1);
			}

		}

		expressionValue = popupStackAndCalculate();
	}

	/**
	 * @return the expression value
	 */
	public Double getExpressionValue() {
		return expressionValue;
	}

	/**
	 * Does the mathematical operation for the given numbers
	 * 
	 * @param rightOperand
	 *            - Right side operand
	 * @param leftOperand
	 *            - Left side Operand
	 * @param operator
	 *            - Operation to be performed
	 * @return result of operation
	 */
	private double calculate(double rightOperand, double leftOperand,
			String operator) {
		switch (operator) {
		case "+":
			return leftOperand + rightOperand;
		case "-":
			return leftOperand - rightOperand;
		case "*":
			return leftOperand * rightOperand;
		case "/":
			return leftOperand / rightOperand;
		case "%":
			return leftOperand % rightOperand;
		case "^":
			return (int) Math.pow(leftOperand, rightOperand);
		default:
			System.out.println("NO OPERATOR MATCH" + operator);
			System.exit(1);
		}
		return 0;
	}

	/**
	 * Pops the Stack content and calculates the value of expression
	 * 
	 * @return the value of the expression
	 */
	private double popupStackAndCalculate() {
		if (numericStack.isEmpty() && operatorStack.isEmpty())
			return 0;
		if (numericStack.size() == 1 && operatorStack.isEmpty())
			return numericStack.pop();
		try {
			double num1 = numericStack.pop();
			double num2 = numericStack.pop();
			String operator = operatorStack.pop();
			numericStack.push(calculate(num1, num2, operator));
		} catch (EmptyStackException e) {
			errorType=inValidExpression;
			return -1;
			//System.err.println("Input Expression is misformed");

		}
		return popupStackAndCalculate();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof String[]){
			errorType=null;
			numericStack = new Stack<>();
			operatorStack = new Stack<>();
			processExpression((String[])arg1);
			((CalculatorControl)arg0).setResult(expressionValue, errorType);
		}
	}

}
