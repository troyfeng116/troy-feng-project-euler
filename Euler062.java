/* -------- UNSOLVED -------- */

/* The cube, 41063625=345^3, can be permuted to produce two other cubes: 56623104=384^3 and 66430125=405^3.
 * 
 * In fact, 41063625 is the smallest cube such that exactly three permutations of its digits are also cubes
 *
 * You are given N, find the smallest cube for which exactly K permutations of its digits are the cube of 
 * some number which is < N. If there are multiple sets, print the minimal element of each in sorted order.
 *
 * INPUTS: 1000 <= N <= 10^6, 3 <= K <= 49, space separated N K */

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Euler062 {
	
	/* Thoughts/approach: I guess we can think of all permutations of a cube as a "family." Each family is
	 * a fixed size, and each families is unique and disjoint (on account of the distribution of digits).
	 * Then we're looking for the smallest cube whose family includes exactly K cubes, where all of those
	 * K cubes must be LESS THAN N^3.
	 *
	 * Since K goes up to 49 and N goes up to 10^6 so N^3 goes up to 10^18, I'm not sure a brute force
	 * approach would be feasible. 18-digit numbers can have 18!/2^8 permutations. Instead, I think we
	 * can keep a HashMap. The key will be a long corresponding to the count of each digit 
	 * (ex. 41063625 = 0002111111; there's 1 of each of 0-5 and 2 6s in 41063625). Thus, keys are unique 
	 * across families but equal within families. Then, value will contain a pair: first will hold the 
	 * count of cubes with that key, and second will hold the min cube in that family. We loop from i:[1,N), 
	 * finding the keys for each i^3 and incrementing in HashMap. Then, we loop over valueSet and return 
	 * min second such that first = K. */

	static class Pair {
		int first;
		long second;
		public Pair(int f, long s) {
			first = f;
			second = s;
		}
	}

	/* tenToThe[k] = 10^k */
	static long[] tenToThe;

	public static void fillTenToThe() {
		tenToThe = new long[10];
		tenToThe[0] = 1;
		for (int i = 1; i < 10; i++) {
			tenToThe[i] = 10*tenToThe[i-1];
		}
	}

	/* Return count of each digit in n as a long, where digit in 10^k place holds the number of times k
	 * appears as a digit in n. */
	public static long countOfDigits(long n) {
		long ans = 0;
		while (n != 0) {
			int digit = (int) (n%10);
			ans += tenToThe[digit];
			n/=10;
		}
		return ans;
	}

	/* Given N,K, return min i such that i^3 is in a family with exactly K cubes less than N^3. */
	public static long solution(long N, int K) {
		Map<Long,Pair> table = new HashMap<Long,Pair>();
		for (int i = 1; i < N; i++) {
			long i2 = (long) i;
			long cube = i2*i2*i2;
			long key = countOfDigits(cube);
			Pair oldEntry = table.getOrDefault(key, new Pair(0,cube));
			Pair newEntry = new Pair(oldEntry.first+1, oldEntry.second);
			table.put(key, newEntry);
		}
		long x = Long.MAX_VALUE;
		for (Pair p: table.values()) {
			if (p.first == K) x = Math.min(x, p.second);
		}
		return x;
	}
	
	public static void main(String[] args) {
		fillTenToThe();
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		long N = Integer.parseInt(inputs[0]);
		int K = Integer.parseInt(inputs[1]);
		System.out.println(solution(N,K));
		s.close();
	}
}
