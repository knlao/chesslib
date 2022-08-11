package com.github.knlao.chesslib.chess.pieces;

import com.github.knlao.chesslib.chess.Board;
import com.github.knlao.chesslib.chess.Color;
import com.github.knlao.chesslib.chess.Match;
import com.github.knlao.chesslib.chess.Piece;
import com.github.knlao.chesslib.chess.Position;

/**
 * The pawn
 * @author kimilao
 * @version 1.0
 */
public class Pawn extends Piece {

	/**
	 * The match it belongs
	 */
	private Match match;
	
	/**
	 * Construct a pawn
	 * @param board The board it belongs
	 * @param color The color of it
	 * @param match The match of chess
	 */
	public Pawn(Board board, Color color, Match match) {
		super(board, color);
		this.match = match;
	}
	
	public boolean[][] positionsAttacked() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		
		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}			
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		else {
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}			
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}		
		}
		
		return mat;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}			
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}	
			
			// special move en passant white
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && thereIsOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
					
					if (match.testMove(getPosition(), new Position(left.getRow() - 1, left.getColumn()), getColor())) {
						mat[left.getRow() - 1][left.getColumn()] = true;
					}
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && thereIsOpponentPiece(right) && getBoard().piece(right) == match.getEnPassantVulnerable()) {
					
					if (match.testMove(getPosition(), new Position(right.getRow() - 1, right.getColumn()), getColor())) {
						mat[right.getRow() - 1][right.getColumn()] = true;
					}
				}
			}
		}
		else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}			
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				if (match.testMove(getPosition(), p, getColor())) {
					mat[p.getRow()][p.getColumn()] = true;
				}
			}
			
			// special move en passant black
			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && thereIsOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
					
					if (match.testMove(getPosition(), new Position(left.getRow() + 1, left.getColumn()), getColor())) {
						mat[left.getRow() + 1][left.getColumn()] = true;
					}
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && thereIsOpponentPiece(right) && getBoard().piece(right) == match.getEnPassantVulnerable()) {
					
					if (match.testMove(getPosition(), new Position(right.getRow() + 1, right.getColumn()), getColor())) {
						mat[right.getRow() + 1][right.getColumn()] = true;
					}
				}
			}			
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
