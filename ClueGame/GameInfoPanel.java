package ClueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameInfoPanel extends JPanel{
	private DiePanel diePanel;
	private GuessPanel guessPanel;
	private GuessResultPanel resultPanel;
	public GameInfoPanel(){
		diePanel = new DiePanel(0);
		guessPanel = new GuessPanel(new ArrayList<Card>()); 
		resultPanel = new GuessResultPanel(new Card("Default Card", Card.CardType.PERSON));
		add(diePanel, BorderLayout.WEST);
		add(guessPanel, BorderLayout.CENTER);
		add(resultPanel, BorderLayout.EAST);
	}
	
	public void updateDie(int num){
		diePanel = new DiePanel(num);
	}
	
	public void updateGuess(Suggestion suggestion){
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(suggestion.getPerson());
		cards.add(suggestion.getWeapon());
		cards.add(suggestion.getRoom());
		guessPanel = new GuessPanel(cards);
	}
	
	public void updateResult(Card result){
		resultPanel = new GuessResultPanel(result);
	}
}
