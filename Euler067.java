/* -------- UNSOLVED -------- */

/* By starting at the top of the triangle below and moving to adjacent numbers on the row below, the 
 * maximum total from top to bottom is 23:
 *
 *		3
 *	   7 4
 *	  2 4 6
 *	 8 5 9 3
 *
 *
 * That is, 3+7+4+9 = 23.
 *
 * Find the maximum total from top to bottom of the triangle given in input. 
 *
 * INPUTS: 1 <= T <= 10, 1 <= N <= 100. For each T, first line contains N, followed by N lines where i'th
 * line contains i space-separated integers. Integers in triangle between 0 and 100, inclusive. */

import java.util.Scanner;

public class Euler067 {
	
	/* Thoughts/approach: DP. To find max sum from top to i'th row, we need all max sums from top to
	 * (i-1)th row. So we keep an array memoizing max sums in each spot on the triangle. */

	public static int solution(int[][] triangle) {
		int n = triangle.length;
		for (int row = 0; row < n; row++) {
			
		}
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			int[][] triangle = new int[n][];
			for (int row = 1; row <= n; row++) {
				String[] inputs = s.nextLine().split(" ");
				triangle[row] = new int[row];
				for (int i = 0; i < row; i++) {
					triangle[row][i] = Integer.parseInt(inputs[i]);
				}
			}
			System.out.println(ans);
		}
		s.close();
	}
}
