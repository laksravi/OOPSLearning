import java.io.IOException;
import java.util.Scanner;


public class TestConnect4Game_server {

	private static void printMessage()	{
		System.out.println("-h		---->	help");
		System.out.println("[-host 		hostName");
		System.out.println(" -port 		port");
		System.out.println(" {-port 		port}");
		System.out.println("or ");
		System.out.println(" no argument");
		System.exit(0);
	   }


	public static void main(String[] args) throws ClassNotFoundException, IOException{
		
		char chosenSymbol='*';
		for (int i = 0; i < args.length; i ++) {
			   	if (args[i].equals("-h")) 
					printMessage();
			}
		
	GameControl_server gameControl_server = new GameControl_server(args);
	Connect4UserView_server gameView = new Connect4UserView_server();
	gameControl_server.addObserver(gameView);
	Connect4GameModel_server gameBoard = new Connect4GameModel_server();
	System.out.println("You have 4 players against each other");
	gameControl_server.addObserver(gameBoard);
	PlayerModel_server[] humPlayer = new PlayerModel_server[4];
		for (int i=0;i<4;i++){
			humPlayer[i] = new HumanPlayer_server((char)(i+97),"Human_Player"+i, gameBoard,gameControl_server);
		}
		gameControl_server.init(gameBoard,humPlayer);
		//gameControl_server.playTheGame();
		
	
}
}
