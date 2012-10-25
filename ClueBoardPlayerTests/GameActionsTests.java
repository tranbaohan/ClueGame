package ClueBoardPlayerTests;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ClueGame.Board;
import ClueGame.BoardCell;
import ClueGame.Card;
import ClueGame.ComputerPlayer;
import ClueGame.HumanPlayer;
import ClueGame.Player;
import ClueGame.Solution;
import ClueGame.Suggestion;
import ClueGame.Card.CardType;

public class GameActionsTests {
	private Board board;
	private static Card mustardCard;
	private static Card knifeCard;
	private static Card greenCard;
	private static Card daggerCard;
	private static Card studyCard;
	private static Card loungeCard;
	
	@BeforeClass
	public static void setCards(){
		mustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		knifeCard = new Card("Knife", Card.CardType.WEAPON);
		greenCard = new Card("Mr. Green", Card.CardType.PERSON);
		daggerCard = new Card("Dagger", Card.CardType.WEAPON);
		studyCard = new Card("Study", Card.CardType.ROOM);
		loungeCard = new Card("Lounge", Card.CardType.ROOM);
		
	}
	@Before
	public void setUp(){
		board = new Board();
	}
	
	@Test
	public void checkingAccusationTest() {
		board.setSolution(new Solution("Mr. Green", "Dagger", "Library"));
		//	Correct accusation
		Assert.assertTrue(board.checkAccusation("Mr. Green", "Dagger", "Library"));
		//	Incorrect accusation
		Assert.assertFalse(board.checkAccusation("Mrs. White", "Dagger", "Library"));
		Assert.assertFalse(board.checkAccusation("Mr. Green", "Candlestick", "Library"));
		Assert.assertFalse(board.checkAccusation("Mr. Green", "Dagger", "Kitchen"));
		Assert.assertFalse(board.checkAccusation("Colonel Mustard", "Rope", "Ballroom"));
	}
	
