/* -------- SOLVED -------- */

/* The Fibonacci sequence is defined by the recurrence relation:
 * 
 * F_n = F_{n-1} + F_{n-2}, F_1 = F_2 = 1
 * 
 * 
 * Hence the first 12 terms will be:
 * 
 * F1 = 1, F2 = 1, F3 = 2, F4 = 3, ..., F12 = 144
 * 
 * The 12th term, F12, is the first term to contain three digits.
 * 
 * What is the first term in the Fibonacci sequence to contain N digits? 
 * 
 * INPUT: 1 <= T <= 5000, 2 <= N <= 5000 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Euler025 {
	
	/* Thoughts/approach: Use O(n) Fibonacci generation. And use arrays, not BigInteger, to make
	 * the problem somewhat interesting. Since calculation is O(max given N), precalculating isn't
	 * worth it. (edit: maybe it is worth it to precalculate...?)
	 * 
	 * To memoize for O(n) Fib generation, HashMap where (k,v) = (k, F_k) where F_k is an array of digits. 
	 * 
	 * Finally, memoize (N,k), where N is num digits and k is first k such that F_k has N digits. */
	
	final static int MAX_N = 5000;
	
	/* fib.get(k) = k'th fibonacci number. */
	static Map<Integer,int[]> fib;
	
	/* nDigits.get(N) returns first k such that fib(k) has N digits. */
	static Map<Integer,Integer> nDigits;
	
	/* Sum of two numbers in array-of-digits form, where digits are reversed. */
	public static int[] add(int[] n1, int[] n2) {
		int[] ans = new int[Math.max(n1.length, n2.length)];
		int i = 0;
		int carry = 0;
		while (i < n1.length && i < n2.length) {
			int num = n1[i] + n2[i] + carry;
			ans[i] = num % 10;
			carry = num >= 10? 1 : 0;
			i++;
		}
		while (i < n1.length) {
			int num = n1[i] + carry;
			ans[i] = num % 10;
			carry = num >= 10? 1 : 0;
			i++;
		}
		while (i < n2.length) {
			int num = n2[i] + carry;
			ans[i] = num % 10;
			carry = num >= 10? 1 : 0;
			i++;
		}
		if (carry != 0) {
			int[] temp = Arrays.copyOf(ans, ans.length+1);
			temp[ans.length] = carry;
			ans = temp;
		}
		return ans;
	}
	
	/* Calculate k'th Fibonacci number, with memoization. Assumes that fib table contains fib(k-1) and
	 * fib(k-2). */
	public static int[] fibonacci(int k) {
		int[] ans = add(fib.get(k-1), fib.get(k-2));
		fib.put(k,ans);
		/* If fib_k length > fib_{k-1} and fib_{k-2} length, add to nDigits table (must be first k with that
		 * many digits) */
		if (ans.length > fib.get(k-1).length) nDigits.put(ans.length, k);
		return ans;
	}
	
	
	public static void main(String[] args) {
		nDigits = new HashMap<Integer,Integer>();
		nDigits.put(1,1);
		/* Precalculate Fibonacci numbers until 5000 digits. */
		fib = new HashMap<Integer,int[]>();
		fib.put(1, new int[] {1});
		fib.put(2, new int[] {1});
		int length = 1;
		int k = 3;
		while (length < MAX_N) {
			if (fibonacci(k).length > length) length = fibonacci(k).length;
			k++;
		}
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			System.out.println(nDigits.get(n));
		}
		s.close();
	}
}
