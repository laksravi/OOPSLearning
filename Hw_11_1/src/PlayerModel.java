
import java.util.Observable;
import java.util.Observer;

public abstract class PlayerModel implements Observer,Runnable{
	private char gameChar;
	private String playerName;
	protected Connect4GameModel connect4Board;
	private GameControl gameControl;
	
	
	public abstract int makeNextMove();
	
	public PlayerModel(char gameChar, String name, Connect4GameModel board,GameControl gameControl){
		this.gameChar=gameChar;
		playerName= name;
		connect4Board=board;
		this.gameControl=gameControl;
	}
	public char getGamePiece(){
		return gameChar;
	}
	public String getName(){
		return playerName;
	}
	public void update(Observable arg0, Object arg1) {
		if( arg1 instanceof Connect4GameModel){
			connect4Board = (Connect4GameModel)arg1;
		}
	}
	
	public void run(){
		gameControl.playTheGame();
	}
	
}
