/* -------- SOLVED -------- */

/* You are given around five-thousand first names, begin by sorting it into alphabetical order. Then 
 * working out the alphabetical value for each name, multiply this value by its alphabetical position 
 * in the list to obtain a name score.
 * 
 * For example, when the list in sample is sorted into alphabetical order, PAMELA, which is worth 
 * 16 + 1 + 13 + 5 + 12 + 1 = 48, is the 5th name in the list. So, PAMELA would obtain a score of 
 * 5 x 48 = 240.
 * 
 * You are given Q queries, each query is a name, you have to print the score.
 * 
 * INPUTS: 1 <= N <= 5200 where N is number of names. Then N lines with a name. Then 1 <= Q <= 100.
 * Then Q lines with a word. */

import java.util.Scanner;

public class Euler022 {
	
	/* Approach: quicksort first. Then binary search and find score, I guess. */
	
	/* Quicksort with random splitter. */
	public static void qSort(String[] arr, int l, int r) {
		if (l >= r) ;
		else {
			int pivot = (int) (Math.random() * (r-l) + l);
			String splitter = arr[pivot];
			arr[pivot] = arr[r];
			arr[r] = splitter;
			int m = l;
			for (int i = l; i < r; i++) {
				if (arr[i].compareTo(splitter) < 0) {
					String temp = arr[i];
					arr[i] = arr[m];
					arr[m] = temp;
					m++;
				}
			}
			arr[r] = arr[m];
			arr[m] = splitter;
			qSort(arr, l, m-1);
			qSort(arr, m+1, r);
		}
	}
	
	/* Run binary search with assumption that target is in names. */
	public static int findName(String[] names, String target, int l, int r) {
		int m = (l+r)/2;
		if (target.compareTo(names[m]) < 0) {
			return findName(names, target, l, m-1);
		}
		else if (target.compareTo(names[m]) > 0) {
			return findName(names, target, m+1, r);
		}
		return m;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numNames = Integer.parseInt(s.nextLine());
		String[] names = new String[numNames];
		for (int t0 = 0; t0 < numNames; t0++) {
			names[t0] = s.nextLine();
		}
		qSort(names, 0, numNames-1);
		int q = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < q; t0++) {
			String n = s.nextLine();
			int index = findName(names,n,0,numNames-1) + 1;
			int ans = 0;
			for (int i = 0; i < n.length(); i++) {
				ans += n.charAt(i) - 64;
			}
			System.out.println(ans * index);
		}
		s.close();
	}
}
