package com.github.knlao.chesslib.chess;

/**
 * The board of chess
 * @author kimilao
 * @version 1.0
 */
public class Board {
	
	/**
	 * The count of rows on the board
	 */
	private final int rows = 8;
	
	/**
	 * The count of columns on the board
	 */
	private final int columns = 8;
	
	/**
	 * The board which store the pieces
	 */
	private Piece[][] pieces = new Piece[rows][columns];

	/**
	 * Get how many rows
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Get how many columns
	 * @return columns
	 */
	public int getColumns() {
		return columns;
	}
	
	/**
	 * Get the piece from the board
	 * @param row The row of the piece
	 * @param column The column of the piece
	 * @return Piece
	 */
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new ChessException("Position is not on the board");
		}
		return pieces[row][column];
	}
	
	/**
	 * Get the piece from the board
	 * @param position The position of the piece
	 * @return Piece
	 */
	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new ChessException("Position is not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	/**
	 * Place a piece on the position
	 * @param piece The piece to be placed
	 * @param position The position
	 */
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new ChessException("There is already a piece on the position");
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.setPosition(position);
	}
	
	/**
	 * Remove the piece on the position
	 * @param position The position
	 * @return <b>Piece</b>: The removed piece<br>
	 *         <b>null</b>: There is no piece on that position
	 */
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new ChessException("Position is not on the board");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece tmp = piece(position);
		tmp.setPosition(null);
		pieces[position.getRow()][position.getColumn()] = null;
		return tmp;
	}
	
	/**
	 * Check if the position is valid
	 * @param row The row
	 * @param column The position
	 * @return <b>true</b>: The position is valid<br>
	 *         <b>false</b>: The position is not valid
	 */
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	/**
	 * Check if the position is valid
	 * @param position The position
	 * @return <b>true</b>: The position is valid<br>
	 *         <b>false</b>: The position is not valid
	 */
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	/**
	 * Check if there is a piece on the position
	 * @param position The position to be checked
	 * @return <b>true</b>: The position has a piece<br>
	 *         <b>false</b>: The position doesn't have a piece
	 */
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new ChessException("Position is not on the board");
		}
		return piece(position) != null;
	}
	
}
