/* -------- SOLVED -------- */

/* A perfect number is a number for which the sum of its proper divisors is exactly equal to the 
 * number. For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which 
 * means that 28 is a perfect number.
 * 
 * A number n is called deficient if the sum of its proper divisors is less than n and it is called 
 * abundant if this sum exceeds n.
 * 
 * As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be 
 * written as the sum of two abundant numbers is 24. By mathematical analysis, it can be shown that 
 * all integers greater than 28123 can be written as the sum of two abundant numbers. However, this 
 * upper limit cannot be reduced any further by analysis even though it is known that the greatest 
 * number that cannot be expressed as the sum of two abundant numbers is less than this limit.
 * 
 * Given N, print YES if it can be expressed as sum of two abundant numbers, else print NO. 
 * 
 * INPUT: 1 <= T <= 100, 0 <= N <= 10^5 */

import java.util.Scanner;

public class Euler23 {
	
	/* Thoughts/approach: Modified Sieve of Eratosthenes to find sums of proper divisors.
	 * 
	 * Then, it's just the sum of pairs in an array to target problem, but checking if abundant first. */
	
	final static int MAX_N = 100000;
	
	/* We will precalculate sums of factors, such that sumFactors[k] holds the sum of the proper divisors
	 * of k. Thus k is abundant iff sumFactors[k] > k. */
	static int[] sumFactors;
	
	/* Memoize found abundant numbers so that we can look up complements quickly. */
	static boolean[] complements;
	
	/* Return true if target can be written as sum of two abundant numbers, false otherwise. */
	public static boolean pairSum(int target) {
		for (int i = 0; i <= target; i++) {
			int comp = target - i;
			if (sumFactors[i] > i) {
				if (complements[comp] || i == comp) {
					complements[i] = true;
					return true;
				}
				complements[i] = true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		sumFactors = new int[MAX_N+1];
		for (int i = 1; i <= MAX_N; i++) {
			for (int j = 2*i; j <= MAX_N; j+= i) {
				sumFactors[j] += i;
			}
		}
		complements = new boolean[MAX_N+1];
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			if (pairSum(n)) System.out.println("YES");
			else System.out.println("NO");
		}
		s.close();
	}
}
