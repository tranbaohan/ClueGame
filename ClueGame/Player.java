package ClueGame;

import java.awt.Color;
import java.util.ArrayList;

import ClueGame.Card.CardType;

public class Player {
	private String name;
	private ArrayList<Card> myCards;
	private Color color;
	private int location;
	
	public Player(){
		color = Color.black;
		myCards = new ArrayList<Card>();
		location = 0;
		name = "";
	}
	
	public Card disproveSuggestion(String person, String weapon, String room){
		return new Card("", CardType.WEAPON);
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public int getLocation() {
		return location;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}
	
	public void addCard(Card aCard){
		
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
}
