package com.github.knlao.chesslib.chess.pieces;

import com.github.knlao.chesslib.chess.Board;
import com.github.knlao.chesslib.chess.Color;
import com.github.knlao.chesslib.chess.Match;
import com.github.knlao.chesslib.chess.Piece;
import com.github.knlao.chesslib.chess.Position;

/**
 * The king
 * @author kimilao
 * @version 1.0
 */
public class King extends Piece {
	
	/**
	 * The match it belongs
	 */
	private Match match;
	
	/**
	 * Construct a king
	 * @param board The board it belongs
	 * @param color The color of it
	 * @param match The match of chess
	 */
	public King(Board board, Color color, Match match) {
		super(board, color);
		this.match = match;
	}
	
	/**
	 * Check if the king can be in the position
	 * @param position The position to be checked
	 * @return <b>true</b>: The king can be in the position<br>
	 *         <b>false</b>: The king cannot be in the position
	 */
	private boolean canMove(Position position) {
		Piece p = getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	/**
	 * Check if the rook can join the castling
	 * @param position The position of the rook
	 * @return <b>true</b>: The rook can join the castling<br>
	 *         <b>false</b>: The rook cannot join the castling
	 */
	private boolean testRookCastling(Position position) {
		Piece p =  getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	
	public boolean[][] positionsAttacked() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		boolean[][] attackedPositions = match.getAttackedPositions(getColor());

		// special move castling
		if (getMoveCount() == 0 && !match.getCheck()) {
			// kingside
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && !attackedPositions[p1.getRow()][p1.getColumn()] && !attackedPositions[p2.getRow()][p2.getColumn()]) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// queenside
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null && !attackedPositions[p1.getRow()][p1.getColumn()] && !attackedPositions[p2.getRow()][p2.getColumn()]) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
}
