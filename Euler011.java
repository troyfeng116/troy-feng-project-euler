/* -------- SOLVED -------- */

/* Given 20x20 grid of integers s.t. 0 <= int <= 100, what is the greagest product of four adjacent numbers in 
 * the same direction (up, down, left, right, or diagonally) in the 20x20 grid?
 * 
 * Input: 20 lines each with 20 integers. */

import java.util.Scanner;

public class Euler11 {
	
	/* Brute force: not that many to try right...? 16*20 up/down to check, 16*20 L/R to check, 
	 * 1+2+...+16+15+...+1 times two diagonal to check. */

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int[][] arr = new int[20][20];
		for (int i = 0; i < 20; i++) {
			String[] inputs = s.nextLine().split(" ");
			for (int j = 0; j < 20; j++) {
				arr[i][j] = Integer.parseInt(inputs[j]);
			}
		}
		
		long ans = 0;
		long diagonal1;
		long diagonal2;
		long vertical;
		long horizontal;
		for (int row = 0; row < 20; row++) {
			for (int col = 0; col < 20; col++) {
				/* Diagonal1 (top left to bottom right) */
				if (row < 17 && col < 17) {
					diagonal1 = arr[row][col] * arr[row+1][col+1] * arr[row+2][col+2] * arr[row+3][col+3];
					if (diagonal1 > ans) ans = diagonal1;
				}
				/* Diagonal2 (bottom left to top right) */
				if (row > 2 && col < 17) {
					diagonal2 = arr[row][col] * arr[row-1][col+1] * arr[row-2][col+2] * arr[row-3][col+3];
					if (diagonal2 > ans) ans = diagonal2;
				}
				/* Vertical (top to bottom). */
				if (row < 17) {
					vertical = arr[row][col] * arr[row+1][col] * arr[row+2][col] * arr[row+3][col];
					if (vertical > ans) ans = vertical;
				}
				/* Horizontal (left to right). */
				if (col < 17) {
					horizontal = arr[row][col] * arr[row][col+1] * arr[row][col+2] * arr[row][col+3];
					if (horizontal > ans) ans = horizontal;
				}
			}
		}
		
		System.out.println(ans);
		s.close();
	}

}
