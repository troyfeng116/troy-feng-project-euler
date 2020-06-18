/* -------- SOLVED -------- */

/* Su Doku (Japanese meaning number place) is the name given to a popular puzzle concept. Its origin 
 * is unclear, but credit must be attributed to Leonhard Euler who invented a similar, and much more 
 * difficult, puzzle idea called Latin Squares. The objective of Su Doku puzzles, however, is to 
 * replace the blanks (or zeros) in a 9 by 9 grid in such that each row, column, and 3 by 3 box contains 
 * each of the digits 1 to 9. Below is an example of a typical starting puzzle grid and its solution 
 * grid.
 *
 * You are given a number of Su Doku. All of them could be solved without guessing and even backtracking. 
 * Surely, you may write every solution that passes tests and fits into constraints. 
 *
 * INPUT FORMAT: 9 lines each containg 9 characters '0'-'9'. '0' means that the place is empty otherwise 
 * the place contains corresponding digit.*/

import java.util.Scanner;

public class Euler96 {
	
	/* Approach: backtracking. */
	
	/* Return true if placing guess at row,col is a legal Sudoku move. If it is
	 * legal, the move is made. */
    public static boolean isLegal(int[][] board, int guess, int row, int col) {
        /* Check if unique in row. */
        for (int c = 0; c < 9; c++)
            if (board[row][c] == guess) return false;
        /* Check if unique in col. */
        for (int r = 0; r < 9; r++) 
            if (board[r][col] == guess) return false;
        /* Check if unique in 3x3. */
        int left = col - col%3;
        int top = row - row%3;
        for (int r = top; r < top+3; r++) {
            for (int c = left; c < left + 3; c++)
                if (board[r][c] == guess) return false;
        }
        /* Since move is legal, place guess and return true. */
        board[row][col] = guess;
        return true;
        
    }

    public static boolean solve(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                /* If board at i,j hasn't been filled */
                if (board[i][j] == 0) {
                	/* For each legal guess, guess it and solve from there. */
                    for (int guess = 1; guess <= 9; guess++) {
                        if (isLegal(board, guess, i, j))
                            if (solve(board)) return true;
                    }
                    /* If none of the attempted solves work, backtrack and return false. */
                    board[i][j] = 0;
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] board = new int[9][9];
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < 9; i++) {
            String rowInput = s.nextLine();
            int row = Integer.parseInt(rowInput);
            for (int j = 8; j >= 0; j--) {
                board[i][j] = row % 10;
                row /= 10;
            }
        }
        solve(board);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                System.out.print(board[i][j]);
            System.out.println();
        }
        s.close();
    }
}
