package ClueGame;

import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DiePanel extends JPanel{
	private JTextField display;
	private JLabel label;
	public DiePanel(int die){
		setLayout(new GridLayout(1,2));
		label = new JLabel("Roll");
		add(label);
		display = new JTextField(((Integer) die).toString());
		display.setEditable(false);
		add(display);
		setBorder(new TitledBorder (new EtchedBorder(), "Die"));
	}
}
