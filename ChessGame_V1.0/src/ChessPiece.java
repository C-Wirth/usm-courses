import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 *
 * Chess Piece: An Abstract Class that contains methods for a generic chess
 * piece
 *
 * @author Colby Wirth
 * @version 23 May 2024
 */
public abstract class ChessPiece {

	String pieceType;

	String name;
	
	public BoardSquare position;

	boolean isWhite;

	boolean hasNextMove;

	boolean isActive;

	boolean hasMoved;

	public static boolean pseudoMoving = false;

	String imagePath;

	/**
	 *
	 * A constructor for a generic chess piece
	 *
	 * @param type          type of piece
	 * @param inputName     name of chess piece
	 * @param initialSquare position of chess piece type BoardSquare
	 * @param color         true: white, false: black
	 * @param inputPath     map the file input via String
	 */
	public ChessPiece(String type, String inputName, BoardSquare initialSquare, boolean color, String inputPath) {

		pieceType = type;

		name = inputName;

		position = initialSquare;

		initialSquare.isOccupied = true; // update isOccupied for this BoardSquare object

		isWhite = color;

		hasNextMove = false;

		hasMoved = false;

		isActive = true;

		imagePath = inputPath;

		piecePrinter(inputPath);

	}

	// not exactly sure of interpolation works, but it gets the job done
	/**
	 * Invoked by constructor, scales and prints the image of the ChessPiece to the
	 * visualBoard (Chess Board)
	 *
	 * @param path
	 */
	public void piecePrinter(String path) {

		final int CHESS_PIECE_SIZE = 100;

		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage scaledImage = new BufferedImage(CHESS_PIECE_SIZE, CHESS_PIECE_SIZE, image.getType()); // scale
																											// image
		Graphics2D g2d = scaledImage.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // use
																												// iterpolation
																												// to
																												// adjust
																												// image
																												// clarity

		g2d.drawImage(image, 0, 0, CHESS_PIECE_SIZE, CHESS_PIECE_SIZE, null); // draw image

		g2d.dispose(); // take out the garbage

		ChessBoard.visualBoard.getGraphics().drawImage(scaledImage, position.xLocation, position.yLocation, null);
	}

	/**
	 * A method that handles the logic for showing valid moves for a ChessPiece
	 *
	 * @return void
	 */
	public void showLegalMoves() {
		ArrayList<BoardSquare> checkingMoves = new ArrayList<>(findLegalMoves());
		ArrayList<BoardSquare> legalMoves = new ArrayList<>();

		if (checkingMoves.size() == 0) {
			return;
		}

		for (BoardSquare square : checkingMoves) { // this is all moves that were legal without accounting for chceck

			if (pseudoMove(square)) {
				continue; // if this is an illegal move continue to next square. Do not print anything
				}

			legalMoves.add(square);

			square.drawHighlightedSquare();
			if (square.isOccupied) {
				BoardSquare.findPieceAtSquare(square).piecePrinter(imagePath);
			}
		}
	}

	/**
	 * pseudoMove is called by legalMoves to simulate all of the opposite color's
	 * moves to check for a if a king is checked
	 *
	 * @return illegalMove | true if move puts own king into check | false if move is legal
	 */
	public boolean pseudoMove(BoardSquare square) {
		boolean illegalMove = false;
		pseudoMoving = true;

		ChessPiece pseudoRemovedPiece = null;

		if (square.isOccupied) {
			pseudoRemovedPiece = BoardSquare.findPieceAtSquare(square);
			pseudoRemovedPiece.removePiece();
		}

		// Save the current position
		BoardSquare oldSquare = this.position;

		// Simulate the move
		this.position = square;
		square.isOccupied = true;
		oldSquare.isOccupied = false;
		oldSquare.OccupiedByWhite = false;

		// Check if this move puts the king in check
		if (this.isWhite == ChessGame.whitesTurn) {

//			ChessGame.whiteIsChecked = false;
//
//			ChessGame.blackIsChecked = false;

			if (this.isWhite) {
				illegalMove = whiteCheckFinder();
			} else {
				illegalMove = blackCheckFinder();
			}
		}
		// Restore the position
		this.position = oldSquare;
		this.position.isOccupied = true;
		square.isOccupied = false;
		if (this.isWhite) {
			this.position.OccupiedByWhite = true;
		}

		// Restore the removed piece if there was any
		if (pseudoRemovedPiece != null) {
			ChessGame.activePieces.add(pseudoRemovedPiece);

			if (pseudoRemovedPiece.isWhite) {
				ChessGame.activeWhitePieces.add(pseudoRemovedPiece);
			} else {
				ChessGame.activeBlackPieces.add(pseudoRemovedPiece);
			}

			pseudoRemovedPiece.isActive = true;
			pseudoRemovedPiece.position.isOccupied = true;
			pseudoRemovedPiece.position.drawSquare();
			pseudoRemovedPiece.piecePrinter(imagePath);
		}

		pseudoMoving = false;
		return illegalMove;
	}

