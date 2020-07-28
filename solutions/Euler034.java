/* -------- SOLVED -------- */

/* 19 is a curious number, as 1! + 9! = 1 + 362880 = 362881 which is divisible by 19.
 * 
 * Find the sum of all numbers below N which divide the sum of the factorial of their digits.
 * 
 * Note: as 1!,...,9! are not sums they are not included. 
 * 
 * 10 <= N <= 10^5 */

import java.util.Scanner;

public class Euler034 {
	
	/* Thoughts/approach: brute force I suppose. Memoize the factorials first. */
	
	/* Store k! for k = 0 to k = 9. */
	static int[] factorial;
	
	/* Return true if n divides the sum of its digit factorials. */
	public static boolean digitFactorial(int n) {
		int sum = 0;
		int copy = n;
		while (copy != 0) {
			sum += factorial[copy % 10];
			copy /= 10;
		}
		return sum % n == 0;
	}
	
	public static void main(String[] args) {
		factorial = new int[10];
		factorial[0] = 1;
		for (int i = 1; i <= 9; i++) {
			factorial[i] = factorial[i-1] * i;
		}
		
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int ans = 0;
		for (int i = 10; i <= n; i++) {
			if (digitFactorial(i)) {
				ans += i;
			}
		}
		System.out.println(ans);
		s.close();
	}
}