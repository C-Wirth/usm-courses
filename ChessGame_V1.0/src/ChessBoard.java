import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * A ChessBoard object creates a encapsualtes the information for a chess board
 * used by ChessGame.java
 * 
 * @author Colby Wirth
 * @version 23 May 2024
 *
 */
public class ChessBoard {

	final static int numSpots = 64;

	final static int numColumns = 8;

	final static int numRows = 8;

	final static int boardSize = 800;

	final static int tileSize = boardSize / 8;
	
	public static Graphics2D gameBoard;

	public static DrawingPanel visualBoard;

	public BoardSquare[][] dataBoard = new BoardSquare[numColumns][numRows];

	public String chessBoardString = "";

	int xRefPoint = 0;

	int yRefPoint = boardSize - tileSize;

	/**
	 * Generic constructor for a ChessBoard
	 */
	public ChessBoard() {

		visualBoard = new DrawingPanel(boardSize, boardSize);

		gameBoard = visualBoard.getGraphics();

		char column = 'A';
		int row = 1;
		Color squareColor;
		Color highlightColor;
		String squareName = "A1"; // initialize squareName
		boolean isWhite;

		for (int c = 0; c < numColumns; ++c) { // iterate through each column
			column = 'A';

			for (int r = 0; r < numRows; ++r) { // iterate through each row

				if ((r + c) % 2 == 0) { // determine tile color

					squareColor = (new Color(188, 87, 71)); // is black -red
					highlightColor = (new Color(218, 167, 112)); // black's highlighted color - bronzish
					isWhite = false;
				} else {
					isWhite = true;
					squareColor = (new Color(245, 219, 196)); // is white - beige
					highlightColor = (new Color(246, 233, 175)); // white's highlighted color - yellow
				}

				squareName = "" + column + row;
				BoardSquare gameSquare = new BoardSquare(squareName, isWhite, tileSize, squareColor, highlightColor,
						xRefPoint, yRefPoint);
				gameSquare.toString();

				if (row == 1 || row == 2 || row == 7 || row == 8) {
					gameSquare.isOccupied = true;
				}

				dataBoard[r][c] = gameSquare;

				chessBoardString = chessBoardString.concat(gameSquare.toString() + "\n");

				gameSquare.drawSquare(); // draw each tile with each iteration
				xRefPoint += tileSize;

				column++;
			}
			row++;
			xRefPoint = 0;
			yRefPoint -= tileSize;
		}

		/**
		 * mouse listener AIC this retrieves all mouseclicks that occur during the game
		 */
		visualBoard.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				int xRef = (e.getX() / 100 * 100);
				int yRef = (e.getY() / 100 * 100);

				BoardSquare selectedSquare = SquareFinderbyPos(xRef, yRef);
				selectedSquare.highlightSquare();

				if (ChessGame.gameOver) {
					try {
						ChessGame.restartGame();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Uses a linear search algorithm to match a set of x-y coords to a highlighted
	 * square Retrieved from chatGPT because I was to lazy to write this myself
	 *
	 * @param xRef the x coord of a mouseClick expressed as an int
	 * @param yRef the y coord of a mouseClick expressed as an int
	 *
	 * @return the BoardSquare the square located at the mouseclick
	 */

	public BoardSquare SquareFinderbyPos(int xRef, int yRef) {

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				if (dataBoard[r][c].xLocation == xRef && dataBoard[r][c].yLocation == yRef) {
					return dataBoard[r][c];
				}
			}
		}
		return null;
	}

	/**
	 * Used by ChessGame.java when each piece is instantiated at the beginning of a
	 * game uses a linear search algorithm to match a name of a square copied from
	 * above
	 *
	 * @param String name | the name of the BoardSquare
	 *
	 * @return the BoardSquare object with the associated String
	 * @return null if out of bounds, ie A9 or I1
	 */
	public BoardSquare SquareFinderByName(String name) {

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				if (dataBoard[r][c].squareName.equals(name)) {
					return dataBoard[r][c];
				}
			}
		}
		return null;
	}

	/**
	 * Used by BoardSquare.java loop through all bond white ardSquares and add
	 * highlighted squares to the ArrayList
	 *
	 * @return BoardSquare the highlighted Square
	 */
	public ArrayList<BoardSquare> SquareFinderByHighlightStatus() {
		ArrayList<BoardSquare> HighlightedSquares = new ArrayList<>();

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				if (dataBoard[r][c].isHighlighted) {
					HighlightedSquares.add(dataBoard[r][c]);
				}
			}
		}
		return HighlightedSquares;
	}

	/**
	 * this method loops through all active pieces for a given color and returns all
	 * legal moves as an ArrayList<BoardSquares>
	 *
	 * @return all legal moves of a given color - color determined ChessGame.whitesTurn boolean value
	 */
	public static ArrayList<BoardSquare> pseudoMoveFinder() {

		ArrayList<BoardSquare> blackLegalMoves = new ArrayList<>();

		ArrayList<BoardSquare> whiteLegalMoves = new ArrayList<>();

		ArrayList<ChessPiece> searchingMoves = new ArrayList<>();

		if (ChessGame.whitesTurn) {
			searchingMoves.addAll(ChessGame.activeBlackPieces);
		} else {
			searchingMoves.addAll(ChessGame.activeWhitePieces);
		}

		for (ChessPiece piece : searchingMoves) {

			ArrayList<BoardSquare> moves = piece.findLegalMoves();

			for (BoardSquare square : moves) {
				if (piece.isWhite) {
					whiteLegalMoves.add(square); // used to check if black's move is legal
				}

				if (!piece.isWhite) {
					blackLegalMoves.add(square); // used to check if white/s move is legal
				}
			}
		}

		if (ChessGame.whitesTurn) {
			return blackLegalMoves;
		}
		
		return whiteLegalMoves;
	}

	/**
	 * toString method returns all 64 BoardSquares from a ChessBoard object
	 *
	 * @return returns an organized string used for testing
	 */
	@Override
	public String toString() {
		return this.chessBoardString;
	}

}