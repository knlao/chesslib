package com.github.knlao.chesslib.chess;

/**
 * The model of the piece in chess
 * @author kimilao
 * @version 1.0
 */
public abstract class Piece {
	
	/**
	 * The position of the piece in the board
	 */
	protected Position position;
	
	/**
	 * The board it belongs
	 */
	private Board board;
	
	/**
	 * The color of the piece
	 */
	private Color color;
	
	/**
	 * How many times it has moved
	 */
	private int moveCount;
	
	/**
	 * Construct a piece
	 * @param board The board it belongs
	 * @param color The color of the piece
	 */
	public Piece(Board board, Color color) {
		this.board = board;
		this.color = color;
		position = null;
	}
	
	/**
	 * Get the board it belongs
	 * @return The board it belongs
	 */
	protected Board getBoard() {
		return board;
	}
	
	/**
	 * Get the color of the piece
	 * @return The color of the piece
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Get how many times it has moved
	 * @return Move count
	 */
	public int getMoveCount() {
		return moveCount;
	}
	
	/**
	 * Increase the move count by 1
	 */
	protected void increaseMoveCount() {
		moveCount++;
	}

	
	/**
	 * Decrease the move count by 1
	 */
	protected void decreaseMoveCount() {
		moveCount--;
	}
	
	/**
	 * Get the current position of the piece
	 * @return The position of the piece
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Set the current position of the piece
	 * @param position The new position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * Check if the position has an opponent piece
	 * @param position The position to be checked
	 * @return <b>true</b>: The position has an opponent piece<br>
	 *         <b>false</b>: The position has either no piece or a same colored piece
	 */
	protected boolean thereIsOpponentPiece(Position position) {
		Piece p = getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
	/**
	 * Get a list of positions guarded by the piece in a 2d-array form,
	 * the 2d-array is like a board and each row and column has a corresponding position,
	 * the value will be true if that is guarded by the piece
	 * @return The positions guarded by the piece
	 */
	public abstract boolean[][] positionsAttacked();
	
	/**
	 * Get a list of possible moves from the piece in a 2d-array form,
	 * the 2d-array is like a board and each row and column has a corresponding position,
	 * the value will be true if that is a possible move for the piece
	 * @return The possible moves from the piece
	 */
	public abstract boolean[][] possibleMoves();

	/**
	 * Check if it can move to the position
	 * @param position The position to be checked
	 * @return <b>true</b>: It can move to the position<br>
	 *         <b>false</b>: It cannot move to the position
	 */
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	/**
	 * Check if it has any possible moves
	 * @return <b>true</b>: It has at least 1 possible move<br>
	 *         <b>false</b>: It has no possible moves
	 */
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
