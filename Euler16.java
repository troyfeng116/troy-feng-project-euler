/* -------- SOLVED -------- */

/* 2^9 = 512 and the sum of its digits is 5 + 1 + 2 = 8.
 * 
 * What is the sum of the digits of the number 2^N? 
 * 
 * Input: 1 <= T <= 100, 1 <= N <= 10^4 */

//import java.math.BigInteger;
import java.util.Scanner;

public class Euler16 {
	
	/* Notes/approach: Obviously can't compute directly with anything... right?
	 * 
	 * BigInteger would work, but once again, cheap. (plus java.math isn't included in given packages)
	 * 
	 * A less cheap way is to represent the powers of two as arrays and write a function to double the
	 * array representation of a number. */
	
	static int[][] powers;
	
	/* Given arr representation of digits, double the number represented by those digits. */
	public static int[] doub(int[] arr) {
		int[] ans;
		if (arr[0] >= 5) ans = new int[arr.length+1];
		else ans = new int[arr.length];
		
		int carry = 0;
		int j = ans.length-1;
		for (int i = arr.length-1; i >= 0; i--) {
			if (arr[i]*2 + carry >= 20) {
				ans[j] = (arr[i]*2 + carry) % 10;
				carry = 2;
			}
			else if (arr[i]*2 + carry >= 10) {
				ans[j] = (arr[i]*2 + carry) % 10;
				carry = 1;
			}
			else {
				ans[j] = arr[i]*2 + carry;
				carry = 0;
			}
			j--;
		}
		if (j == 0) ans[0] = 1;
		return ans;
	}
	
	public static void main(String[] args) {
		powers = new int[10001][];
		int[] dig = new int[] {1};
		powers[0] = dig;
		for (int i = 1; i < 10001; i++) {
			powers[i] = doub(powers[i-1]);
		}
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		//BigInteger twoConst = BigInteger.ONE.add(BigInteger.ONE);
		for (int i = 0; i < t; i++) {
			/*int n = Integer.parseInt(s.nextLine());
			BigInteger num = twoConst.pow(n);
			String str = num.toString();
			int sum = 0;
			for (int j = 0; j < str.length(); j++) {
				sum += str.charAt(j) - 48;
			}
			System.out.println(sum);*/
			
			int n = Integer.parseInt(s.nextLine());
			/*int[] digits = new int[] {1};
			int pow = 0;
			while (pow < n) {
				digits = doub(digits);
				pow++;
			}
			int ans = 0;
			for (int digit: digits) ans += digit;
			System.out.println(ans);*/
			int[] res = powers[n];
			int ans = 0;
			for (int digit: res) ans += digit;
			System.out.println(ans);
		}
		s.close();
	}
}
