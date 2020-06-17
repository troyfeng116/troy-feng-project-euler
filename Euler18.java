/* -------- SOLVED -------- */

/* By starting at the top of the triangle below and moving to adjacent numbers on the row below, the maximum 
 * total from top to bottom is 24. The path is denoted by numbers in bold.
 * 
 * 		3
 * 	   7 4
 *    2 4 6
 *   8 5 9 3
 *   
 * That is, path 3+7+4+9 = 23.
 * 
 * Find the maximum total from top to bottom of the triangle given in input. 
 * 
 * INPUTS: 1 <= T <= 10, 1 <= N <= 15, 0 <= entries < 100 */

import java.util.Scanner;

public class Euler18 {
	
	/* Pascal's Triangle-esque. Track max sum in each spot as we go. Jagged array to store. */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			/* Jagged array, such that pascal[i] contains an array of length i+1. */
			int[][] pascal = new int[n][];
			int tip = Integer.parseInt(s.nextLine());
			if (n == 1) {
				System.out.println(tip);
				continue;
			}
			pascal[0] = new int[] {tip};
			for (int i = 1; i < n-1; i++) {
				String[] rowInputs = s.nextLine().split(" ");
				pascal[i] = new int[i+1];
				pascal[i][0] = pascal[i-1][0] + Integer.parseInt(rowInputs[0]);
				for (int j = 1; j < i; j++) {
					int next = Integer.parseInt(rowInputs[j]);
					pascal[i][j] = Math.max(pascal[i-1][j-1] + next, pascal[i-1][j] + next);
				}
				pascal[i][i] = pascal[i-1][i-1] + Integer.parseInt(rowInputs[i]);
			}
			pascal[n-1] = new int[n];
			String[] lastRow = s.nextLine().split(" ");
			int max = Integer.parseInt(lastRow[0]) + pascal[n-2][0];
			for (int i = 1; i < n-1; i++) {
				int next = Integer.parseInt(lastRow[i]);
				max = Math.max(max, Math.max(pascal[n-2][i-1] + next, pascal[n-2][i] + next));
			}
			max = Math.max(max, pascal[n-2][n-2] + Integer.parseInt(lastRow[n-1]));
			System.out.println(max);
		}
		s.close();
	}
}
