package com.github.knlao.chesslib.chess;

/**
 * The positions on the board
 * @author kimilao
 * @version 1.0
 */
public class Position {

	/**
	 * The row of the position
	 */
	private int row;
	
	/**
	 * The column of the position
	 */
	private int column;
	
	/**
	 * construct a position in a numeric way
	 * @param row The row of the board
	 * @param column The column of the board
	 */
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Construct a position in a formal way
	 * @param column The file of the board
	 * @param row The rank of the board
	 */
	public Position(char column, int row) {
		this.column = (int)(column - 'a');
		this.row = 8 - row;
	}
	
	/**
	 * Get the row of the board
	 * @return The row of the board
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Get the column of the board
	 * @return The column of the board
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Set the row of the board
	 * @param row The new row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Set the column of the board
	 * @param column The new column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * Set the position of the board
	 * @param row The new row
	 * @param column The new column
	 */
	public void setValues(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
}
