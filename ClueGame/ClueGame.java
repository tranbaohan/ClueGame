package ClueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ClueGame extends JFrame {
	private Board board;
	private DetectiveNote note;
	
	public ClueGame(){
		JOptionPane.showMessageDialog(null, "You are Mr. Green, press Next Player to begin.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ClueGame");
		setSize(655, 710);
		addFeatures();
		setVisible(true);
	}
	
	public void addFeatures(){
		board = new Board();
		note = new DetectiveNote(board);
		add(board, BorderLayout.CENTER);
		add(new PlayerDisplayPanel(board.getHuman().getMyCards()), BorderLayout.EAST);
		add(new GameControlPanel(board), BorderLayout.SOUTH);
		
		//	File menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	public JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		//	Detective note option
		JMenuItem noteItem = new JMenuItem("Detective Notes");
		class NoteItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				note.setVisible(true);
			}
		}
		noteItem.addActionListener(new NoteItemListener());
		menu.add(noteItem);
		//	Exit option
		JMenuItem exitItem = new JMenuItem("Exit");
		class ExitItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		exitItem.addActionListener(new ExitItemListener());
		menu.add(exitItem);
		return menu;
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
}
