/* -------- SOLVED -------- */

/* n! = n x (n-1) x ... x 3 x 2 x 1. 
 * 
 * For example, 10! = 3628800, and the sum of the digits in the number 10! is 27. 
 * 
 * Find the sum of the digits in the number N!. 
 * 
 * 1 <= T <= 100, 0 <= N <= 1000*/

import java.util.Scanner;

public class Euler20 {
	
	/* Thoughts/approach: BigInteger would probably work...
	 * 
	 * Another approach might be to precalculate N! up to N=1000 using String multiplication, which
	 * would let us trim trailing zeroes. But string multiplication is tricky... 
	 * 
	 * Because BigInteger is cheap, I think I'll do array multiplication with memoization. To do
	 * > one digit multiplication, I think multiplying by one digit at a time and then adding results
	 * is the easiest way. */
	
	/* All arrays of digits contain the digits of a number in reverse order they would appear in that number.
	 * So 12345 -> [5,4,3,2,1]. */
	
	static final int N_MAX = 1000;
	
	/* Table is a jagged 2D array, where table[k] holds the digits of k! without leading or trailing zeroes. */
	static int[][] table;
	
	/* Multiply arr of digits by x, where x < 10. */
	public static int[] multiplyOneDigit(int[] arr, int x) {
		if (x == 0) return new int[] {0};
		if (x == 1) return arr;
		int[] ans = new int[arr.length+1];
		int carry = 0;
		for (int i = 0; i < arr.length; i++) {
			int num = x * arr[i] + carry;
			ans[i] = num % 10;
			carry = num/10;
		}
		if (carry > 0) {
			ans[arr.length] = carry;
		}
		return ans;
	}
	
	/* Add two arrays of digits. */
	public static int[] add(int[] n1, int[] n2) {
		int[] ans = new int[Math.max(n1.length, n2.length) + 1];
		int i1 = 0;
		int i2 = 0;
		int i = 0;
		int carry = 0;
		while (i1 < n1.length && i1 < n2.length) {
			int num = n1[i1] + n2[i2] + carry;
			ans[i] = num % 10;
			carry = num >= 10? 1 : 0;
			i1++;
			i2++;
			i++;
		}
		while (i1 < n1.length) {
			int num = n1[i1] + carry;
			ans[i] = num % 10;
			carry = num >= 10? 1 : 0;
			i1++;
			i++;
		}
		while (i2 < n2.length) {
			int num = n2[i2] + carry;
			ans[i] = num % 10;
			carry = num >= 10? 1 : 0;
			i2++;
			i++;
		}
		if (carry > 0) {
			ans[ans.length-1] = carry;
		}
		return ans;
	}
	
	/* Multiply arr of digits by int x, where x <= 1000. */
	public static int[] multiply(int[] arr, int x) {
		if (x >= 100) {
			int[] temp = multiply(arr, x/100);
			int[] hundreds = new int[temp.length+2];
			for (int i = 2; i < hundreds.length; i++) {
				hundreds[i] = temp[i-2];
			}
			return add(hundreds, multiply(arr, x % 100));
		}
		else if (x >= 10) {
			int[] temp = multiply(arr, x/10);
			int[] tens = new int[temp.length+1];
			for (int i = 1; i < tens.length; i++) {
				tens[i] = temp[i-1];
			}
			return add(tens, multiply(arr, x % 10));
		}
		else return multiplyOneDigit(arr, x);
	}
	
	/* Trim leading zeroes. */
	public static int[] trim(int[] arr) {
		int numFront = 0;
		while (numFront < arr.length && arr[numFront] == 0) {
			numFront++;
		}
		int numBack = 0;
		while (numBack < arr.length && arr[arr.length - numBack - 1] == 0) {
			numBack++;
		}
		int[] ans = new int[arr.length - numFront - numBack];
		for (int i = numFront; i < arr.length - numBack; i++) {
			ans[i-numFront] = arr[i];
		}
		return ans;
	}
	
	/* Generate n!. */
	public static int[] factorial(int n) {
		if (table[n] != null) return table[n];
		return multiply(factorial(n-1), n);
	}
	
	public static void main(String[] args) {
		table = new int[N_MAX+1][];
		table[0] = new int[] {1};
		for (int i = 1; i <= N_MAX; i++) {
			table[i] = trim(factorial(i));
		}
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			int[] digits = factorial(n);
			int sum = 0;
			for (int i = 0; i < digits.length; i++) {
				sum += digits[i];
			}
			System.out.println(sum);
		}
		s.close();
	}
}
