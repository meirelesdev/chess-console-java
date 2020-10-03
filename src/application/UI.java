package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[45m"; // CODIGO COR PURPLE
//	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m"; // CODIGO COR VERDE
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));

			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 at h8.");
		}

	}

	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		printCapturedPiece(captured);
		System.out.println(ANSI_RED_BACKGROUND + "TOTAL DE TURNOS: " + chessMatch.getTurn() + ANSI_RESET);
		if (!chessMatch.getCheckMate()) {
			if (chessMatch.getCurrentPlayer() == Color.WHITE) {
				System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "Aguardando movimento da peça "
						+ chessMatch.getCurrentPlayer() + "..." + ANSI_RESET);
			} else {
				System.out.println(ANSI_BLACK_BACKGROUND + ANSI_WHITE + "Aguardando movimento da peça "
						+ chessMatch.getCurrentPlayer() + "..." + ANSI_RESET);
			}

			if (chessMatch.getCheck()) {
				System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_RED + "Rei " + chessMatch.getCurrentPlayer()
						+ " in CHECK!" + ANSI_RESET);
			}
		} else {
			System.out.println(ANSI_WHITE_BACKGROUND + ANSI_GREEN + "CHECKMATE!!!");
			System.out.println("Vencedor "+ chessMatch.getCurrentPlayer() + ANSI_RESET);
		}

	}

	public static void printBoard(ChessPiece[][] piece) {
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|                  Jogo de Xadrez                   |"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);

		for (int i = 0; i < piece.length; i++) {
			System.out.print(ANSI_GREEN_BACKGROUND + "| " + (8 - i) + " |" + ANSI_RESET + " ");

			for (int j = 0; j < piece.length; j++) {
				printPiece(piece[i][j], false);
			}
			System.out.println();
		}
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|   |  a  |  b  |  c  |  d  |  e  |  f  |  g  |  h  |"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);
	}

	public static void printBoard(ChessPiece[][] piece, boolean[][] possibleMoves) {

		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|                  Jogo de Xadrez                   |"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);

		for (int i = 0; i < piece.length; i++) {
			System.out.print(ANSI_GREEN_BACKGROUND + "| " + (8 - i) + " |" + ANSI_RESET + " ");

			for (int j = 0; j < piece.length; j++) {
				printPiece(piece[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|   |  a  |  b  |  c  |  d  |  e  |  f  |  g  |  h  |"
				+ ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "-----------------------------------------------------"
				+ ANSI_RESET);
	}

	private static void printPiece(ChessPiece piece, boolean moves) {

		if (moves) {
			if (piece == null) {
				System.out.print(ANSI_BLUE_BACKGROUND + " - " + ANSI_RESET + " " + ANSI_WHITE + ANSI_GREEN_BACKGROUND
						+ "|" + ANSI_RESET);
			} else {
				if (piece.getColor() == Color.WHITE) {
					System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK + " " + piece + " " + ANSI_RESET + " "
							+ ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|" + ANSI_RESET);
				} else {
					System.out.print(ANSI_BLUE_BACKGROUND + ANSI_WHITE + " " + piece + " " + ANSI_RESET + " "
							+ ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|" + ANSI_RESET);
				}
			}
		} else {
			if (piece == null) {
				System.out.print(" -  " + ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|" + ANSI_RESET);
			} else {
				if (piece.getColor() == Color.WHITE) {
					System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + " " + piece + " " + ANSI_RESET + " "
							+ ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|" + ANSI_RESET);
				} else {
					System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE + " " + piece + " " + ANSI_RESET + " "
							+ ANSI_GREEN_BACKGROUND + ANSI_WHITE + "|" + ANSI_RESET);
				}
			}
		}

		System.out.print(" ");
	}

	private static void printCapturedPiece(List<ChessPiece> captured) {

		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE)
				.collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK)
				.collect(Collectors.toList());
		System.out.print(ANSI_GREEN_BACKGROUND + ANSI_WHITE + "                  PEÇAS CAPTURADAS:                  "
				+ ANSI_RESET);
		System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK);
		System.out.print("BRANCA: ");
		System.out.print(Arrays.toString(white.toArray()) + " ");
		System.out.println(ANSI_RESET);
		System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE);
		System.out.print("PRETO:  ");
		System.out.println(Arrays.toString(black.toArray()) + " ");
		System.out.print(ANSI_RESET);
	}
}
