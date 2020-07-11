/* -------- SOLVED -------- */

/* A googol 10^100 is a massive number: one followed by one-hundred zeros. 100^100 is almost unimaginably 
 * large: one followed by two-hundred zeros. Despite their size, the sum of the digits in each number is 
 * only 1.
 * 
 * Considering natural numbers of the form, a^b, where a,b < N, what is the maximum digital sum?
 *
 * INPUTS: 5 <= N <= 200
 * Sample output: N=5 -> 13, as 4^4 = 256, and 2+5+6=13 is the maximal digital sum in that range. */

import java.util.Scanner;
import java.util.Arrays;

public class Euler056 {
	
	/* Thoughts/approach: we can either solve the problem in like 5 lines of code using BigInteger, or
	 * we can probably memoize results and use array multiplication, brute forcing all a,b < N. I don't
	 * think using array multiplication would time out. We would need to use a 3-D array, or a
	 * NxN array of arrays. It wouldn't be pretty or efficient but I don't see another way.
	 *
	 * We can find how many digits 2^200 has by solving: 10^{x-1} = 2^200. Taking log_10 of both sides,
	 * x-1 = log{2^100} = 100*log(2) ~= 60.2 -> x = 61.2, which means 2^200 has 60 digits, which means
	 * 200^200 has 260 digits; that seems manageable. If we had an NxN array of arrays, I guess the 
	 * approach would be something like:
	 *	for each a:[1,N] in the first row
	 * 		for each column b:[1,N]
	 *			given that we know a^{b-1}, we can find a^b in O(#digits) using array multiplication.
	 *  within loop, track max, and print after loop ends */

	/* Return int[] corresponding to multiplying array-of-digits representation of n by d*10^k, i.e.
	 * multiplying n by d<9 and then multiply by 10^k. All array-of-digits notation is reversed. */
	public static int[] multiplyOneDigit(int[] n, int d, int k) {
		if (d == 0) return new int[] {0};
		int[] ans = new int[n.length+k];
		int carry = 0;
		for (int i = k; i < ans.length; i++) {
			int mult = d*n[i-k] + carry;
			ans[i] = mult%10;
			carry = mult/10;
		}
		if (carry > 0) {
			ans = Arrays.copyOf(ans, ans.length+1);
			ans[ans.length-1] = carry;
		}
		return ans;
	}

	/* Return int[] corresponding to adding array-of-digits representations (reversed) of n1 and n2. */
	public static int[] add(int[] n1, int[] n2) {
		int[] ans = new int[Math.max(n1.length, n2.length)];
		int carry = 0;
		int i = 0;
		while (i < n1.length && i < n2.length) {
			int sum = n1[i] + n2[i] + carry;
			ans[i] = sum%10;
			carry = sum>=10? 1 : 0;
			i++;
		}
		while (i < n1.length) {
			int sum = n1[i] + carry;
			ans[i] = sum%10;
			carry = sum>=10? 1 : 0;
			i++;
		}
		while (i < n2.length) {
			int sum = n2[i] + carry;
			ans[i] = sum%10;
			carry = sum>=10? 1 : 0;
			i++;
		}
		if (carry > 0) {
			ans = Arrays.copyOf(ans, ans.length+1);
			ans[ans.length-1] = carry;
		}
		return ans;
	}

	/* Multiply a by num, where num < 200. */
	public static int[] multiply(int[] a, int num) {
		int[] ans = multiplyOneDigit(a, num%10, 0);
		if (num >= 10) ans = add(ans, multiplyOneDigit(a, (num/10)%10, 1));
		if (num >= 100) ans = add(ans, multiplyOneDigit(a, num/100, 2));
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		/* table[i][j] holds i^j in array-of-digits form. We will ignore the first 2 rows, as 0^j and 1^j
		 * are trivial and useless for this problem. */
		int[][][] table = new int[n][n][];
		/* Fill first col, since x^0 = 0 for all x. */
		for (int row = 2; row < n; row++) table[row][0] = new int[] {1};

		int max = 0;
		for (int a = 2; a < n; a++) {
			for (int b = 1; b < n; b++) {
				/* Fill a^b with a^{b-1}*a. */
				table[a][b] = multiply(table[a][b-1], a);
				int sum = 0;
				for (int i = 0; i < table[a][b].length; i++) {
					sum += table[a][b][i];
				}
				max = Math.max(sum,max);
			}
		}
		System.out.println(max);
		s.close();
	}
}
