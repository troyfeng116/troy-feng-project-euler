/* -------- SOLVED -------- */

/* Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
 * If d(a) = b and d(b) = a, where a != b, then a and b are an amicable pair and each of a and b are called 
 * amicable numbers.
 * 
 * For example, the proper divisors of 220 are 1,2,4,5,10,11,20,22,44,55, and 110 therefore d(220) = 284. The 
 * proper divisors of 284 are 1,2,4,71, and 142 so d(284) = 220.
 * 
 * Evaluate the sum of all the amicable numbers under N. 
 * 
 * INPUTS: 1 <= T <= 1000, 1 <= N <= 10^5. */

//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Scanner;

public class Euler21 {
	
	/* Thoughts/approach: if we know prime factorization of N, finding sum of factors is straightforward.
	 * 
	 * Notice that if two numbers are amicable, then the sum of their divisors must be equal.
	 * 
	 * Does it make sense to store prime factorizations of all N up to 100,000 in jagged arrays? */
	
	final static int MAX_N = 100000;
	
	/* A modified Sieve of Eratosthenes such that sumFactors[k] holds the sum of the proper factors of
	 * k. */
	static int[] sumFactors;
	
	/* powers[k] holds an array whose i'th index holds k^i. */
	//static int[][] powers;
	
	/* primeFactorization[k] holds an array whose i'th index holds an array representing the prime
	 * factorization of k. */
	//static int[][] primeFactors;
	
	/* Stores (k,v) such that k is the sum of factors of v. */
	//static Map<Integer,Integer> sumFactors;
	
	//public static int sumFactors(int n) {
		/*if (factors[n] == n) return n+1;
		int[] exponents = new int[n];
		while (n != 1) {
			int factor = factors[n];
			exponents[factor]++;
			n /= factor;
		}*/
		/*int[] exponents = primeFactors[n];
		int ans = 1;
		for (int i = 2; i < exponents.length; i++) {
			if (exponents[i] != 0) {
				int sum = 0;
				for (int j = 0; j <= exponents[i]; j++) {
					sum += powers[i][j];
				}
				ans *= sum;
			}
		}
		return ans;
	}*/
	
	/* Return largest k such that base^k <= MAX_N */
	/*public static int greatestPow(int base) {
		int k = 0;
		int num = 1;
		while (num <= MAX_N) {
			num *= base;
			k++;
		}
		return k;
	}*/
	
	public static void main(String[] args) {
		sumFactors = new int[MAX_N+1];
		//powers = new int[MAX_N+1][];
		
		/* Modified Sieve of Eratosthenes. */
		for (int i = 1; i <= MAX_N; i++) {
			//if (sumFactors[i] == 1) {
				//sumFactors[i] = 1;
				
				/*powers[i] = new int[greatestPow(i) + 1];
				int pow = 1;
				for (int exp = 0; exp < powers[i].length; exp++) {
					powers[i][exp] = pow;
					pow *= i;
				}*/
			//}
			for (int j = 2*i; j <= MAX_N; j+=i) {
				sumFactors[j] += i;
			}
		}
		
		/*for (int i = 0; i < 100001; i++) System.out.println(sumFactors[i]);
		System.exit(0);*/
		
		/*primeFactors = new int[MAX_N+1][];
		primeFactors[1] = new int[] {1};
		for (int i = 2; i <= MAX_N; i++) {
			if (factors[i] == i) primeFactors[i] = new int[] {1,i};
			else {
				primeFactors[i] = Arrays.copyOf(primeFactors[i/factors[i]], i);
				primeFactors[i][factors[i]]++;
			}
		}*/
		
		/*for (int i = 0; i <= 10; i++) {
			if (powers[i] != null) {
				for (int j = 0; j < powers[i].length; j++) {
					System.out.print(powers[i][j] + " ");
				}
			}
			System.out.println();
		}
		System.out.println(sumFactors(10));
		System.exit(0);*/
		
		/*sumFactors = new HashMap<Integer,Integer>();
		boolean[] isAmicable = new boolean[MAX_N+1];
		for (int i = 1; i <= MAX_N; i++) {
			int sum = sumFactors(i) - i;
			if (sumFactors.containsKey(i)) {
				if (sumFactors.get(i) == sum) {
					isAmicable[sumFactors.get(i)] = true;
					isAmicable[i] = true;
					//System.out.println("found" + i);
				}
			}
			sumFactors.put(sum,i);
		}*/
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			int ans = 0;
			for (int i = 1; i < n; i++) {
				if (sumFactors[i] <= MAX_N && sumFactors[sumFactors[i]] == i && i != sumFactors[i]) {
					System.out.println("found " + i + " sum of factors  = " + sumFactors[i]);
					ans += i;
				}
				//if (isAmicable[i]) ans += i;
			}
			System.out.println(ans);
		}
		s.close();
	}
}
