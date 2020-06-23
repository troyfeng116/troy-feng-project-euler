/* -------- UNSOLVED -------- */

/* An irrational decimal fraction is created by concatenating the positive integers:
 * 
 * 0.123456789101112131415161718192021...
 * 
 * It can be seen that the 12th digit of the fractional part is 1.
 * 
 * If d_n represents the n'th digit of the fractional part, find the value of the following expression:
 * 
 * d_{i_1} x d_{i_2} x d_{i_3} x d_{i_4} x d_{i_5} x d_{i_6} x d_{i_7}
 * 
 * INPUT: 1 <= T <= 10^5, T lines with 7 space-separated integers i_k, where 1 <= i_k <= 10^18. */

import java.util.Scanner;

public class Euler040 {
	
	/* Thoughts/approach: I think given n, we can calculate d_n in pretty much constant time. We can
	 * bracket by powers of 10; from 1-9, there are 9 digits. From 10-99, there are 90*2 = 180 digits. 
	 * From 100-999, there are 3*900 = 2700 digits. Ex. for n=200, we know there are 189 digits before
	 * 100 and that the 190'th digit is the 1 in 100. We need 11 digits to go from 189'th digit to 
	 * 200'th. 11/3 = 3 with remainder 2, so our answer is the second digit of the 4rd three-digit
	 * number, which is 103. So d_200 should be 0. */
	
	static final long MAX_N = 1000000000000000000L;
	
	static long[] tenToThe;
	
	/* bracket[k] holds the number of digits in Champernowne's constant that come before (non-inclusive)
	 * 10^k. */
	static long[] bracket;
	
	/* Return the n'th digit of Champernowne's constant. */
	public static int champDigit(long n) {
		int pow = 0; /* Number of digits in number containing n'th digit. */
		while (bracket[pow] < n) {
			pow++;
		}
		long base = tenToThe[pow-1]; /* The largest power of ten <= the number containing n'th digit. */
		long nDigits = bracket[pow-1]; /* #digits up to power of ten bracket. */
		long numPast = (n-nDigits-1) / pow;
		long num = base + numPast; /* The actual number containing the n'th digit. */
		int leftover = (int) ((n-nDigits-1) % pow); /* The exact digit to be retrieved. */
		return Long.toString(num).charAt(leftover) - 48;
	}
	
	public static void main(String[] args) {
		tenToThe = new long[19];
		tenToThe[0] = 1;
		for (int i = 1; i <= 18; i++) {
			tenToThe[i] = tenToThe[i-1] * 10;
		}
		
		bracket = new long[19];
		int pow = 1;
		/* From 10^{pow-1} inclusive to 10^pow non-inclusive, there are 9*10^{pow-1} numbers, each with
		 * pow digits, that account for a total of 9*10^{pow-1}*pow total digits that appear in
		 * Champernowne's constant. */
		while (bracket[pow-1] <= MAX_N) {
			bracket[pow] = bracket[pow-1] + 9 * tenToThe[pow-1] * pow;
			pow++;
		}
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String[] inputs = s.nextLine().split(" ");
			int ans = 1;
			for (int i = 0; i < 7; i++) {
				long n = Long.parseLong(inputs[i]);
				ans *= champDigit(n);
			}
			System.out.println(ans);
		}
		s.close();
	}
}
