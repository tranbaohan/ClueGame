package ClueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import ClueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	
	public ComputerPlayer(){
		seenCards = super.getMyCards();
		lastRoomVisited = ' ';
	}
	
	public ComputerPlayer(String str, String c, int loc) {
		super(str, c, loc);
	}

	public BoardCell pickLocation(Set<BoardCell> targets){
		for (BoardCell i: targets)
			if (i.isRoom() && (((RoomCell) i).getInitial() == lastRoomVisited))
				return i;
		Object[] cell = targets.toArray();
		Random generator = new Random();
		int random = generator.nextInt(cell.length);
		return (BoardCell) cell[random];
	}
	
	public Suggestion createSuggestion(String room){
		ArrayList<Card> unseenWeapon = new ArrayList<Card>();
		ArrayList<Card> unseenPerson = new ArrayList<Card>();
		for (Card i: Board.deck){
			if (!seenCards.contains(i) && (i.getType() == CardType.WEAPON))
				unseenWeapon.add(i);
			else if (!seenCards.contains(i) && (i.getType() == CardType.PERSON))
				unseenPerson.add(i);
		}
		Random generator = new Random();
		int r1 = generator.nextInt(unseenWeapon.size());
		int r2 = generator.nextInt(unseenPerson.size());
		return new Suggestion(unseenPerson.get(r2), unseenWeapon.get(r1), new Card(room, CardType.ROOM));
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
	
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
}
