/**
* This program is the driver and interacts with the player.
* 
* author: Nisha Sukumar
*/
import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		Deck one = new Deck();
		Scanner input = new Scanner(System.in);
		one.fillDeck();
		one.distributeCard();

		//the current card on the table
		Card inPlay = one.flipCard();
		
		//stores the chosen suit of the person who played an 8
		int chosenSuit = -1;
		String[] suits = new String[] {"Hearts", "Spades", "Diamonds", "Clubs"};

		//while no one has 0 card keep the game going
		while(one.opHand.size()!=0 && one.myHand.size()!=0) {
			//my turn
			boolean check = false;
			while(check == false) {
				int cardNumber = -1;
				//while player has not chosen a legal card
				while(cardNumber == -1 || cardNumber >= one.myHand.size()) {
					System.out.println("Table: " + inPlay.denomination + " of " + inPlay.suit + "\n");
					if(chosenSuit != -1) {
						System.out.println("Chosen suit: " + suits[chosenSuit]+ "\n");
					}
					System.out.println(one.toString() + "\n");
					System.out.print("card: ");
					cardNumber = (input.nextInt() - 1);
					System.out.print("\n");
					if(cardNumber == -1) {
						one.drawCardMe();
					}
					else if (cardNumber >= one.myHand.size()) {
						System.out.println("Invalid Play\n");
					}
				}
				
				//if the card is an 8
				if(one.myHand.get(cardNumber).denomination.equals("8")) {
					inPlay = one.myHand.get(cardNumber);
					one.placeCardMe(one.myHand.get(cardNumber));
					while(chosenSuit < 0 || chosenSuit > 3) {
						System.out.println("Choose a suit: [1] Hearts, [2] Spades, [3] Diamonds, [4] Clubs");
						System.out.print("Suit: ");
						chosenSuit = (input.nextInt()-1);
						System.out.print("\n");
						
					}
					check = true;
				}
				else if(chosenSuit != -1 && one.myHand.get(cardNumber).suit.equals(suits[chosenSuit])) { //if the previous card was an 8
					inPlay = one.myHand.get(cardNumber);
					one.placeCardMe(one.myHand.get(cardNumber));
					chosenSuit = -1;
					check = true;
				}
				else if(one.myHand.get(cardNumber).denomination.equals(inPlay.denomination) || one.myHand.get(cardNumber).suit.equals(inPlay.suit)) { //make sure the card is legal
					inPlay = one.myHand.get(cardNumber);
					one.placeCardMe(one.myHand.get(cardNumber));
					check = true;
				}
				else {
					System.out.println("Invalid PLay\n");
				}
			}
			if(one.myHand.size()==0) {
				break;
			}
			
			//opponent turn
			System.out.println("Table: " + inPlay.denomination + " of " + inPlay.suit + "\n");
			if(chosenSuit != -1) {
				System.out.println("Chosen suit: " + suits[chosenSuit] + "\n");
			}
			//location of the 8 card
			int eightLoc = -1;
			check = false;
			//if the card played by player is not an 8
			if(chosenSuit == -1) {
				for(int i = 0; i < one.opHand.size(); i++) {
					//check if hand contains a legal card
					if(one.opHand.get(i).denomination.equals(inPlay.denomination) || one.opHand.get(i).suit.equals(inPlay.suit)) {
						inPlay = one.opHand.get(i);
						one.placeCardOp(one.opHand.get(i));
						check = true;
						break;
					}
					//get the location of an 8 if there is one in the hand
					if(one.opHand.get(i).denomination.equals("8")) {
						eightLoc = i;
					}
				}
			}
			else { //if the card played by player is an 8
				for(int i = 0; i < one.opHand.size(); i++) {
					if(one.opHand.get(i).suit.equals(suits[chosenSuit])) {
						inPlay = one.opHand.get(i);
						one.placeCardOp(one.opHand.get(i));
						check = true;
						break;
					}
					if(one.opHand.get(i).denomination.equals("8")) {
						eightLoc = i;
					}
				}
			}
			//if there was no legal cards in the hand
			while(check == false) {
				//use the 8 card and choose the suit with the freatest number of cards
				if(eightLoc != -1) { 
					inPlay = one.opHand.get(eightLoc);
					one.placeCardOp(one.opHand.get(eightLoc));
					chosenSuit = one.greatestSuit();
					eightLoc = -1;
					break;
				}
				//if no 8 draw cards from deck
				Card opCard = one.drawCardOp();
				if(chosenSuit == -1) { //if the card played by player is not an 8
					if(opCard.denomination.equals(inPlay.denomination) || opCard.suit.equals(inPlay.suit)) {
						one.placeCardOp(opCard);
						inPlay = opCard;
						check = true;
					}
				}
				else { //if the card played by player is an 8
					if(opCard.suit.equals(suits[chosenSuit])) {
						one.placeCardOp(opCard);
						inPlay = opCard;
						chosenSuit = -1;
						check = true;
					}	
				}
				//if an 8 is drawn store location
				if(opCard.denomination.equals("8")) {
					eightLoc = (one.opHand.size()-1);
				}
			}
			System.out.println("Your opponent played: " + inPlay.denomination + " of " + inPlay.suit + "\n");
		}
		//win and loss statements
		if(one.myHand.size()==0) {
			System.out.println("YOU WON!");
		}
		else {
			System.out.println("YOU LOST :(");
		}
		
		
	}

}
