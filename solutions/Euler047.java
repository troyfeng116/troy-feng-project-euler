/* -------- SOLVED -------- */

/* The first two consecutive numbers to have two distinct prime factors are:
 * 		
 * 		14 = 2x7
 * 		15 = 3x5
 * 
 * The first three consecutive numbers to have three distinct prime factors are:
 * 
 * 		644 = 2^2 x 7 x 23
 * 		645 = 3 x 5 x 43
 * 		646 = 2 x 17 x 19
 * 
 * Given N find all the K consecutive integers, where first integer is <=N, to have exactly K distinct prime 
 * factors. Print the first of these numbers in ascending order. 
 * 
 * INPUTS: 20 <= N <= 2x10^6, 2 <= K <= 4, space separated N K */

import java.util.Scanner;

public class Euler047 {
	
	/* Thoughts/approach: there are similar algorithms to the Sieve of Eratosthenes to store the sum of factors,
	 * sum of prime factors, number of factors, and number of prime factors of each integer. Here, we are interested
	 * in the number of prime factors. After sieving, we need to count the number of subarrays of length K each
	 * with only the value K, which is O(n*k) (acceptable since k <= 4). */
	
	static final int MAX_N = 2000000;
	
	/* numPrimeFactors[k] stores the number of prime factors of k. */
	static int[] numPrimeFactors;
	
	/* Modified Sieve of Eratosthenes. If we encounter a number with no prime factors up to that point, that number
	 * itself must be prime. */
	public static void fillPrimeFactors() {
		numPrimeFactors = new int[MAX_N+1];
		for (int i = 2; i <= MAX_N; i++) {
			if (numPrimeFactors[i] == 0) {
				for (int j = i; j <= MAX_N; j+=i) {
					numPrimeFactors[j]++;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		fillPrimeFactors();
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int N = Integer.parseInt(inputs[0]);
		int K = Integer.parseInt(inputs[1]);
		/* i is the first integer, i+1 is second integer, ..., i+k-1 is k'th integer. */
		for (int i = 0; i <= N; i++) {
			int j = i;
			while (j < i+K && numPrimeFactors[j] == K) j++;
			if (j == i+K) System.out.println(i);
		}
		s.close();
	}
}
