import java.util.Scanner;


public class TestConnect4Game {

	public static void main(String[] args){
	
		System.out.println("Please select\n 1: human vs Computer game\n 2. Human vs Human");
		int input=1;
	Scanner myScanner = new Scanner(System.in);
	if(myScanner.hasNextLine()){
		input= myScanner.nextInt();
	}
	
	GameControl gameControl = new GameControl();
	Connect4UserView gameView = new Connect4UserView();
	gameControl.addObserver(gameView);
	
	Connect4GameModel gameBoard = new Connect4GameModel();
	
	PlayerModel compPlayer = new ComputerPlayer('x', "Comp_Player",gameBoard,gameControl);
	PlayerModel humPlayer2 = new HumanPlayer('#', "Aarti", gameBoard,gameControl);
	PlayerModel humPlayer = new HumanPlayer('+',"Lakshmi", gameBoard,gameControl);

	
	if(input == 1){
	gameControl.addObserver(compPlayer);
	gameControl.addObserver(gameBoard);
	gameControl.init(gameBoard,compPlayer, humPlayer);
	
	}
	else{
		gameControl.addObserver(gameBoard);
		gameControl.init(gameBoard,humPlayer2, humPlayer);
		
	}
	//gameControl.playTheGame();
	
	
}
}
