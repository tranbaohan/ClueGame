package ClueGame;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GameControlPanel extends JPanel{
	private ButtonPanel buttonPanel;
	public GameControlPanel(Board board){
		buttonPanel = new ButtonPanel(board);
		setLayout(new GridLayout(2,1));
		add(buttonPanel);
		add(board.getInfoPanel());
	}
	
	public void updateWhoseTurn(Player player){
		buttonPanel.updateWhoseTurn(player);
	}
}
