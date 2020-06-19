/* -------- SOLVED -------- */

/* We shall say that an N-digit number is pandigital if it makes use of all the digits 1 to N exactly once; for 
 * example, the 5-digit number, 15234, is 1 through 5 pandigital.
 * 
 * The product 7254 is unusual, as the identity, 39 × 186 = 7254, containing multiplicand, multiplier, and 
 * product is 1 through 9 pandigital.
 * 
 * Find the sum of all products whose multiplicand/multiplier/product identity can be written as a 1 through N
 * pandigital.
 * 
 * HINT: Some products can be obtained in more than one way so be sure to only include it once in your sum.
 * 
 * INPUT: integer N, 4 <= N <= 9*/

import java.util.Scanner;

public class Euler032 {
	
	/* Thoughts/approach: Generate all permutations of 1-N, and then test all ways to split up each permutation
	 * into three pieces and see if the multiplication is true...? We can avoid checking some partitions of each
	 * permutation (ex. first two pieces can't be bigger than third piece) */
	
	
	/* Track found products to avoid duplicates. */
	static boolean[] found;
	
	/* Given a permutation, return next lexicographic permutation. Kinda similar to generating next biggest
	 * integer by rearranging digits. Pick last index k such that arr[k] < arr[k+1]. If none exists, it must
	 * be the last permutation. Pick last index l such that arr[l] > arr[k]. Swap k and l, and then reverse all
	 * elements from k+1 to the end (effectively sorting them). */
	public static boolean nextPermutation(int[] arr) {
		int k = Integer.MAX_VALUE;
		for (int i = arr.length-2; i >= 0; i--) {
			if (arr[i] < arr[i+1]) {
				k = i;
				break;
			}
		}
		if (k == Integer.MAX_VALUE) return false;
		int swap = arr.length-1;
		for (int i = arr.length-1; i > k; i--) {
			if (arr[i] > arr[k]) {
				swap = i;
				break;
			}
		}
		int temp = arr[swap];
		arr[swap] = arr[k];
		arr[k] = temp;
		/* Reverse k+1 up to last element, inclusive.  */
		for (int i = k+1; i <= (arr.length-1+k)/2; i++) {
			temp = arr[i];
			arr[i] = arr[arr.length-1-(i-k-1)];
			arr[arr.length-1-(i-k-1)] = temp;
		}
		return true;
	}
	
	/* Given a pandigital permutation and two "cuts", return if it yields a true multiplcation. A cut of
	 * 3 means 3 elements. So cut1 = 3 and cut2 = 2 for p.length = 9 means pieces of size 3,2, and 4. */
	public static int cut(int[] p, int cut1, int cut2) {
		int n1 = 0;
		for (int i = 0; i <= cut1-1; i++) {
			n1 = n1 * 10 + p[i];
		}
		int n2 = 0;
		for (int i = cut1; i <= cut1+cut2-1; i++) {
			n2 = n2 * 10 + p[i];
		}
		int n3 = 0;
		for (int i = cut1+cut2; i < p.length; i++) {
			n3 = n3 * 10 + p[i];
		}
		return n1 * n2 == n3? n3 : -1;
		
	}
	
	/* Given a permutation (pandigital int), return how many partitions yield a true multiplication. */
	public static int count(int[] p) {
		int n = p.length;
		int ans = 0;
		for (int cut1 = 1; cut1 < n/2; cut1++) {
			for (int cut2 = 1; cut2 < n - cut1; cut2++) {
				int mult = cut(p,cut1,cut2);
				if (cut(p,cut1,cut2) > 0 && !found[mult]) {
					ans += mult;
					found[mult] = true;
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		/* 100 * 100 = 10000 which is already more than 9 digits. So any pandigital numbers we want to track to
		 * avoid duplicates will fall into this range. */
		found = new boolean[10000]; 
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int ans = 0;
		int[] p = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = i+1;
		}
		ans += count(p);
		while (nextPermutation(p)) {
			ans += count(p);
		}
		System.out.println(ans);
		s.close();
	}
}