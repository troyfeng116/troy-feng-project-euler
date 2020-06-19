/* -------- SOLVED -------- */

/* The number, 197, is called a circular prime because all rotations of the digits: 197, 791, and 719, are 
 * themselves prime.
 * 
 * There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97. Sum of which
 * is 446. 
 * 
 * Find the sum of circular primes that are below N.
 * 
 * NOTE: rotations can exceed N.
 * 
 * INPUT: 10 <= N <= 10^6 */

import java.util.Scanner;

public class Euler035 {
	
	/* Thoughts/approach: sieve up to 10^7, since rotations can exceed MAX_N.
	 * 
	 * If a number has any digit that's even or 5, it can't be circular.
	 * 
	 * To rotate a number one digit to the right (i.e. abcd -> dabc), save last digit, divide by 10,
	 * and add last digit to front. */
	
	static final int MAX_N = 1000000;
	
	static int[] tenToThe;
	
	static boolean[] composite;
	
	static boolean[] found;
	
	/* If n is circular, return sum of all circular primes and mark all as found. Else return -1. */
	public static int isCircular(int n) {
		int numDigits = okDigits(n);
		if (numDigits > 0) {
			int ans = n;
			int rotation = (n%10) * tenToThe[numDigits-1] + n/10;
			while (rotation != n) {
				if (composite[rotation]) return -1;
				ans += rotation;
				rotation = (rotation%10) * tenToThe[numDigits-1] + rotation/10;
			}
			found[n] = true;
			int r2 = (n%10) * tenToThe[numDigits-1] + n/10;
			while (r2 != n) {
				found[r2] = true;
				r2 = (r2%10) * tenToThe[numDigits-1] + r2/10;
			}
			return ans;
		}
		
		return -1;
	}
	
	/* If no digit in n is even or 5, return number of digits in n. Else return -1. */
	public static int okDigits(int n) {
		int count = 0;
		while (n != 0) {
			if ((n % 10) % 2 == 0 || n % 5 == 0) return -1;
			count++;
			n = n/10;
		}
		return count;
	}
	
	public static void main(String[] args) {
		/* Sieve up to 10^7. */
		composite = new boolean[MAX_N*10+1];
		for (int i = 2; i <= MAX_N*10; i++) {
			if (!composite[i] && i <= 3300) {
				for (int j = i*i; j <= MAX_N*10; j+=i) {
					composite[j] = true;
				}
			}
		}
		/* Store powers of ten. */
		tenToThe = new int[7];
		tenToThe[0] = 1;
		for (int i = 1; i <= 6; i++) {
			tenToThe[i] = tenToThe[i-1] * 10;
		}
		
		found = new boolean[MAX_N*10+1];
		
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int ans = 17; /* start at 2+3+5+7 */
		for (int k=11; k<n; k+=6) {
			if (!composite[k] && !found[k]) {
				int x = isCircular(k);
				if (x > 0) {
					ans += x;
				}
			}
			if (k+2 < n && !composite[k+2] && !found[k+2]) {
				int x = isCircular(k+2);
				if (x > 0) {
					ans += x;
				}
				
			}
		}
		System.out.println(ans);
		s.close();
	}
}
