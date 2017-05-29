import Model.*;
import Control.*;
import View.*;

public class TestConnect4Game {

	public static void main(String[] args){
	
	Connect4GameModel gameBoard = new Connect4GameModel();
	PlayerModel compPlayer = new ComputerPlayer('x', "Comp_Player",gameBoard);
	PlayerModel humPlayer = new HumanPlayer('+',"Lakshmi", gameBoard);
	Connect4UserView gameView = new Connect4UserView();
	GameControl gameControl = new GameControl();
	gameControl.addObserver(gameView);
	gameControl.addObserver(compPlayer);
	gameControl.addObserver(gameBoard);
	gameControl.init(gameBoard,compPlayer, humPlayer);
	gameControl.playTheGame();
}
}
