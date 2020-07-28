/* -------- SOLVED -------- */

/* There are exactly ten ways of selecting three from five:
 *
 * 123, 124, 125, 134, 135, 145, 234, 235, 245, 345
 * 
 * In combinatorics, we use the notation, {5 C 3} = 10. In general,
 *
 * {n C r} = n! / (r! * (n-r)!)
 *
 * It is not until n=23, that a value exceeds one-million:
 * 
 * {23 C 10} = 11440066
 * 
 * How many, not necessarily distinct, values of {n C r}, for n <= N, are greater than K? 
 * 
 * INPUTS: 2 <= N <= 1000, 1 <= K <= 10^18, space-separated N K */

import java.util.Scanner;

public class Euler053 {
	
	/* Thoughts/approach: The question is asking, how many distinct pairs (n,r) with 0 <= r <= n <= N
	 * satisfy {n C r} > K? We can use basic combinatorics properties to simplify this quite a bit. First,
	 * if {n C r} > K, then {(n+1) C r} > K. This means we need not calculate massive binomial
	 * combinatorics. In addition, {n C r} = {n C (n-r)} (trivially). This symmetry will probably be
	 * useful as well, as we need only loop r up to n/2.
	 *
	 * Those familiar with binomial theorem and Pascal's triangle are probably familiar with Pascal's
	 * identity {n C r} + {n C (r+1)} = {(n+1) C (r+1)}, which can also be proved quickly from the
	 * factorial definition. So we can probably devise a Pascal's Triangle approach to generating {n C r}.
	 * Namely, if we have values of {n C r} for 0 <= r <= n/2, then we can quickly find all values of
	 * {(n+1) C r} for 0 <= r <= (n+1)/2. To memoize Pascal's Triangle, we can use a 2D array such that
	 * arr[i][j] returns {i C j}; this array can probably be triangular if we want to conserve space.
	 *
	 * Finally, as Long.MAX_VALUE is around 9x10^18, we'll be able to detect and avoid long overflow as
	 * we go. */

	/* Return count of pairs (n,r) with r <= n <= N such that {n C r} > K. */
	public static int solution(int N, long K) {
		int ans = 0;
		/* table[n][r] = {n C r} for r <= n/2. */
		long[][] table = new long[N+1][];
		/* Base cases (tip of Pascal's triangle) */
		table[0] = new long[1];
		table[0][0] = 1;
		table[1] = new long[2];
		table[1][0] = 1;
		table[1][1] = 1;
		for (int n = 2; n <= N; n++) {
			table[n] = new long[n+1];
			table[n][0] = 1;
			for (int r = 1; r <= n/2; r++) {
				/* Check if {n C r} > K while being careful about long overflow. */
				table[n][r] = K-table[n-1][r-1]>table[n-1][r] ? table[n-1][r-1]+table[n-1][r] : K+1;
				table[n][n-r] = table[n][r];
				/* Once {n C r} is greater than K, for all subsequent r'>r up to n/2, {n C r'} > K as well. */
				if (table[n][r] > K) {
					ans += 2*(n/2-r+1);
					if (n%2==0) ans--;
					/* Effectively, filling the rest of the row with K+1 suffices, as all we are concerned about
					 * is whether {n C r} is greater than K at that point. i.e. when finding {(n+1) C r}, all we
					 * need to know is that {n C r} was greater than K to conclude {(n+1) C r} is also greater. */
					for (int i = r; i <= n-r; i++) table[n][i] = K+1;
					break;
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int N = Integer.parseInt(inputs[0]);
		long K = Long.parseLong(inputs[1]);
		System.out.println(solution(N,K));
		s.close();
	}
}
