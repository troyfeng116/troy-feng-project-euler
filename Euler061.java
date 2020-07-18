/* -------- SOLVED -------- */

/* Triangle, square, pentagonal, hexagonal, heptagonal, and octagonal numbers are all figurate (polygonal) 
 * numbers and are generated by the following formulae:
 *
 *		Triangle 		T_n = n*(n+1)/2 	1,3,6,10,15,...
 *		Square 			S_n = n^2			1,4,9,16,25,...
 *		Pentagon 		P_n = n*(3n-1)/2	1,5,12,22,35,...
 *		Hexagon 		Hex_n = n*(2n-1)	1,6,15,28,45,...
 *		Heptagon 		Hep_n = n*(5n-3)/2 	1,7,18,34,55,...
 *		Octagon 		O_n = n*(3n-2)		1,8,21,40,65,...
 *
 * The ordered set of three 4-digit numbers: 8128, 2882, 8281, has three interesting properties:
 *		1. The set is cyclic, in that the last two digits of each number is the first two digits of the next
 *		   number (including the last number with the first).
 *		2. Each polygonal type: triangle (T_127 = 8128), square (S_91 = 8281), and pentagonal (P_44 = 2882), 
 * 		   is represented by a different number in the set.
 *		3. This is the only set of 4-digit numbers with this property.
 *
 * You are given a set of numbers N \in {3,4,5,6,7,8}. Find the sum of 4-digit numbers from N-gonal sets that 
 * respect the above property. If there are multiple such numbers print their sums in sorted order. 
 *
 * INPUTS: 3 <= T <= 6, then T numbers N space separated 3 <= N <= 8. 
 * Sample: input T=3, then N=3,4,5 --> output 19291 */

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Euler061 {
	
	/* Thoughts/approach: We are looking for sets of T numbers, without ANY duplicates it seems, that belong
	 * to N-gonal sets specified by a set of T total Ns. We need only check 4-digit N-gonal numbers. i.e. we
	 * need to find T distinct 4-digit figurate numbers, aligning with the given set of T Ns, that are cyclic 
	 * (by 2 digits). For N={3,4,5}, say we loop over 4-digit T_n and arrive at 8128. To get a cyclic square 
	 * number, we need a square that begins with 28. So I'm thinking we can keep HashMaps, mapping from 
	 * key \in [10,99] to val \in [1000,9999], where map.get(key) returns a four digit number that is figurate
	 * and has first two digits equal to key. These HashMaps can be pre-calculated. Then, given a set of N 
	 * say {3,4,5}, we would loop through all 4-digit T_n and search for T_n % 100 in the S_n and P_n HashMaps. 
	 *
	 * EDIT: At first, I made the mistake of thinking that only one figurate number existed for each 2-digit
	 * mapping (i.e. only one 4-digit figurate number begins with digits ab). This is clearly not the case,
	 * and I had to change my mappings from int->int to int->List. */

	/* Each of the following HashMaps contains two-digit keys. map.get(key) holds the four-digit figurate
	 * number(s) whose first two digits are equal to key. */
	static Map<Integer,List<Integer>> tri;
	static Map<Integer,List<Integer>> square;
	static Map<Integer,List<Integer>> pent;
	static Map<Integer,List<Integer>> hex;
	static Map<Integer,List<Integer>> hep;
	static Map<Integer,List<Integer>> oct;
	/* table.get(3) returns tri, table.get(4) returns square, etc. */
	static List<Map<Integer,List<Integer>>> table;

	/* Fill all the HashMaps with mappings of figurate numbers from 1000 to 9999. */
	public static void fillMaps() {
		tri = new HashMap<Integer,List<Integer>>();
		square = new HashMap<Integer,List<Integer>>();
		pent = new HashMap<Integer,List<Integer>>();
		hex = new HashMap<Integer,List<Integer>>();
		hep = new HashMap<Integer,List<Integer>>();
		oct = new HashMap<Integer,List<Integer>>();
		for (int n = 1; n*(n+1)/2 < 10000; n++) {
			/* n'th triangular number */
			int t = n*(n+1)/2;
			if (t >= 1000) {
				List<Integer> list = tri.containsKey(t/100) ? tri.get(t/100) : new ArrayList<Integer>();
				list.add(t);
				tri.put(t/100, list);
			}
			/* n'th square number */
			int s = n*n;
			if (s >= 10000) continue;
			if (s >= 1000) {
				List<Integer> list = square.containsKey(s/100) ? square.get(s/100) : new ArrayList<Integer>();
				list.add(s);
				square.put(s/100, list);
			}
			/* n'th pentagonal number */
			int p = n*(3*n-1)/2;
			if (p >= 10000) continue;
			if (p >= 1000) {
				List<Integer> list = pent.containsKey(p/100) ? pent.get(p/100) : new ArrayList<Integer>();
				list.add(p);
				pent.put(p/100, list);
			}
			/* n'th hexagonal number */
			int h1 = n*(2*n-1);
			if (h1 >= 10000) continue;
			if (h1 >= 1000) {
				List<Integer> list = hex.containsKey(h1/100) ? hex.get(h1/100) : new ArrayList<Integer>();
				list.add(h1);
				hex.put(h1/100, list);
			}
			/* n'th heptagonal number */
			int h2 = n*(5*n-3)/2;
			if (h2 >= 10000) continue;
			if (h2 >= 1000) {
				List<Integer> list = hep.containsKey(h2/100) ? hep.get(h2/100) : new ArrayList<Integer>();
				list.add(h2);
				hep.put(h2/100, list);
			}
			/* n'th octogonal number */
			int o = n*(3*n-2);
			if (o >= 10000) continue;
			if (o >= 1000) {
				List<Integer> list = oct.containsKey(o/100) ? oct.get(o/100) : new ArrayList<Integer>();
				list.add(o);
				oct.put(o/100, list);
			}
		}
		/* Add HashMaps to table, s.t. table.get(3) returns tri, table.get(6) returns hex, etc. */
		table = new ArrayList<Map<Integer,List<Integer>>>();
		for (int i = 0; i < 3; i++) table.add(null);
		table.add(tri);
		table.add(square);
		table.add(pent);
		table.add(hex);
		table.add(hep);
		table.add(oct);
	}

	/* Check if next occurs in used[]. */
	public static boolean alreadyUsed(int next, int[] used) {
		for (int i = 0; i < used.length; i++) {
			if (used[i]==next) return true;
		}
		return false;
	}

	/* Generate next lexicographic permutation of p[]. */
	public static boolean nextPermutation(int[] p) {
		int n = p.length;
		int k = n-2;
		while (k >= 0 && p[k] >= p[k+1]) k--;
		if (k < 0) return false;
		int l = n-1;
		while (p[l] <= p[k]) l--;
		int temp = p[l];
		p[l] = p[k];
		p[k] = temp;
		for (int i = k+1; i <= (n+k)/2; i++) {
			temp = p[n-i+k];
			p[n-i+k] = p[i];
			p[i] = temp;
		}
		return true;
	}

	/* Find sets of cyclic N-gonal numbers corresponding to given ordering of N[]. */
	public static void findCyclic(int[] N, int cur, int[] usedSoFar, Set<Integer> ans) {
		int T = N.length;
		if (cur == T) {
			/* Check if end and beginning are cyclic. */
			if (usedSoFar[T-1]%100 != usedSoFar[0]/100) return;
			int sum = 0;
			for (int i: usedSoFar) sum += i;
			ans.add(sum);
			return;
		}
		if (cur == 0) {
			int F = N[0];
			/* For each possible starting F-gonal number, find set of cyclic figurates. */
			for (List<Integer> l: table.get(F).values()) {
				for (int first: l) {
					usedSoFar[0] = first;
					findCyclic(N,1,usedSoFar,ans);
				}
			}
		}
		else {
			int prev = usedSoFar[cur-1];
			int key = prev%100;
			int n = N[cur];
			/* For each n-gonal figurate starting with key, extend this cycle. */
			if (table.get(n).containsKey(key)) {
				for (int next: table.get(n).get(key)) {
					if (!alreadyUsed(next,usedSoFar)) {
						usedSoFar[cur] = next;
						findCyclic(N,cur+1,usedSoFar,ans);
						usedSoFar[cur] = 0;
					}
				}
			}
		}
		
	}

	/* Starting with non-decreasing permutation of N[], generate all permutations and find sets of cyclic
	 * figurate numbers corresponding to each ordering of N[]. */
	public static Set<Integer> solution(int[] N) {
		Set<Integer> ans = new HashSet<Integer>();
		findCyclic(N, 0, new int[N.length], ans);
		while (nextPermutation(N)) {
			findCyclic(N, 0, new int[N.length], ans);
		}
		return ans;
	}

	/* Insert x into n, a la insertion sort. */
	public static void insert(int x, int[] n) {
		int k = n.length-2;
		while (k >= 0 && n[k] == 0) k--;
		while (k >= 0 && n[k] > x) {
			n[k+1] = n[k];
			k--;
		}
		n[k+1] = x;
	}
	
	public static void main(String[] args) {
		fillMaps();
		Scanner s = new Scanner(System.in);
		int T = Integer.parseInt(s.nextLine());
		String[] NInputs = s.nextLine().split(" ");
		int[] N = new int[T];
		/* Store input N in non-decreasing order. */
		for (int i = 0; i < T; i++) {
			int next = Integer.parseInt(NInputs[i]);
			insert(next, N);
		}	
		List<Integer> ans = new ArrayList<Integer>(solution(N));
		Collections.sort(ans);
		for (int n: ans) System.out.println(n);
		s.close();
	}
}
