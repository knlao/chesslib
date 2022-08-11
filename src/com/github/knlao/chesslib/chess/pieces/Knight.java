package com.github.knlao.chesslib.chess.pieces;

import com.github.knlao.chesslib.chess.Board;
import com.github.knlao.chesslib.chess.Color;
import com.github.knlao.chesslib.chess.Match;
import com.github.knlao.chesslib.chess.Piece;
import com.github.knlao.chesslib.chess.Position;

/**
 * The knight
 * @author kimilao
 * @version 1.0
 */
public class Knight extends Piece {

	/**
	 * The match it belongs
	 */
	private Match match;
	
	/**
	 * Construct a knight
	 * @param board The board it belongs
	 * @param color The color of it
	 */
	public Knight(Board board, Color color, Match match) {
		super(board, color);
		this.match = match;
	}
	
	/**
	 * Check if the knight can be in the position
	 * @param position The position to be checked
	 * @return <b>true</b>: The knight can be in the position<br>
	 *         <b>false</b>: The knight cannot be in the position
	 */
	private boolean canMove(Position position) {
		Piece p = getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	@Override
	public boolean[][] positionsAttacked() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		
		p.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		p.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "N";
	}

}
