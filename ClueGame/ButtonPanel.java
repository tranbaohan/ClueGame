package ClueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
	private JButton next;
	private JButton makeAccusation;
	private WhoseTurnPanel whoseTurnPanel;
	private AccusationWindow accusationWindow;
	public ButtonPanel(Board board){
		accusationWindow = new AccusationWindow(board);
		whoseTurnPanel = new WhoseTurnPanel(board.getHuman());
		next = new JButton("Next Player");
		makeAccusation = new JButton("Make an Accusation");
		setLayout(new GridLayout(1,3));
		next.addActionListener(new ButtonListener());
		makeAccusation.addActionListener(new ButtonListener());
		add(whoseTurnPanel);
		add(next);
		add(makeAccusation);
	}
	
	public void setMakeAccusation(boolean val){
		makeAccusation.setEnabled(val);
	}
	
	public void updateWhoseTurn(Player player){
		whoseTurnPanel = new WhoseTurnPanel(player);
		if (player instanceof HumanPlayer)
			setMakeAccusation(true);
		else 
			setMakeAccusation(false);
	}
	
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == makeAccusation){
				accusationWindow.setVisible(true);
				makeAccusation.setEnabled(false);
			} else if (e.getSource() == next){
				
			}
			
		}
	}

}
