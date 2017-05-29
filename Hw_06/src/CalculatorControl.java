import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Control Class which has the program flow
 * 
 * @author laksh
 * 
 */
public class CalculatorControl extends Observable {
	private String[] expression;
	private Double result;
	private String error;
	private CalculatorView myCalculatorView;
	private CalculatorModel myCalculatorModel;
	private Boolean setInput = false;
	private boolean stopCalculation = false;

	/**
	 * calculation boolean variable
	 */
	public void stopCalculating() {
		stopCalculation = true;
	}

	public CalculatorControl() {
		expression = null;
		myCalculatorModel = null;
		myCalculatorView = null;
	}

	/**
	 * Sets the Model and view Object
	 * 
	 * @param calcModel
	 *            model object of the calculator
	 * @param calcView
	 *            view object of calculator
	 */
	public void init(CalculatorModel calcModel, CalculatorView calcView) {
		myCalculatorView = calcView;
		myCalculatorModel = calcModel;
	}

	public void setExpression(String[] expression) {
		this.expression = expression;
	}

	/**
	 * Sets the result and error (if expression is not valid)
	 * 
	 * @param result
	 *            value of given expression
	 * @param error
	 *            , error type in the expression
	 */
	public void setResult(Double result, String error) {
		this.error = error;
		this.result = result;
	}

	/**
	 * Sets state chage after initaiting. Passes boolean valued notifier to View
	 * and gets Input expression from view.
	 * 
	 */
	public void startCalculating() {
		do {
			// Signal that calculator is initialized and Input needs to be
			// fetched
			setChanged();
			notifyObservers(setInput);
			
			//After getting Input evaluate the expression.
			setChanged();
			notifyObservers(expression);

			if (this.error == null) {
				setChanged();
				notifyObservers(result);
			} else {
				setChanged();
				notifyObservers(error);
			}
		} while (!stopCalculation);
	}

}
