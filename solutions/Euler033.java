/* -------- UNSOLVED 50/100 -------- */

/* The fraction 49/98 is a curious fraction. An inexperienced mathematician while attempting to simplify 
 * it may incorrectly believe that49/98 = 4/8 is obtained by canceling the 9's.
 * 
 * We shall consider fractions like, 30/50 = 3/5, to be trivial examples.
 * 
 * Which means fractions where trailing 0's are cancelled are trivial. So we will ignore all the cases 
 * where we have to cancel 0's.
 * 
 * You will be given 2 integers N and K. N represents the number of digits in Numerator and Denominator, and 
 * K represents the exact number of digits to be "cancelled" from Numerator and Denominator. Find every 
 * non-trivial fraction, where
 *  	(1) numerator is less than denominator, and
 *  	(2) the value of the reduced fraction is equal to the original fraction.
 *  
 *  Sum all the Numerators and the Denominators of the original fractions, and print them separated by a space.
 *  
 *  Input: Space separated N and K, 2 <= N <= 4, 1 <= K <= N-1 */

import java.util.Scanner;

public class Euler033 {
	
	/* Thoughts/approach: We represent 2-digit integer ab as 10a+b. So to get from ab to a ("cancel" b), we
	 * subtract 9a+b from ab. And to "cancel" a, we subtract 10a (or ab/10).
	 * 
	 * For 3-digit integer 100a + 10b + c:
	 * 		to "cancel" c and get from abc to ab = 10a+b, we subtract 90a+9b+c.
	 * 		to "cancel" b and get to ac = 10a+c, we subtract 90a+10b.
	 * 		to "cancel" a and get to bc = 10+c, we subtract 100a.
	 * 
	 * Since N is restricted to 4 digits, it shouldn't be too hard to brute force. For (k+1) digit integers
	 * up to all N digit integers, check all combinations of numerator and denominator. Need to check: at least
	 * k digits in common. Delete k digits at a time and check if cross products equal.
	 * 
	 * Might be helpful to represent ints as array?
	 * 
	 * EDIT: This brute force approach works for N=2 and N=3, but N=4 is timing out.
	 * 
	 *  
	 * 
	 * NEW APPROACH: Given n/d, we are looking for an equal n1/d1. So n1/n = d1/d.
	 * 
	 * For each n: for each n1 with (N-K) digits, reduce n1/n using GCD. The simplified fraction is
	 * (n1/gcd) / (n/gcd). For each big one multiples of that reduced fraction, check if any of those
	 * multiples have a denominator (d) with the digits of n that n1 doesn't have. Then verify d1 has
	 * the other digits of d.
	 * 
	 * Ex. for n = 49, we'd look at n1=4 and n1=9. For n1=4, it's already reduced. We iterate through
	 * multiples of 4/49 such that d has N digits; in this case, we get to 8/98. Then, since 98 has
	 * the digit 9 which n has but n1 doesn't, we check if d1=8 has the other digits of d. Since it does
	 * have an 8, 49/98 must work. */
	

	
	/* FIRST ATTEMPT: WORKS for N=2 and N=3 but times out for N=4. */

	static int[] tenToThe;
	
	public static int gcd(int a, int b) {
		if (a == b) return a;
		if (a == 0) return b;
		if (b == 0) return a;
		return gcd(b, a%b);
	}
	
	public static boolean isCurious(int num, int denom, int n, int k) {
		//System.out.println("-------- ISCURIOUS --------");
		
		int[][] pairs = numCommon(num, denom);
		/*
		System.out.print("PAIRS: ");
		for (int i = 0; i < pairs.length; i++) {
			System.out.print(pairs[i][0] + " " + pairs[i][1] + "   ");
		}
		System.out.println();
		*/
		int numCommon = pairs.length;
		if (numCommon < k) return false;
		// For each combo of k pairs, remove and check cross product.
		if (numCommon == 1) {
			return check(num, denom, pairs, new boolean[] {true});
		}
		if (numCommon == 2) {
			if (k == 2) {
				return check(num, denom, pairs, new boolean[] {true,true});
			}
			if (k == 1) {
				if (check(num, denom, pairs, new boolean[] {true,false})) return true;
				if (check(num, denom, pairs, new boolean[] {false,true})) return true;
				return false;
			}
		}
		if (numCommon == 3) {
			if (k == 1) {
				if (check(num, denom, pairs, new boolean[] {true,false,false})) return true;
				if (check(num, denom, pairs, new boolean[] {false,true,false})) return true;
				if (check(num, denom, pairs, new boolean[] {false,false,true})) return true;
				return false;
			}
			if (k == 2) {
				if (check(num, denom, pairs, new boolean[] {true,true,false})) return true;
				if (check(num, denom, pairs, new boolean[] {false,true,true})) return true;
				if (check(num, denom, pairs, new boolean[] {true,false,true})) return true;
				return false;
			}
			if (k == 3) {
				return check(num, denom, pairs, new boolean[] {true,true,true});
			}
		}
		if (numCommon == 4) {
			if (check(num, denom, pairs, new boolean[] {true,true,true,false})) return true;
			if (check(num, denom, pairs, new boolean[] {true,true,false,true})) return true;
			if (check(num, denom, pairs, new boolean[] {true,false,true,true})) return true;
			if (check(num, denom, pairs, new boolean[] {false,true,true,true})) return true;
			return false;
		}
		
		return false;
	}

