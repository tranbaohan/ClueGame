package ClueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import ClueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	private boolean accuse = false;
	private Suggestion savedSuggestion;
	
	public ComputerPlayer(){
		seenCards = super.getMyCards();
		lastRoomVisited = ' ';
	}
	
	public ComputerPlayer(String str, String c, int loc) {
		super(str, c, loc);
		seenCards = super.getMyCards();
		lastRoomVisited = ' ';
	}

	public BoardCell pickLocation(Set<BoardCell> targets){
		//	return room if applicable
		for (BoardCell i: targets)
			if (i.isRoom() && !(((RoomCell) i).getInitial() == lastRoomVisited))
				return i;
		//	pick random cell
		Object[] cell = targets.toArray();
		Random generator = new Random();
		int random = generator.nextInt(cell.length);
		return (BoardCell) cell[random];
	}
	
	public Suggestion createSuggestion(String room){
		ArrayList<Card> unseenWeapon = new ArrayList<Card>();
		ArrayList<Card> unseenPerson = new ArrayList<Card>();
		//	Check for unseen card
		for (Card i: Board.deck){
			if (!seenCards.contains(i) && (i.getType() == CardType.WEAPON))
				unseenWeapon.add(i);
			else if (!seenCards.contains(i) && (i.getType() == CardType.PERSON))
				unseenPerson.add(i);
		}
		//	pick randomly from unseen card
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
	
	public void makeMove(Board board){
		//	Make accusation?
		if (accuse){
			JOptionPane.showMessageDialog(null, "Accusation :" + savedSuggestion.getPerson().getName() + savedSuggestion.getWeapon().getName() + savedSuggestion.getRoom().getName(), "Accusation", JOptionPane.INFORMATION_MESSAGE);
			if (board.checkAccusation(savedSuggestion.getPerson().getName(), savedSuggestion.getWeapon().getName(), savedSuggestion.getRoom().getName())){
				JOptionPane.showMessageDialog(null, this.getName() + " is the winner", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			} else 
				JOptionPane.showMessageDialog(null, "Incorrect accusation, please continue.", "Result", JOptionPane.INFORMATION_MESSAGE);
		} else {		
			//	Pick location, move there, repaint board
			BoardCell newLocation = pickLocation(board.getTargets());
			setLocation(board.calcIndex(newLocation.getRow(), newLocation.getCol()));
			board.repaint();
			//	create suggestion
			Suggestion suggestion = null;
			Card result = null;
			if (newLocation.isRoom()){
				char roomIni = ((RoomCell) newLocation).getInitial();
				suggestion = this.createSuggestion(board.getRooms().get(roomIni));
				result = board.handleSuggestion(suggestion, this);
				board.getInfoPanel().updateGuess(suggestion);
				board.getInfoPanel().updateResult(result);
			}
			//	If no one can disprove
			if (result == null){
				accuse = true;
				savedSuggestion = suggestion;
			}
		}
	}	
}
