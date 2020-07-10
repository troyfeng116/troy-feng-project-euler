/* -------- SOLVED -------- */

/* If we take 47, reverse and add, 47 + 74 = 121, which is palindromic.
 *
 * Not all numbers produce palindromes so quickly. For example,
 *
 *		349 + 943 = 1292
 *		1292 + 2921 = 4213
 *		4213 + 3124 = 7337
 *
 * That is, 349 took three iterations to arrive at a palindrome.
 *
 * Although no one has proved it yet, it is thought that some numbers, like 196, never produce a palindrome.
 * A number that never forms a palindrome through the reverse and add process is called a Lychrel number.
 * Due to the theoretical nature of these numbers, and for the purpose of this problem, we shall assume that 
 * a number is Lychrel until proven otherwise. In addition you are given that for every number below 10^5, 
 * it will either
 * 		(i) become a palindrome in less than 60 iterations, or,
 *		(ii) no one, with all the computing power that exists, has managed so far to map it to a palindrome.
 *
 * Note that a lot of numbers converge to the same palindrome, for example:
 * 		{19,28,29,37,38,46,47,56,64,65,73,74,82,83,91,92,110,121} 
 * all converge to 121, a total of 18 numbers.
 * 
 * Note: For this problem we have assumed palindrome numbers like 55 or 121 to be non-Lychrel for their 
 * 0'th iteration.
 * 
 * Given N, find the palindrome to which maximum numbers between [1,N] converge. Print the palindrome and 
 * the count (space-separated).
 *
 * INPUTS: 100 <= N <= 10^5 */

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Euler055 {
	
	/* Thoughts/approach: seems straightforward. Loop starting numbers from 1 to N, for each starting number,
	 * add reverses until either we hit a palindrome or we reach 60 iterations. One thing to note is that
	 * it SEEMS most numbers will converge quickly, that is before long overflow. So I think we can just
	 * ignore terms that cause long overflow, as the most common convergence points shouldn't be massive. */

	static int MAX_ITERATIONS = 60;
	/* table.get(k) returns the number of integers for whom the add/reverse process converges to k. Note that
	 * table.get(k) exists only if k is a palindrome. */
	static Map<Long,Integer> table;

	/* Return -1 if n is a palindrome, or the reverse of n if it is not. */
	public static long reverse(long n) {
		long copy = n;
		long ans = 0;
		while (copy != 0) {
			ans = ans*10 + copy%10;
			copy /= 10;
		}
		return ans==n? -1 : ans;
	}

	/* Return the palindrome found by iterating n. */
	public static long iterate(int n) {
		int iteration = 0;
		long sum = n;
		long reverse = 0;
		while ((reverse = reverse(sum)) > 0 && iteration < 60 && Long.MAX_VALUE - reverse > sum) {
			sum += reverse;
			iteration++;
		}
		return reverse<0? sum : -1;
	}
	
	public static void main(String[] args) {
		table = new HashMap<Long,Integer>();
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		long palindromeAns = 0;
		int countAns = 0;
		for (int n = 1; n <= N; n++) {
			long result = iterate(n);
			if (result > 0) {
				if (!table.containsKey(result)) table.put(result, 1);
				else {
					int newCount = table.get(result) + 1;
					table.remove(result);
					table.put(result, newCount);
				}
				if (table.get(result) >= countAns) {
					countAns = table.get(result);
					palindromeAns = result;
				}
			}
		}
		System.out.println(palindromeAns + " " + countAns);
		s.close();
	}
}
