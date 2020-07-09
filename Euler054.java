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

public class Euler054 {
	
	/* Thoughts/approach: First, A 2 3 4 5 is a straight/straight flush, with highest card 5. */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
