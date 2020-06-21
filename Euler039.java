/* -------- SOLVED -------- */

/* If p is the perimeter of a right angle triangle with integral length sides, {a, b, c}, there are exactly 
 * three solutions for p = 120:
 * 
 * {20,48,52} , {24,45,51} , {30,40,50}
 * 
 * For which value of p <= N is the number of solutions maximized? If there are multiple values, print the
 * smallest.
 * 
 * INPUTS: 1 <= T <= 10^5, 12 <= N <= 5 x 10^6 */

import java.util.Scanner;

public class Euler039 {
	
	/* Thoughts/approach: Probably something like Euclid's Pythagorean triple generation formula?
	 * 
	 * Primitive Pythagorean triple iff a = k*(m^2-n^2), b = k*(2mn), c = 2*(m^2+n^2), where gcd(m,n)=1 and
	 * m > n > 0.
	 * 
	 * If one of m/n is even, we get unique (non-scaled) Pythagorean triples. So I'm thinking nested loops,
	 * one over evens and one over odds, generating base primitive triples, and then incrementing multiples
	 * of their perimeters. We'll also need an array to store solutions p for each N, as the inputs can be
	 * pretty large so this all has to be pre-calculated. */
	
	static final int MAX_N = 5000000;

	/* numSolutions[N] holds the number of primitive right triangles with perimeter equal to N. */
	static int[] numSolutions;

	/* ans[N] holds p<=N such that p has the most solutions. */
	static int[] ans;
	
	public static int gcd(int m, int n) {
		if (m == n) return m;
		if (m == 0) return n;
		if (n == 0) return m;
		return gcd(n,m%n);
	}
	
	public static void main(String[] args) {
		numSolutions = new int[MAX_N+1];
		/* Iterate m and n over all combinations of coprime even/odd pairs. Since c=m^2+n^2, we need only
		 * iterate up to sqrt(MAX_N) (and even that's overkill).  */
		for (int m = 2; m*m <= MAX_N; m+=2) {
			for (int n = 1; n*n+m*m <= MAX_N; n+=2) {
				if (gcd(m,n) == 1) {
					int a = Math.abs(m*m-n*n);
					int b = 2*m*n;
					int c = m*m+n*n;
					int p = a+b+c;
					if (p > MAX_N) ;
					else {
						for (int j = p; j <= MAX_N; j+=p) {
							numSolutions[j]++;
						}
					}
				}
			}
		}
		
		ans = new int[MAX_N+1];
		int max = 0;
		for (int i = 0; i <= MAX_N; i++) {
			if (numSolutions[i] > numSolutions[max]) {
				max = i;
			}
			ans[i] = max;
		}
		
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			System.out.println(ans[n]);
		}
		s.close();
	}
}
