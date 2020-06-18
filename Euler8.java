/* -------- SOLVED -------- */

/* Find the greatest product of K consecutive digits in the N digit number. 
 * 
 * INPUTS: 1 <= T <= 100, 1 <= K <= 7, K <= N <= 1000 */

import java.util.Scanner;

public class Euler8 {
	
	/* Naive approach: for each substring of length K, compare product against max. Worst case 70000 compares... 
	 * 
	 * Idea: from i = 0, slide incl. window i->j, where j = i+k-1. When slide, divide by i and multiply by j. */
	
	/* Given k <= n.length() and n is only non-zero digits, return largest product of k consecutive digits in n. */
	public static int findMax(String n, int k) {
		int product = 1;
		for (int j = 0; j < k; j++) {
			product *= (n.charAt(j) - 48);
		}
		int ans = product;
		/* Check product from i to i+k-1, inclusive. */
		for (int i = 1; i+k-1 < n.length(); i++) {
			product /= (n.charAt(i-1) - 48);
			product *= (n.charAt(i+k-1) - 48);
			if (product > ans) ans = product;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int i = 0; i < t; i++) {
			String[] inputs = s.nextLine().split(" ");
			int k = Integer.parseInt(inputs[1]);
			String numInput = s.nextLine();
			String[] subNumbers = numInput.split("0");
			int ans = 0;
			for (String sub: subNumbers) {
				if (sub.length() < k) ;
				else {
					ans = Math.max(findMax(sub,k), ans);
				}
			}
			System.out.println(ans);
		}
		s.close();
	}

}
