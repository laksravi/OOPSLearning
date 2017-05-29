

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;



public class GameControl_server extends Observable{

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
	private Socket connection;
	private static String hostName; 
	private static int	port = 1800;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
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
		connection=aServerSocket.accept();
		System.out.println("\nConnection received from "+connection.getInetAddress().getHostName());
	}
	
	public void createStreams() throws IOException, ClassNotFoundException{
		output=new ObjectOutputStream(connection.getOutputStream());
		input=new ObjectInputStream(connection.getInputStream());
		
	}
	
	public void sendData(String message) throws IOException{
		output.writeObject(message);
		output.flush();
	}
	
	public void sendInt(int message) throws IOException{
		output.writeObject(message);
		output.flush();
	}
	
	
	public String getData() throws ClassNotFoundException, IOException{
		return (String)input.readObject();
	}
	
	public int getInt() throws ClassNotFoundException, IOException{
		return (int)input.readObject();
	}
	
	public void closeConnections() throws IOException{
		output.close();
		input.close();
		connection.close();
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
			
				// Set the Player's move and gamePiece
				
			if((index)%2==0){
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
			
			if((index+1)%2==1){
				String currentStatus=currentUserMove+" "+currentPlayerGamePiece;
				sendData(currentStatus);
				currentStatus=getData();
				String[] tokens=currentStatus.split("\\s+");
				if(tokens[0].equals("Game")){
					System.out.println(currentStatus);
					//System.out.println(getData());
					break;
				}
				 currentUserMove=Integer.parseInt(tokens[0]);
				 currentPlayerGamePiece=tokens[1].charAt(0);
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
		finally
		{
			System.out.println("Thanku for playing,GoodBye !!!");
			closeConnections();
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

	public void init(Connect4GameModel_server gameBoard, PlayerModel_server p1) throws IOException, ClassNotFoundException {
		connect4Board = gameBoard;
		myPlayers = new PlayerModel_server[2];
		myPlayers[0] = p1;
		int ready=1;
		sendInt(ready);
		int ready2=getInt();
		if(ready2==1){
			new Thread(myPlayers[0]).start();
			System.out.println("Server Player started");
		}
		else{
			System.out.println("Client not created, try again");
		}
		//new Thread(myPlayers[1]).start();
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
