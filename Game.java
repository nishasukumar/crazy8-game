import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		Deck one = new Deck();
		Scanner input = new Scanner(System.in);
		one.fillDeck();
		one.distributeCard();
		
		Card inPlay = one.flipCard();
		int chosenSuit = -1;
		String[] suits = new String[] {"Hearts", "Spades", "Diamonds", "Clubs"};
		while(one.opHand.size()!=0 && one.myHand.size()!=0) {
			//my turn
			boolean check = false;
			while(check == false) {
				int cardNumber = -1;
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
				
				if(one.myHand.get(cardNumber).denomination.equals("8")) {
					inPlay = one.myHand.get(cardNumber);
					one.placeCardMe(one.myHand.get(cardNumber));
					while(chosenSuit < 1 || chosenSuit > 4) {
						System.out.println("Choose a suit: [1] Hearts, [2] Spades, [3] Diamonds, [4] Clubs");
						System.out.print("Suit: ");
						chosenSuit = (input.nextInt()-1);
						System.out.print("\n");
						
					}
					check = true;
				}
				else if(chosenSuit != -1 && one.myHand.get(cardNumber).suit.equals(suits[chosenSuit])) {
					inPlay = one.myHand.get(cardNumber);
					one.placeCardMe(one.myHand.get(cardNumber));
					chosenSuit = -1;
					check = true;
				}
				else if(one.myHand.get(cardNumber).denomination.equals(inPlay.denomination) || one.myHand.get(cardNumber).suit.equals(inPlay.suit)) {
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
			int eightLoc = -1;
			check = false;
			if(chosenSuit == -1) {
				for(int i = 0; i < one.opHand.size(); i++) {
					if(one.opHand.get(i).denomination.equals(inPlay.denomination) || one.opHand.get(i).suit.equals(inPlay.suit)) {
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
			else {
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
			while(check == false) {
				if(eightLoc != -1) {
					inPlay = one.opHand.get(eightLoc);
					one.placeCardOp(one.opHand.get(eightLoc));
					chosenSuit = one.greatestSuit();
					eightLoc = -1;
					break;
				}
				Card opCard = one.drawCardOp();
				if(chosenSuit == -1) {
					if(opCard.denomination.equals(inPlay.denomination) || opCard.suit.equals(inPlay.suit)) {
						one.placeCardOp(opCard);
						inPlay = opCard;
						check = true;
					}
				}
				else {
					if(opCard.suit.equals(suits[chosenSuit])) {
						one.placeCardOp(opCard);
						inPlay = opCard;
						chosenSuit = -1;
						check = true;
					}	
				}
				if(opCard.denomination.equals("8")) {
					eightLoc = (one.opHand.size()-1);
				}
			}
			System.out.println("Your opponent played: " + inPlay.denomination + " of " + inPlay.suit + "\n");
		}
		
		if(one.myHand.size()==0) {
			System.out.println("YOU WON!");
		}
		else {
			System.out.println("YOU LOST :(");
		}
		
		
	}

}
