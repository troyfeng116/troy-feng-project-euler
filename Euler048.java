/* -------- UNSOLVED: 80/100 -------- */

/* Consider the series,
 * 
 * 		1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317
 *  
 * Find the last ten digits of the series,
 *  
 * 		1^1 + 2^2 + 3^3 + ... + N^N
 * 
 * Note: you do not need to print leading zeros. Ex. for N=10, print 405071317.
 * 
 * INPUTS: 1 <= N <= 2x10^6 */

import java.util.Scanner;

public class Euler048 {
	
	/* Thoughts/approach: It seems we'll have to implement multiplication with digit arrays if we don't sell out
	 * and use Java's BigInteger class (which we won't). We can also use quick exponentiation to reduce the
	 * complexity of finding N^N down to O(log(n)) time. 
	 * 
	 * We need only preserve the last 10 digits of any multiplication. */
	
	/* multiplyOne multiplies n, which is the reverse-order digit representation of an integer, by k<10.
	 * The first numZeroes digits of the result will be zeroes. So this operation corresponds to multiplying
	 * n by k*10^{numZeroes}. n will contain only ten digits, and the result will also contain only the last
	 * ten digits. */
	public static int[] multiplyOneDigit(int[] n, int k, int numZeroes) {
		int[] ans = new int[10];
		if (numZeroes >= 10) return ans;
		int carry = 0;
		/* First numZeroes digits should be zero (correspond to last 10 digits in actual num). */
		for (int i = numZeroes; i < 10; i++) {
			int x = n[i-numZeroes] * k + carry;
			ans[i] = x % 10;
			carry = x/10;
		}
		return ans;
	}
	
	/* Multiplies n1 and n2 by splitting n2 into digits and multiplying one by one. */
	public static int[] multiply(int[] n1, int[] n2) {
		int[] ans = new int[10];
		for (int nZeroes = 0; nZeroes < 10; nZeroes++) {
			ans = add(ans, multiplyOneDigit(n1,n2[nZeroes],nZeroes));
		}
		return ans;
	}
	
	/* Add n1 and n2. Only preserves 10 digits. */
	public static int[] add(int[] n1, int[] n2) {
		int carry = 0;
		int[] ans = new int[10];
		for (int i = 0; i < 10; i++) {
			int x = n1[i] + n2[i] + carry;
			ans[i] = x % 10;
			carry = x>=10? 1 : 0;
		}
		return ans;
	}
	
	/* This quick exponentiation algorithm reduces runtime of calculating x^n from linear to logarithmic
	 * complexity. */
	public static int[] pow(int[] n1, int pow) {
		if (pow == 1) return n1;
		int[] n2 = pow(n1,pow/2);
		int[] n3 = multiply(n2,n2);
		if (pow%2 == 0) return n3;
		return multiply(n3,n1);
	}
	
	/* Convert n to reverse digit representation in 10-digit array. */
	public static int[] toArray(int n) {
		int[] ans = new int[10];
		int i = 0;
		while (n != 0 && i < 10) {
			ans[i] = n % 10;
			n /= 10;
			i++;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int[] ans = new int[10];
		for (int i = 1; i <= n; i++) {
			if (i%10 == 0) ;
			else ans = add(ans, pow(toArray(i), i));
		}
		int index = 9;
		while (ans[index] == 0) index--;
		while (index >= 0) {
			System.out.print(ans[index]);
			index--;
		}
		System.out.println();
		s.close();
	}
}