	/**
	 * a method that removes a position of a chess piece
	 */
	public void removePiece() {
		if (!pseudoMoving) {
			this.position.drawSquare();
		}

		ChessGame.activePieces.remove(this);
		this.isActive = false;

		if (this.isWhite) {
			ChessGame.activeWhitePieces.remove(this);
			return;
		}

		ChessGame.activeBlackPieces.remove(this);
		return;

	}

	/**
	 * this method checks all of black's legal moves and returns true if white is in
	 * check
	 *
	 * @return true if white is checked | false is white is not checked
	 */
	public static boolean whiteCheckFinder() {
		if (!ChessGame.whitesTurn) {
			return false;
		}

		ArrayList<BoardSquare> blackLegalMoves = new ArrayList<>(ChessBoard.pseudoMoveFinder());

		for (BoardSquare blackMove : blackLegalMoves) {
			ChessPiece pieceAtMove = BoardSquare.findPieceAtSquare(blackMove);

			if ((pieceAtMove != null) && pieceAtMove.name.equals("whiteKing")) {
			return true;
			}
		}
		return false;
	}

	/**
	 * this method checks all of black's legal moves and returns true if white is in
	 * check
	 *
	 * @return true if white is checked | false is white is not checked
	 */
	public static boolean blackCheckFinder() {
		if (ChessGame.whitesTurn) {
			return false;
		}

		ArrayList<BoardSquare> whiteLegalMoves = new ArrayList<>(ChessBoard.pseudoMoveFinder());

		for (BoardSquare whiteMove : whiteLegalMoves) {
			ChessPiece pieceAtMove = BoardSquare.findPieceAtSquare(whiteMove);

			if ((pieceAtMove != null) && pieceAtMove.name.equals("blackKing")) {
				return true;
			}
		}
		return false;

	}

	/**
	 * a method that handles the move logic for any ChessPiece
	 *
	 * @param BoardSquare newSquare the position that is being attempted to move to
	 */
	public void movePiece(BoardSquare newSquare) {

		if (!KingPiece.isCastling) {
			if (BoardSquare.findPieceAtSquare(this.position).isWhite != ChessGame.whitesTurn) {
				return;
			}
		}

		BoardSquare.eraseOldHighlights(ChessBoard.gameBoard, this);

		if (newSquare.isOccupied) {
			BoardSquare.findPieceAtSquare(newSquare).removePiece();
		}

		BoardSquare oldSquare = this.position;

		this.position = newSquare;
		newSquare.isOccupied = true;

		piecePrinter(newSquare.squareName);

		oldSquare.drawSquare();
		oldSquare.isOccupied = false;
		oldSquare.OccupiedByWhite = false;

		if (!pseudoMoving) {
			this.hasMoved = true;
		}

		if (this.isWhite) {
			this.position.OccupiedByWhite = true;
		}

		ChessGame.whitesTurn ^= true;

		if (this instanceof PawnPiece && (this.position.row == '1' || this.position.row == '8')) { // queening
			((PawnPiece) this).queening();
		}
	}

