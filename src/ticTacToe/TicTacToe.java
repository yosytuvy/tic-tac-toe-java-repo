package ticTacToe;

import java.util.Scanner;
import java.util.Random;

public abstract class TicTacToe {
	static Scanner console = new Scanner(System.in);
	Random rand = new Random();

	/**
	 * the function prints out the board that was passed.
	 * 'board' is a Two-dimensional 4*4 String Array
	 *  representing the board (ignore 0 indexes).
	 * @param board the board to print.
	 */
	static void drawBoard(String[][] board) {		
		System.out.println("-----------");
		System.out.println(" " + board[1][1] + " | " + board[1][2] + " | " + board[1][3]);
		System.out.println("-----------");
		System.out.println(" " + board[2][1] + " | " + board[2][2] + " | " + board[2][3]);
		System.out.println("-----------");
		System.out.println(" " + board[3][1] + " | " + board[3][2] + " | " + board[3][3]);
		System.out.println("-----------");
	}

	/**
	 * Check if the player want to play again,
	 * otherwise it return false.
	 * @return True if the player want to play again, otherwise return False.
	 */
	static boolean playAgain() {
		System.out.println("To play again press y \npress any other key to leave the game.");
		String answer = console.nextLine();
		return answer.toLowerCase().equals("y");
	}

	/**
	 * given a board and a palyer's letter, this function return true if that player has won.
	 * @param bo is stands for the board.
	 * @param le is stands for the letter(X/O).
	 * @return True if his won other wise return False.
	 */
	static boolean isWinner(String[][] bo, String le) {
		return ((bo[1][1].equals(le) && bo[1][2].equals(le) && bo[1][3].equals(le)) || // across the top
				(bo[2][1].equals(le) && bo[2][2].equals(le) && bo[2][3].equals(le)) || // across the middle
				(bo[3][1].equals(le) && bo[3][2].equals(le) && bo[3][3].equals(le)) || // across the bottom
				(bo[1][1].equals(le) && bo[2][1].equals(le) && bo[3][1].equals(le)) || // down the left side
				(bo[1][2].equals(le) && bo[2][2].equals(le) && bo[3][2].equals(le)) || // down the middle 
				(bo[1][3].equals(le) && bo[2][3].equals(le) && bo[3][3].equals(le)) || // down the right side
				(bo[1][1].equals(le) && bo[2][2].equals(le) && bo[3][3].equals(le)) || // diagonal
				(bo[1][3].equals(le) && bo[2][2].equals(le) && bo[3][1].equals(le)));  // diagonal 
	}

	/**
	 * Make a duplicate of the board list.
	 * @param board the original board.
	 * @return Two-dimensional array with the duplicate board.
	 */
	static String[][] getBoardCopy(String[][] board){
		String[][] dupeBoard = new String[4][4];
		for(int i = 1; i < board.length; i++) {
			for(int j = 1; j < board[i].length; j++) {
				dupeBoard[i][j] = board[i][j];
			}
		}
		return dupeBoard;
	}

	/**
	 * Check if the passed move is free on the passed board.
	 * @param board the passed board.
	 * @param rowMove the row to check.
	 * @param colMove the column to check.
	 * @return True if the place is free otherwise return False.
	 */
	static boolean isSpaceFree(String[][] board, int rowMove, int colMove) {
		return board[rowMove][colMove].equals(" ");
	}

	/**
	 * Let the player type his move.
	 * @param board the board to put the move.
	 * @return Array that the first index stand for the row chosen and second index for the column chosen. 
	 */
	static int[] getPlayerMove(String[][] board) {
		String[] options = new String[]{"1", "2", "3"};
		String rowMove = "0";
		String colMove = "0";
		while (true) {
			System.out.println("Enter first number for row, \nand second number for colomn. (1-3)");
			rowMove = console.nextLine();
			colMove = console.nextLine();
			for(int i = 0; i < options.length; i++) // loop for checking the input is between 1-3
			{										// and if the place chosen is free.
				if(colMove.equals(options[i]))
				{
					for(int j = 0; j < options.length; j++)
					{
						if(rowMove.equals(options[j]) &&
								isSpaceFree(board, Integer.parseInt(rowMove), Integer.parseInt(colMove)))
						{
							int[] move = new int[] {Integer.parseInt(rowMove), Integer.parseInt(colMove)};
							return move;
						}
					}
				}
			}
		}
	}

