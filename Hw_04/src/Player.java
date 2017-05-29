import java.util.Scanner;

/**
 * Class to implement the Player's Functions
 * 
 * @author Lakshmi Ravi
 * @author Aarti Gorder
 * 
 */
public class Player implements PlayerInterface {
	private static final int FAIL_MOVE = -1;
	protected Connect4Field myConnect4Field;
	private String name;
	private char gamePiece;

	public Player(Connect4Field aConnect4Field, String string, char c) {
		myConnect4Field = aConnect4Field;
		name = string;
		gamePiece = c;
	}

	public Player() {

	}

	public char getGamePiece() {
		return gamePiece;
	}

	public String getName() {
		return name;
	}

	/**
	 * Get the input from the human through Scanner
	 */
	public int nextMove() {
		Scanner myScanner = null;
		System.out.println("Human's Turn...");
		System.out.println("Enter a Value");
		// Get the value from the user and passes it to the game
		myScanner = new Scanner(System.in);
		if (myScanner.hasNextLine()) {
			try {
				int move = Integer.parseInt(myScanner.nextLine());
				// myScanner.close();
				return move;
			} catch (NumberFormatException ne) {
				System.err.println("Entered Value should be a number!! ");
			}
		} else {
			myScanner.close();
			System.out.println("NO value entered");
		}
		return FAIL_MOVE;
	}
}
