/* -------- SOLVED -------- */

/* Take the number 192 and multiply it by each of 1, 2, and 3:
 * 
 * 192 x 1 = 192
 * 192 x 2 = 384
 * 192 x 3 = 576
 * 
 * By concatenating each product we get the 1 to 9 pandigital, 192384576. We will call 192384576 the concatenated 
 * product of 192 and (1,2,3).
 * 
 * The same can be achieved by starting with 9 and multiplying by 1, 2, 3, 4, and 5, giving the pandigital
 * 918273645, which is the concatenated product of 9 and (1,2,3,4,5). Let's call 9 the multiplier M.
 * 
 * A similar process can be done for 1 to 8 pandigital also. 18 when multiplied by (1,2,3,4) gives 18365472 which is 
 * 1-8 pandigital.
 * 
 * You are given N and K where k = 8 or 9, find the multipliers for that given K below N and print them in ascending
 * order.
 * 
 * INPUT: 100 <= N <= 10^5, 8 <= K <= 9 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Euler038 {
	
	/* Thoughts/approach: Generate all permutations of 1-K. For each permutation, check if the first 1-digit number is
	 * a multiplier. Then check first 2 digits, 3 digits, and 4 digits. 
	 * 
	 * This approach does NOT find multipliers in increasing order, so we have to sort them after all are found.
	 * Moreover, another drawback of this approach is we have to check all 8! or 9! permutations no matter what N 
	 * is. But I find this approach more interesting than just brute forcing from 9 to n... */
	

	/* Return true is there is a next lexicographical permutation of p, where p consists of digits from 1-8 or 1-9.
	 * Generates next permutation if true. */
	public static boolean hasNextPermutation(int[] p) {
		/* To find next permutation, find rightmost index s.t. p[k] < p[k+1]. Then find rightmost index l such that
		 * p[k] < p[l]. Swap p[k] and p[l], and invert the array from k+1 to end. (same as finding next biggest
		 * integer) */
		int k = p.length-2;
		while (k >= 0 && p[k] > p[k+1]) k--;
		if (k < 0) return false;
		int l = p.length-1;
		while (p[l] < p[k]) l--;
		int temp = p[k];
		p[k] = p[l];
		p[l] = temp;
		/* Invert rest of permutation. */
		for (int i = k+1; i <= (p.length+k)/2; i++) {
			temp = p[i];
			p[i] = p[p.length-i+k];
			p[p.length-i+k] = temp;
		}
		return true;
	}
	
	/* Converts l to r left-inclusive indices of p to an integer. If r exceeds boundary, returns -1. */
	public static int toInt(int[] p, int l, int r) {
		if (r > p.length) return -1;
		int ans = 0;
		for (int i = l; i < r; i++) {
			ans = ans*10 + p[i];
		}
		return ans;
	}
	
	/* If first i digits of p form a multiplier x s.t. x, x*2, x*3,... forms a k-pandigital sequence,
	 * returns x. Else returns 0. */
	public static int isPanMultiple(int[] p, int i, int k) {
		int mult = toInt(p,0,i);
		if (i == 2) {
			/* A 2-digit multiplier m can form a 8-pandigital iff m*1 through m*4 is all 2 digits, or if m*2 is three 
			 * digits. */
			if (k == 8) {
				boolean twoTwoTwoTwo = mult*2==toInt(p,i,i+2) && mult*3==toInt(p,i+2,i+4) && mult*4==toInt(p,i+4,i+6);
				boolean twoThreeThree = mult*2==toInt(p,i,i+3) && mult*3==toInt(p,i+3,i+6);
				return twoTwoTwoTwo || twoThreeThree ? mult : 0;
			}
			/* A 2-digit multiplier m can form a 9-pandigital iff m*1 through m*3 is 2 digits and m*4 is 3 digits. */
			boolean twoTwoTwoThree = mult*2==toInt(p,i,i+2) && mult*3==toInt(p,i+2,i+4) && mult*4==toInt(p,i+4,i+7);
			return twoTwoTwoThree ? mult : 0;
		}
		if (i == 3) {
			/* A 3-digit multiplier can't form an 8-pandigital. */
			if (k == 8) return 0;
			/* A 3-digit multiplier m can form a 9-pandigital iff m*1 through m*3 is 3 digits. */
			boolean threeThreeThree = mult*2==toInt(p,i,i+3) && mult*3==toInt(p,i+3,i+6);
			return threeThreeThree ? mult : 0;
		}
		if (i == 4) {
			/* A 4-digit multiplier m can form an 8-pandigital iff m*2 is 4 digits. */
			if (k == 8) {
				boolean fourFour = mult*2==toInt(p,i,i+4);
				return fourFour ? mult : 0;
			}
			/* A 4-digit multiplier m can form a 9-pandigital iff m*2 is 5 digits. */
			boolean fourFive = mult*2==toInt(p,i,i+5);
			return fourFive ? mult : 0;
			
		}
		return 0;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int k = Integer.parseInt(inputs[1]);
		int[] permutation = new int[k];
		for (int i = 0; i < k; i++) {
			permutation[i] = i+1;
		}
		/* Store found multiples. */
		List<Integer> ans = new ArrayList<Integer>();
		/* It can be quickly verified that the only one-digit multiplier is 9 for n=9. */
		if (k == 9) ans.add(9);
		/* Check first permutation (for sake of completeness). */
		for (int i = 2; i <= 4; i++) {
			int mult = isPanMultiple(permutation, i, k);
			if (mult > 0 && mult < n) ans.add(mult);
		}
		/* Now, for each permutation, we have to check if the first 2, 3, or 4 digits are a valid multiplier. */
		while (hasNextPermutation(permutation)) {
			for (int i = 2; i <= 4; i++) {
				int mult = isPanMultiple(permutation, i, k);
				if (mult > 0 && mult < n) ans.add(mult);
			}
		}
		Collections.sort(ans);
		for (int i = 0; i < ans.size(); i++) {
			System.out.println(ans.get(i));
		}
		s.close();
	}
}
