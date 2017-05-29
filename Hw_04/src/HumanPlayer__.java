import java.util.Scanner;

public class HumanPlayer__ extends Player {
	private static final int FAIL_MOVE = -1;

	public HumanPlayer__(Connect4Field aConnect4Field, String string, char c) {
		super(aConnect4Field, string, c);
	}

	/**
	 * Gets the user specified Move
	 */
	@Override
	public int nextMove() {
		Scanner myScanner = null;
		System.out.println("Enter a Value");
		// Get the value from the user and passes it to the game
		myScanner = new Scanner(System.in);
		if (myScanner.hasNext()) {
			try {
				int move = Integer.parseInt(myScanner.nextLine());
				//myScanner.close();
				return move;
			} catch (NumberFormatException ne) {
				System.err
						.println("Entered Value should be a number!! Try again");
			}
		}else{
			System.out.println("NO value entered");
		}
		//myScanner.close();
		return FAIL_MOVE;
	}
}
