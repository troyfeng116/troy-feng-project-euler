/* -------- UNSOLVED: 50/100 --------*/

/* Based on Fifteen Puzzle. In this case, it's NxN board. (N^2-1)/2 red tiles, N^2/2 blue tiles.
 * 
 * A move L,U,R,D means you move the white tile to the other way. (i.e. L means you switch white with tile
 * to the right).
 * 
 * For a move sequence m1m2...mk, checksum is defined as: checksum0 = 0, checksumi = (checksum{i-1} + mi) mod 100000007,
 * where mi is the ASCII value of the mi \in [LURD].
 * 
 * Given starting config S and end E, find all shortest paths from S to E. Find sum of all checksums for paths of
 * minimum length.
 * 
 * 
 * CONSTRAINTS: 2 <= N <= 4. Input given as N, then N lines for S, then N lines for E. 
 * 
 * L = 76, R = 82, U = 85, D = 68 
 * 
 * Sample: 
 * 
4
WRBB
RRBB
RRBB
RRBB
RRBB
RBBB
RWRB
RRBB

 * should return 91440058.
*/

//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
//import java.util.List;
import java.util.Queue;
import java.util.Scanner;
//import java.util.Set;

public class Euler244 {
	
	/* Approach: BFS. Once a solution is found, stop all other solutions that exceed its steps.
	 * 
	 * NOTES: 
	 * 		bi-directional search? */
	
	static class Entry {
		long config; /* Configuration of tiles. */
		int length; /* Length of sequence L|U|R|D taken to reach config. */
		int checkSum; /* CheckSum of sequence taken to reach config. */
		int pos; /* Index of the blank tile in config. */
		
		public Entry(long config, int pos, int length, int checkSum) {
			this.config = config;
			this.pos = pos;
			this.length = length;
			this.checkSum = checkSum;
		}
	}
	
	static final int MOD = 100000007;
	
	static Map<Long,Integer> visited; // Track visited configs and length to get there.
		
	static int minimum; // Store minimum steps.
	
	static int n; // Store dimension globally.
	
	static Map<Character,Integer> charToInt;
	
	static Map<Integer,Character> intToChar;
	
	static long[] tenToThe;
	
	static boolean found = false;
	
	static long sum = 0;
	
	/* Check if pos can be swapped with nextPos in board. If true, swaps them. */
	public static boolean reachable(int pos, int next) {
		//if (next >= n*n || next < 0) return false;
		if (pos == next) return false;
		int p1 = pos+1; /* RIGHT */
		int p2 = pos-1; /* LEFT */
		int p3 = pos+n; /* DOWN */
		int p4 = pos-n; /* UP */
		/* Top left corner. */
		if (pos == 0) return next == p1 || next == p3;
		/* Top right corner. */
		if (pos == n-1) return next == p2 || next == p3;
		/* Bottom left corner. */
		if (pos == n*n-n) return next == p1 || next == p4;
		/* Bottom right corner. */
		if (pos == n*n-1) return next == p2 || next == p4;
		/* Top edge. */
		if (pos < n ) return next == p1 || next == p2 || next == p3;
		/* Bottom edge. */
		if (pos > n*n-n ) return next == p1 || next == p2 || next == p4;
		/* Left edge. */
		if (pos % n == 0) return next == p1 || next == p3 || next == p4;
		/* Right edge. */
		if (pos % n == n-1) return next == p2 || next == p3 || next == p4;
		/* Middle. */
		return next == p1 || next == p2 || next == p3 || next == p4;
	}
	
	/* Given n dimension of board, return direction of shift from pos to next. */
	public static int getDirection(int pos, int next) {
		if (next == pos+1) return (int) 'L';
		if (next == pos-1) return (int) 'R';
		if (next == pos+n) return (int) 'U';
		return (int) 'D';
	}
	
	/* Given that num is n*n digits, return number from swapping digits at indices pos and i, given that
	 * the digit at pos is 3. */
	public static long swap(long num, int numDigits, int pos, int i) {
		int iTemp = (int) ((num % tenToThe[numDigits-i]) / tenToThe[numDigits-i-1]);
		num -= 3 * tenToThe[numDigits-pos-1];
		num -= iTemp * tenToThe[numDigits-i-1];
		num += 3 * tenToThe[numDigits-i-1];
		num += iTemp * tenToThe[numDigits-pos-1];
		return num;
	}
	
	/* BFS. */
	/* Pseudocode: While queue not empty, remove head of queue. Add all reachable non visited configs from that head to queue.
	 * Visited tracks configs (length n). Paths tracks paths (length minimum).
	 * In queue, stored are configs reached by unique paths. First n*n elements are the config. Following indices store the path. */
	public static void bfs(long config, int startPos, long target) {
		Queue<Entry> q = new LinkedList<Entry>();
		Entry first = new Entry(config, startPos, 0, 0);
		q.add(first);
		while (!q.isEmpty()) {
			Entry e = q.remove();
			int length = e.length;
			if (length >= minimum) break;
			long currentConfig = e.config;
			int pos = e.pos;
			//int[] test = new int[] {pos-1, pos+1, pos-n, pos+n};
			for (int i = 0; i < n*n; i++) {//(int i: test) {
				/* For each reachable position, test if target or add to queue. */
				if (reachable(pos, i)) {
					long newConfig = swap(currentConfig, n*n, pos, i);
					int direction = getDirection(pos, i);
					int newCheckSum = (int) ((((long) e.checkSum * 243) + direction) % MOD);
					if (newConfig != target) {
						if (visited.containsKey(newConfig) && visited.get(newConfig) > length+1) ;
						else {
							visited.put(newConfig,length+1);
							q.add(new Entry(newConfig, i, length+1, newCheckSum));
						}
					}
					else {
						if (!found) {
							found = true;
							minimum = length+1;
						}
						sum += newCheckSum;
						sum = sum % MOD;
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		visited = new HashMap<Long,Integer>();
		minimum = Integer.MAX_VALUE;
		
		charToInt = new HashMap<Character,Integer>();
		charToInt.put('B', 1);
		charToInt.put('R', 2);
		charToInt.put('W', 3);
		
		intToChar = new HashMap<Integer,Character>();
		intToChar.put(1, 'B');
		intToChar.put(2, 'R');
		intToChar.put(3, 'W');
		
		tenToThe = new long[17];
		tenToThe[0] = 1;
		for (int i = 1; i <= 16; i++) {
			tenToThe[i] = tenToThe[i-1] * 10;
		}

		Scanner s = new Scanner(System.in);
		n = Integer.parseInt(s.nextLine());
		/* Read and store start. */
		long start = 0;
		int blankStart = 0;
		for (int i = 0; i < n; i++) {
			String sInput = s.nextLine();
			for (int j = 0; j < n; j++) {
				if (sInput.charAt(j) == 'W') blankStart = i*n+j;
				start = start*10 + charToInt.get(sInput.charAt(j));
			}
		}
		visited.put(start, 0);
		/* Read and store end. */
		long end = 0;
		for (int i = 0; i < n; i++) {
			String eInput = s.nextLine();
			for (int j = 0; j < n; j++) {
				end = end*10 + charToInt.get(eInput.charAt(j));
			}
		}
		//long startTime = System.nanoTime();
		bfs(start, blankStart, end);
		//long endTime = System.nanoTime();
		//long duration = (endTime - startTime) / 1000000;
		
		System.out.println(sum);
		//System.out.println("Runtime: " + duration + " ms");
		s.close();
	}
}
