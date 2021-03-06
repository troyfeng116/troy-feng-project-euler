/* -------- SOLVED -------- */

/* The primes 3, 7, 109, and 673, are quite remarkable. By taking any two primes and concatenating them 
 * in any order the result will always be prime. For example, taking 7 and 109, both 7109 and 1097 are prime.
 * The sum of these four primes, 792, represents the lowest sum for a set of four primes with this property.
 *
 * Find the sums of all sets of K primes for which any two primes (prime < N) concatenate to produce another 
 * prime. Print the sums in sorted order.
 *
 * INPUTS: 100 <= N <= 20000, 3 <= K <= 5, space separated N K */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Euler060 {
	
	/* Thoughts/approach: For concatenation of two integers, string concatenation would work but perhaps
	 * counting digits and adding n1*10^numDigits to n2 might be better? 
	 *
	 * After playing around, I found that there are 47673 PAIRS of primes less than 20000 that, when
	 * concatenated with each other, form another prime by inputting N=20000 and K=2 (!). This does not 
	 * bode well for searching for K = 3, 4, or 5 using the method described above. I think precalculating
	 * and memoizing pairs might work.
	 * 
	 * A nice observation: All pairs are equivalent mod 3 with the exception of those including 3 itself.
	 * i.e. p1 and p2 can only be a concatenable pair if p1 and p2 are equivalent mod 3. This is because
	 * a number is equivalent to the sum of its digits mod 3. So all K-tuples must be in one group,
	 * excluding 3. Finally, once we have all possible pairs sorted, we can test all sets of K-tuples
	 * with the same first prime, and then test if all of the second primes are pairs. This will help
	 * speed up generating pairs, as we reduce primality testing by about 50%.
	 *
	 * Once we have all concatenable pairs sorted, say we're looking for K-tuples whose least prime is 3. 
	 * We look at all sets of K-1 pairs whose first prime is 3, say (3,p1), (3,p2), and (3,p3) for K=4. 
	 * Then (p1,p2), (p1,p3), and (p2,p3) must all also be valid prime pairs. We can binary search for 
	 * those. */

	static final int MAX_N = 20000;
	static final int[] tenToThe = new int[] {1,10,100,1000,10000,100000};
	static boolean[] composite;
	/* prime[k] retrieves the k'th prime (zero-based). i.e. prime[0] = 2, prime[1] = 3, ... */
	static int[] prime;
	
	/* Precomputed number of pairs of concatenable primes. */
	static final int NUM_PAIRS = 47673;
	/* Holds the concatenable pairs of primes in sorted order. */
	static int[][] pairs;
	/* numPairsWith[k] holds the number of concatenable pairs where k is the first in the pair. */
	static int[] numPairsWith;

	/* prime1[k] retrieves the k'th prime equiv to 1%3 (0-based). i.e. prime1[0] = 7, prime[1] = 13, ... */
	static int[] prime1;
	/* prime1[k] retrieves the k'th prime equiv to 2%3 (0-based), not including 2 or 5. i.e. prime1[0] = 11, 
	 * prime[1] = 17, ... */
	static int[] prime2;

	/* Sieve primes up to 20000000, fill in-order mappings of k to k'th prime equiv to 1%3 or 2%3 in 
	 * prime1[] and prime2[], respectively, and fill in-order mapping of k to k'th prime in prime[]. */
	public static void sieve() {
		composite = new boolean[20000001];
		/* Sieve primes <= MAX_N. */
		int count1 = 0;
		int count2 = 0;
		for (int i = 2; i <= MAX_N; i++) {
			if (!composite[i]) {
				if (i%3==1) count1++;
				else if (i%3==2 && i>5) count2++;
				for (int j = i*i; j < composite.length; j+=i) {
					composite[j] = true;
				}
			}
		}
		prime = new int[count1+count2+3];
		prime[0] = 2;
		prime[1] = 3;
		prime[2] = 5;
		prime[3] = 7;
		int index = 4;
		prime1 = new int[count1];
		prime2 = new int[count2];
		prime1[0] = 7;
		int index1 = 1;
		int index2 = 0;
		for (int x = 11; x <= MAX_N; x+=6) {
			if (!composite[x]) {
				prime2[index2] = x;
				index2++;
				prime[index] = x;
				index++;
			}
			if (x+2 <= MAX_N && !composite[x+2]) {
				prime1[index1] = x+2;
				index1++;
				prime[index] = x+2;
				index++;
			}
		}
	}

	/* Generate all concatenable prime pairs and store in sorted order using prime1 and prime2. Similar
	 * to merging sorted lists. */
	public static void fillPairs() {
		pairs = new int[NUM_PAIRS][2];
		int index = 0;
		for (int j = 3; j < prime.length; j++) {
			int p = prime[j];
			if (isPrime(concat(3,p)) && isPrime(concat(p,3))) {
				pairs[index][0] = 3;
				pairs[index][1] = p;
				index++;
			}
		}
		int i1 = 0;
		int i2 = 0;
		while (i1 < prime1.length && i2 < prime2.length) {
			if (prime1[i1] < prime2[i2]) {
				int a = prime1[i1];
				for (int j = i1+1; j < prime1.length; j++) {
					int b = prime1[j];
					if (isPrime(concat(a,b)) && isPrime(concat(b,a))) {
						pairs[index][0] = a;
						pairs[index][1] = b;
						index++;
					}
				}
				i1++;
			}
			else {
				int a = prime2[i2];
				for (int j = i2+1; j < prime2.length; j++) {
					int b = prime2[j];
					if (isPrime(concat(a,b)) && isPrime(concat(b,a))) {
						pairs[index][0] = a;
						pairs[index][1] = b;
						index++;
					}
				}
				i2++;
			}
		}
		while (i1 < prime1.length) {
			int a = prime1[i1];
			for (int j = i1+1; j < prime1.length; j++) {
				int b = prime1[j];
				if (isPrime(concat(a,b)) && isPrime(concat(b,a))) {
					pairs[index][0] = a;
					pairs[index][1] = b;
					index++;
				}
			}
			i1++;
		}
		while (i2 < prime2.length) {
			int a = prime2[i2];
			for (int j = i2+1; j < prime2.length; j++) {
				int b = prime2[j];
				if (isPrime(concat(a,b)) && isPrime(concat(a,b))) {
					pairs[index][0] = a;
					pairs[index][1] = b;
					index++;
				}
			}
			i2++;
		}
	}

	/* Fill numPairsWith with how many times each prime appears as first in concatenable pairs. */
	public static void fillNumPairsWith() {
		numPairsWith = new int[MAX_N+1];
		for (int i = 0; i < pairs.length-1; i++) {
			int count = 1;
			while (i < pairs.length-1 && pairs[i][0] == pairs[i+1][0]) {
				count++;
				i++;
			}
			numPairsWith[pairs[i][0]] = count;
		}
		/* Increment count for last pair's first prime. */
		numPairsWith[pairs[pairs.length-1][0]]++;
	}

	/* Return n^pow % mod. */
	public static long pow(long n, long pow, long mod) {
		if (pow == 1) return n%mod;
		long half = pow(n, pow/2, mod);
		long ans = mult(half,half,mod);
		if (pow%2 == 1) ans = mult(ans, n, mod);
		return ans%mod;
	}

	/* Return a*b % mod. */
	public static long mult(long a, long b, long mod) {
		long x = (long) (((double)a*b)/mod);
		long ans = (a*b-x*mod);
		return ans<0? ans+mod : ans;
	}

	/* Miller-Rabin primality test. Note that for n < 4,759,123,141, it is enough and deterministic to test 
	 * a = 2, 7, and 61
	 * ( https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test ) */
	static final int[] witnessesToCheck = new int[] {2,7,61};
	public static boolean isPrime(long n) {
		if (n < composite.length) return !composite[(int) n];
		if (n%3 == 0 || n%5 == 0) return false;
		long d = n-1;
		int s = 0;
		while (d%2 == 0) {
			s++;
			d/=2;
		}
		for (int iter = 0; iter < witnessesToCheck.length; iter++) {
			int a = witnessesToCheck[iter];
			long x = pow(a,d,n);
			if (x == 1 || x == n-1) continue;
			boolean witness = false;
			for (int i = 0; i < s-1; i++) {
				x = mult(x,x,n);
				if (x == n-1) {
					witness = true;
					break;
				}
			}
			if (witness) continue;
			return false;
		}
		return true;
	}

	/* Return concatenation of n1 and n2. */
	public static long concat(int n1, int n2) {
		return (long) tenToThe[(int)Math.floor(Math.log10(n2)+1)]*n1 + n2;
	}

	/* Search for key k in pairs[][]. Binary search, return first index at which k occurs or -1. */
	public static int searchFirst(int k, int l, int r) {
		if (l > r) return -1;
		int m = (l+r)/2;
		if (k < pairs[m][0]) return searchFirst(k,l,m-1);
		if (k > pairs[m][0]) return searchFirst(k,m+1,r);
		while (m > 0 && pairs[m-1][0] == k) m--;
		return m;
	}

	/* Search for pair of primes (p1,p2) in pairs, using binary search to find first occurrence of p1 and
	 * then searching for p2 using binary search. */
	public static boolean searchPair(int p1, int p2) {
		int m = searchFirst(p1, 0, pairs.length-1);
		if (m < 0) return false;
		return searchPairAux(p2,m,m+numPairsWith[p1]-1);
	}

	/* Binary search for p2 in the second pair. Given that for all i:[l,r], pairs[i][0] = p1 from searchPair. */
	public static boolean searchPairAux(int p2, int l, int r) {
		if (l > r) return false;
		int m = (l+r)/2;
		if (p2 < pairs[m][1]) return searchPairAux(p2, l, m-1);
		if (p2 > pairs[m][1]) return searchPairAux(p2, m+1, r);
		return true;
	}

	/* Given the index of the first prime, add sums of all concatenable K-tuples starting with first prime
	 * to ans. */
	public static void findTuples(int firstPrime, int N, int K, List<Integer> ans) {
		int first = prime[firstPrime];
		int numTotal = numPairsWith[first];
		if (numTotal < K-1) return;
		int index = searchFirst(first, 0, pairs.length-1);
		/* For each pair with first = pair[0] */
		for (int i = index; i < index+numTotal && pairs[i][1] < N; i++) {
			int second = pairs[i][1];
			for (int j = i+1; j < index+numTotal && pairs[j][1] < N; j++) {
				int third = pairs[j][1];
				if (searchPair(second,third)) {
					if (K==3) {
						ans.add(first+second+third);
						continue;
					}
					for (int z = j+1; z < index+numTotal && pairs[z][1] < N; z++) {
						int fourth = pairs[z][1];
						if (searchPair(second,fourth) && searchPair(third,fourth)) {
							if (K==4) {
								ans.add(first+second+third+fourth);
								continue;
							}
							for (int y = z+1; y < index+numTotal && pairs[y][1] < N; y++) {
								int fifth = pairs[y][1];
								if (searchPair(second,fifth) && searchPair(third,fifth) && searchPair(fourth,fifth)) {
									ans.add(first+second+third+fourth+fifth);
								}
							}
						}
					}
				}
			}
		}
	}

	/* For each starting prime (3,7,11,13,...), find all K-tuples with that starting prime and add to ans. */
	public static List<Integer> solution(int N, int K) {
		List<Integer> ans = new ArrayList<Integer>();
		findTuples(1,N,K,ans);
		for (int i = 3; i < prime.length && prime[i] < N; i++) {
			findTuples(i,N,K,ans);
		}
		return ans;
	}
	
	public static void main(String[] args) {
		sieve();
		fillPairs();
		fillNumPairsWith();

		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int N = Integer.parseInt(inputs[0]);
		int K = Integer.parseInt(inputs[1]);
		List<Integer> res = solution(N,K);
		Collections.sort(res);
		for (int i = 0; i < res.size(); i++) System.out.println(res.get(i));
		s.close();
	}
}
