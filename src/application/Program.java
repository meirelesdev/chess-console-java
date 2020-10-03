package application;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Qual peça deseja mover?  ");
				ChessPosition source = UI.readChessPosition(sc);
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Onde deseja colocar esta peça?  ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				if(chessMatch.getPromoted() != null) {
					
					System.out.println("Parabens!!");
					System.out.println("Escolha uma peça para trocar por este peão");
					System.out.println("[R]ainha");
					System.out.println("[T]orre");
					System.out.println("[C]avalo");
					System.out.println("[B]ispo");
					
					String type = sc.nextLine().toUpperCase();
					
					while( !type.equals("R") && !type.equals("T") && !type.equals("C") && !type.equals("B")) {
						System.out.println("Valor invalido tente novamente:");
						System.out.println("[R]ainha");
						System.out.println("[T]orre");
						System.out.println("[C]avalo");
						System.out.println("[B]ispo");
						type = sc.nextLine().toUpperCase();
					}
					
					
					chessMatch.replacePromotedPiece(type);
				}
					
			} catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
				
			} catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch(InvalidParameterException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}
