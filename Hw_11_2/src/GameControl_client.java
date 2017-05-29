

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;



public class GameControl_client extends Observable{

	private int currentUserMove;
	private boolean isGameTie;
	private boolean isGameWin;
	private Connect4GameModel_client connect4Board;
	private PlayerModel_client myPlayers[];
	private PlayerModel_client currentPlayer;
	private char currentPlayerGamePiece;
	private char opponetGamePiece;
	private String gameMessage;
	private static int index=1;
	private ServerSocket aServerSocket;
	private Socket client;
	String hostName = "localhost";
	private static int	port = 1800;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public GameControl_client(String args[]){
		if ( args.length != 0 )
			parseArgs(args);
		try { 
            connectToServer();
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
	   }
	
	public void parseArgs(String args[]) {

		for (int i = 0; i < args.length; i ++) {
			System.out.println("args[i]="+args[i]);
		   	if (args[i].equals("-h")) 
				printMessage();
		   	else if (args[i].equals("-host")) {
		   		hostName = args[++i];}
		   	else if (args[i].equals("-port")) {
				port = new Integer(args[++i]).intValue();
		   	}
		}
	   }
	
	private String getData() throws ClassNotFoundException, IOException {
		return (String)input.readObject();
	}
	
	public void sendData(String message) throws IOException{
		output.writeObject(message);
		output.flush();
	}
	
	private void connectToServer() throws IOException {
		System.out.println("I m trying to connect to server");
		client=new Socket(hostName,port);
		System.out.println("Connected to "+client.getInetAddress().getHostName());
	}
	
	
	public void createStreams() throws IOException, ClassNotFoundException{
		output=new ObjectOutputStream(client.getOutputStream());
		input=new ObjectInputStream(client.getInputStream());
		System.out.println("\n\nConnection successful");
		
	}
	
	public void closeConnections() throws IOException{
		output.close();
		input.close();
		client.close();
	}

	/**
	 * Plays the actual game Player-1 and 2 makes their moves
	 * If the player is Human Player, notifies View for input move.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	
		
	public void playTheGame() throws ClassNotFoundException, IOException {
		try{
		do {
			
			 if((index%2)==1){
				 String data=getData();
				 String[] tokens=data.split("\\s+");
				 if(tokens[0].equals("Game")){
					 System.out.println(data);
					 break;
				 }
				 currentUserMove=Integer.parseInt(tokens[0]);
				 currentPlayerGamePiece=tokens[1].charAt(0);
			 }
			 else{
				// Set the Player's move and gamePiece
				
				gameMessage = null;
				index=index%2;
				currentPlayerGamePiece = myPlayers[index].getGamePiece();
				currentPlayer = myPlayers[index];
				setChanged();
				notifyObservers(myPlayers[index]);
				
				// Set the Player's move and gamePiece
				currentUserMove = myPlayers[index].makeNextMove();
			 }
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
					sendData(gameMessage);
					break;
				}
				if (gameMessage != null) {
					setChanged();
					notifyObservers(gameMessage);
				}
			
			if((index)%2==0){
				String currentStatus=currentUserMove+" "+currentPlayerGamePiece;
				sendData(currentStatus);
				
			}
	
			index++;
		} while (!(isGameTie || isGameWin));
	}catch (Exception e){
	 try{
			if (isGameTie || isGameWin) {
				gameMessage = "Game Won by Player "
						+ myPlayers[index].getName();
				sendData(gameMessage);
				
			}
		}catch(Exception e1){
			
		}
	 
	}
		finally{
			System.out.println("Thank you for playing,GoodBye!!!");
			closeConnections();
		}
	}

	

	public PlayerModel_client getCurrentPlayer() {
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

	public void init(Connect4GameModel_client gameBoard, PlayerModel_client p2) throws ClassNotFoundException, IOException {
		connect4Board = gameBoard;
		myPlayers = new PlayerModel_client[2];
		myPlayers[0] = p2;
		int ready=(int)input.readObject();
		if(ready==1){	
			output.writeObject(ready);
		new Thread(myPlayers[0]).start();
		
		}
		
	}

	public Connect4GameModel_client getGameBoard() {
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
