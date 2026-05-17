import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * A BoardSquare object creates a BoardSquare Object
 *
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class BoardSquare {

	String squareName;

	char[] BsNameArray = new char[2];

	public Color tileColor;

	public Color highlightedColor;

	public boolean isWhite;

	public int size;

	public int xLocation;

	public int yLocation;

	public boolean isOccupied;

	public boolean OccupiedByWhite;

	public boolean isClicked;

	public boolean isHighlighted;

	public static ChessPiece showingPiece;

	public char column;

	public char row;

	final static int COLUMN_FONT_OFFSET_X = (ChessBoard.tileSize / 10) * 9;

	final static int COLUMN_FONT_OFFSET_Y = (ChessBoard.tileSize / 10) * 9;

	final static int ROW_FONT_OFFSET_X = (ChessBoard.tileSize / 10) * 1;

	final static int ROW_FONT_OFFSET_Y = (ChessBoard.tileSize / 10) * 2;

	/**
	 * Constructor for a Boardsquare
	 *
	 * @param inputName  tile name - ie A1
	 * @param whiteOrNot - if white then true, if black then false
	 * @param inputSize  - size of a board - retrieved from a ChessBoard.java object
	 * @param inputColor either black (blue) or white
	 */
	public BoardSquare(String inputName, boolean whiteOrNot, int inputSize, Color inputColor, Color altColor,
			int xRefPoint, int yRefPoint) {
		squareName = inputName;
		column = inputName.charAt(0);
		row = inputName.charAt(1);
		tileColor = inputColor;
		highlightedColor = altColor;
		isWhite = whiteOrNot;
		size = inputSize;
		isOccupied = false;
		isClicked = false;
		isHighlighted = false;
		xLocation = xRefPoint;
		yLocation = yRefPoint;
	}

	/**
	 * Used for testing - Standard toString method, encapsualte all info of a
	 * BoardSquare
	 *
	 * @return formatted String with all data of a BoardSquare
	 */
	@Override
	public String toString() {
		return squareName + "," + tileColor + "isWhite: " + isWhite + "," + "Tile Size: " + size + "x" + size + ","
				+ "X Location: " + xLocation + ",Y Location " + yLocation + ",Occupied Status: " + isOccupied
				+ "IsHighlighted: " + isHighlighted + " isClicked: " + isClicked;
	}

	/**
	 * this method prints the row labels on a bs
	 */
	public void rowPrinter() {
		ChessBoard.gameBoard.setColor(Color.BLACK);
		ChessBoard.gameBoard.drawString(this.row + "", xLocation + ROW_FONT_OFFSET_X, yLocation + ROW_FONT_OFFSET_Y);

	}

	/**
	 * this method prints the column labels on a bs
	 */
	public void columnPrinter() {

		ChessBoard.gameBoard.setColor(Color.BLACK);
		ChessBoard.gameBoard.drawString(this.column + "", xLocation + COLUMN_FONT_OFFSET_X, yLocation + COLUMN_FONT_OFFSET_Y);
	}

	/**
	 * this method draws a BoardSquare with its B/W colors
	 */
	public void drawSquare() {
		ChessBoard.gameBoard.setColor(tileColor);
		ChessBoard.gameBoard.fillRect(xLocation + 1, yLocation, size, size);
		this.isHighlighted = false;

		if (this.column == 'A')
			rowPrinter();

		if (this.row == '1')
			columnPrinter();
	}

	/**
	 * this method draws a BoardSquare with its highlighted colors
	 */
	public void drawHighlightedSquare() {
		ChessBoard.gameBoard.setColor(highlightedColor);
		ChessBoard.gameBoard.fillRect(xLocation, yLocation, size, size);
		this.isHighlighted = true;

		if (this.column == 'A')
			rowPrinter();

		if (this.row == '1')
			columnPrinter();

	}

	/**
	 * this method handles the logic for when a square is highlighted or clicked
	 *
	 * @param gameBoard the boardSquare to draw to
	 */
	public void highlightSquare() {
		ChessPiece activePiece = findPieceAtSquare(this);

		if (this.isClicked) {
			eraseOldHighlights(ChessBoard.gameBoard, activePiece);
			return;
		}

		if (this.isHighlighted) {
			showingPiece.movePiece(this); // after this method whitesTurn ^= true has occured
			ChessGame.checkmateFinder();
			return;
		}

		if (!this.isHighlighted) {
			eraseOldHighlights(ChessBoard.gameBoard, activePiece);
			drawHighlightedSquare();
			this.isClicked = true;

			if (this.isOccupied) {
				activePiece.piecePrinter(squareName);
				activePiece.showLegalMoves();
				showingPiece = activePiece;
			}
		}
	}

	/**
	 * A helper method for highlightSquare erases ALL previous highlighted squares
	 * to B/W
	 */
	public static void eraseOldHighlights(Graphics2D gameBoard, ChessPiece activePiece) {
		ArrayList<BoardSquare> oldHighlights = new ArrayList<>(ChessGame.gameBoard.SquareFinderByHighlightStatus());
		for (BoardSquare square : oldHighlights) {

			if (!square.isOccupied) {
				square.resetSquareHighlight(activePiece);
			} else {
				final ChessPiece currPiece = findPieceAtSquare(square);
				square.resetSquareHighlight(currPiece);
			}
		}
	}

	/**
	 * A helper method for eraseOldHighlights resets a clicked square to B/W
	 */
	private void resetSquareHighlight(ChessPiece activePiece) {
		drawSquare();
		this.isClicked = false;

		if (this.isOccupied) {
			activePiece.piecePrinter(squareName);
		}
	}

	/**
	 * LinearSearch through activePieces<ChessPieces> to find what ChessPiece
	 * occupies a BoardSquare
	 *
	 * @param square the given square
	 * @return The piece occupying called BoardSquare
	 */
	public static ChessPiece findPieceAtSquare(BoardSquare square) {
		for (ChessPiece piece : ChessGame.activePieces) {
			if (piece.position == square) {
				return piece;
			}
		}
		return null;
	}
}
