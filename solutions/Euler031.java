/* -------- SOLVED -------- */

/* In England the currency is made up of pound £, and pence, p, and there are eight coins in general circulation:
 * 
 * 1p, 2p, 5p, 10p, 20p, 50p, 1£ (100p), 2£ (200p)
 * 
 * It is possible to make 2£ in the following way:
 * 
 * 1£ + 50p + 2x20p + 5p + 2p + 3x1p
 * 
 * How many different ways can N p be made using any number of coins? As the result can be large print answer 
 * mod 1000000007.
 * 
 * INPUT: 1 <= T <= 10^4, 1 <= N <= 10^5, N given in p. */

import java.util.Scanner;

public class Euler031 {
	
	/* Thoughts/approach: Classic ways to make change problem. */
	
	final static int MAX_N = 100000;
	
	final static int MOD = 1000000007;
	
	final static int[] coins = new int[] {1,2,5,10,20,50,100,200};
	
	public static void main(String[] args) {
		/* Precalculate and memoize. */
		int[] dp = new int[MAX_N+1];
		dp[0] = 1;
		for (int coin: coins) {
			for (int val = coin; val <= MAX_N; val++) {
				dp[val] += dp[val-coin];
				dp[val] = dp[val] % MOD;
			}
		}
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			System.out.println(dp[n]);
		}
		s.close();
	}
}