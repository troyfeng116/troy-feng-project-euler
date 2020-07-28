/* -------- SOLVED -------- */

/* The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 to 9
 * in some order, but it also has a rather interesting sub-string divisibility property.
 * 
 * Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:
 * 
 * 	d2d3d4 is divisible by 2
 * 	d3d4d5 is divisible by 3
 * 	d4d5d6 is divisible by 5
 * 	d5d6d7 is divisible by 7
 * 	d6d7d8 is divisible by 11
 * 	d7d8d9 is divisible by 13
 * 	d8d9d10 is divisible by 17
 * 
 * Find the sum of all 0 to N pandigital numbers with this property.
 * 
 * INPUTS: An integer N, 3 <= N <= 9 */

import java.util.Scanner;

public class Euler043 {
	
	/* Thoughts/approach: another fairly straightforward permutation generation problem. */
	
	/* Stores primes 2 through 17 inclusive in indices 1 through 7 inclusive. */
	static int[] prime;
	
	/* Generate next lexicographical permutation of elements in p. */
	public static boolean nextPermutation(int[] p) {
		int n = p.length;
		int k = n-2;
		while (k >= 0 && p[k] > p[k+1]) k--;
		if (k < 0) return false;
		int l = n-1;
		while (p[l] < p[k]) l--;
		int temp = p[l];
		p[l]= p[k];
		p[k] = temp;
		for (int i = k+1; i <= (n+k)/2; i++) {
			temp = p[i];
			p[i] = p[n-i+k];
			p[n-i+k] = temp;
		}
		return true;
	}
	
	/* Return true if permutation p[] satisfies the given substring divisibility requirement. */
	public static boolean subStrDiv(int[] p) {
		int window = 0;
		for (int i = 1; i < 4; i++) {
			window = window*10 + p[i];
		}
		if (window % 2 != 0) return false;
		/* Slide the 3-element window. */
		for (int i = 4; i < p.length; i++) {
			window = window % 100;
			window = window*10 + p[i];
			if (window % prime[i-2] != 0) return false;
		}
		return true;
	}
	
	/* Convert digit array representation to long. */
	public static long toLong(int[] p) {
		long ans = 0;
		for (int i = 0; i < p.length; i++) {
			ans = ans*10 + p[i];
		}
		return ans;
	}
	
	public static void main(String[] args) {
		prime = new int[] {0,2,3,5,7,11,13,17};
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		long ans = 0;
		int[] p = new int[n+1];
		for (int i = 1; i <= n; i++) p[i] = i;
		while (nextPermutation(p)) {
			if (subStrDiv(p)) ans += toLong(p);
		}
		System.out.println(ans);
		s.close();
	}
}
