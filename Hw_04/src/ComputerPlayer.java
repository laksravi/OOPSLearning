
public class ComputerPlayer extends Player {
	private int myLastMove;

	public ComputerPlayer(Connect4Field aConnect4Field, String string, char c) {
		super(aConnect4Field, string, c);
	}

	/**
	 * returns the move with highest possiblity of winning
	 */
	@Override
	public int nextMove() {
		Connect4Field tempConnect4Field = new Connect4Field(myConnect4Field);
		System.out.println("Computer's Turn...");
		// WIN AND DEFENSE MOVE
		for (int columns = 0; columns < Connect4Field.getMaxColumns(); columns++) {
			// Primary Cases - Win Move and Defend Opponent Win Move
			if (tempConnect4Field.checkIfPiecedCanBeDroppedIn(columns)) {
				tempConnect4Field.dropPieces(columns, getGamePiece());
				// If winning Move - drop in that Column
				if (tempConnect4Field.didLastMoveWin()
						|| tempConnect4Field.isItaDraw()) {
					myLastMove = columns;
					return columns;
				}
				// If we don't drop in that opponent would win
				tempConnect4Field = new Connect4Field(myConnect4Field);
				char opponentPlayerGamePiece = myConnect4Field.getHumanPlayer()
						.getGamePiece();
				tempConnect4Field.dropPieces(columns, opponentPlayerGamePiece);
				if (tempConnect4Field.didLastMoveWin()
						|| tempConnect4Field.isItaDraw()) {
					myLastMove = columns;
					return columns;
				}
			}
		}
		// RELATED TO PREVIOUS MOVE
		tempConnect4Field = new Connect4Field(myConnect4Field);
		if (tempConnect4Field.checkIfPiecedCanBeDroppedIn(myLastMove)) {
			int currentRow = tempConnect4Field.getHighestRowNumber(myLastMove);
			// Check how many rows below it have Game Pieces.
			while ((currentRow < Connect4Field.getMaxRows())
					&& (tempConnect4Field.getBoardMatrix()[currentRow + 1][myLastMove] == getGamePiece())) {
				currentRow = currentRow + 1;
			}
			if(currentRow -4 >=0 ){
				return myLastMove;
			}
			
		}

		tempConnect4Field = new Connect4Field(myConnect4Field);
		char[][] myBoardMatrix = tempConnect4Field.getBoardMatrix();
		// Returns if columns have more game pieces
		for (int columns = 0; columns < Connect4Field.getMaxColumns(); columns++) {
			// Check possible rows
			int gamePiecesConsecutiveCount = 0;
			int possibleRows = tempConnect4Field.getHighestRowNumber(columns);
			for (int rows = Connect4Field.getMaxRows() - 1; rows < possibleRows; rows--) {
				if (myBoardMatrix[rows][columns] == this.getGamePiece()) {
					gamePiecesConsecutiveCount++;
				} else {
					gamePiecesConsecutiveCount = 0;
				}
			}
			if (gamePiecesConsecutiveCount >= 2) {
				myLastMove = columns;
				return columns;
			}
		}
		// Check if dropping in the same column is productive

		// Check if there is any increase in Consecutive characters in any row.
		for (int columns = 0; columns < Connect4Field.getMaxColumns(); columns++) {
			for (int rows = Connect4Field.getMaxRows() - 1; rows >= 0; rows--) {
				tempConnect4Field = new Connect4Field(myConnect4Field);
				String rowStringbefore = new String(
						tempConnect4Field.getBoardMatrix()[rows]);
				if (tempConnect4Field.checkIfPiecedCanBeDroppedIn(columns)) {
					tempConnect4Field.dropPieces(columns, getGamePiece());
				}
				String rowStringafter = new String(
						tempConnect4Field.getBoardMatrix()[rows]);
				if (getConsecutiveChars(rowStringafter, getGamePiece()) > getConsecutiveChars(
						rowStringbefore, getGamePiece())) {
					myLastMove = columns;
					return columns;
				}
			}
		}
		for (int column = 0; column < Connect4Field.getMaxColumns(); column++) {
			if (myConnect4Field.checkIfPiecedCanBeDroppedIn(column)) {
				myLastMove = column;
				return column;
			}
		}
		myLastMove = -1;
		return -1;
	}

	/**
	 * Counts the maximum consec. occurance of gamepiece in the string
	 * 
	 * @param myString
	 *            in which max. character count has to be taken
	 * @param gamePiece : which character to look for
	 * @return the max consecutive occurance
	 */
	private int getConsecutiveChars(String myString, char gamePiece) {
		int firstInstance = myString.indexOf("" + gamePiece);
		if (firstInstance < 0)
			return firstInstance;
		int maxInstance = 1;
		int currentInstance = 1;
		for (int index = firstInstance; index < myString.length(); index++) {
			if (myString.charAt(index) == gamePiece) {
				currentInstance++;
			} else {
				if (currentInstance > maxInstance) {
					maxInstance = currentInstance;
				}
				currentInstance = 0;
			}
		}
		return maxInstance;

	}
}
