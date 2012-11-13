package ClueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AccusationWindow extends JDialog{
	private JComboBox room, person, weapon;
	private JButton submit, cancel;
	private String gRoom, gPerson, gWeapon;
	private Board board;
	public AccusationWindow(Board board){
		this.board = board;
		setModal(true);
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		setLayout(new GridLayout(4,2));
		setupComboBox(board);
		add(new JLabel("Room"));
		add(room);
		add(new JLabel("Weapon"));
		add(weapon);
		add(new JLabel("Person"));
		add(person);
		submit.addActionListener(new ButtonListener());
		cancel.addActionListener(new ButtonListener());
		add(submit);
		add(cancel);
		setTitle("Make an accusation");
		setSize(200, 200);
	}
	
	public void setupComboBox(Board board){
		room = new JComboBox();
		weapon = new JComboBox();
		person = new JComboBox();
		for (Card i: board.getDeck())
			if (i.getType() == Card.CardType.WEAPON)
				weapon.addItem(i.getName());
			else if (i.getType() == Card.CardType.PERSON)
				person.addItem(i.getName());
			else if (i.getType() == Card.CardType.ROOM)
				room.addItem(i.getName());
	}
	
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submit){
				gRoom = (String) room.getSelectedItem();
				gPerson = (String) person.getSelectedItem();
				gWeapon = (String) weapon.getSelectedItem();
				check(board);
			} else if (e.getSource() == cancel){
				setVisible(false);
			}
		}
	}
	
	public void check(Board board){
		if (board.checkAccusation(gPerson, gWeapon, gRoom)){
			JOptionPane.showMessageDialog(null, this.getName() + " is the winner", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(null, "Incorrect accusation, please continue.", "Result", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
		}
	}
}
