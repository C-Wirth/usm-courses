import java.util.ArrayList;

/**
 * A King Piece extends abstract class ChessPiece
 * 
 * @author Colby Wirth
 * @version 23 May 2024
 */
public class KingPiece extends ChessPiece {

	public static boolean isCastling = false;

	/**
	 * A constructor for a KingPiece
	 *
	 * @param type           type of piece
	 * @param inputName      name of chess piece
	 * @param initialSquare  position of chess piece type BoardSquare
	 * @param color          true: white, false: black
	 * @param activeStatus   true: piece is active, false: piece is inactive
	 * @param hasMoved       always initated to false for Kings
	 * @param inputPath      the file i/o path for the Knight image
	 */
	public KingPiece(String pieceType, String inputName, BoardSquare inputPosition, boolean isWhite, String inputPath) {
		super(pieceType, inputName, inputPosition, isWhite, inputPath);
	}

	@Override
	/**
	 * this method handles the moving logic for a KingPiece
	 */
	public void movePiece(BoardSquare newSquare) {
		if ((this.isWhite==ChessGame.whitesTurn) && !(this.hasMoved) && newSquare.column == 'G') { // user chooses to castle king side
			castleKingSide(newSquare);
		}

		if ((this.isWhite==ChessGame.whitesTurn) && !(this.hasMoved) && newSquare.column == 'C') { // user chooses to castle queen side
			castleQueenSide(newSquare);
		} else { // user chooses to castle queen side
			super.movePiece(newSquare);
		}

	}

	@Override
	public ArrayList<BoardSquare> findLegalMoves() {

		ArrayList<BoardSquare> legalMoves = new ArrayList<>();

		String curSquare;

		char col = this.position.squareName.charAt(0);
		int rowNum = Character.getNumericValue(this.position.row);

		// check left
		if (col > 'A') {
			if (rowNum >= 1) {
				for (int r = rowNum + 1, i = 1; /* r >= 1 || */ i <= 3; r--, i++) {
					if (r == 9 || r == 0) {
						continue;
					}
					curSquare = "" + (char) (col - 1) + "" + r;
					if (captureAdder(curSquare, legalMoves)) {
						addSquare(curSquare, legalMoves);
					}
				}
			}
		}

		// check center
		for (int r = rowNum + 1, i = 1; r >= 1 && i <= 3; r--, i++) {
			if ((r == 9) || r == rowNum) {
				continue;
			}

			curSquare = "" + (col) + "" + r;
			if (captureAdder(curSquare, legalMoves)) {
				addSquare(curSquare, legalMoves);
			}
		}

		// check right
		if (col < 'H') {
			if (rowNum >= 1) {
				for (int r = rowNum + 1, i = 1; /* r >= 1 || */ i <= 3; r--, i++) {
					if (r == 9 || r == 0) {
						continue;
					}
					curSquare = "" + (char) (col + 1) + "" + r;
					if (captureAdder(curSquare, legalMoves)) {
						addSquare(curSquare, legalMoves);
					}
				}
			}
		}


		if (!this.hasMoved) {
			kingSideCastleChecker(legalMoves);
			queenSideCastleChecker(legalMoves);
		}

		return legalMoves;
	}

	/**
	 * a helper method for findLegalMoves that adds BoardSquares to legalMoves if it
	 * is a legal move for kingPieces
	 *
	 * @param square the square to be added
	 * @param moves the list of squares
	 */
	private void addSquare(String square, ArrayList<BoardSquare> moves) {
		if (!ChessGame.gameBoard.SquareFinderByName(square).isOccupied) {
			moves.add(ChessGame.gameBoard.SquareFinderByName(square));
		}
	}

