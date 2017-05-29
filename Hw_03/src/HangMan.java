import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Class which is used to maintain the state of every Player in the game
 * 
 * @author Lakshmi Ravi
 * @author Aarthi Gorde
 * 
 */
class Player implements Comparable<Player> {
	private String playerName;
	private int points;
	private int attempts;
	private HangState hangstate;

	Player() {
		playerName = "";
		points = 0;
		attempts = 0;
	}

	Player(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * Gets the points of the player
	 * @return points-player
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @return wrong attempts of the player
	 */
	public int getAttempts() {
		return attempts;
	}
	/**
	 * Increase points for the corresponding action
	 */
	public void madeCorrectguess() {
		points += 10;
	}
	/**
	 *
	 * Increase points for the corresponding action
	 */
	public void madeWrongGuess() {
		points -= 5;
	}

	/**
	 * Increase attempts that went wrongly guessed
	 */
	public void increaseAttempts() {
		attempts++;
	}
	/**
	 * @return the player's name
	 */
	public String getPlayerName() {
		return playerName;
	}

	public void gaveToughWordGuess() {
		points += 5;
	}

	public int compareTo(Player anotherPlayer) {
		return this.points != anotherPlayer.points ? anotherPlayer.points
				- this.points : this.attempts - anotherPlayer.attempts;
	}

}

/**
 * Hang state of a particular game
 * 
 * @author Lakshmi Ravi
 * 
 */
class HangState {
	private int state;
	private static int[][] HANG_MATRIX = { { 0, 1, 1, 1, 1, 1, 2, 2, 2, 2 },
			{ 0, 1, 0, 0, 0, 0, 0, 2, 0, 0 }, { 0, 1, 0, 0, 0, 0, 0, 2, 0, 0 },
			{ 0, 1, 0, 0, 0, 0, 0, 3, 0, 0 }, { 0, 1, 0, 0, 0, 0, 3, 0, 3, 0 },
			{ 0, 1, 0, 0, 0, 0, 0, 3, 0, 0 }, { 0, 1, 0, 0, 0, 0, 0, 4, 0, 0 },
			{ 0, 1, 0, 0, 0, 0, 5, 4, 6, 0 }, { 0, 1, 0, 0, 0, 5, 0, 4, 0, 6 },
			{ 0, 1, 0, 0, 0, 0, 0, 4, 0, 0 }, { 0, 1, 0, 0, 0, 0, 7, 4, 8, 0 },
			{ 0, 1, 0, 0, 0, 7, 0, 0, 0, 8 }, { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	HangState() {
		state = 0;

	}

	public void increaseState() {
		state++;
		printState();
	}

	private void printState() {
		for (int row = 0; row < 13; row++) {
			for (int col = 0; col < 10; col++) {
				if ((HANG_MATRIX[row][col] > 0)
						&& (HANG_MATRIX[row][col] <= state)) {
					System.out.print("#");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}

/**
 * In the word guess game
 * 
 * @author Lakshmi Ravi
 * @author Aarthi Gorde
 * 
 */
class Word {
	private String word;
	private char[] guessState;

	Word(String word) {
		this.word = word.toLowerCase();
		createGuessWord();
	}

	private void createGuessWord() {
		guessState = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			guessState[i] = '-';
		}
	}

	/**
	 * Checks if the Guess Char exists in the String replaces the guessChar with
	 * "-" if it exists
	 * 
	 * @param guessChar
	 *            - character guessed to be present
	 * @return exists or not
	 */
	public boolean doesCharExist(String guessChar) {
		if (guessChar.length() > 1) {
			System.out
					.println("A single Character Should be entered.\n Marked as Wrong attempt");
			return false;
		}
		int charIndex = word.indexOf(guessChar);
		if ((charIndex >= 0) && (guessState[charIndex] == guessChar.charAt(0))) {
			System.out
					.println("Already Found character, counted as wrong attempt");
			return false;
		}
		if (word.contains(guessChar)) {
			changeGuessState(guessChar.charAt(0));
			return true;
		}
		return false;
	}

	public void printGuessState() {
		for (int index = 0; index < guessState.length; index++) {
			System.out.print(guessState[index]);
		}
		System.out.println("");
	}

	/**
	 * Changes the guess State with Guess characters
	 * 
	 * @param guessChar
	 */
	private void changeGuessState(char guessChar) {
		int index = word.indexOf("" + guessChar);
		while (index >= 0) {
			guessState[index] = guessChar;
			index = word.indexOf("" + guessChar, index + 1);
		}
		System.out.println(guessState);
	}
	/**
	 * @return actual string selected from the word file
	 */
	public String getActualWord() {
		return word;
	}

	public boolean doneMatching() {
		if ((guessState == null) && ((word == null) || (word.length() == 0))) {

		}
		for (int index = 0; index < word.length(); index++) {
			if (guessState[index] != word.charAt(index))
				return false;
		}
		return true;
	}
}

/**
 * A class which is used to get Inputs for the program
 * Reading a file. 
 * Selecting a word from the file.
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 *
 */
class Input {
	private int randNumber;
	private int totalLines;
	private String myWord;
	private String wordsFileName;
	private File myWordsFile;

	Input() {
		wordsFileName = "Words.txt";
		myWordsFile = new File(wordsFileName);
	}

	/**
	 * Get a random number 
	 * within the lines in the words file
	 */
	private void setRandNumber() {
		try {
			FileReader myfile = new FileReader(myWordsFile);
			LineNumberReader lr = new LineNumberReader(myfile);
			lr.skip(Long.MAX_VALUE);
			totalLines = lr.getLineNumber() + 1;
		} catch (FileNotFoundException e) {
			System.err.println("The Words File is not found in the program.."
					+ wordsFileName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error while reading the words file");
		}
		Random rand = new Random();
		randNumber = rand.nextInt(totalLines);
		randNumber = randNumber % totalLines;

	}

	/**
	 * Get the word at the position of random number
	 * @return randomly chosen word from the list
	 */
	public String getRandWord() {
		// generates a random number and get a random word in the list
		try {
			// Reads the Word.txt from Input Stream
			FileInputStream inputStream = new FileInputStream(myWordsFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			setRandNumber();
			// Skip the lines till the random number
			for (int linenumber = 0; linenumber < randNumber; linenumber++) {
				reader.readLine();
			}
			// returns the string in the required line
			myWord = reader.readLine();
			return myWord;
		} catch (FileNotFoundException e) {
			System.err.println("Problem in getting the File in InputStream");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error reading the file through BufferedReader");
			System.exit(1);
		}
		return null;
	}

}

/**
 * Game Hang Man -which involves several players Player has to guess a randomly
 * selected word
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 * 
 */
public class HangMan {
	private Player players[];
	private HangState hangState;
	private Input gameInput;
	private Word word;
	private int playersCount;

	HangMan() {
		gameInput = new Input();
	}

	/**
	 * Get the Input from the word list
	 * 
	 */
	public void playGame(String[] playerNames) {

		/*
		 * Get the List of player Names Assign a Player object for each object
		 */
		players = new Player[playerNames.length];
		System.out.println("The Players are :");
		for (String myplayer : playerNames) {
			System.out.print(myplayer + "  ");
			players[playersCount++] = new Player(myplayer);
		}
		System.out.println("");
		/*
		 * We have 2 players Guesser and word-giver
		 */
		Scanner myScanner = new Scanner("Started");
		while (myScanner.hasNextLine()
				&& (!myScanner.nextLine().equals("EXIT"))) {
			for (int index = 0; index < players.length; index++) {

				// 2 players - Initialised
				// Attempts set to 0
				boolean guessMade = false;
				int attempts = 0;
				Player guesser = players[index];
				Player wordGiver = index < (players.length - 1) ? players[index + 1]
						: players[0];
				System.out.println("Guesser is " + guesser.getPlayerName()
						+ "\t Word Giver is " + wordGiver.getPlayerName());

				// Get a random word from the Input files
				String randomWord = gameInput.getRandWord();
				word = new Word(randomWord);
				hangState = new HangState();
				word.printGuessState();
				while ((!guessMade) && (attempts < 8)) {
					// New Hang-state for New Game
					// Get the inputs from Scanner and start comparing with the
					// word object
					// If scanner o/p == randomword or attempts > 8

					System.out.print("Guess a character...");
					guesser.increaseAttempts();
					myScanner = new Scanner(System.in);
					if(!myScanner.hasNextLine()){
						System.out.println("HEYYYYY");
					}
					String guessCharacter = myScanner.nextLine();
					guessCharacter = guessCharacter.toLowerCase();
					// word.printGuessState();
					if (!word.doesCharExist(guessCharacter)) {
						hangState.increaseState();
						attempts++;
					} else if (word.doneMatching()) {
						System.out
								.println("Word Matches. Congratulations!!!  "
										+ guesser.getPlayerName()
										+ "  won the round!!");
						guessMade = true;
						guesser.madeCorrectguess();
					}
				}
				if (attempts >= 8) {
					wordGiver.gaveToughWordGuess();
					guesser.madeWrongGuess();
					System.out.println("The word was  " + word.getActualWord());
				}

				System.out.println("****************************************"
						+ "**************************************");
			}
			System.out
					.println("Type EXIT to exit the game, any-character to continue");
			myScanner = new Scanner(System.in);
		}

		printScoresofPlayers(players);
	}

	/**
	 * Print the scores of Players in Ascending Order 
	 * @param players
	 */
	private void printScoresofPlayers(Player[] players) {
		System.out.println("############### Score Board ##################");
		System.out.println("PlayerName\tPoints\tAttempts");
		Arrays.sort(players);
		for (Player participant : players) {
			System.out.println(participant.getPlayerName() + "\t\t"
					+ participant.getPoints() + "\t"
					+ participant.getAttempts());
		}
	}

	public static void main(String[] args) {
		HangMan myHangmanGame = new HangMan();
		if ((args != null) && (args.length != 0)) {
			myHangmanGame.playGame(args);
		}
	}

}