	@Test
	public void selectingTargetLocationTest() {
	//	*********************** Random selection test
		ComputerPlayer player = new ComputerPlayer();
		// Pick a location with no rooms in target, just 4 targets
		board.calcTargets(board.calcIndex(3, 5), 2);
		int loc_1_5Tot = 0;
		int loc_5_5Tot = 0;
		int loc_4_4Tot = 0;
		int loc_4_2Tot = 0;
		// Run the test 400 times
		for (int i=0; i<400; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(1, 5)))
				loc_1_5Tot++;
			else if (selected == board.getCellAt(board.calcIndex(5, 5)))
				loc_5_5Tot++;
			else if (selected == board.getCellAt(board.calcIndex(4, 4)))
				loc_4_4Tot++;
			else if (selected == board.getCellAt(board.calcIndex(4, 2)))
				loc_4_2Tot++;
			else
				fail("Invalid target selected");
		}
		Assert.assertEquals(400, loc_1_5Tot + loc_5_5Tot + loc_4_4Tot + loc_4_2Tot);
		// Ensure each target was selected more than once
		Assert.assertTrue(loc_1_5Tot > 10);
		Assert.assertTrue(loc_5_5Tot > 10);
		Assert.assertTrue(loc_4_4Tot > 10);	
		Assert.assertTrue(loc_4_2Tot > 10);	
		
		
	//	*********************** Room preference selection test
		board.calcTargets(board.calcIndex(15, 16), 3);
		//	Last room visited is something else
		player.setLastRoomVisited('C');
		//	Run the test 100 times
		int room = 0;
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(15, 15)))
				room++;
			else
				fail("Invalid target selected");
		}
		Assert.assertEquals(100, room);
		
		
	//	*********************** Room visited selection test
		board.calcTargets(board.calcIndex(0, 4), 1);
		player.setLastRoomVisited('C');
		room = 0;
		int loc_0_5 = 0;
		int loc_1_4 = 0;
		//	Run the test 200 times
		for (int i=0; i<200; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(0, 3)))
				room++;
			else if (selected == board.getCellAt(board.calcIndex(0, 5)))
				loc_0_5++;
			else if (selected == board.getCellAt(board.calcIndex(1, 4)))
				loc_1_4++;
			else
				fail("Invalid target selected");
		}
		//	Room is last visited
		Assert.assertEquals(room, 0);
		Assert.assertEquals(loc_0_5 + loc_1_4, 200);
		Assert.assertTrue(loc_1_4 > 10);	
		Assert.assertTrue(loc_0_5 > 10);	
	}
	
	@Test
	public void disprovingSuggestionTest() {
		// 	Test for one player, one correct match
		Player player = new Player();
		player.addCard(mustardCard);
		player.addCard(knifeCard);
		player.addCard(greenCard);
		player.addCard(daggerCard);
		player.addCard(studyCard);
		player.addCard(loungeCard);
		Assert.assertEquals(player.disproveSuggestion("Colonel Mustard", "Candlestick", "Library"), mustardCard);
		Assert.assertEquals(player.disproveSuggestion("Mrs. White", "Dagger", "Library"), daggerCard);
		Assert.assertEquals(player.disproveSuggestion("Mrs. White", "Candlestick", "Study"), studyCard);
		Assert.assertEquals(player.disproveSuggestion("Mrs. White", "Candlestick", "Library"), null);
		
		//	Test for one player, multiple possible matches
		int mustard = 0;
		int knife = 0;
		int lounge = 0;
		for (int i=0; i<200; i++){
			Card selected = player.disproveSuggestion("Colonel Mustard", "Knife", "Lounge");
			if (selected == mustardCard)
				mustard++;
			else if (selected == knifeCard)
				knife++;
			else if (selected == loungeCard)
				lounge++;
			else 
				fail("Returned card is not part of suggestion.");
		}
		Assert.assertEquals(200, mustard + knife + lounge);
		Assert.assertTrue(mustard > 10);
		Assert.assertTrue(knife > 10);
		Assert.assertTrue(lounge > 10);
		
		//	Create 3 players, 1 human 2 comps
		ArrayList<ComputerPlayer> comp = board.getComputer();
		HumanPlayer human = board.getHuman();
		comp.add(new ComputerPlayer());
		comp.add(new ComputerPlayer());
		//	Distribute cards
		human.addCard(greenCard);
		human.addCard(loungeCard);
		comp.get(0).addCard(daggerCard);
		comp.get(0).addCard(knifeCard);
		comp.get(1).addCard(studyCard);
		comp.get(1).addCard(mustardCard);
		
		//	Suggestion that none could disprove
		Suggestion  suggest = new Suggestion(new Card("Candlestick", CardType.WEAPON), 
				new Card("Mrs. White", CardType.PERSON), new Card("Library", CardType.ROOM));
		Assert.assertEquals(board.handleSuggestion(suggest, human), null);
		Assert.assertEquals(board.handleSuggestion(suggest, comp.get(0)), null);
		Assert.assertEquals(board.handleSuggestion(suggest, comp.get(1)), null);
		
		//	Suggestion that only human can disprove
		suggest = new Suggestion(new Card("Candlestick", CardType.WEAPON), greenCard, 
				new Card("Library", CardType.ROOM));
		Assert.assertEquals(board.handleSuggestion(suggest, human), null);
		Assert.assertEquals(board.handleSuggestion(suggest, comp.get(0)), greenCard);
		Assert.assertEquals(board.handleSuggestion(suggest, comp.get(1)), greenCard);
		
		//	Random player's disprove if 2 can disprove
		suggest = new Suggestion(knifeCard, new Card("Miss Scarlet", Card.CardType.PERSON), studyCard);
		int comp1 = 0;
		int comp2 = 0;
		for (int i=0; i<200; i++){
			if (board.handleSuggestion(suggest, human) == studyCard)
				comp1++;
			else if (board.handleSuggestion(suggest, human) == knifeCard)
				comp2++;
			else 
				fail("Invalid suggestion's handler.");
		}
		Assert.assertEquals(200, comp1 + comp2);
		Assert.assertTrue(comp1 > 10);
		Assert.assertTrue(comp2 > 10);
		
	}
	
	@Test
	public void makingSuggestionTest() {
		ComputerPlayer comp = new ComputerPlayer();
		//	Add all the cards to seen (except dagger, knife and mr. green)
		board.loadCards();
		for (Card i: board.getCards())
			if (!i.equals(knifeCard) && !i.equals(greenCard) && !i.equals(daggerCard))
				comp.updateSeen(i);
		//	Set location to Hall
		comp.setLocation(board.calcIndex(7,  11));
		
		//	Test random suggestion
		int knifeGreen = 0;
		int daggerGreen = 0;
		for (int i=0; i<150; i++){
			Suggestion suggest = comp.createSuggestion();
			//	Correct room?
			Assert.assertEquals(new Card("Hall", Card.CardType.ROOM), suggest.getRoom());
			//	Random combo?
			if (suggest.getPerson().equals(greenCard) && suggest.getWeapon().equals(daggerCard))
				daggerGreen++;
			else if (suggest.getPerson().equals(greenCard) && suggest.getWeapon().equals(knifeCard))
				knifeGreen++;
			else 
				fail("Invalid suggestion.");
		}
		Assert.assertEquals(daggerGreen + knifeGreen, 150);
		Assert.assertTrue(daggerGreen > 10);
		Assert.assertTrue(knifeGreen > 10);
		
		//	Add more card, only mr green and knife left
		comp.updateSeen(daggerCard);
		//	New location: Study
		comp.setLocation(board.calcIndex(7, 3));
		knifeGreen = 0;
		//	Test 1 correct suggestion
		for (int i=0; i<100; i++){
			Suggestion suggest = comp.createSuggestion();
			//	Correct room?
			Assert.assertEquals(new Card("Study", Card.CardType.ROOM), suggest.getRoom());
			//	1 possible suggestion 
			if (suggest.getPerson().equals(greenCard) && suggest.getWeapon().equals(knifeCard))
				knifeGreen++;
			else 
				fail("Invalid suggestion.");
		}
		Assert.assertEquals(100, knifeGreen);
		
	}

}
