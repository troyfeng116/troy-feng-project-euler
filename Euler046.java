/* -------- SOLVED -------- */

/* It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a 
 * prime and twice a square.'
 * 
 * 		9 = 7 + 2*1^2
 * 		15 = 7 + 2*2^2
 * 		21 = 3 + 2*3^2
 * 		25 = 7 + 2*3^2
 * 		27 = 19 + 2*2^2
 * 		33 = 31 + 2*1^2
 * 
 * It turns out that the conjecture was false as you'll discover some values can't be represented as a sum of 
 * prime and twice a square.
 * 
 * You are given N, print the number of ways N can be represented as a sum of prime and twice a square.
 * 
 * For example, 15 can be represented in two ways as 7+2*2^2 and 13+2*1^2.
 * 
 * INPUT: 1 <= T <= 100, 9 <= N < 5x10^5, N is odd and composite */

import java.util.Scanner;

public class Euler046 {
	
	/* Thoughts/approach: Sieve up to 500000, and for each odd prime < N, check if (N-prime)/2 is a perfect 
	 * square. */
	
	static final int MAX_N = 499999;
	static boolean[] composite;
	
	/* Return true if k is a perfect square. */
	public static boolean perfectSquare(int k) {
		int sqrt = (int) Math.sqrt(k);
		return sqrt*sqrt == k;
	}
	
	/* Sieve primes up to MAX_N. */
	public static void sieve() {
		composite = new boolean[MAX_N+1];
		for (int i=2; i<=MAX_N; i++) {
			if (!composite[i] && i<=708) {
				for (int j=i*i; j<=MAX_N; j+=i) {
					composite[j] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		sieve();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0=0; t0<t; t0++) {
			int N = Integer.parseInt(s.nextLine());
			int ans = 0;
			if (perfectSquare((N-3)/2)) ans++;
			for (int k=5; k<N; k+=6) {
				if (!composite[k] && perfectSquare((N-k)/2)) ans++;
				if (k+2<N && !composite[k+2] && perfectSquare((N-k-2)/2)) ans++;
			}
			System.out.println(ans);
		}
		s.close();
	}
}
