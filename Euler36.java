/* -------- SOLVED -------- */

/* The decimal number, 585 = 1001001001 (binary), is palindromic in both bases.
 * 
 * Find the sum of all natural numbers, less than N, which are palindromic in base 10 and base K.
 * 
 * (Please note that the palindromic number, in either base, may not include leading zeros.) 
 * 
 * INPUTS: 10 <= N <= 10^6, 2 <= K <= 9, space-separated.*/

import java.util.Scanner;

public class Euler36 {
	
	/* Thoughts/approach: One potentially useful observation is that a number that ends in zero can't
	 * be a palindrome (because of the no leading zeroes condition). So, we can skip all numbers that
	 * evenly divide K.
	 * 
	 * Using arrays to represent all numbers might not be super space efficient, but I don't see another
	 * way. Using arrays also makes testing whether a number is a palindrome easier.
	 * 
	 * Converting from base 10 to base k might be not very time efficient. */
	
	
	/* Returns array representation of converting n base 10 to base k < 10. */
	public static int[] convertBase(int n, int k) {
		/* Find first digit and set size of return array. */
		int place = largestPow(n,k);
		int len = place+1;
		int[] ans = new int[len];
		int chunk = (int) Math.pow(k, place);
		int digit = n/chunk;
		ans[0] = digit;
		n -= digit * chunk;
		/* Find digits one at a time and whittle down n. */
		while (n != 0) {
			place = largestPow(n,k);
			chunk = (int) Math.pow(k, place);
			digit = n/chunk;
			ans[len-place-1] = digit;
			n -= digit*chunk;
		}
		return ans;
	}
	
	/* Return largest power i of k such that k^i <= n. Helper for convertBase. */
	public static int largestPow(int n, int k) {
		int ans = 0;
		int pow = 1;
		while (pow*k <= n) {
			pow *= k;
			ans++;
		}
		return ans;
	}
	
	/* Check if n (in array of digits form) is a palindrome. */
	public static boolean isPalindrome(int[] n) {
		for (int i = 0; i < n.length/2; i++) {
			if (n[i] != n[n.length-i-1]) return false;
		}
		return true;
	}
	
	/* Overloaded isPalindrome method for base 10 ints. */
	public static boolean isPalindrome(int n) {
		int powTen = 1;
		while (powTen * 10 <= n) {
			powTen *= 10;
		}
		while (n != 0) {
			int first = n/powTen;
			int last = n%10;
			if (first != last) return false;
			n = (n % powTen) / 10;
			powTen /= 100;
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int k = Integer.parseInt(inputs[1]);
		int ans = 0;
		for (int i = 1; i < n; i++) {
			/* Skip numbers that end in 0 in base 10 or base k. */
			if (i%k != 0 && i%10 != 0) {
				if (isPalindrome(i) && isPalindrome(convertBase(i,k))) {
					ans += i;
				}
			}
		}
		System.out.println(ans);
		s.close();
	}
}
