package ClueGame;

import java.util.ArrayList;
import java.util.Set;

import ClueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	
	public ComputerPlayer(){
		seenCards = super.getMyCards();
		lastRoomVisited = ' ';
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets){
		return new WalkwayCell();
	}
	
	public Suggestion createSuggestion(){
		return new Suggestion(new Card("", CardType.WEAPON), new Card("", CardType.WEAPON), 
				new Card("", CardType.WEAPON));
	}
	
	public void updateSeen(Card aCard){
		seenCards.add(aCard);
	}

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
	
}
