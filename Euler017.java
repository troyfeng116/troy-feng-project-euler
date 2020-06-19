/* -------- SOLVED -------- */

/* The numbers 1 to 5 written out in words are 
 * 
 * One, Two, Three, Four, Five
 * 
 * First character of each word will be capital letter for example:
 * 
 * 104382426112 
 * is 
 * One Hundred Four Billion Three Hundred Eighty Two Million Four Hundred Twenty Six Thousand One Hundred Twelve
 * 
 * Given a number, you have to write it in words. 
 * 
 * INPUTS: 1 <= T <= 10, 0 <= N <= 10^12 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Euler017 {
	
	/* Thoughts/approach: hash 1-20, 30-90, 100, then 10^3, 10^6, 10^9.
	 * 
	 * Edge cases to deal with immediately: zero and 10^12.
	 * 
	 * Read as long or String? String maybe better...
	 * 
	 * Split number into groups of 3 digits (last 3, next 3, etc.) 
	 * 
	 * Consider any 3-digit number. If == 0, nothing (edge case). If < 19, hash. If < 100, no hundreds (hash n % 10 
	 * for tens and then hash ones). Else hundreds place is n % 100. Then, add 10^3 place (thousand, million, billion, 
	 * trillion) after the 3-digits. */
	
	static Map<Integer,String> table;
	
	/* Populate the table with unique number strings. */
	static void populate() {
		table.put(1,"One");
		table.put(2,"Two");
		table.put(3,"Three");
		table.put(4,"Four");
		table.put(5,"Five");
		table.put(6,"Six");
		table.put(7,"Seven");
		table.put(8,"Eight");
		table.put(9,"Nine");
		table.put(10,"Ten");
		table.put(11,"Eleven");
		table.put(12,"Twelve");
		table.put(13,"Thirteen");
		table.put(14,"Fourteen");
		table.put(15,"Fifteen");
		table.put(16,"Sixteen");
		table.put(17,"Seventeen");
		table.put(18,"Eighteen");
		table.put(19,"Nineteen");
		
		table.put(20,"Twenty");
		table.put(30,"Thirty");
		table.put(40,"Forty");
		table.put(50,"Fifty");
		table.put(60,"Sixty");
		table.put(70,"Seventy");
		table.put(80,"Eighty");
		table.put(90,"Ninety");
		
		table.put(100, "Hundred");
		table.put(1000, "Thousand");
		table.put(1000000, "Million");
	}
	
	/* Takes integer 1 <= N <= 999 and returns string representation. If recursive call passes 0, return empty string. */
	public static String toStr(int n) {
		StringBuffer ans = new StringBuffer();
		if (n == 0) return "";
		if (n > 100) {
			ans.append(table.get(n/100));
			ans.append(" Hundred ");
			ans.append(toStr(n % 100));
		}
		else if (n > 20) {
			ans.append(table.get(n - n%10));
			ans.append(" ");
			ans.append(toStr(n % 10));
		}
		else {
			ans.append(table.get(n));
			ans.append(" ");
		}
		return ans.toString();
	}
	
	public static void main(String[] args) {
		table = new HashMap<Integer,String>();
		populate();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String num = s.nextLine();
			long n = Long.parseLong(num);
			int len = num.length();
			if (len == 13) System.out.println("One Trillion");
			else if (num.equals("0")) System.out.println("Zero");
			else {
				StringBuffer ans = new StringBuffer();
				if (n >= 1000000000) {
					long bils = n / 1000000000;
					ans.append(toStr((int) bils) + "Billion "); 
					n = n % 1000000000;
				}
				if (n >= 1000000) {
					long mils = n / 1000000;
					ans.append(toStr((int) mils) + "Million ");
					n = n % 1000000;
				}
				if (n >= 1000) {
					long thos = n / 1000;
					ans.append(toStr((int) thos) + "Thousand ");
					n = n % 1000;
				}
				ans.append(toStr((int) n));
				System.out.println(ans.deleteCharAt(ans.length()-1).toString());
			}
		}
		s.close();
	}
}
