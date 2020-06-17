/* -------- SOLVED -------- */

/* A palindromic number reads the same both ways. The smallest 6 digit palindrome made from the product of two 3-digit 
 * numbers is 101101 = 143 * 707.
 * 
 * Find the largest palindrome made from the product of two 3-digit numbers which is less than N. 
 * 
 * INPUT: T <= 100, 101101 < N < 1000000.
 * 
 * */

import java.util.Scanner;

public class Euler4 {
	
	/* NOTES/APPROACH:
	 * 
	 * abccba = a0000a + b00b0 + cc00 = 100001a + 10010b + 1100c 
	 * So any 6-digit palindrome is divisible by 11.
	 * 
	 * 318x318 = 101124 > 101101 and 1000x1000 = 1000000. So for any six-digit number, having one factor between 318
	 * and 1000 guarantees it is product of two 3-digit numbers. */
	
	
	/* A cumbersome method that returns the largest palindrome strictly less than n, where n[] represents the digits 
	 * of N. Assumes 101101 < N < 1000000, so n.length = 6. */
	public static int findPalindrome(int[] n) {
		if (n[2] != 0 || n[3] != 0) { /* At least one of middle digits is non-zero. */
			if (n[2] == n[3]) {
				if (n[1] < n[4]) {
					n[5] = n[0];
					n[4] = n[1];
					return intValue(n);
				}
				n[2]--;
				n[3]--;
				n[4] = n[1];
				n[5] = n[0];
				return intValue(n);
				
			}
			n[4] = n[1];
			n[5] = n[0];
			if (n[2] < n[3]) { /* If n[2] < n[3], just set n[3] = n[2]. */
				n[3] = n[2];
				return intValue(n);
			}
			/* Else, n[3] >= n[2]. Since n[2] != 0 (else it would've triggered n[2]<n[3] above), decrement it. */
			n[2]--;
			n[3] = n[2];
			return intValue(n);
		}
		/* Case where n[2] = n[3] = 0. */
		n[2] = n[3] = 9;
		if (n[4] != 0 || n[1] != 0) { /* Same logic as above. */
			n[5] = n[0];
			if (n[1] < n[4]) {
				n[1] = n[4];
				return intValue(n);
			}
			n[1]--;
			n[4] = n[1];
			return intValue(n);
		}
		/* Deja vu... */
		n[1] = n[4] = 9;
		n[0]--;
		n[5] = n[0];
		return intValue(n);
		
	}
	
	/* Helper function to convert int[] to int. */ 
	public static int intValue(int[] arr) {
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			ans = ans * 10 + arr[i];
		}
		return ans;
	}
	
	/* Helper function to convert 6-digit int to int[]. */
	public static int[] toArray(int n) {
		int[] ans = new int[6];
		for (int place = 5; place >= 0; place--) {
			ans[place] = n % 10;
			n /= 10;
		}
		return ans;
	}
	
	/* Takes a 6-digit palindrome n as argument. Returns true if n can be written as product of two 3-digit integers, and
	 * false otherwise. N must be divisible by 11 (property of 6-digit palindromes). Moreover, the 3-digit factors must be
	 * between 999 and n/999. Thus, we can start from the first multiple of 11 geq n/999, and test multiples of 11 until 
	 * 990. */
	public static boolean hasFactors(int n) {
		int copy = n;
		int start = copy/999;
		while (start % 11 != 0) start++;
		for (int i = start; i <= 990; i++) {
			if (copy % i == 0) return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = s.nextInt();
		for (int i = 0; i < t; i++) {
			int n = s.nextInt();
			int[] digits = toArray(n);
			int palindrome = findPalindrome(digits);
			while (!hasFactors(palindrome)) {
				palindrome = findPalindrome(toArray(palindrome));
			}
			System.out.println(palindrome);
		}
		s.close();
	}
}