	/* toRemove is parallel to pairs. If toRemove[l], then remove pair[l]. There should be k indices of
	 * toRemove that are true. */
	public static boolean check(int num, int denom, int[][] pairs, boolean[] toRemove) {
		//System.out.println("-------- CHECK --------");
		
		int[] numIndices = new int[pairs.length];
		int[] denomIndices = new int[pairs.length];
		for (int i = 0; i < pairs.length; i++) {
			numIndices[i] = pairs[i][0];
			denomIndices[i] = pairs[i][1];
		}
		/*
		System.out.print("NUMINDICES: ");
		for (int i: numIndices) System.out.print(i + " ");
		System.out.println();
		System.out.print("DENOMINDICES: ");
		for (int i: denomIndices) System.out.print(i + " ");
		System.out.println();
		*/
		int num2 = process(num, numIndices, toRemove);
		int denom2 = process(denom, denomIndices, toRemove);
		return num2 * denom == num * denom2 && num2 != 0;
	}

	/* indices and toRemove are the same size. If toRemove[l], then remove digit at indices[l]. */
	public static int process(int x, int[] indices, boolean[] toRemove) {
		//System.out.println("-------- PROCESS --------");
		
		int[] digits = toArray(x);
		for (int i = 0; i < toRemove.length; i++) {
			if (toRemove[i]) {
				digits[indices[i]] = -1;
			}
		}
		/*
		System.out.println("DIGITS AFTER MARKING TO REMOVE");
		for (int i: digits) System.out.print(i + " ");
		System.out.println();
		*/
		int place = 0;
		int ans = 0;
		for (int i = digits.length-1; i >= 0; i--) {
			if (digits[i] >= 0) {
				ans += tenToThe[place] * digits[i];
				place++;
			}
		}
		
		//System.out.println("FINAL ANS " + ans);
		
		return ans;
	}
	
	public static int[] toArray(int x) {
		int copy = x;
		int size = 0;
		while (copy != 0) {
			size++;
			copy /= 10;
		}
		int[] ans = new int[size];
		for (int i = 0; i < size; i++) {
			ans[size-i-1] = x % 10;
			x /= 10;
		}
		return ans;
	}
	
	/* Return pairs of common digits between n1 and n2. Pairs[l] contains a pair of indices i and j
	 * such that the i'th digit of n1 and the j'th digit of n2 are equal. */
	public static int[][] numCommon(int n1, int n2) {
		int count = 0;
		String str1 = Integer.toString(n1);
		String str2 = Integer.toString(n2);
		int len = str1.length();
		boolean[] matched = new boolean[len];
		for (int i = 0; i < len; i++) {
			if (str1.charAt(i) == '0') continue;
			int j = str2.indexOf(str1.charAt(i));
			while (j >= 0 && matched[j]) {
				j = str2.indexOf(str1.charAt(i),j+1);
			}
			if (j >= 0 && !matched[j]) {
				count++;
				matched[j] = true;
			}
		}
		int[][] ans = new int[count][2];
		int found = 0;
		boolean[] matched2 = new boolean[len];
		for (int i = 0; i < len; i++) {
			if (matched[i]) {
				int j = str1.indexOf(str2.charAt(i));
				while (j >= 0 && matched2[j]) {
					j = str1.indexOf(str2.charAt(i),j+1);
				}
				if (j >= 0 && !matched2[j]) {
					matched2[j] = true;
					ans[found][0] = j;
					ans[found][1] = i;
					found++;
				}
			}
		}
	
		return ans;
	}
	
	public static void main(String[] args) {
		tenToThe = new int[6];
		tenToThe[0] = 1;
		for (int i = 1; i <= 5; i++) {
			tenToThe[i] = tenToThe[i-1] * 10;
		}
		/*
		System.out.println(isCurious(3016,6032,4,2));
		System.exit(0);
		*/
		// 4 3 -> 255983 467405
		
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int k = Integer.parseInt(inputs[1]);
		int nSum = 0;
		int dSum = 0;
		for (int numerator = tenToThe[n-1]; numerator < tenToThe[n]; numerator++) {
			for (int denominator = numerator+1; denominator < tenToThe[n]; denominator++) {
				//System.out.println(numerator + "  " + denominator);
				if (isCurious(numerator,denominator,n,k)) {
					System.out.println("FOUND: " + numerator + " " + denominator);
					nSum += numerator;
					dSum += denominator;
				}
			}
		}
		System.out.println(nSum + " " + dSum);
		s.close();
	}
}