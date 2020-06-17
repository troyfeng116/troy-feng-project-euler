/* -------- SOLVED -------- */

/* The following iterative sequence is defined for the set of positive integers:
 * 
 * n -> n/2 if n odd
 * n -> 3n+1 if n even
 * 
 * Using the rule above and starting with 13, we generate the following sequence:
 * 
 * 13 -> 40 -> 20 -> 10 -> 5 -> 16 -> 8 -> 4 -> 2 -> 1
 * 
 * It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. Although it has not 
 * been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.
 * 
 * Which starting number <= N produces the longest chain? If many possible such numbers are there print the 
 * maximum one. 
 * 
 * INPUT: 1 <= T <= 10^4, 1 <= N <= 5x10^6 */

import java.util.Scanner;

public class Euler14 {
	
	/* Idea: memoize length of sequence for each N? So map stores <k,v>, where k is integer <= 5x10^6 and v is length
	 * of Collatz sequence starting from k. 
	 * 
	 * Then iterate from 1 to 5x10^6, tracking max and storing it in an array so that arr[i] = max length from starting
	 * point <= i. (Can we do this in one step?)
	 * 
	 * From i to 5x10^6, start sequence and store values you run into in HashMap.
	 * 
	 * EDIT: can't simply loop because some terms increase (so exceed 5x10^6). We have to use fixed-length array to memoize
	 * and not hashmap to avoid stack overflow. For the terms that go over N_MAX, we just won't memoize those. */
	
	final static int N_MAX = 5000000;
	
	/* Table[N] = length of Collatz path from N. */
	static int[] table;
	
	/* Find length of Collatz path from n. */
	public static int collatz(long n) {
		/* If larger than N_MAX, don't memoize; just recurse until we hit memoized point. */
		if (n > N_MAX) {
			if (n % 2 == 0) return 1 + collatz(n/2);
			return 1 + collatz(3*n+1);
		}
		/* Else, no issues casting n to int. */
		if (table[(int) n] != 0) return table[(int) n];
		if (n % 2 == 0) {
			int ans = 1 + collatz(n/2);
			table[(int) n] = ans;
			return ans;
		}
		int ans = 1 + collatz(3*n+1);
		table[(int) n] = ans;
		return ans;
	}
	
	public static void main(String[] args) {
		table = new int[N_MAX+1];
		table[1] = 1;
		int[] max = new int[N_MAX + 1]; /* max[i] stores the greatest N <= i such that collatz(N) is longest. */
		int bestN = 0; /* N that yields longest path thus far. */
		int longest = 0; /* Longest path thus far. */
		for (int i = 2; i <= N_MAX; i++) {
			int length = collatz(i);
			if (length >= longest) {
				longest = length;
				bestN = i;
			}
			max[i] = bestN;
		}
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int i = 0; i < t; i++) {
			int n = Integer.parseInt(s.nextLine());
			System.out.println(max[n]);
		}
		s.close();
	}

}
