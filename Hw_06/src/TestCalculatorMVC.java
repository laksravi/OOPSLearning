
public class TestCalculatorMVC {

	public static void main(String[] args){
		CalculatorView calcView = new CalculatorView();
		CalculatorControl calcControlRunner = new CalculatorControl();
		CalculatorModel calcModel = new CalculatorModel();
		
		
		calcControlRunner.addObserver(calcView);
		calcControlRunner.addObserver(calcModel);
		calcControlRunner.init(calcModel, calcView);
		calcControlRunner.startCalculating();
		
		}
}
