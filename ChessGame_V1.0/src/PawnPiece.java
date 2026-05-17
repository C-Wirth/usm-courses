import java.util.ArrayList;

/**
 * A Pawn Piece extends abstract class ChessPiece
 *
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class PawnPiece extends ChessPiece {

	public static int numQueens = 0;

	public static boolean showingMoves = false;

	/**
	 * A constructor for a pawnPiece
	 *
	 * @param type                   type of piece
	 * @param inputName              name of chess piece
	 * @param initialSquare          position of chess piece type BoardSquare
	 * @param color                  true: white, false: black
	 * @param inputPath              the file i/o path for the Pawn image
	 * @param doubleAdvancedLastMove always initialized to false
	 */
	public PawnPiece(String pieceType, String inputName, BoardSquare inputPosition, boolean isWhite, String inputPath) {
		super(pieceType, inputName, inputPosition, isWhite, inputPath);

	}

	// no en Passant yet
	/**
	 * A method that finds all legal moves assoociated with a PawnPiece and returns
	 * an ArrayList
	 *
	 * @return an ArrayList of legal moves for a PawnPiece piece
	 */
	@Override
	public ArrayList<BoardSquare> findLegalMoves() {

		ArrayList<BoardSquare> legalMoves = new ArrayList<>();

		char currColumn = (position.column); // letters A-H
		char rightColumn = (char) (currColumn + 1);
		char leftColumn = (char) (currColumn - 1);

		char aboveRow;
		char twoAboveRow;

		if (this.isWhite) { // find moves for white Pawns

			aboveRow = (char) (position.row + 1);
			twoAboveRow = (char) (position.row + 2);
		}

		else { // find moves for black pawns
			aboveRow = (char) (position.row - 1);
			twoAboveRow = (char) (position.row - 2);
		}

		// find diagnoal captues
		for (int i = 0; i <= 1; i++) {
			BoardSquare diagnoalCaptures = null;

			if (i == 1) {
				diagnoalCaptures = ChessGame.gameBoard.SquareFinderByName("" + leftColumn + aboveRow);
			}

			if (i == 0) {
				diagnoalCaptures = ChessGame.gameBoard.SquareFinderByName("" + rightColumn + aboveRow);
			}

			if (diagnoalPieceChecker(diagnoalCaptures)) {
				legalMoves.add(diagnoalCaptures);
			}

		}

		char[] positionAbove = { currColumn, aboveRow };
		char[] positionTwoAbove = { currColumn, twoAboveRow };

		// if square in front is occupied add nothing

		if (ChessGame.gameBoard.SquareFinderByName(new String(positionAbove)).isOccupied) {
			return legalMoves;
		} else {
			legalMoves.add(ChessGame.gameBoard.SquareFinderByName(new String(positionAbove)));
		}

		// if square twoInfront is not occupied add square
		if (!this.hasMoved && !ChessGame.gameBoard.SquareFinderByName(new String(positionTwoAbove)).isOccupied) {
			legalMoves.add(ChessGame.gameBoard.SquareFinderByName(new String(positionTwoAbove)));
		}

		return legalMoves;

	}

	/**
	 * this method checks dianoal squares for other pieces for capture
	 *
	 * @param square
	 * @return
	 */
	public Boolean diagnoalPieceChecker(BoardSquare square) {
		ChessPiece curPiece = BoardSquare.findPieceAtSquare(square);
		if (!(curPiece == null)) {
			if (!(this.isWhite == curPiece.isWhite)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * this method handles the logic for queening a pawn - note a pawn can only
	 * become a queen in this version
	 */
	public void queening() {
		numQueens++;

		this.removePiece();
		this.position.isOccupied = false;

		String pieceColor = "whiteQueen.png";
		if(!this.isWhite) {
			pieceColor = "blackQueen.png";
		}

		ChessPiece queen = new QueenPiece("queen", "queen" + numQueens, this.position, this.isWhite,ChessGame.imagePath + pieceColor);
		ChessGame.activePieces.add(queen);
		queen.hasMoved = true;
		this.position.isOccupied = true;
		
		if (this.isWhite)
			ChessGame.activeWhitePieces.add(queen);
		else
			ChessGame.activeBlackPieces.add(queen);


			
	}
}
