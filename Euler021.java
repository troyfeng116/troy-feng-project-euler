/* -------- SOLVED -------- */

/* Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
 * If d(a) = b and d(b) = a, where a != b, then a and b are an amicable pair and each of a and b are called 
 * amicable numbers.
 * 
 * For example, the proper divisors of 220 are 1,2,4,5,10,11,20,22,44,55, and 110 therefore d(220) = 284. The 
 * proper divisors of 284 are 1,2,4,71, and 142 so d(284) = 220.
 * 
 * Evaluate the sum of all the amicable numbers under N. 
 * 
 * INPUTS: 1 <= T <= 1000, 1 <= N <= 10^5. */

import java.util.Scanner;

public class Euler21 {
	
	/* Thoughts/approach: if we know prime factorization of N, finding sum of factors is straightforward.
	 * 
	 * Notice that if two numbers are amicable, then the sum of their divisors must be equal.
	 * 
	 * Does it make sense to store prime factorizations of all N up to 100,000 in jagged arrays?
	 * 
	 * EDIT: There's a better way to store sum of all factors... */
	
	final static int MAX_N = 100000;
	
	/* A modified Sieve of Eratosthenes-esque such that sumFactors[k] holds the sum of the proper factors 
	 * of k. */
	static int[] sumFactors;
	
	public static void main(String[] args) {
		sumFactors = new int[MAX_N+1];
		for (int i = 1; i <= MAX_N; i++) {
			for (int j = 2*i; j <= MAX_N; j+=i) {
				sumFactors[j] += i;
			}
		}
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			int ans = 0;
			for (int i = 1; i < n; i++) {
				if (sumFactors[i] <= MAX_N && sumFactors[sumFactors[i]] == i && i != sumFactors[i]) {
					//System.out.println("found " + i + " sum of factors  = " + sumFactors[i]);
					ans += i;
				}
			}
			System.out.println(ans);
		}
		s.close();
	}
}
