package ClueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

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
		ArrayList<Card> possible = new ArrayList<Card>();
		//	check for suitable cards
		for (Card i: myCards)
			if (i.getName().equals(person) || i.getName().equals(weapon) | i.getName().equals(room))
				possible.add(i);
		//	return random card or null if no card
		if (possible.size() == 0)
			return null;
		else {
			Random generator = new Random();
			int r = generator.nextInt(possible.size());
			return possible.get(r);
		}
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
	
	public Color strToColor(String strColor){
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}
	
}
