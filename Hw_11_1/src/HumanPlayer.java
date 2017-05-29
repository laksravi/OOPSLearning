

public class HumanPlayer extends PlayerModel{
	public HumanPlayer(char gameChar, String name, Connect4GameModel board,GameControl gameControl) {
		super(gameChar, name, board, gameControl);
	}
	private boolean needUserNextMove;
	private int userMove;
	
	public int makeNextMove(char[][] boardMatrix) {
		return userMove;
	}
	
	public void setUserMove(int i){
		userMove = i;
	}
	
	public boolean needUserMove(){
		return needUserNextMove;
	}
	public int makeNextMove() {
		return userMove;
	}



}
