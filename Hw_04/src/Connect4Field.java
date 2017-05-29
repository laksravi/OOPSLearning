import java.util.Arrays;
import java.util.Scanner;

public class Connect4Field implements Connect4FieldInterface {

	private char[][] boardMatrix;
	private static final int MAX_COLUMN_NUMBER = 25;
	private static final int MAX_ROW_NUMBER = 9;

	private int lastMoveRow;
	private int lastMoveColumn;
	private char lastMoveChar;
	private PlayerInterface[] myPlayers;

	Connect4Field() {
		boardMatrix = new char[MAX_ROW_NUMBER][];
		for (int rows = 0; rows < MAX_ROW_NUMBER; rows++) {
			boardMatrix[rows] = new char[25];
			Arrays.fill(boardMatrix[rows], '1');
			for (int columns = rows; columns < MAX_COLUMN_NUMBER - rows; columns++) {
				boardMatrix[rows][columns] = '0';
			}
		}
	}

	/**
	 *  Copies all the values instead of pointing at the reference.
	 * @param aConnect4Field : the object which has to be cloned
	 */
	Connect4Field(Connect4Field aConnect4Field) {
		this.boardMatrix = new char[MAX_ROW_NUMBER][];
		for (int row = 0; row < MAX_ROW_NUMBER; row++) {
			boardMatrix[row] = new char[MAX_COLUMN_NUMBER];
			for (int column = 0; column < MAX_COLUMN_NUMBER; column++) {
				this.boardMatrix[row][column] = aConnect4Field.getBoardMatrix()[row][column];
			}
		}
	}

	/**
	 * @return max rows in the board
	 */
	public static int getMaxColumns() {
		return MAX_COLUMN_NUMBER;
	}

	/**
	 * @return maximum rows in the board
	 */
	public static int getMaxRows() {
		return MAX_ROW_NUMBER;
	}

	/**
	 * @return the board Matrix
	 */
	public char[][] getBoardMatrix() {
		return boardMatrix;
	}

	/**
	 * Returns the opponent's information
	 * @return humanPlayer Object
	 */
	public PlayerInterface getHumanPlayer() {
		if ((myPlayers == null) || (myPlayers.length < 2)) {
			System.err.println("No players Initialized before playing");
			System.exit(1);
		}
		return myPlayers[0];
	}

	
	public boolean checkIfPiecedCanBeDroppedIn(int column) {
		if ((column < 0) || (column > MAX_COLUMN_NUMBER)) {
			return false;
		} else {
			for (int rows = MAX_ROW_NUMBER - 1; rows >= 0; rows--) {
				if (boardMatrix[rows][column] == '0') {
					return true;
				}
			}
		}
		return false;
	}

	public void dropPieces(int column, char gamePiece) {
		lastMoveChar = gamePiece;
		lastMoveRow = -1;
		lastMoveColumn = column;
		for (int rows = MAX_ROW_NUMBER - 1; rows >= 0; rows--) {
			if (boardMatrix[rows][column] == '0') {
				boardMatrix[rows][column] = gamePiece;
				lastMoveRow = rows;
				break;
			}
		}
	}

	/**
	 * Gets the bottom-most free row
	 * 
	 * @param column
	 *            in which the free row has to be obtained
	 * @return bottom-most row number
	 */
	public int getHighestRowNumber(int column) {
		for (int rows = MAX_ROW_NUMBER - 1; rows >= 0; rows--) {
			if (boardMatrix[rows][column] == '0') {
				return rows;
			}
		}
		return -1;
	}

