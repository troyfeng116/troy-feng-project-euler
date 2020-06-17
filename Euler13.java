/* -------- SOLVED -------- */

/* Work out the first ten digits of the sum of N 50-digit numbers. 
 * 
 * INPUT: 1 <= N <= 10^3, then N 50-digit integers. */

//import java.math.BigInteger;
import java.util.Scanner;

public class Euler13 {
	
	/* Cheap approach: BigInteger, right? 
	 * 
	 * Less cheap approach: simulate addition using arrays. */
	
	/* Assumes that n1 and n2 contain the digits of two numbers in reverse order (so the i'th element refers
	 * to the 10^i place). */
	public static int[] add(int[] n1, int[] n2) {
		int[] ans = new int[53];
		int i = 0;
		int carry = 0;
		while (i < n1.length && i < n2.length) {
			if (n1[i] + n2[i] + carry >= 10) {
				ans[i] = (n1[i] + n2[i] + carry) % 10;
				carry = 1;
			}
			else {
				ans[i] = n1[i] + n2[i] + carry;
				carry = 0;
			}
			i++;
		}
		while (i < n1.length) {
			if (n1[i] + carry >= 10) {
				ans[i] = (n1[i] + carry) % 10;
				carry = 1;
			}
			else {
				ans[i] = n1[i] + carry;
				carry = 0;
			}
			i++;
		}
		while (i < n2.length) {
			if (n2[i] + carry >= 10) {
				ans[i] = (n2[i] + carry) % 10;
				carry = 1;
			}
			else {
				ans[i] = n2[i] + carry;
				carry = 0;
			}
			i++;
		}
		if (carry == 1) ans[i] = carry;
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
	// Cheap but efficient BigInteger solution.
		/*BigInteger ans = BigInteger.ZERO;
		for (int i = 0; i < n; i++) {
			ans = ans.add(new BigInteger(s.nextLine()));
		}
		System.out.println(ans.toString().substring(0,10));*/
		
	// A more clever approach without BigInteger.
		int[] ans = new int[53];
		for (int i = 0; i < n; i++) {
			String input = s.nextLine();
			int[] digits = new int[50];
			for (int place = 0; place < 50; place++) {
				digits[place] = input.charAt(49-place) - 48;
			}
			ans = add(ans, digits);
		}
		int index = ans.length-1;
		while (ans[index] == 0) index--;
		for (int i = index; i > index-10; i--) {
			System.out.print(ans[i]);
		}
		System.out.println();
		
		s.close();
	}

}
