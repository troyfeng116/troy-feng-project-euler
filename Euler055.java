/* -------- UNSOLVED -------- */

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
 * 		(i) become a palindrome in less than  iterations, or,
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

public class Euler055 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		
		s.close();
	}
}
