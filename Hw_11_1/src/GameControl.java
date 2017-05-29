

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;



public class GameControl extends Observable{

	private int currentUserMove;
	private boolean isGameTie;
	private boolean isGameWin;
	private Connect4GameModel connect4Board;
	private PlayerModel myPlayers[];
	private PlayerModel currentPlayer;
	private char currentPlayerGamePiece;
	private char opponetGamePiece;
	private String gameMessage;
	private static int index=0;


	/**
	 * Plays the actual game Player-1 and 2 makes their moves
	 * If the player is Human Player, notifies View for input move.
	 * 
	 */
	
		
	public void playTheGame() {
		do {
			synchronized(connect4Board){		
				// Set the Player's move and gamePiece
				//System.out.println("I am in starting with Game ="+Thread.currentThread().getName());
				gameMessage = null;
				index=index%2;
				currentPlayerGamePiece = myPlayers[index].getGamePiece();
				currentPlayer = myPlayers[index];
				setChanged();
				notifyObservers(myPlayers[index]);
				
				// Set the Player's move and gamePiece
				currentUserMove = myPlayers[index].makeNextMove();
				setChanged();
				notifyObservers(index);
				
				setChanged();
				notifyObservers(this);
				if (isGameTie || isGameWin) {
					gameMessage = "Game Won by Player "
							+ myPlayers[index].getName();
					setChanged();
					notifyObservers(this);
					setChanged();
					notifyObservers(gameMessage);
					break;
				}
				if (gameMessage != null) {
					setChanged();
					notifyObservers(gameMessage);
				}
			index++;
			//System.out.println("I am in exiting with Game ="+Thread.currentThread().getName());
			connect4Board.notify();
			try {
				connect4Board.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		} while (!(isGameTie || isGameWin));

	}

	public PlayerModel getCurrentPlayer() {
		return currentPlayer;
	}

	public char getCurrentGamePiece() {
		return currentPlayerGamePiece;
	}

	public char getOpponentGamePiece() {
		return opponetGamePiece;
	}

	public int getCurrentgameMove() {
		return currentUserMove;
	}

	public void init(Connect4GameModel gameBoard, PlayerModel p1, PlayerModel p2) {
		connect4Board = gameBoard;
		myPlayers = new PlayerModel[2];
		myPlayers[0] = p1;
		myPlayers[1] = p2;
		new Thread(myPlayers[0]).start();
		new Thread(myPlayers[1]).start();
	}

	public Connect4GameModel getGameBoard() {
		return connect4Board;
	}

	public void setGameTie() {
		isGameTie = true;
	}

	public void setGameError(String error) {
		gameMessage = error;
	}

	public void setGameWin() {
		isGameWin = true;
	}
	
}
