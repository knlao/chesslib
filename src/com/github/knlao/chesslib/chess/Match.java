package com.github.knlao.chesslib.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.knlao.chesslib.chess.pieces.Bishop;
import com.github.knlao.chesslib.chess.pieces.King;
import com.github.knlao.chesslib.chess.pieces.Knight;
import com.github.knlao.chesslib.chess.pieces.Pawn;
import com.github.knlao.chesslib.chess.pieces.Queen;
import com.github.knlao.chesslib.chess.pieces.Rook;

/**
 * The match of chess
 * @author kimilao
 * @version 1.0
 */
public class Match {
	
	/**
	 * How many times the players moved the pieces, 
	 * both white and black
	 */
	private int turn;
	
	/**
	 * The current player's color
	 */
	private Color currentPlayer;

	/**
	 * The board
	 */
	private Board board;
	
	/**
	 * If one is in check
	 */
	private boolean check;
	
	/**
	 * If one has been checkmated
	 */
	private boolean checkmate;
	
	/**
	 * The pawn which can be en passant
	 */
	private Piece enPassantVulnerable;
	
	/**
	 * The pawn which will be promoted
	 */
	private Piece promoted;

	/**
	 * The list of pieces on the board
	 */
	private List<Piece> piecesOnBoard = new ArrayList<>();
	
	/**
	 * The list of captured pieces
	 */
	private List<Piece> capturedPieces = new ArrayList<>();
	
	/**
	 * Construct a match
	 */
	public Match() {
		board = new Board();
		turn = 1;
		currentPlayer = Color.WHITE;
		initBoard();
	}
	
	/**
	 * Get turn
	 * @return Turn
	 */
	public int getTurn() {
		return turn;
	};
	
	/**
	 * Get the color of the current player
	 * @return The color of the current player
	 */
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Get if one is in check
	 * @return <b>true</b>: One is in check<br>
	 *         <b>false</b>: No one is in check
	 */
	public boolean getCheck() {
		return check;
	}
	
	/**
	 * Get if one has been checkmated
	 * @return <b>true</b>: One has been checkmated<br>
	 *         <b>false</b>: No one has been checkmated
	 */
	public boolean getCheckmate() {
		return checkmate;
	}
	
	/**
	 * Get the piece which can be en passant
	 * @return The piece which can be en passent
	 */
	public Piece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	/**
	 * Get the piece which will be promoted
	 * @return The piece which will be promoted
	 */
	public Piece getPromoted() {
		return promoted;
	}
	
