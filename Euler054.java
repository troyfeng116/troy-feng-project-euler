/* -------- UNSOLVED -------- */

/* In the card game poker, a hand consists of five cards and are ranked, from lowest to highest, in the 
 * following way:
 * 
 * 		High Card : Highest value card.
 *		One Pair : Two cards of the same value.
 *		Two Pairs : Two different pairs.
 *		Three of a Kind : Three cards of the same value.
 * 		Straight : All cards are consecutive values.
 * 		Flush : All cards of the same suit.
 * 		Full House : Three of a kind and a pair.
 * 		Four of a Kind : Four cards of the same value.
 *		Straight Flush : All cards are consecutive values of same suit.
 *		Royal Flush : Ten, Jack, Queen, King, Ace, in same suit.
 * (More on Poker Hands here: http://en.wikipedia.org/wiki/List_of_poker_hands)
 * 
 * The cards are valued in the order:
 *
 *		2,3,4,5,6,7,8,9,10,J,Q,K,A
 *
 * If two players have the same ranked hands then the rank made up of the highest value wins; for example, 
 * a pair of eights beats a pair of fives (see example 1 below). But if two ranks tie, for example, both 
 * players have a pair of queens, then highest cards in each hand are compared (see example 4 below); if 
 * the highest cards tie then the next highest cards are compared, and so on.
 * 
 * Consider the following five hands dealt to two players:
 *
 * 	Hand 	Player1				Player2				Winner		
 * 	1		5H 5C 6S 7S KD 		2C 3S 8S 8D TD 		Player2
 * 			Pair of 5s 			Pair of 8s 			
 * 	2		5D 8C 9S JS AC 		2C 5C 7D 8S QH		Player1
 *			Highest card A 		Highest card Q
 * 	3		2D 9C AS AH AC 		3D 6D 7D TD QD 		Player2
 *			Three A 			Flush in Diamonds	
 * 	4		4D 6S 9H QH QC		3D 6D 7H QD QS 		Player1
 *			Pair of Q 			Pair of Q
 * 			Highest card 9 		Highets card 7
 *	5		2H 2D 4C 4D 4S 		3C 3D 3S 9S 9D 		Player1
 * 			Full House			Full House
 * 			With Three Fours	With Three Threes
 * 
 * INPUTS: 1 <= T <= 1000
 * Each test case contains ten cards (separated by a single space): the first five are Player 1's cards 
 * and the last five are Player 2's cards. You can assume that all hands are valid (no invalid characters 
 * or repeated cards), each player's hand is in no specific order, and in each hand there is a clear 
 * winner. 
 * 
 * OUTPUTS: for each T, print "Player 1" if 1 has winning hand, or "Player 2" else, each on new line. */

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Euler054 {
	
	/* Thoughts/approach: First, A 2 3 4 5 is a straight/straight flush, with highest card 5. I wonder
	 * if we can write a function mapping String[] hand -> int score, that allows for an easy comparison
	 * where the higher score has the winning hand?
	 * 
	 * There are 10 types of poker hands we need to rank, and each has tiebreaker by next highest rank.
	 * Let's assign brackets, accessed by (k,v) mappings: 
	 * 		high card "HC" -> 0
	 * 		one pair "OP" -> 100
	 * 		two pairs "TP" -> 200
	 * 		three of kind "TOK" -> 300
	 * 		straight "S" -> 400
	 * 		flush "F" -> 500
	 *		full house "FH" -> 600
	 * 		four of kind "FOK" -> 700
	 *		straight flush "SF" -> 800
	 *		royal flush "RF" -> 900
	 *
	 * For a hand of 5 cards, we first assign it a baseline score, and then add the highest card.
	 *
	 * We can check suits first for flush/straight flush/royal flush; if we find a flush, there can be no
	 * repeated cards, and the minimum score is 500.
	 *
	 * To process an array (hand) of five rank/suit strings, I think creating an int[] countRanks of size
	 * 15, where countRanks[rank] returns the count of rank in a hand, might be a good way to proceed. The
	 * size is 15 instead of 14 because Ace count will be storeed in both 1 and 14, so we can detect A-5
	 * flush but preserve high rank of A. */

	/* map.get(key) returns the score associated with that kind of hand. ex. map.get("TOK") retrieves 300,
	 * the score we have given to "three of a kind." */
	static Map<String,Integer> score;

	public static void fillScore() {
		score = new HashMap<String,Integer>();
		score.put("HC",0);
		score.put("OP",100);
		score.put("TP",200);
		score.put("TOK",300);
		score.put("S",400);
		score.put("F",500);
		score.put("FH",600);
		score.put("FOK",700);
		score.put("SF",800);
		score.put("RF",900);
	}

	/* Return the score assigned to hand p, where first we assign a score based on the type of hand and
	 * our score table (i.e. if full house, then we assign score.get("FH") = 600). Then we add the
	 * highest card for comparison. */
	public static int getScore(String[] p) {
		int[] countRanks = countRanks(p);
		int highCount = 1; /* Count of most frequent rank. */
		int secondHighCount = 1; /* Count of second most frequent rank. */
		int highest = 0; /* Highest rank. */
		int mostFreq = 0; /* Most frequently appearing rank. */
		for (int rank = 2; rank < countRanks.length; rank++) {
			if (countRanks[rank] != 0) {
				highest = rank;
				if (countRanks[rank] >= highCount) {
					mostFreq = rank;
					secondHighCount = highCount;
					highCount = countRanks[rank];
				}
				if (countRanks[rank] > secondHighCount) secondHighCount = countRanks[rank];
			}
		}
		if (highCount == 1) {
			// Check F, store in boolean.
			// Check S (careful of A-5; check for T-A, then A-5), store in boolean.
			// This deals with HC, S, F, SF, and RF.
			return 0;
		}
		if (highCount == 2) {
			// Check TP.
			// Else OP.
			return 0;
		}
		if (highCount == 3) {
			// Check FH.
			// Check TOK 
			return 0;
		}
		// check FOK.
		return 0;
	}

	/* Return a count of each rank in hand p. i.e. if hand were [2, 2, 2, 3, 3], return 
	 * [0, 0, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]. Note that the length is 15 and not 14, because
	 * of the Ace as a special exception, which is stored in both 1 and 14, so that we can detect 
	 * lowest-ace flush but preserve A as highest rank (ex. for royal flush). */
	public static int[] countRanks(String[] p) {
		int[] ans = new int[15];
		for (int i = 0; i < p.length; i++) {
			int rank = getNumRank(p[i].substring(0,1));
			ans[rank]++;
			if (rank == 14) ans[1]++;
		}
		return ans;
	}

	/* Convert string representation of rank to integer. "A"->14, "K"->13, etc. */
	public static int getNumRank(String rank) {
		int ans = 0;
		try {
			ans = Integer.parseInt(rank);
		} catch (NumberFormatException e) {
			switch (rank) {
				case "T": 
					ans = 10;
					break;
				case "J": 
					ans = 11;
					break;
				case "Q": 
					ans = 12;
					break;
				case "K": 
					ans = 13;
					break;
				case "A": 
					ans = 14;
					break;
				default: break;
			}
		}
		return ans;
	}

	/* Given p1 and p2 representing two players' poker hands, return 1 or 2 if player 1 or player 2 has
	 * the winning hand, respectively. */
	public static int winning(String[] p1, String[] p2) {

		return 1;
	}
	
	public static void main(String[] args) {
		fillScore();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String[] inputs = s.nextLine().split("");
			String[] player1 = new String[5];
			String[] player2 = new String[5];
			for (int i = 0; i < 5; i++) {
				player1[i] = inputs[i];
				player2[i+5] = inputs[i+5];
			}
		}
		s.close();
	}
}
