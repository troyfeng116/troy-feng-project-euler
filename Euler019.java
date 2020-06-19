/* -------- SOLVED -------- */

/* You are given the following information, but you may prefer to do some research for yourself.
 * 
 * 1 Jan 1900 was a Monday.
 * Thirty days has September,
 * April, June and November.
 * All the rest have thirty-one,
 * Saving February alone,
 * Which has twenty-eight, rain or shine.
 * And on leap years, twenty-nine.
 * 
 * A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
 * 
 * How many Sundays fell on the first of the month between two dates (both inclusive)? 
 * 
 * INPUTS: 1 <= T <= 100, 1900 <= Y1 <= 10^16, Y1 <= Y2 <= Y1+1000, 1 <= M1,M2 <= 12, 1 <= D1,D2 <= 31
 * 
 * For example,
 
 2
 1900 1 1
 1910 1 1
 2000 1 1
 2020 1 1
 
 * should return 18 and 35. */

import java.util.Scanner;

public class Euler019 {
	
	/* Thoughts/approach: I HATE CALENDAR PROBLEMS >:(
	 * 
	 * 1/1/1900 was Monday. So if we calculate the days between 1/1/1900 and D/M/Y (inclusive so that days between
	 * Mon 1/1/1900 and Sun 1/7/1900 is 7), only days that are 0 mod 7 from 1/1/1900 are Sundays.
	 * 
	 * ZELLER'S CONGRUENCE: Given D/M/Y, let K = year of century or Y % 100 and J = zero-based century 
	 * or floor(Y / 100). Note that M goes from 3->14, where March = 3 and Feb = 14.
	 * Then day of week is D + floor(13(M+1)/5) + K + floor(K/4) + floor(J/4) - 2J, all mod 7, where 0->Saturday 
	 * Since -2 mod 7 = 5 mod 7, replace -2J term with 5J. (Gregorian form) 
	 * 
	 * Equiv: D + floor(13(m+1)/5) + Y + floor(Y/4) - floor(Y/100) + floor(Y/400) all mod 7 */
	
	/* Implement Zeller's Congruence and return true if M/D/Y is a Sunday. */
	public static boolean zeller(int d, int m, long y) {
		if (m <= 2) {
			m += 12;
			y--;
		}
		long dayOfWeek = (d + (13 * (m+1) / 5) + y + y/4 - y/100 + y/400) % 7;
		return dayOfWeek == 1;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int i = 0; i < t; i++) {
			String[] dateInput1 = s.nextLine().split(" ");
			long y1 = Long.parseLong(dateInput1[0]);
			int m1 = Integer.parseInt(dateInput1[1]);
			int d1 = Integer.parseInt(dateInput1[2]);
			String[] dateInput2 = s.nextLine().split(" ");
			long y2 = Long.parseLong(dateInput2[0]);
			int m2 = Integer.parseInt(dateInput2[1]);
			
			int ans = 0;
			
			if (d1 == 1)
				if (zeller(1,m1,y1)) ans++;
			
			if (y1 == y2) {
				for (int m = m1+1; m <= m2; m++) {
					if (zeller(1,m,y1)) ans++;
				}
				System.out.println(ans);
				continue;
			}

			for (int m = m1+1; m <= 12; m++)
				if (zeller(1,m,y1)) ans++;
			
			for (int since = 1; since < y2-y1; since++) {
				long y = y1 + since;
				for (int m = 1; m <= 12; m++)
					if (zeller(1,m,y)) ans++;
			}
			
			for (int m = 1; m <= m2; m++)
				if (zeller(1,m,y2)) ans++;
			
			System.out.println(ans);
		}
		s.close();
	}

}
