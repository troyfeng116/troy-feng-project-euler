/* -------- UNSOLVED -------- */

/* A common security method used for online banking is to ask the user for three random characters from 
 * a passcode. For example, if the passcode was 531278, they may ask for the 2nd, 3rd, and 5th characters; 
 * the expected reply would be: 317.
 *
 * Given that the three characters are always asked for in order, analyse the file so as to determine the 
 * shortest possible secret passcode of unknown length.
 *
 * Assume all characters in your password are different. You're given T successful login attempts each 
 * containing 3 characters with ASCII codes from 33 to 126 both inclusive. You need to recover the 
 * shortest original password possible. If there are multiple choices, select the lexicographically 
 * smallest one.
 *
 * If something went wrong and the original password cannot be recovered, output SMTH WRONG. ex. 
5
SMH
TON
RNG
WRO
THG
 * should output SMTHWRONG, while
3
an0
n/.
.#a
 * should output SMTH WRONG.
 *
 * INPUTS: 1 <= T <= 3000 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Euler079 {
	
	/* Thoughts/approach: Each T gives us the relative orders of 3 characters in the password. It seems
	 * that we might be able to approach this as a digraph/topsort problem. I.e. if u comes before v, then
	 * we add an edge (u,v) to the graph. Process all T inputs. Then run topological sort. When we add a 
	 * node with in-degree 0, we reset and keep picking the node with in-degree 0 with lowest ASCII. */

	static final String NONE = "SMTH WRONG";
	static final int numNodes = 126-33+1;

	public static boolean allEdgesGone(int[] indegree) {
		for (int v: indegree) {
			if (v >= 0) return false;
		}
		return true;
	}

	public static String findPassword(boolean[][] adj, int[] indegree) {
		String ans = "";
		while (!allEdgesGone(indegree)) {
			boolean foundIndegreeZero = false;
			for (int u = 0; u < indegree.length; u++) {
				if (indegree[u] == 0) {
					indegree[u]--;
					foundIndegreeZero = true;
					for (int v = 0; v < adj[u].length; v++) {
						if (adj[u][v]) {
							System.out.println("Removed edge " +((char) (u+33)) + ','+((char) (v+33)));
							indegree[v]--;
							System.out.println("Edge " + ((char) (v+33)) + " now has indegree " + indegree[v]);
						}
					}
					ans += (char)(u+33);
					break;
				}
			}
			if (!foundIndegreeZero) break;
		}
		return allEdgesGone(indegree)? ans : NONE;
	}
	
	public static void main(String[] args) {
		boolean[][] adj = new boolean[numNodes][numNodes];
		int[] indegree = new int[numNodes];
		Arrays.fill(indegree,-1);
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String input = s.nextLine();
			for (int i = 0; i < 2; i++) {
				int u = input.charAt(i)-33;
				int v = input.charAt(i+1)-33;
				adj[u][v] = true;
				indegree[u] = indegree[u]==-1? 0 : indegree[u];
				indegree[v] = indegree[v]==-1? 1 : indegree[v]+1;
			}
		}
		System.out.println(findPassword(adj,indegree));
		s.close();
	}
}
