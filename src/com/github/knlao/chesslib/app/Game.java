package com.github.knlao.chesslib.app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.github.knlao.chesslib.chess.ChessException;
import com.github.knlao.chesslib.chess.Match;
import com.github.knlao.chesslib.chess.Piece;
import com.github.knlao.chesslib.chess.Position;

/**
 * The game of chess
 * @author kimilao
 * @version 1.0
 */
public class Game {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Match match = new Match();
		List<Piece> captured = new ArrayList<>();
		
		while (!match.getCheckmate()) {

			try {
				UI.clearScreen();
				UI.printMatch(match, captured);
				System.out.println();
				System.out.print("Source position: ");
				Position source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = match.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(match.getPieces(), possibleMoves);
				System.out.println();
				System.out.print("Target position: ");
				Position target = UI.readChessPosition(sc);
				
				Piece capturedPiece = match.performChessMove(source, target);
				
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (match.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
						System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					match.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		System.out.println("CHECKMATE!!!");
	}
	
}
