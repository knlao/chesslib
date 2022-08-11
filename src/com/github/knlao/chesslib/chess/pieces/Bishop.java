package com.github.knlao.chesslib.chess.pieces;

import com.github.knlao.chesslib.chess.Board;
import com.github.knlao.chesslib.chess.Color;
import com.github.knlao.chesslib.chess.Match;
import com.github.knlao.chesslib.chess.Piece;
import com.github.knlao.chesslib.chess.Position;

/**
 * The bishop
 * @author kimilao
 * @version 1.0
 */
public class Bishop extends Piece {

	/**
	 * The match it belongs
	 */
	private Match match;
	
	/**
	 * Construct a bishop
	 * @param board The board it belongs
	 * @param color The color of it
	 */
	public Bishop(Board board, Color color, Match match) {
		super(board, color);
		this.match = match;
	}
	
	@Override
	public boolean[][] positionsAttacked() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			if (match.testMove(getPosition(), p, getColor())) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
}
