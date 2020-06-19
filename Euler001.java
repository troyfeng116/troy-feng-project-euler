/* -------- SOLVED -------- */

/* If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3,5,6 and 9. The sum of these 
 * multiples is 23.
 * 
 * Find the sum of all the multiples of 3 or 5 below N.
 * 
 * INPUT: 1st line T, T lines N.*/

import java.util.Scanner;

public class Euler001 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int nTests = Integer.parseInt(s.nextLine());
		for (int i = 0; i < nTests; i++) {
			long n = Long.parseLong(s.nextLine());
			long n1 = (n-1)/3;
			long n2 = (n-1)/5;
			long n3 = (n-1)/15;
			long sum3 = 3 * n1 * (n1+1)/2;
			long sum5 = 5 * n2 * (n2+1)/2;
			long overlap = 15 * n3 * (n3+1)/2;
			System.out.println(sum3 + sum5 - overlap);
		}
		s.close();
	}
}
