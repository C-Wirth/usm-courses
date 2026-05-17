import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * ChessGame runs the main game for a chess game
 * 
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class ChessGame {

	public static ChessBoard gameBoard;

	public static List<ChessPiece> activePieces = new ArrayList<>();

	public static List<ChessPiece> activeWhitePieces = new ArrayList<>();

	public static List<ChessPiece> activeBlackPieces = new ArrayList<>();

	public static String imagePath = "chessImages/";

	public static boolean whitesTurn = true;

	public static boolean whiteIsChecked = false;

	public static boolean blackIsChecked = false;

	public static boolean whiteIsCheckMated = false;

	public static boolean blackIsCheckMated = false;

	public static boolean gameOver = false;

	/**
	 * main method run the game
	 *
	 * @throws IOException if pieces.txt is invalid or improperly formatted file
	 */
	public static void main(String args[]) throws IOException {
		gameStarter();
	}

	/**
	 * handles the logic for starting a game
	 * 
	 * @throws IOException if pieces.txt is invalid or improperly formatted file
	 */
	public static void gameStarter() throws IOException {
		int startRound = JOptionPane.showConfirmDialog(null, "Play Chess Game?", "Game Starter",
				JOptionPane.YES_OPTION);

		if (startRound == 0) { // condition to start a chess game: startRound == 0 prompted by user input
			gameBoard = new ChessBoard();
			pieceBuilder(); // instantiate all starting 32 chess pieces and add them to the activePieces
							// ArrayList
		}

	}

	/**
	 * handles the logic for retarting a game
	 * 
	 * @throws IOException if pieces.txt is invalid or improperly formatted file
	 */
	public static void restartGame() throws IOException {

		String winner;
		if (!whitesTurn) {
			winner = "White";
		} else {
			winner = "Black";
		}

		int restartRound = JOptionPane.showConfirmDialog(null, winner + " wins!  Play Chess Game again?",
				"Game restarter", JOptionPane.YES_OPTION);

		if (restartRound != 0) {
			return;
		}

		whitesTurn = true;

		whiteIsChecked = false;

		blackIsChecked = false;

		whiteIsCheckMated = false;

		blackIsCheckMated = false;

		gameOver = false;

		activePieces.removeAll(activePieces);

		activeWhitePieces.removeAll(activeWhitePieces);

		activeBlackPieces.removeAll(activeBlackPieces);

		gameBoard = new ChessBoard();

		pieceBuilder();
	}

	/**
	 * a method that reads "pieces.txt" and builds each chess piece at the beginning
	 * of a game
	 *
	 * @throws IOException if pieces.txt is invalid or improperly formatted file
	 */
	public static void pieceBuilder() throws IOException {

		BufferedReader pieceReader = new BufferedReader(new FileReader("pieces"));
		String curPiece;

		while ((curPiece = pieceReader.readLine()) != null) {
			String[] elements = curPiece.split(",");

			switch (elements[0]) {
			case "pawn":
				ChessPiece pawn = new PawnPiece(elements[0], elements[1], gameBoard.SquareFinderByName(elements[2]),
						Boolean.parseBoolean(elements[3]), imagePath + elements[4]);
				if (pawn.isWhite) {
					activeWhitePieces.add(pawn);
				} else {
					activeBlackPieces.add(pawn);
				}
				activePieces.add(pawn);
				pawn.position.isOccupied = true;
				break;

			case "rook": // dont forget to add default hasNextMove condition (false) to all subsequent
							// chess pieces
				ChessPiece rook = new RookPiece(elements[0], elements[1], gameBoard.SquareFinderByName(elements[2]),
						Boolean.parseBoolean(elements[3]), imagePath + elements[4]);
				if (rook.isWhite) {
					activeWhitePieces.add(rook);
				} else {
					activeBlackPieces.add(rook);
				}

				activePieces.add(rook);
				rook.position.isOccupied = true;
				break;

			case "knight":
				ChessPiece knight = new KnightPiece(elements[0], elements[1], gameBoard.SquareFinderByName(elements[2]),
						Boolean.parseBoolean(elements[3]), imagePath + elements[4]);
				if (knight.isWhite) {
					activeWhitePieces.add(knight);
				} else {
					activeBlackPieces.add(knight);
				}
				activePieces.add(knight);
				knight.position.isOccupied = true;
				break;

			case "bishop":
				ChessPiece bishop = new BishopPiece(elements[0], elements[1], gameBoard.SquareFinderByName(elements[2]),
						Boolean.parseBoolean(elements[3]), imagePath + elements[4]);
				if (bishop.isWhite) {
					activeWhitePieces.add(bishop);
				} else {
					activeBlackPieces.add(bishop);
				}
				activePieces.add(bishop);
				bishop.position.isOccupied = true;
				break;

			case "queen":
				ChessPiece queen = new QueenPiece(elements[0], elements[1], gameBoard.SquareFinderByName(elements[2]),
						Boolean.parseBoolean(elements[3]), imagePath + elements[4]);
				if (queen.isWhite) {
					activeWhitePieces.add(queen);
				} else {
					activeBlackPieces.add(queen);
				}
				activePieces.add(queen);
				queen.position.isOccupied = true;
				break;

			case "king":
				ChessPiece king = new KingPiece(elements[0], elements[1], gameBoard.SquareFinderByName(elements[2]),
						Boolean.parseBoolean(elements[3]), imagePath + elements[4]);
				if (king.isWhite) {
					activeWhitePieces.add(king);
				} else {
					activeBlackPieces.add(king);
				}
				activePieces.add(king);
				king.position.isOccupied = true;
				break;
			}
		}
		pieceReader.close();
	}
	
	/**
	 * handles the logic for determining if a King is checkmatted
	 */
	public static void checkmateFinder() {

		ArrayList<ChessPiece> pieces = new ArrayList<>();

		if (!whitesTurn && ChessPiece.blackCheckFinder()) { // white just moved now see if black is in check mate
			pieces.addAll(activeBlackPieces);
		}

		else if (whitesTurn && ChessPiece.whiteCheckFinder()) { // black just moved now see if white is in check mate
			pieces.addAll(activeWhitePieces);
		} else { 
			return;
		}

		for (ChessPiece cp : pieces) {

			ArrayList<BoardSquare> checkingMoves = cp.findLegalMoves();

			for (BoardSquare bs : checkingMoves) {

				if (!cp.pseudoMove(bs)) {
					return; // this is a legal move, return
				}
			}
		}
		if (!whitesTurn) {
			whiteIsCheckMated = true;
		} else {
			blackIsCheckMated = true;
		}
		gameOver = true;
		return;
	}
}
