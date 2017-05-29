

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;



public class GameControl_server extends Observable{
	private static int PLAYERS_IN_GAME=4;
	private int currentUserMove;
	private boolean isGameTie;
	private boolean isGameWin;
	private Connect4GameModel_server connect4Board;
	private PlayerModel_server myPlayers[];
	private PlayerModel_server currentPlayer;
	private char currentPlayerGamePiece;
	private char opponetGamePiece;
	private String gameMessage;
	private static int index=0;
	private ServerSocket aServerSocket;
	private Socket connection[] = new Socket[4];
	private static String hostName; 
	private static int	port = 1800;
	private ObjectOutputStream[] output = new ObjectOutputStream[4];
	private ObjectInputStream[] input= new ObjectInputStream[4];
	
	public GameControl_server(String args[]){

		if ( args.length != 0 ){
			parseArgs(args);
			}
			
		try { 
            aServerSocket = new ServerSocket(port);
            System.out.println ("Listening on port: " + aServerSocket.getLocalPort());
            waitForConnection();
            createStreams();
            
        } catch(Exception e) {
            System.out.println(e);
        }
		
	}
	
	private void printMessage()	{
		System.out.println("-h		---->	help");
		System.out.println("[-host 		hostName");
		System.out.println(" -port 		port");
		System.out.println(" {-port 		port}");
		System.out.println("or ");
		System.out.println(" no argument");
		System.exit(0);
	   }
	
	public void parseArgs(String args[]) {
		for (int i = 0; i < args.length; i ++) {
		   	if (args[i].equals("-h")) {
				printMessage();
		   	}
		   	else if (args[i].equals("-host")) {
		   		hostName = args[++i];
		   		}
		   	else if (args[i].equals("-port")) {
		   		port = new Integer(args[++i]).intValue();
		   	}
		}
	   }
	
	public void waitForConnection() throws Exception{
		for(int i=0; i<PLAYERS_IN_GAME;i++){
		connection[i]=aServerSocket.accept();
		System.out.println("\nConnection received from "+connection[i].getInetAddress().getHostName());
		}
		System.out.println("All the players joined the game!!");
	}
	
	public void createStreams() throws IOException, ClassNotFoundException{
		for(int i=0;i<PLAYERS_IN_GAME;i++){
		output[i]=new ObjectOutputStream(connection[i].getOutputStream());
		input[i]=new ObjectInputStream(connection[i].getInputStream());
		}
		
	}
	
	public void sendData(int i,String message) throws IOException{
		output[i].writeObject(message);
		output[i].flush();
	}
	
	public void sendInt(int i,int message) throws IOException{
		output[i].writeObject(message);
		output[i].flush();
	}
	
	
	public String getData(int i) throws ClassNotFoundException, IOException{
		System.out.println(input[i].readObject());
		return (String)input[i].readObject();
	}
	
	public int getInt(int i) throws ClassNotFoundException, IOException{
		return (int)input[i].readObject();
	}
	
	public void closeConnections() throws IOException{
		for(int i=0;i<PLAYERS_IN_GAME;i++){
		output[i].close();
		input[i].close();
		connection[i].close();
		}
	}

	/**
	 * Plays the actual game Player-1 and 2 makes their moves
	 * If the player is Human Player, notifies View for input move.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public void playTheGame() throws ClassNotFoundException, IOException {
		System.out.println("Playing the game");
		try{
			do {
				System.out.println("Playing this");
				String currentStatus=currentUserMove+" "+currentPlayerGamePiece;
				for(int i=0;i<4;i++){
				sendData(i,"");
				}
				currentStatus=getData(index%4);
				String[] tokens=currentStatus.split("\\s+");
				if(tokens[0].equals("Game")){
					System.out.println("TOKEN Game");
					System.out.println(currentStatus);
					//System.out.println(getData());
					break;
				}
				 currentUserMove=Integer.parseInt(tokens[0]);
				 currentPlayerGamePiece=tokens[1].charAt(0);
			 index++;
			
		} while (!(isGameTie || isGameWin));
		}catch (Exception e){
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Thanku for playing,GoodBye !!!");
			closeConnections();
		}
	}

	
	public void playTheGame4(){
		for(int i=0;i< PLAYERS_IN_GAME;i++){
			
		}
	}
	public PlayerModel_server getCurrentPlayer() {
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

	public void init(Connect4GameModel_server gameBoard, PlayerModel_server[] p1) throws IOException, ClassNotFoundException {
		connect4Board = gameBoard;
		myPlayers = new PlayerModel_server[PLAYERS_IN_GAME];
		myPlayers = p1;
		int ready=1;
		for(int i=0;i<PLAYERS_IN_GAME;i++){
		sendInt(i,ready);
		}
		for(int i=0;i<PLAYERS_IN_GAME;i++){
			int ready2=(i);
			}
		int ready2=getInt(1);
		/*//for(int i=0;i<PLAYERS_IN_GAME;i++){
			//new Thread(myPlayers[i]).start();
			System.out.println("Server Player started");
		}*/
		
		this.playTheGame();
	}

	public Connect4GameModel_server getGameBoard() {
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