	/**
	 * this private helper method checks for a valid king side castle | used by
	 * findLegalMoves
	 * 
	 * @param moves the list of legal moves for the king piece
	 */
	private void kingSideCastleChecker(ArrayList<BoardSquare> moves) {
		if (this.isWhite) {

			BoardSquare G1 = ChessGame.gameBoard.SquareFinderByName("G1");
			BoardSquare H1 = ChessGame.gameBoard.SquareFinderByName("H1");

			if (!ChessGame.gameBoard.SquareFinderByName("F1").isOccupied && !G1.isOccupied
					&& !BoardSquare.findPieceAtSquare(H1).equals(null)) {
				moves.add(G1);
			}

		} else {

			BoardSquare G8 = ChessGame.gameBoard.SquareFinderByName("G8");
			BoardSquare H8 = ChessGame.gameBoard.SquareFinderByName("H8");

			if (!ChessGame.gameBoard.SquareFinderByName("F8").isOccupied && !G8.isOccupied
					&& !BoardSquare.findPieceAtSquare(H8).equals(null)) {
				moves.add(G8);
			}
		}

		return;
	}
	/**
	 * this private helper method checks for a valid king side castle | used byfindLegalMoves
	 * 
	 * @param moves the list of legal moves for the king piece
	 */
	private void 	queenSideCastleChecker(ArrayList<BoardSquare> moves) {
		if (this.isWhite) {
			BoardSquare A1 = ChessGame.gameBoard.SquareFinderByName("A1");
			BoardSquare B1 = ChessGame.gameBoard.SquareFinderByName("B1");
			BoardSquare C1 = ChessGame.gameBoard.SquareFinderByName("C1");
			BoardSquare D1 = ChessGame.gameBoard.SquareFinderByName("D1");

			if (!B1.isOccupied && !C1.isOccupied && !D1.isOccupied && !A1.equals(null)) {
				moves.add(C1);
			}
		} else {
			BoardSquare A8 = ChessGame.gameBoard.SquareFinderByName("A8");
			BoardSquare B8 = ChessGame.gameBoard.SquareFinderByName("B8");
			BoardSquare C8 = ChessGame.gameBoard.SquareFinderByName("C8");
			BoardSquare D8 = ChessGame.gameBoard.SquareFinderByName("D8");

			if (!B8.isOccupied && !C8.isOccupied && !D8.isOccupied && !A8.equals(null)) {
				moves.add(C8);
			}
		}
		return;
	}
	
	/**
	 * this method handles moving a king side rook for castling
	 * 
	 * @param newSquare the square to be castled to 
	 */
	public void castleKingSide(BoardSquare newSquare) {
		isCastling = true;
		if (this.isWhite && ChessGame.whitesTurn) {
			ChessPiece kingSideRook = BoardSquare.findPieceAtSquare(ChessGame.gameBoard.SquareFinderByName("H1"));
			kingSideRook.movePiece(ChessGame.gameBoard.SquareFinderByName("F1"));
		}

		if (!this.isWhite && !ChessGame.whitesTurn) {
			ChessPiece kingSideRook = BoardSquare.findPieceAtSquare(ChessGame.gameBoard.SquareFinderByName("H8"));
			kingSideRook.movePiece(ChessGame.gameBoard.SquareFinderByName("F8"));
		}

		super.movePiece(newSquare);

		ChessGame.whitesTurn ^= true;

		isCastling = false;
	}

	/**
	 * this method handles moving a queen side rook for castling
	 * 
	 * @param newSquare the square to be castled to 
	 */
	public void castleQueenSide(BoardSquare newSquare) {
		isCastling = true;
		if (this.isWhite && ChessGame.whitesTurn) {
			ChessPiece queenSideRook = BoardSquare.findPieceAtSquare(ChessGame.gameBoard.SquareFinderByName("A1"));
			queenSideRook.movePiece(ChessGame.gameBoard.SquareFinderByName("D1"));
		}

		if (!this.isWhite && !ChessGame.whitesTurn) {
			ChessPiece queenSideRook = BoardSquare.findPieceAtSquare(ChessGame.gameBoard.SquareFinderByName("A8"));
			queenSideRook.movePiece(ChessGame.gameBoard.SquareFinderByName("D8"));
		}

		super.movePiece(newSquare);

		ChessGame.whitesTurn ^= true;

		isCastling = false;
	}
}