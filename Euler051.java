/* -------- UNSOLVED -------- */

/* By replacing the 1st digit of *3, it turns out that six of the nine possible values: 13, 23, 43, 53, 73, 
 * and 83 are all prime.
 * 
 * By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number is the first example 
 * having seven primes among the ten generated numbers, yielding the family: 56003, 56113, 56333, 56443, 56663, 
 * 56773, and 56993. Consequently 56003, being the first member of this family, is the smallest prime with this 
 * property.
 * 
 * Find the smallest N-digit prime which, by replacing K digits of the number (not necessarily adjacent digits) 
 * with the same digit, is part of an L prime value family. 
 * 
 * Note 1: It is guaranteed that solution does exist.
 * Note 2: Leading zeros should not be considered.
 * Note 3: If there are several solutions, choose the "lexicographically" smallest one (one sequence is 
 * considered "lexicographically" smaller than another if its first element which does not match the corresponding 
 * element in another sequence is smaller) 
 * 
 * INPUTS: 2 <= N <= 7, 1 <= K <= N, 1 <= L <= 8, space-separated N K L
 * OUTPUT the L members of the family */

import java.util.Scanner;

public class Euler051 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int k = Integer.parseInt(inputs[1]);
		int l = Integer.parseInt(inputs[2]);
		s.close();
	}
}
