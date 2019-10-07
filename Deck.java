/**
* This program contains all methods that involve Cards when playing crazy8.
* 
* author: Nisha Sukumar
*/

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	public ArrayList<Card> deck = new ArrayList<Card>();
	public ArrayList<Card> myHand = new ArrayList<Card>();
	public ArrayList<Card> opHand = new ArrayList<Card>();
	public ArrayList<Card> discardDeck = new ArrayList<Card>();
	
	public Deck() {
		
	}
	
	//creates an array list of cards that is in random order
	public ArrayList<Card> fillDeck(){
		String[] suits = new String[] {"Hearts", "Spades", "Diamonds", "Clubs"};
		String[] denomination = new String[] {"K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2", "A"};
		for(int i = 0; i < suits.length; i++) {
			for(int j = 0; j < denomination.length; j++) {
				Card c = new Card(suits[i],denomination[j]);
				deck.add(c);
			}
		}
		Collections.shuffle(deck);
		return deck;
	}
	
	//fills the arraylist holding the player's and the opponent's hand with the first 10 Cards from the deck arraylist
	public ArrayList<Card> distributeCard() {
		for(int i = 0; i < 10; i = i + 2) {
			myHand.add(deck.get(i));
			opHand.add(deck.get(i+1));
			deck.remove(i);
			deck.remove(i);
		}
		//this.deck = d;
		return deck;
	}
	
	//gets a legal first card from the deck to start the game
	public Card flipCard() {
		if(deck.size() == 0) {
			switchDecks();
		}
		while(deck.get(0).denomination.equals("8")) {
			discardDeck.add(deck.get(0));
			deck.remove(0);
		}
		Card temp = deck.get(0);
		discardDeck.add(temp);
		deck.remove(0);
		//deck = d;
		return temp;
	}
	
	//moves the discard pile Cards to the deck when the deck is empty
	public ArrayList<Card> switchDecks(){
		deck = discardDeck;
		discardDeck.clear();
		Collections.shuffle(deck);
		return deck;
	}
	
	//gets a Card and places it into the player's hand array list
	public Card drawCardMe() {
		if(deck.size() == 0) {
			switchDecks();
		}
		Card temp = deck.get(0);
		myHand.add(temp);
		deck.remove(0);
		return temp;
	}
	
	//gets a Card and places it into the computer's hand array list
	public Card drawCardOp() {
		if(deck.size() == 0) {
			switchDecks();
		}
		Card temp = deck.get(0);
		opHand.add(temp);
		deck.remove(0);
		return temp;
	}
	
	//takes a Card out of the arraylist of the player's hand
	public void placeCardMe(Card c) {
		discardDeck.add(c);
		myHand.remove(c);
	}
	
	//takes a Card out of the arraylist of the opponents's hand
	public void placeCardOp(Card c) {
		discardDeck.add(c);
		opHand.remove(c);
	}
	
	//finds the suit with the greatest number of Cards in the opponent's hand
	public int greatestSuit() {
		int heartNum = 0;
		int spadeNum = 0;
		int diamondNum = 0;
		int clubsNum = 0;
		
		for(int i = 0; i < opHand.size(); i++) {
			if(opHand.get(i).suit.equals("Hearts")) {
				heartNum++;
			}
			else if(opHand.get(i).suit.equals("Spades")) {
				spadeNum++;
			}
			else if(opHand.get(i).suit.equals("Diamonds")) {
				diamondNum++;
			}
			else if(opHand.get(i).suit.equals("Clubs")) {
				clubsNum++;
			}
		}
		
		if(heartNum > spadeNum && heartNum > diamondNum && heartNum > clubsNum) {
			return 0;
		}
		else if(spadeNum > heartNum && spadeNum> diamondNum && spadeNum > clubsNum) {
			return 1;
		}
		
		else if(diamondNum > heartNum && diamondNum > spadeNum && diamondNum > clubsNum) {
			return 2;
		}
		
		return 3;
	}
	
	//displays the Cards in the player's hand
	public String toString() {
		String display = "Your Hand: { | ";
		for(int i = 0; i < myHand.size(); i++) {
			display += ("[" + (i+1) + "] " + myHand.get(i).denomination + " of " + myHand.get(i).suit + " | ");
		}
		display += "}";
		return display;
	}

}
