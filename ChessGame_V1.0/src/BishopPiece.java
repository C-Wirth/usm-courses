import java.util.ArrayList;

/**
 * A BishopPiece Piece extends abstract class ChessPiece
 * 
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class BishopPiece extends ChessPiece {

	/**
	 * A constructor for a BishopPiece
	 *
	 * @param type           type of piece
	 * @param inputName      name of chess piece
	 * @param initialSquare  position of chess piece type BoardSquare
	 * @param color          true: white, false: black
	 * @param inputPath
	 */
	public BishopPiece(String pieceType, String inputName, BoardSquare inputPosition, boolean isWhite,
					   String inputPath) {
		super(pieceType, inputName, inputPosition, isWhite, inputPath);

	}
	/**
	 * A method that finds all legal moves assoociated with a BishopPiece and
	 * returns an ArrayList | calls bishopMover from ChessPiece.Java
	 *
	 * @return an ArrayList of legal moves for a BishopPiece piece
	 */
	@Override
	public ArrayList<BoardSquare> findLegalMoves() {
		return super.bishopMover();
	}
}