	/**
	 * A method that handles the diagnoal movements of Bishops and Queens
	 *
	 * @return ArrayList<BoardSquare> legal moves
	 */
	public ArrayList<BoardSquare> bishopMover() {
		ArrayList<BoardSquare> legalMoves = new ArrayList<>();
		char col = this.position.column;
		char row = this.position.row;

		// check left and up
		if (col > 'A' && row < '8') {
			for (char curCol = col, curRow = row; curCol > 'A' && curRow < '8';) {
				curCol--;
				curRow++;
				Boolean breaking = addSquare("" + curCol + "" + curRow, legalMoves);

				if (breaking) {
					break;
				}
			}
		}

		// check left and down
		if (col > 'A' && row <= '8') {
			for (char curCol = col, curRow = row; curCol > 'A' && curRow > '1';) {
				curCol--;
				curRow--;
				Boolean breaking = addSquare("" + curCol + "" + curRow, legalMoves);

				if (breaking) {
					break;
				}
			}
		}

		// check right and down
		if (col < 'H' && row > '1') {
			for (char curCol = col, curRow = row; curCol < 'H' && curRow > '1';) {
				curCol++;
				curRow--;
				Boolean breaking = addSquare("" + curCol + "" + curRow, legalMoves);

				if (breaking) {
					break;
				}
			}
		}

		// check right and up
		if (col < 'H' && row < '8') {
			for (char curCol = col, curRow = row; curCol < 'H' && curRow < '8';) {
				curCol++;
				curRow++;
				Boolean breaking = addSquare("" + curCol + "" + curRow, legalMoves);

				if (breaking) {
					break;
				}
			}
		}

		return legalMoves;
	}

	/**
	 * a helper method for bishopMover that adds BoardSquares to legalMoves if it is
	 * a legal moves
	 *
	 * @param square the position of the square
	 * @param moves  ArrayList<BoardSquare> of legal moves
	 */
	private boolean addSquare(String square, ArrayList<BoardSquare> moves) {
		if (captureAdder(square, moves)) {
			return true;
		}
		moves.add(ChessGame.gameBoard.SquareFinderByName(square));
		return false;
	}

	/**
	 * a helper method that assists movement of minor pieces, queen and king
	 *
	 * @return true if the ChessPiece can move to an occupied Square and take piece
	 * @return false if the ChessPiece cannot move to an occupied Square
	 */
	public Boolean captureAdder(String pos, ArrayList<BoardSquare> list) {
		BoardSquare curSquare = ChessGame.gameBoard.SquareFinderByName(pos);

		if (curSquare.isOccupied) {
			ChessPiece piece = BoardSquare.findPieceAtSquare(curSquare);

			if (piece != null && this.isWhite != piece.isWhite) {
				list.add(curSquare);
			}
			return true;
		} else {
			list.add(curSquare);
		}

		return false;
	}

	/**
	 * a method that handles the vertical and horizonal movements of rooks and
	 * queens
	 */
	public ArrayList<BoardSquare> rookMover() {
		ArrayList<BoardSquare> legalMoves = new ArrayList<>();

		char col = this.position.column;
		char row = this.position.row;

		// Iterate upwards through rows
		for (char c = (char) (col + 1); c <= 'H'; c++) {
			String pos = "" + c + row;

			if (captureAdder(pos, legalMoves)) {
				break;
			}
		}

		// Iterate backwards through rows
		for (char c = (char) (col - 1); c >= 'A'; c--) {
			String pos = "" + c + row;

			if (captureAdder(pos, legalMoves)) {
				break;
			}
		}

		// Iterate upwards through column
		for (char r = (char) (row + 1); r <= '8'; r++) {
			String pos = "" + col + r;

			if (captureAdder(pos, legalMoves)) {
				break;
			}
		}

		// Iterate backwards through columns
		for (char r = (char) (row - 1); r >= '1'; r--) {
			String pos = "" + col + r;

			if (captureAdder(pos, legalMoves)) {
				break;
			}
		}

		return legalMoves;
	}

	/**
	 * a toString method for a generic ChessPiece
	 */
	@Override
	public String toString() {
		return this.name + "," + this.position.squareName;

	}

	public abstract ArrayList<BoardSquare> findLegalMoves();

}