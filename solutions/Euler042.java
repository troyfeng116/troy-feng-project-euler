/* -------- SOLVED -------- */

/* The n'th term of a sequence of triangle numbers is given by,
 * 
 * t_n = 1/2 * n * (n+1)
 * 
 * so the first ten triangle numbers are:
 * 
 * 1,3,6,10,15,21,28,36,45,55
 * 
 * You are given an integer. If it is a triangular number t_n, print the term n corresponding to this number, 
 * else print -1.
 * 
 * INPUTS: 1 <= T <= 10^5, 1 <= t_n <= 10^18  */

import java.util.Scanner;

public class Euler042 {
	
	/* Thoughts/approach: No long overflow, so pretty straightforward. */
	
	/* If t_n is triangular, return n. Else return -1. */
	public static long isTriangular(long t_n) {
		long n = (long) Math.sqrt(t_n * 2);
		if (n * (n+1)  == t_n*2) {
			return n;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long t_n = Long.parseLong(s.nextLine());
			System.out.println(isTriangular(t_n));
		}
		s.close();
	}
}
