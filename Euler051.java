/* -------- UNSOLVED -------- */

/* By replacing the 1st digit of *3, it turns out that six of the nine possible values: 13, 23, 43, 53, 73, 
 * and 83 are all prime.
 * 
 * By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number is the first example 
 * having seven primes among the ten generated numbers, yielding the family: 56003, 56113, 56333, 56443, 56663, 
 * 56773, and 56993. Consequently 56003, being the first member of this family, is the smallest prime with this 
 * property.
 * 
 * Find the smallest N-digit prime which, by replacing K digits of the number (not necessarily adjacent digits) 
 * with the same digit, is part of an L prime value family. 
 * 
 * Note 1: It is guaranteed that solution does exist.
 * Note 2: Leading zeros should not be considered.
 * Note 3: If there are several solutions, choose the "lexicographically" smallest one (one sequence is 
 * considered "lexicographically" smaller than another if its first element which does not match the corresponding 
 * element in another sequence is smaller) 
 * 
 * INPUTS: 2 <= N <= 7, 1 <= K <= N, 1 <= L <= 8, space-separated N K L
 * OUTPUT the L members of the family */

import java.util.Scanner;

public class Euler051 {
	
	/* Thoughts/approach: first we can sieve primes up to 10^7 (to cover all seven-digit numbers). 
	 * Let N=7, K=3, L be fixed for example. One logical way to approach the problem is to loop through all
	 * N-K or four-digit integers (0000 -> 9999), and try all ways to place 3 stars between them. The order
	 * (for lexicographic order) would be ***abcd -> abcd***. If a or ab or abc or abcd is zero, then that
	 * portion can't go in the front. And if d is even, then d can't be in the back. Ex. for abcd=2153, we
	 * would try starting from ***2153 since 1112153 would be smallest starting point in that family. Then
	 * after trying all combinations with a * in the first place, we would move on to 2***153, etc. For each
	 * abcd, there are maximum CHOOSE(7,3) arrangements of stars to try. We can test each combination in
	 * 10 O(1) steps (ex. for ***2153, we would increment by 1110000 in testing for primality).
	 *
	 * abcd=0000 is a tricky edge case (I think a, ab, abc, abcd = 0are all tricky for preserving 
	 * lexicographically ordered checking). Cases where a=1 are also tricky, because all stars in front don't
	 * give the lexicographically smallest. 
	 *
	 * I'm thinking of writing a function that takes N and K and returns an array of boolean arrays that
	 * represent arrangements of digits and stars. The array would contain N choose K arrays of length N, each
	 * with K trues distributed throughout representing stars. Worst case for number of combinations would
	 * be 7 choose 3 = 35 combinations, each of which must worst case tested with each of 0000 through 9999 for
	 * 10000 total four-digit possibilities, each of which in turn has at most 10 primality tests, for worst 
	 * case 3.5 million O(1) operations. Generating the base 7-digit number to begin with is worst case one loop
	 * through a length 7 boolean array. In all, this shoudn't time out. */

	final static int MAX = 10000000;
	static int[] tenToThe;
	static boolean[] composite;

	/* Sieve primes up to MAX. */
	public static void sieve() {
		composite = new boolean[MAX];
		for (int i = 2; i < MAX; i++) {
			if (!composite[i] && i <= Math.sqrt(MAX)) {
				for (int j = i*i; j < MAX; j+=i) {
					composite[j] = true;
				}
			}
		}
	}

	/* Fill powers of ten (up to 10^6 for 7-digit numbers). */
	public static void fillTenToThe() {
		tenToThe = new int[7];
		tenToThe[0] = 1;
		for (int i = 1; i < 7; i++) {
			tenToThe[i] = tenToThe[i-1] * 10;
		}
	}

	/* Generate all ways to arrange K trues and N-K falses. T corresponds to star digit. */
	public static boolean[][] generateStarArrangements(int N, int K) {
		boolean[][] ans = new boolean[choose(N,K)][N];
		for (int i = 0; i < K; i++) ans[0][i] = true;
		for (int i = 1; i < ans.length; i++) {
			nextCombination(ans[i-1],ans[i]);
		}
		return ans;
	}

	/* Given that arr1 holds some combination of T and F, stores next reverse lexicographical permutation of
	 * T and F (T=1, F=0) in arr2. Pre-condition: arr2 is all F. ex. arr1 = 1110000 -> arr2 = 1101000, and
	 * arr1 = 001011 -> arr2 = 000111. */
	public static void nextCombination(boolean[] arr1, boolean[] arr2) {
		int n = arr1.length;
		int k = n-2;
		while (k >= 0 && !(arr1[k] && !arr1[k+1])) k--;
		if (k < 0) return;
		arr2[k] = false;
		arr2[k+1] = true;
		/* Copy from 1->k to arr2. */
		for (int i = 0; i < k; i++) {
			arr2[i] = arr1[i];
		}
		/* Copy reverse arr1 from k+2->n to arr2. */
		for (int i = k+2; i < n; i++) {
			arr2[i] = arr1[n-i+k+1];
		}
	}

	/* Return N choose K, N >= K. */
	public static int choose(int N, int K) {
		int ans = 1;
		for (int i = Math.max(N-K,K)+1; i <= N; i++) ans *= i;
		for (int i = 2; i <= Math.min(K,N-K); i++) ans /= i;
		return ans;
	}

	/* Once smallest lexicographic sequence of L primes is found, prints in format specified. */
	public static void solution(int N, int K, int L) {
		boolean[][] combs = generateStarArrangements(N,K);
	}
	
	public static void main(String[] args) {
		sieve();
		fillTenToThe();
		boolean[][] test = generateStarArrangements(7,3);
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < test[i].length; j++) {
				if (test[i][j]) System.out.print(1 + " ");
				else System.out.print(0 + " ");
			}
			System.out.println();
		}

		System.exit(0);

		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int k = Integer.parseInt(inputs[1]);
		int l = Integer.parseInt(inputs[2]);
		s.close();
	}
}
