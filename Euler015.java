/* -------- SOLVED -------- */

/* Starting in the top left corner of a 2x2 grid, and only being able to move to the right and down, there are exactly 6
 * routes to the bottom right corner.
 * 
 * How many such routes are there through a NxM grid? As number of ways can be very large, print it modulo 10^9 + 7. 
 * 
 * 1 <= T <= 10^3, 1 <= N <= 500, 1 <= M <= 500 */

import java.util.Scanner;

public class Euler015 {
	
	/* Initial thoughts/approach: Pascal's triangle?
	 * 
	 * An NxM board has (N+1) by (M+1) vertices. */
	
	/* Index n,m corresponds to (n+1,m+1) vertex of grid. So 1,1 corresponds to 1x1. And n,m correspond to n x m. */
	static long[][] np; 
	
	public static void main(String[] args) {
		np = new long[501][501];
		for (int i = 0; i < 501; i++) {
			np[0][i] = 1;
			np[i][0] = 1;
		}
		for (int i = 1; i < 501; i++) {
			for (int j = 1; j < 501; j++) {
				np[i][j] = (np[i-1][j] + np[i][j-1]) % 1000000007;
			}
		}
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int i = 0; i < t; i++) {
			String[] inputs = s.nextLine().split(" ");
			int n = Integer.parseInt(inputs[0]);
			int m = Integer.parseInt(inputs[1]);
			System.out.println(np[n][m]);
		}
		s.close();
	}
}
