/* -------- SOLVED -------- */

/* It can be seen that the number, 125874, and its double, 251748, contain exactly the same digits, but 
 * in a different order.
 * 
 * Given N, find all the positive integers, x <= N, such that x, 2x, ..., Kx contain the same digits. 
 * 
 * INPUTS: 125875 <= N <= 2000000, 2 <= K <= 6, space-separated N K
 * OUTPUT: all the K multiples. If greater than one x, print each in a new line.
 * 
 * Note 1: It is guaranteed a solution exists.
 * Note 2: You should not consider solutions with leading 0's. */

import java.util.Scanner;

public class Euler052 {
	
	/* Thoughts/approach: clearly, a family x, 2x, ..., kx of all permutations must all also have the same
	 * number of digits. So this immediately limits our searches to brackets between 10^t and 10^{t+1}/k,
	 * i.e. for k = 2 we only need to search x from, say, 1000 to 4999 and not 1000 to 9999.
	 * 
	 * Other than that, we can determine whether two numbers are permutations of each other in O(#digits)
	 * time, using a function that maps integer n1 -> long n2, where n2 is 10 digits long and the i'th
	 * place digit denotes the number of times the digit i appears in n1. */

	static long[] tenToThe;

	/* Initialize tenToThe so that tenToThe[k] = 10^k. */
	public static void fillTenToThe() {
		tenToThe = new long[10];
		tenToThe[0] = 1;
		for (int i = 1; i < 10; i++) {
			tenToThe[i] = tenToThe[i-1] * 10;
		}
	}

	/* Convert integer n to a 10-digit long n, where the i'th place of the long is the number of times
	 * i appears in n as a digit. Ex. 4209 -> 1000010101. Thus if numEachDigit(n1) = numEachDigit(n2),
	 * and n1 != n2, then n1 and n2 must be permutations of each other. */
	public static long numEachDigit(int n) {
		long ans = 0;
		while (n != 0) {
			int digit = n%10;
			ans += tenToThe[digit];
			n /= 10;
		}
		return ans;
	}

	public static void solution(int N, int K) {
		int pow = 0;
		while (tenToThe[pow] <= N) {
			for (int x = (int) tenToThe[pow]; x < (int) tenToThe[pow+1]/K+1 && x <= N; x++) {
				long target = numEachDigit(x);
				int mult = 1;
				while (numEachDigit(x*(mult+1)) == target && mult < K) {
					mult++;
				}
				if (mult == K) {
					System.out.print(x);
					for (int i = 2; i <= K; i++) System.out.print(" " + i*x);
					System.out.println();
				}
			}
			pow++;
		}
	}
	
	public static void main(String[] args) {
		fillTenToThe();

		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int N = Integer.parseInt(inputs[0]);
		int K = Integer.parseInt(inputs[1]);
		solution(N,K);
		s.close();
	}
}