	/**
	 * chose random place from a given list if the place is free.
	 * @param board stands for the board to check.
	 * @param list list of places to choose from.
	 * @return Two-dimensional array size[9][2] that contain all free spaces on the board.
	 * if all the palaces that in the given  list is taken it return null.
	 */
	static int[] chooseRandomMoveFromList(String[][] board, int[][] list){
		int possibleMovesIndex = 0;
		int[][] possibleMovesTemp = new int[9][2];
		for(int i = 0; i < list.length; i++) {
			if(isSpaceFree(board, list[i][0], list[i][1])) {
				possibleMovesTemp[possibleMovesIndex][0] = list[i][0];
				possibleMovesTemp[possibleMovesIndex][1] = list[i][1];
				possibleMovesIndex ++;
			}
		}

		if(possibleMovesIndex > 0) {
			int[][] possibleMoves = new int[possibleMovesIndex][2];
			for(int i = 0; i < possibleMovesIndex; i++) {
				possibleMoves[i] = possibleMovesTemp[i];
			}
			Random rand = new Random();
			int n = rand.nextInt(possibleMovesIndex);
			return possibleMoves[n];
		}
		return null;
	}

	/**
	 * algorithm for computer move.
	 * @param board the board to put the move.
	 * @return Array that the first index stand for the row chosen and second index for the column chosen.
	 */
	static int[] getComputerMove(String[][] board){
		String computerLetter = "O";
		String playerLetter = "X";
		int[] move = new int[2];

		// loop to check if the computer can win in the next move.
		for(int i = 1; i < board.length; i++) {
			for(int j = 1; j < board[i].length; j++) {
				String[][] copy = getBoardCopy(board);
				if(isSpaceFree(board, i, j)) {
					copy[i][j] = computerLetter;
					if(isWinner(copy, computerLetter)) {
						move[0] = i;
						move[1] = j;
						return move;
					}
				}
			}
		}

		// loop to check if the player can win in the next move and block them.
		for(int i = 1; i < board.length; i++) {
			for(int j = 1; j < board[i].length; j++) {
				String[][] copy = getBoardCopy(board);
				if(isSpaceFree(board, i, j)) {
					copy[i][j] = playerLetter;
					if(isWinner(copy, playerLetter)) {
						move[0] = i;
						move[1] = j;
						return move;
					}
				}
			}
		}

		// try to take randomly one of the corners, if they are free.
		int[][] corners = {{1, 1}, {1, 3}, {3, 1}, {3, 1}};
		move = chooseRandomMoveFromList(board, corners);
		if(move != null) {
			return move;
		}

		// try to take the center, if its free
		int[] center = {2, 2};
		if(isSpaceFree(board, 2, 2)) {
			return center;
		}

		// try to take randomly one of the sides.
		int[][] sides = {{1, 2}, {2, 1}, {2, 3}, {3, 2}};
		move = chooseRandomMoveFromList(board, sides);
		return move;
	}

	/**
	 * check if the board is fool.
	 * @param board the board to check.
	 * @return True if every place on the board has been taken, otherwise return false.
	 */
	static boolean isBoardFull(String[][] board) {
		for(int i = 1; i < board.length; i++) {
			for(int j = 1; j < board[i].length; j++) {
				if(isSpaceFree(board, i, j)) {
					return false;
				}
			}
		}
		return true;
	}



	public static void main(String[] args) {

		System.out.println("Wolcome to tic tac toe!!!");
		String playerShape = "X";
		String coputerShape = "O";
		int[] move = new int[2];
		while (true) {
			// Reset the board
			String[][] theBoard = new String[4][4];
			for(int i = 1; i < theBoard.length; i++) { // ignore 0 indexes 
				for(int j = 1; j < theBoard[i].length; j++) {
					theBoard[i][j] = " ";
				}
			}
			boolean gameIsPlaying = true;
			String turn = "player";
			while(gameIsPlaying) {
				if(turn.equals("player")) {
					// Player turn
					drawBoard(theBoard);
					move = getPlayerMove(theBoard);
					theBoard[move[0]][move[1]] = playerShape;
					if(isWinner(theBoard, playerShape)) {
						drawBoard(theBoard);
						System.out.println("Hooray!! You have won the game!!!!");
						gameIsPlaying = false;
					}
					else {
						if(isBoardFull(theBoard)) {
							drawBoard(theBoard);
							System.out.println("The game is a tie!");
							break;
						}
						else {
							turn = "computer";
						}
					}
				}
				else {
					// computer turn
					move = getComputerMove(theBoard);
					theBoard[move[0]][move[1]] = coputerShape;
					if(isWinner(theBoard, coputerShape)) {
						drawBoard(theBoard);
						System.out.println("The computer has beaten you! You lose...");
						gameIsPlaying = false;
					}
					else {
						if(isBoardFull(theBoard)) {
							drawBoard(theBoard);
							System.out.println("The game is tie!");
							break;
						}
						else {
							turn = "player";
						}
					}
				}
			}
			if(!playAgain()) {
				System.out.println("Thank you for Playing!! \nbye bye!!");
				break;
			}
		}
		console.close();	
	}
}
