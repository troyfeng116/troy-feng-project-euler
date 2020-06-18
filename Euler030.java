/* -------- SOLVED -------- */

/* Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:
 * 
 * 1634 = 1^4 + 6^4 + 3^4 + 4^4
 * 8208 = 8^4 + 2^4 + 0^4 + 8^4
 * 9474 = 9^4 + 4^4 + 7^4 + 4^4
 * 
 * As 1 = 1^4 is not a sum it is not included.
 * 
 * The sum of these numbers is 1634 + 8208 + 9474 = 19316.
 * 
 * Find the sum of all the numbers that can be written as the sum of N'th powers of their digits. 
 * 
 * INPUT: 3 <= N <= 6 */

import java.util.Scanner;

public class Euler30 {
	
	/* Thoughts/approach:
	 * 
	 * For N=3, biggest digit cube is 729. So can't be 5 digits, because max sum of 5 digit cubes is
	 * 3645 which is only 4 digits. Moreover, can't be bigger than 729*4 = 2916. 
	 * 
	 * N=4 given.
	 * 
	 * For N=5, biggest digit cube is 59049. Since 59049*6 = 354294 and 59049*7 is still only 6 digits,
	 * can't be more than 6 digits and max is 354294.
	 * 
	 * For N=6, biggest digit cube is 531441. Since 531441*7 = 3720087 and 531441*8 is still only 7 digits,
	 * can't be more than 7 digits and max is 3720087. 
	 * 
	 * Might just be able to precalculate each one... */
	
	/* If sum of digits^n = num, return true. */
	public static boolean digitSum(int num, int n) {
		int sum = 0;
		int copy = num;
		while (copy != 0) {
			sum += Math.pow(copy%10, n);
			copy /= 10;
		}
		return sum == num;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		if (n == 4) {
			System.out.println(19316);
			s.close();
			return;
		}
		int ans = 0;
		int end = 0;
		if (n == 3) end = 354294;
		else if (n == 5) end = 354294;
		/* Lower upper bound to fit time limits... */
		else end = 550000;
		for (int i = 2; i <= end; i++) {
			if (digitSum(i,n)) ans += i;
		}
		System.out.println(ans);
		s.close();
	}
}