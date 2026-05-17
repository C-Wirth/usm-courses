import java.util.ArrayList;

/**
 * A Rook Piece extends abstract class ChessPiece
 * 
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class RookPiece extends ChessPiece {

	/**
	 * A constructor for a KingPiece
	 *
	 * @param type          type of piece
	 * @param inputName     name of chess piece
	 * @param initialSquare position of chess piece type BoardSquare
	 * @param color         true: white, false: black
	 * @param inputPath     the file i/o path for the Rook image
	 */
	public RookPiece(String pieceType, String inputName, BoardSquare inputPosition, boolean isWhite, String inputPath) {
		super(pieceType, inputName, inputPosition, isWhite, inputPath);
	}

	/**
	 * overrides findLegalMoves with rookMover found in ChessPiece.Java
	 *
	 * @return an ArrayList of legal moves for a BishopPiece piece
	 */
	@Override
	public ArrayList<BoardSquare> findLegalMoves() {
		return super.rookMover();
	}
}