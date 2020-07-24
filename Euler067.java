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

	/* Given jagged array triangle, return maximum path sum from top to bottom. */
	public static int solution(int[][] triangle) {
		int n = triangle.length;
		int[][] dp = new int[n][];
		dp[0] = new int[] {triangle[0][0]};
		dp[1] = new int[] {triangle[1][0]+triangle[0][0], triangle[1][1]+triangle[0][0]};
		/* For every (0-based) row except bottom row, starting from 3rd row */
		for (int row = 2; row < n-1; row++) {
			dp[row] = new int[row+1];
			int[] tRow = triangle[row];
			/* There's only one path to get to the left-most and right-most edges of triangle */
			dp[row][0] = tRow[0] + dp[row-1][0];
			dp[row][row] = tRow[row] + dp[row-1][row-1];
			/* For each of the middle elements in row, choose max path to that point */
			for (int i = 1; i < row; i++) {
				dp[row][i] = Math.max(dp[row-1][i],dp[row-1][i-1]) + tRow[i];
			}
		}
		/* Find max path to bottom row. */
		int[] lastRow = triangle[n-1];
		int ans = lastRow[0] + dp[n-2][0];
		for (int i = 1; i < n-1; i++) {
			ans = Math.max(ans, Math.max(dp[n-2][i],dp[n-2][i-1])+lastRow[i]);
		}
		return Math.max(ans, lastRow[n-1]+dp[n-2][n-2]);
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			int[][] triangle = new int[n][];
			for (int row = 1; row <= n; row++) {
				String[] inputs = s.nextLine().split(" ");
				triangle[row-1] = new int[row];
				for (int i = 0; i < row; i++) {
					triangle[row-1][i] = Integer.parseInt(inputs[i]);
				}
			}
			System.out.println(solution(triangle));
		}
		s.close();
	}
}