	/**
	 * Get the pieces on the board in a 2d-array form
	 * @return The pieces on the board
	 */
	public Piece[][] getPieces() {
		Piece[][] mat = new Piece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = board.piece(i, j);
			}
		}
		return mat;
	}
	
	/**
	 * Get the possible moves of the piece from the position in a 2d-array form
	 * @param position The position on the board
	 * @return A list of possible moves
	 */
	public boolean[][] possibleMoves(Position position) {
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	/**
	 * Perform a chess move
	 * @param from The source position
	 * @param to The target position
	 * @return <b>Piece</b>: The captured Piece<br>
	 *         <b>null</b>: No piece is captured
	 */
	public Piece performChessMove(Position from, Position to) {

		validateSourcePosition(from);
		validateTargetPosition(from, to);
		Piece capturedPiece = makeMove(from, to);
		
		if (testCheck(currentPlayer)) {
			undoMove(from, to, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		Piece movedPiece = board.piece(to);
		
		// special move pawn promotion
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && to.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && to.getRow() == 7)) {
				promoted = board.piece(to);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckmate(opponent(currentPlayer))) {
			checkmate = true;
		}
		else {
			nextTurn();
		}
		
		// special move en passant
		if (movedPiece instanceof Pawn && (to.getRow() == from.getRow() - 2 || to.getRow() == from.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return capturedPiece;
	}
	
	/**
	 * Replace a promoted piece
	 * @param type The new type of the piece
	 * @return The new piece
	 */
	public Piece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new ChessException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			return promoted;
		}
		
		Position pos = promoted.getPosition();
		Piece p = board.removePiece(pos);
		piecesOnBoard.remove(p);
		
		Piece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnBoard.add(newPiece);
		
		return newPiece;
	}
	
	/**
	 * Convert a string to a piece for promotion
	 * @param type The type of the new piece
	 * @param color The color of the new piece
	 * @return The new piece
	 */
	private Piece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color, this);
		if (type.equals("N")) return new Knight(board, color, this);
		if (type.equals("R")) return new Rook(board, color, this);
		if (type.equals("Q")) return new Queen(board, color, this);
		return null;
	}
	
	/**
	 * Make a chess move
	 * @param from The source position
	 * @param to The target position
	 * @return <b>Piece</b>: The captured piece<br>
	 *         <b>null</b>: No piece is captured
	 */
	private Piece makeMove(Position from, Position to) {
		Piece p = board.removePiece(from);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(to);
		board.placePiece(p, to);
		
		if (capturedPiece != null) {
			piecesOnBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// special move castling kingside
		if (p instanceof King && to.getColumn() == from.getColumn() + 2) {
			Position source = new Position(from.getRow(), from.getColumn() + 3);
			Position target = new Position(from.getRow(), from.getColumn() + 1);
			Piece rook = board.removePiece(source);
			board.placePiece(rook, target);
			rook.increaseMoveCount();
		}
		
		// special move castling queenside
		if (p instanceof King && to.getColumn() == from.getColumn() - 2) {
			Position source = new Position(from.getRow(), from.getColumn() - 4);
			Position target = new Position(from.getRow(), from.getColumn() - 1);
			Piece rook = board.removePiece(source);
			board.placePiece(rook, target);
			rook.increaseMoveCount();
		}

		// special move en passant
		if (p instanceof Pawn) {
			if (from.getColumn() != to.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(to.getRow() + 1, to.getColumn());
				}
				else {
					pawnPosition = new Position(to.getRow() - 1, to.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	
	/**
	 * Undo a move
	 * @param from The source position
	 * @param to The target position
	 * @param capturedPiece The captured piece
	 */
	private void undoMove(Position from, Position to, Piece capturedPiece) {
		Piece p = board.removePiece(to);
		p.decreaseMoveCount();
		board.placePiece(p, from);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, to);
			capturedPieces.remove(capturedPiece);
			piecesOnBoard.add(capturedPiece);
		}

		// special move castling kingside
		if (p instanceof King && to.getColumn() == from.getColumn() + 2) {
			Position source = new Position(from.getRow(), from.getColumn() + 3);
			Position target = new Position(from.getRow(), from.getColumn() + 1);
			Piece rook = board.removePiece(target);
			board.placePiece(rook, source);
			rook.decreaseMoveCount();
		}
		
		// special move castling queenside
		if (p instanceof King && to.getColumn() == from.getColumn() - 2) {
			Position source = new Position(from.getRow(), from.getColumn() - 4);
			Position target = new Position(from.getRow(), from.getColumn() - 1);
			Piece rook = board.removePiece(target);
			board.placePiece(rook, source);
			rook.decreaseMoveCount();
		}

		// special move en passant
		if (p instanceof Pawn) {
			if (from.getColumn() != to.getColumn() && capturedPiece == enPassantVulnerable) {
				Piece pawn = board.removePiece(to);
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, to.getColumn());
				}
				else {
					pawnPosition = new Position(4, to.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	/**
	 * Validate the source position
	 * @param position The source position
	 */
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != board.piece(position).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	/**
	 * Validate the target position
	 * @param source The source position
	 * @param target The target position
	 */
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	/**
	 * Next turn
	 */
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	/**
	 * Get the color's opponent's color
	 * @param color The color
	 * @return The opponent's color
	 */
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	/**
	 * Get the king of the color
	 * @param color The color
	 * @return The king
	 */
	private Piece king(Color color) {
		List<Piece> list = piecesOnBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return p;
			}
		}

		throw new ChessException("There is no " + color + " king on the board");
	}
	
	/**
	 * Check if the color's player is in check
	 * @param color The color of the player
	 * @return <b>true</b>: The player is in check<br>
	 *         <b>false</b>: The player is not in check
	 */
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getPosition();
		List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> x.getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.positionsAttacked();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the color's player is checkmated
	 * @param color The color of the player
	 * @return <b>true</b>: The player is checkmated<br>
	 *         <b>false</b>: The player is not checkmated
	 */
	private boolean testCheckmate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = p.getPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Get all attacked positions in a 2d-array form
	 * @param color The color which is attacked
	 */
	public boolean[][] getAttackedPositions(Color color) {
		boolean[][] ret = new boolean[board.getRows()][board.getColumns()];
		List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> x.getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat;
			mat = p.positionsAttacked();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					ret[i][j] = ret[i][j] || mat[i][j];
				}
			}
		}
		return ret;
	}
	
	/**
	 * Test if the move is valid (won't get the king in check)
	 * @param from
	 * @param to
	 * @param color
	 * @return <b>true</b>: The move is valid<br>
	 *         <b>false</b>: The move is invalid
	 * @TODO wip
	 */
	public boolean testMove(Position from, Position to, Color color) {
		Piece p = board.removePiece(from);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(to);
		board.placePiece(p, to);
		
		if (capturedPiece != null) {
			piecesOnBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// special move castling kingside
		if (p instanceof King && to.getColumn() == from.getColumn() + 2) {
			Position source = new Position(from.getRow(), from.getColumn() + 3);
			Position target = new Position(from.getRow(), from.getColumn() + 1);
			Piece rook = board.removePiece(source);
			board.placePiece(rook, target);
			rook.increaseMoveCount();
		}
		
		// special move castling queenside
		if (p instanceof King && to.getColumn() == from.getColumn() - 2) {
			Position source = new Position(from.getRow(), from.getColumn() - 4);
			Position target = new Position(from.getRow(), from.getColumn() - 1);
			Piece rook = board.removePiece(source);
			board.placePiece(rook, target);
			rook.increaseMoveCount();
		}

		// special move en passant
		if (p instanceof Pawn) {
			if (from.getColumn() != to.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(to.getRow() + 1, to.getColumn());
				}
				else {
					pawnPosition = new Position(to.getRow() - 1, to.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnBoard.remove(capturedPiece);
			}
		}
		
		boolean isInCheck = testCheck(color);
		
		undoMove(from, to, capturedPiece);
		
		return !isInCheck;
	}
	
	/**
	 * Place a new piece
	 * @param column The column on the piece
	 * @param row The row of the piece
	 * @param piece The new piece
	 */
	private void placeNewPiece(char column, int row, Piece piece) {
		board.placePiece(piece, new Position(column, row));
		piecesOnBoard.add(piece);
	}
	
	/**
	 * Initialize the board
	 */
	private void initBoard() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE, this));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE, this));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE, this));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE, this));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE, this));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE, this));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE, this));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK, this));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK, this));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK, this));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK, this));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK, this));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK, this));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
	
}
