/* -------- SOLVED -------- */

/* A permutation is an ordered arrangement of objects. For example, dabc is one possible permutation of the 
 * word abcd. If all of the permutations are listed alphabetically, we call it lexicographic order. The 
 * lexicographic permutations of abc are:
 * 
 * abc acb bac bca cab cba
 * 
 * What is the N'th lexicographic permutation of the word abcdefghijklm? 
 * 
 * INPUT: 1 <= T <= 1000, 1 <= N <= 13! */

import java.util.Scanner;

public class Euler24 {
	
	/* Permutation generation might work, but 13! is larger than max int, so array memoization won't work and
	 * Lists are less efficient. Would take a long time...
	 * 
	 * Approach: There are 12! permutations that start with a. In fact, any given k letters, there are (13-k)!
	 * permutations starting with those k letters. 
	 * 
	 * So you should be able to determine all thirteen letters based on N. Bracket N into some range of k*12! and
	 * (k+1)*12! to find first letter. Then within that range, bracket into k*11! and (k+1)*11!. 
	 * 
	 * I think the set of {1!,...,k!} forms a basis of numbers from 1 to k! of sorts with coefficients 
	 * {1,2,...,k}. 
	 * 
	 * Big observation: Map a->0,b->1,...,m->12. Say the first letter is c=2. Then there are 2 letters before
	 * c, so assuming everything after c is sorted (i.e. the permutation is the first one to start with c),
	 * there's 2*12! permutations before c. Thus, the permutation with c in front and everything after c in 
	 * place is the (2*12! + 1)'th permutation. But everything after c is not necessarily sorted.
	 * 
	 * Then, say the next letter is e. If everything after e (abdfghijkm) were sorted, there would be similarly
	 * (3*11! + 1) permutations before it (the ones starting with a, b, and d).
	 * 
	 * In total, the permutation starting with "ce" and everything else sorted is the (2*12! + 3*11! + 1)'th
	 * lexicographic permutation. In particular, the reverse permutation (the 13!'th permutation) has
	 * 12*12! + 11*11! + ... + 1*1! = 13! - 1 permutations before it (cool).
	 * 
	 * So to map from permutation to N, for each letter, you count the number of letters after it that would
	 * lexicographically come before it if sorted, then multiply by factorial base. The resulting factorial 
	 * base number converted to base 10 is N-1, the number of permutations before this permutation.
	 * 
	 * Thus, to map from N to permutation, we convert N-1 to factorial base first (essentially calculate the number
	 * of permutations before it). Then we place letters one by one. */
	
	static final int MAX_N = 13;
	
	/* Precalculate factorials up to 12! */
	static long[] factorial;
	
	/* Return array such that SUM_{0->12} ans[i] * i! = n-1. */
	public static int[] bracket(long n) {
		int[] ans = new int[13];
		long n1 = n-1;
		for (int k = 12; k >= 0; k--) {
			ans[12-k] = (int) (n1 / factorial[k]);
			n1 = n1 % factorial[k];
		}
		return ans;
	}
	
	public static void main(String[] args) {
		factorial = new long[MAX_N+1];
		factorial[0] = 1;
		for (int i = 1; i <= MAX_N; i++) {
			factorial[i] = factorial[i-1] * i;
		}
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long n = Long.parseLong(s.nextLine());
			/* We'll placed unused digits one at a time according to the factorial base. */
			boolean[] used = new boolean[13];
			int[] fBase = bracket(n);
			int[] ans = new int[13];
			for (int i = 0; i < 13; i++) {
				int numLess = 0;
				int index = 0;
				while (numLess < fBase[i] && index < 12) {
					if (!used[index]) numLess++;
					index++;
				}
				while (used[index] && index < 12) index++;
				ans[i] = index;
				used[index] = true;
			}
			for (int i: ans) System.out.print((char) (i+97));
			System.out.println();
		}
		s.close();
	}
}
