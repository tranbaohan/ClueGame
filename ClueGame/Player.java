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
	
	public Player(String str, String c, int loc){
		color = strToColor(c);
		myCards = new ArrayList<Card>();
		location = loc;
		name = str;
	}
	
	public Card disproveSuggestion(String person, String weapon, String room){
		for (Card i: myCards)
			if (i.getName().equals(person) || i.getName().equals(weapon) | i.getName().equals(room))
				return i;
		return null;
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
		myCards.add(aCard);
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
	public Color strToColor(String str){
		return Color.BLUE;
	}
	
}
