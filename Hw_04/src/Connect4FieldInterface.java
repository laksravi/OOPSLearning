public interface Connect4FieldInterface {
	/**
	 * Checks if a piece can be dropped in a column
	 * @param column -in which the piece should be dropped
	 * @return true if there is a place in the column and false if it's full
	 */
	public boolean checkIfPiecedCanBeDroppedIn(int column);
	/**
	 * Drop pieces in the particular column
	 * @param column -
	 * @param gamePiece
	 */
	public void dropPieces(int column, char gamePiece);
	
	/**  
	 * @return if the last move won or not
	 */
	boolean didLastMoveWin();
	/**
	 * @return if the game over without any winner
	 */
	public boolean isItaDraw();
	
	/**
	 * Initialise the players in the game
	 * @param playerA : one of the game player
	 * @param playerB : one of the game player
	 */
	public void init( PlayerInterface playerA, PlayerInterface playerB );
	public String toString();
	/**
	 * Two players plays the game against each other  
	 */
	public void playTheGame();
}