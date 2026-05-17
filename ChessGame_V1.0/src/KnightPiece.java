import java.util.ArrayList;

/**
 * A KnightPiece extends abstract class ChessPiece
 * 
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class KnightPiece extends ChessPiece {

	/**
	 * A constructor for a KnightPiece
	 *
	 * @param type           type of piece
	 * @param inputName      name of chess piece
	 * @param initialSquare  position of chess piece type BoardSquare
	 * @param color          true: white, false: black
	 * @param activeStatus   true: piece is active, false: piece is inactive
	 * @param hasMoved       : always initated to false for Knights
	 * @param inputPath the file i/o path for the Knight image
	 */
	public KnightPiece(String pieceType, String inputName, BoardSquare inputPosition, boolean isWhite,String inputPath) {
		super(pieceType, inputName, inputPosition, isWhite,inputPath);
	}

	/**
	 * A method that finds all legal moves assoociated with a KnightPiece and returns
	 * an ArrayList
	 *
	 * @return an ArrayList of legal moves for a KnightPiece piece
	 */
	@Override
	public ArrayList<BoardSquare> findLegalMoves() {

		ArrayList<BoardSquare> legalMoves = new ArrayList<>();
		char col = this.position.squareName.charAt(0);
		char row = this.position.squareName.charAt(1);
		String curSquare;

		if (col >= 'C') {
			if (row <= '7') { // first move
				curSquare = "" + (char) (col - 2) + "" + (char) (row + 1);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare(curSquare, legalMoves);
				}
			}
			if (row >= '2') {// second move
				curSquare = "" + (char) (col - 2) + "" + (char) (row - 1);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare("" + (char) (col - 2) + "" + (char) (row - 1), legalMoves);
				}
			}
		}

		if (col >= 'B') {
			if (row <= '6') { // third move
				curSquare = "" + (char) (col - 1) + "" + (char) (row + 2);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare(curSquare, legalMoves);
				}
			}
			if (row >= '3') {// fourth move
				curSquare = "" + (char) (col - 1) + "" + (char) (row - 2);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare(curSquare, legalMoves);
				}
			}
		}

		if (col <= 'G') {
			if (row <= '6') { // fifth move
				curSquare = "" + (char) (col + 1) + "" + (char) (row + 2);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare(curSquare, legalMoves);
				}
			}
			if (row >= '3') {// sixth move
				curSquare = "" + (char) (col + 1) + "" + (char) (row - 2);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare(curSquare, legalMoves);
				}
			}
		}

		if (col <= 'F') {
			if (row <= '7') { // seventh move
				curSquare = "" + (char) (col + 2) + "" + (char) (row + 1);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare(curSquare, legalMoves);
				}
			}
			if (row >= '3') {// eigth move
				curSquare = "" + (char) (col + 2) + "" + (char) (row - 1);
				if(captureAdder(curSquare, legalMoves)) {
					addSquare(curSquare, legalMoves);
				}
			}
		}

		return legalMoves;
	}

	/**
	 * a private helper method that adds BoardSquares to legalMoves if it is a legal
	 * moves
	 *
	 * @param square the square to be added
	 * @param moves the list of squares
	 */
	private void addSquare(String square, ArrayList<BoardSquare> moves) {
		if (!ChessGame.gameBoard.SquareFinderByName(square).isOccupied) {
			moves.add(ChessGame.gameBoard.SquareFinderByName(square));
		}
	}
}