package com.github.knlao.chesslib.chess;

/**
 * Custom exception about chess
 * @author kimilao
 * @version 1.0
 */
public class ChessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Raise a chess exception
	 * @param msg error message
	 */
	public ChessException(String msg) {
		super(msg);
	}
}
