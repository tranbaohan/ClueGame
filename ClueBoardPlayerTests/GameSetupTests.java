package ClueBoardPlayerTests;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ClueGame.Board;
import ClueGame.Card;
import ClueGame.ComputerPlayer;
import ClueGame.HumanPlayer;

public class GameSetupTests {
	private Board board;
	
	@Before
	public void setUp(){
		board = new Board();
		board.loadCards();
		board.loadPlayers();
	}
	
	@Test
	public void loadPeopleTest() {
		//	Check human player
		HumanPlayer human = board.getHuman();
		Assert.assertTrue(human.getName().equals("Mr. Green"));
		Assert.assertTrue(human.getColor().equals(Color.GREEN));
		Assert.assertTrue(human.getLocation() == 384);
		
		//	Check computer player
		ComputerPlayer computer = board.getComputer().get(0);
		Assert.assertTrue(computer.getName().equals("Mrs. White"));
		Assert.assertTrue(computer.getColor().equals(Color.WHITE));
		Assert.assertTrue(computer.getLocation() == 220);
		
		computer = board.getComputer().get(4);
		Assert.assertTrue(computer.getName().equals("Mrs. Peacock"));
		Assert.assertTrue(computer.getColor().equals(Color.BLUE));
		Assert.assertTrue(computer.getLocation() == 13);
	}
	
	@Test
	public void loadCardsTest() {
		ArrayList<Card> cards = board.getCards();
		//	Test for correct number of cards
		Assert.assertEquals(cards.size(), 21);
		//	Correct number of cards each type
		int person = 0;
		int weapon = 0;
		int room = 0;
		for (Card i : cards){
			if (i.getType() == Card.CardType.PERSON)
				person++;
			if (i.getType() == Card.CardType.WEAPON)
				weapon++;
			if (i.getType() == Card.CardType.ROOM)
				room++;
		}
		Assert.assertEquals(person, 6);
		Assert.assertEquals(weapon, 6);
		Assert.assertEquals(room, 9);
		//	Random card test
		Assert.assertTrue(cards.contains(new Card("Mrs. Peacock", Card.CardType.PERSON)));
		Assert.assertTrue(cards.contains(new Card("Miss Scarlet", Card.CardType.PERSON)));
		Assert.assertTrue(cards.contains(new Card("Kitchen", Card.CardType.ROOM)));
		Assert.assertTrue(cards.contains(new Card("Study", Card.CardType.ROOM)));
		Assert.assertTrue(cards.contains(new Card("Candlestick", Card.CardType.WEAPON)));
		Assert.assertTrue(cards.contains(new Card("Dagger", Card.CardType.WEAPON)));
	}

	@Test
	public void dealCardsTest() {
		//	Make sure all the cards are dealt
		board.deal();
		ArrayList<Card> cards = board.getCards();
		Assert.assertEquals(cards.size(), 0);
		//	All players have the same number of cards
		int numCards = 3;
		Assert.assertEquals(board.getHuman().getMyCards().size(), numCards);
		for (ComputerPlayer i: board.getComputer())
			Assert.assertEquals(i.getMyCards().size(), numCards);
		//	Make sure one card is not given to two different players.
		Set<Card> listCards = new HashSet<Card>();
		for (Card i: board.getHuman().getMyCards())
			listCards.add(i);
		for (ComputerPlayer i: board.getComputer())
			for (Card j: i.getMyCards())
				listCards.add(j);
		Assert.assertEquals(listCards.size(), 18); 	// 18 unique cards for players
	}
}
