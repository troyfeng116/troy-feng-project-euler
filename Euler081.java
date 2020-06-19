/* -------- SOLVED -------- */

/* Find minimal sum of N x N array from top left to bottom right, with only R or D moves. 
 *
 * INPUTS: 1 <= N <= 1000, 1 <= entries <= 10^9 */

import java.util.Scanner;
	
public class Euler081 {
	
/* Approach: Dynamic programming with one O(n^2) pass. */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		long[][] mins = new long[n][n];
		
		/* Read first row, storing sums. */
		String[] inputs1 = s.nextLine().split(" ");
		mins[0][0] = Integer.parseInt(inputs1[0]);
		for (int i = 1; i < n; i++)
			mins[0][i] = Integer.parseInt(inputs1[i]) + mins[0][i-1];
		
		/* Read subsequent rows, tracking minimum sum. */
		for (int i = 1; i < n; i++) {
			String[] inputs2 = s.nextLine().split(" ");
			mins[i][0] = mins[i-1][0] + Integer.parseInt(inputs2[0]);
			for (int j = 1; j < n; j++) {
				int entry = Integer.parseInt(inputs2[j]);
				mins[i][j] = mins[i-1][j]<=mins[i][j-1] ? mins[i-1][j]+entry : mins[i][j-1]+entry;
			}
		}
		System.out.println(mins[n-1][n-1]);
		s.close();
	}
	
}
