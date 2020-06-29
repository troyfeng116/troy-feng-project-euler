/* -------- SOLVED -------- */

/* The arithmetic sequence, 1487, 4817, 8147 in which each of the terms increases by 3330 is unusual in 
 * two ways: (i) each of the three terms are prime, and, (ii) each of the 4-digit numbers are permutations 
 * of one another.
 * 
 * There are no arithmetic sequences made up of three 1-, 2-, or 3-digit primes, exhibiting this property.
 * 
 * You are given N and K, find all sequences of length K where first element is less than N and the K elements 
 * are permutations of each other, are prime and are in AP (Arithmetic Progression).
 * 
 * Print the answer as concatenated integer formed by joining the K terms.
 * 
 * INPUT: 2000 <= N <= 1000000, 3 <= K <= 4, space separated N K */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Euler49 {
	
	/* Thoughts/approach: Brute force would be to loop through primes up to N, look at prime permutations,
	 * and see if any form an arithmetic progression of length 3 or 4. Seeing as to how the inputs go up to
	 * six digits, that would definitely be too slow.
	 * 
	 * There's a neat way to store prime permutations. We can represent the prime number 1019 as 020000001,
	 * where the i'th (zero-based) digit of that ten-digit number is the number of times i appears in the
	 * original number. This mapping has the useful property that numbers that are digit-wise permutations
	 * of each other will map to the same key.  */
	
	/* Primes in permutation sequences may be larger than 10^6. */
	static final int MAX_N = 10000000;
	static boolean[] composite;
	static long[] tenToThe;
	
	/* table.get(k) returns a list of primes with the same digit permutation as k. */
	static Map<Long,List<Integer>> table;
	
	/* ans maintains a list of strings representing concatenated arithmetic sequences of primes (not 
	 * necessarily sorted). */
	static List<String> ans;
	
	
	/* 78498 primes <= MAX_N */
	public static void sieve() {
		composite = new boolean[MAX_N+1];
		for (int i = 2; i <= MAX_N; i++) {
			if (!composite[i] && i <= 1000) {
				for (int j = i*i; j <= MAX_N; j+=i) {
					composite[j] = true;
				}
			}
		}
	}
	
	/* Fill tenToThe for O(n) powers of ten. */
	public static void fillTenToThe() {
		tenToThe = new long[10];
		tenToThe[0] = 1;
		for (int i = 1; i <= 9; i++) {
			tenToThe[i] = 10 * tenToThe[i-1];
		}
	}
	
	/* Insert prime into table based on number-of-digits representation as key mapping. Used in fillTable. */
	public static void putInTable(int prime) {
		long key = countDigits(prime);
		if (table.containsKey(key)) table.get(key).add(prime);
		else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(prime);
			table.put(key, list);
		}
	}
	
	/* Fill table such that if k is prime, then table.get(k) returns a list of primes that are permutations
	 * of the digits of k. Note that each list is in increasing order. */
	public static void fillTable() {
		table = new HashMap<Long,List<Integer>>();
		for (int i = 11; i <= MAX_N; i+=6) {
			if (!composite[i]) {
				putInTable(i);
			}
			if (i+2 <= MAX_N && !composite[i+2]) {
				putInTable(i);
			}
		}
	}
	
	/* Return number-of-digits representation of n. */
	public static long countDigits(int n) {
		long ans = 0;
		while (n != 0) {
			ans += tenToThe[n%10];
			n /= 10;
		}
		return ans;
	}
	
	/* Search a list of at least k primes, all of which are permutations of each other's digits. If an
	 * arithmetic progression of k primes is found, the string formed by concatenating those terms is
	 * added to static ArrayList ans. Note that list is in increasing order. Checks to ensure that first
	 * term is < N. */
	public static void search(List<Integer> list, int k, int n) {
		int len = list.size();
		for (int first = 0; first+k-1 < len; first++) {
			int x1 = list.get(first);
			if (x1 >= n) return;
			for (int second = first+1; second+k-2 < len; second++) {
				int x2 = list.get(second);
				int d = x2 - x1;
				aux(list, k-2, len, second+1, d, ""+x1+x2, x2);
			}
		}
	}
	
	/* Auxiliary recursive function used in search. Searches for an arithmetic sequence of length k,
	 * among the start -> n-1 last terms of list, with common difference d. */
	public static void aux(List<Integer> list, int k, int len, int start, int d, String soFar, int prev) {
		//System.out.println("calling aux with " + soFar);
		if (k == 0) ans.add(soFar);
		else if (start == len) ;
		else {
			for (int i = start; i < len; i++) {
				int x = list.get(i);
				if (x - prev == d) aux(list, k-1, len, i+1, d, soFar+x, x);
			}
		}
	}
	
	/* Modified sorting algorithm based on quickSort that prioritizes length of string, then lexicographic
	 * order. */
	public static void sort(List<String> list, int l, int r) {
		if (l >= r) return;
		String splitter = list.get(r);
		int lenSplitter = splitter.length();
		int m = l;
		for (int i = l; i < r; i++) {
			if (list.get(i).length() < lenSplitter) {
				Collections.swap(list, i, m);
				m++;
			}
			else if (list.get(i).compareTo(splitter) < 0) {
				Collections.swap(list, i, m);
				m++;
			}
		}
		Collections.swap(list, m, r);
		sort(list, l, m-1);
		sort(list, m+1, r);
	}
	
	
	public static void main(String[] args) {
		/* Pre-calculations. */
		sieve();
		fillTenToThe();
		fillTable();
		ans = new ArrayList<String>();
		
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int k = Integer.parseInt(inputs[1]);
		for (int p = 11; p < n; p+=6) {
			if (!composite[p]) {
				long key = countDigits(p);
				if (table.containsKey(key) && table.get(key).size() >= k) {
					search(table.get(key), k, n);
					table.remove(key);
				}
			}
			if (p+2 < n && !composite[p+2]) {
				long key = countDigits(p+2);
				if (table.containsKey(key) && p+2 < n && table.get(key).size() >= k) {
					search(table.get(key), k, n);
					table.remove(key);
				}
			}
		}
		sort(ans, 0, ans.size()-1);
		for (int i = 0; i < ans.size(); i++) {
			System.out.println(ans.get(i));
		}
		
		s.close();
	}
}
