import java.io.IOException;
import java.util.Scanner;


public class TestConnect4Game_client {

	private static void printMessage()	{
		System.out.println("-h		---->	help");
		System.out.println("[-host 		hostName");
		System.out.println(" -port 		port");
		System.out.println(" {-port 		port}");
		System.out.println("or ");
		System.out.println(" no argument");
		System.exit(0);
	   }
	
	
	
	public static void main(String[] args){
	
	char chosenSymbol='+';
	for (int i = 0; i < args.length; i ++) {
		   	if (args[i].equals("-h")) 
				printMessage();
		   	else if (args[i].equals("-symbol")) {
		   		chosenSymbol = args[++i].charAt(0);}
		   
		}
	
	GameControl_client gameControl_client = new GameControl_client(args);
	Connect4UserView_client gameView = new Connect4UserView_client();
	gameControl_client.addObserver(gameView);
	
	Connect4GameModel_client gameBoard = new Connect4GameModel_client();
	
	PlayerModel_client humPlayer = new HumanPlayer_client(chosenSymbol,"Human_Player2", gameBoard,gameControl_client);
	gameControl_client.addObserver(gameBoard);
	try {
		gameControl_client.init(gameBoard,humPlayer);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}
}
