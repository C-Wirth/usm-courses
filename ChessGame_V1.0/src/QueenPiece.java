import java.util.ArrayList;

/**
 * A QueenPiece extends abstract class ChessPiece
 * 
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class QueenPiece extends ChessPiece {

	/**
	 * A constructor for a QueenPiece
	 *
	 * @param type           type of piece
	 * @param inputName      name of chess piece
	 * @param initialSquare  position of chess piece type BoardSquare
	 * @param color          true: white, false: black
	 * @param inputPath      the fili i/o for a QueenPiefce
	 */
	public QueenPiece(String pieceType, String inputName, BoardSquare inputPosition, boolean isWhite,
					  String inputPath) {
		super(pieceType, inputName, inputPosition, isWhite,inputPath);
	}

	/**
	 * A method that finds all legal moves associated with a QueenPiece and
	 * returns an ArrayList | calls bishopMover and rookMover from ChessPiece.Java
	 *
	 * @return an ArrayList of legal moves for a QueenPiece
	 */
	@Override
	public ArrayList<BoardSquare> findLegalMoves() {
		ArrayList<BoardSquare> legalMoves = new ArrayList<>();
		legalMoves.addAll(super.rookMover());
		legalMoves.addAll(super.bishopMover());
		return legalMoves;
	}
}