	public boolean didLastMoveWin() {
		if (lastMoveRow == -1) {
			return false;
		}
		// Check row
		int continuousCharacterCount = 0;
		for (int index = 0; index < 4; index++) {
			if (((lastMoveRow + index) < 0)
					|| ((lastMoveRow + index) >= MAX_ROW_NUMBER)) {
				continue;
			}
			if (boardMatrix[lastMoveRow + index][lastMoveColumn] == lastMoveChar) {
				continuousCharacterCount++;
			} else if (continuousCharacterCount >= 4) {
				return true;
			} else {
				continuousCharacterCount = 0;
			}
		}
		if (continuousCharacterCount >= 4) {
			//System.out.println("Yeah the Player Won!!");
			return true;
		}
		// Check for previous 3 and next 4 columns
		continuousCharacterCount = 0;
		for (int index = -3; index < 4; index++) {
			if ((lastMoveColumn + index < 0)
					|| (lastMoveColumn + index >= MAX_COLUMN_NUMBER)) {
				continue;
			}
			if (boardMatrix[lastMoveRow][lastMoveColumn + index] == lastMoveChar) {
				continuousCharacterCount++;
			} else if (continuousCharacterCount >= 4) {
				return true;
			} else {
				continuousCharacterCount = 0;
			}
		}
		if (continuousCharacterCount >= 4) {
			// System.out.println("The Player Won!!");
			return true;
		}

		// check diagnol - previous 3 and next 3 diagnol elements
		continuousCharacterCount = 0;
		for (int index = -3; index < 4; index++) {
			if ((lastMoveColumn + index < 0)
					|| (lastMoveColumn + index >= MAX_COLUMN_NUMBER)
					|| (lastMoveRow + index < 0)
					|| (lastMoveRow + index >= MAX_ROW_NUMBER)) {
				continue;
			}
			if (boardMatrix[lastMoveRow + index][lastMoveColumn + index] == lastMoveChar) {
				continuousCharacterCount++;
			} else if (continuousCharacterCount >= 4) {
				return true;
			} else {
				continuousCharacterCount = 0;
			}
		}
		if (continuousCharacterCount >= 4) {
			return true;
		}
		// System.out.println("No, the Player didn't win..");
		return false;
	}

	public boolean isItaDraw() {
		for (int rows = 0; rows < MAX_ROW_NUMBER; rows++) {
			for (int columns = 0; columns < MAX_COLUMN_NUMBER; columns++) {
				if (boardMatrix[rows][columns] == '0') {
					return false;
				}
			}
		}
		return true;
	}

	
	//Initialise 2 players
	public void init(PlayerInterface playerA, PlayerInterface playerB) {
		myPlayers = new PlayerInterface[2];
		try {
			myPlayers[0] = new Player(this, playerA.getName(),
					playerA.getGamePiece());
			myPlayers[1] = new ComputerPlayer(this, playerB.getName(),
					playerB.getGamePiece());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Players not initialised properly");
			System.exit(1);
		}
	}
	
	/**
	 * Play mode where there is no computer player
	 */
	public void makeAllAsHumans(){
		myPlayers[1]= new Player(this,myPlayers[1].getName(),myPlayers[1].getGamePiece());
	}
	
	/**
	 * Plays the actual game
	 */
	public void playTheGame() {
		System.out.println("Playing the Game" + myPlayers[0].getName()
				+ "  vs " + myPlayers[1].getName());
		boolean isgameOver = false;
		do {
			for (int index = 0; index < 2; index++) {
				System.out.println("Turn of Player "
						+ myPlayers[index].getName());
				int playerMove = myPlayers[index].nextMove();
				if (checkIfPiecedCanBeDroppedIn(playerMove)) {
					System.out.println("Dropping in Column  " + playerMove);
					dropPieces(playerMove, myPlayers[index].getGamePiece());
					System.out.println(this);
					if (isItaDraw()) {
						System.out.println("Game Over!! It is a draw..");
						isgameOver = true;
						break;
					}
					if (didLastMoveWin()) {
						System.out.println("Player  "
								+ myPlayers[index].getName()
								+ "  won the Game!!!");
						isgameOver = true;
						break;
					}
				} else {
					System.out.println("Can't be Dropped in Column "
							+ playerMove);
				}
			}
		} while (!isgameOver);

	}

	public String toString() {
		String myBoardString = "";
		for (int rows = 0; rows < MAX_ROW_NUMBER; rows++) {
			for (int columns = 0; columns < MAX_COLUMN_NUMBER; columns++) {
				if (boardMatrix[rows][columns] == '1') {
					myBoardString += " ";
				} else {
					myBoardString += boardMatrix[rows][columns];
				}
			}
			myBoardString += "\n";
		}
		return myBoardString;

	}

}
