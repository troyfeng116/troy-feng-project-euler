/* -------- SOLVED --------*/

/* By listing the first six prime numbers: 2,3,5,7,11, and 13, we can see that the 6th prime is 13.
 * 
 * What is the N'th prime number? 
 * 
 * INPUT: 1 <= T <= 10^3, 1 <= N <= 10^4*/


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Euler7 {
	
	static Map<Integer,Integer> table;
	
	/* Takes an integer n of the form 6k+1 or 6k-1 and tests if it's prime. */
	public static boolean isPrime(int n) {
		for (int i = 5; i*i <= n; i+=6) {
			if (n % i == 0 || n % (i+2) == 0) return false;
		}
		return true;
	}
	
	/* Under assumption that n'th prime is in table and n+1'th prime is not, find n+1'th prime and put in
	 * table. */
	public static int findNextPrime(int n) {
		int currentPrime = table.get(n);
		if (currentPrime % 6 == 5) {
			currentPrime += 2;
			while (!isPrime(currentPrime)) {
				currentPrime += 4;
				if (isPrime(currentPrime)) {
					table.put(n+1,currentPrime);
					return table.get(n+1);
				}
				currentPrime += 2;
			}
			table.put(n+1, currentPrime);
			return currentPrime;
		}
		currentPrime += 4;
		while (!isPrime(currentPrime)) {
			currentPrime += 2;
			if (isPrime(currentPrime)) {
				table.put(n+1,currentPrime);
				return table.get(n+1);
			}
			currentPrime += 4;
		}
		table.put(n+1, currentPrime);
		return currentPrime;
	}
	
	/* Takes n and largest found prime (k'th prime) where found<n as arguments and returns n'th prime. */
	public static int generatePrime(int n, int found) {
		int k = found;
		int prime = table.get(found);
		while (k < n) {
			prime = findNextPrime(k);
			k++;
		}
		return prime;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		table = new HashMap<Integer,Integer>();
		table.put(1,2);
		table.put(2,3);
		table.put(3,5);
		int biggest = 3;
		for (int i = 0; i < t; i++) {
			int n = Integer.parseInt(s.nextLine());
			if (n <= biggest) {
				System.out.println(table.get(n));
			}
			else {
				System.out.println(generatePrime(n,biggest));
				biggest = n;
			}
		}
		s.close();
	}

}
