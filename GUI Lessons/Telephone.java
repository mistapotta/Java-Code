import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Telephone extends JFrame implements ActionListener
{
	private JLabel output;
	private JButton clear;
	private JButton[] buttons;

	public Telephone()
	{
		//JJFrame Stuff
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Telephone");
		//initalize components
		output = new JLabel(" ", JLabel.CENTER);
		clear = new JButton("CLEAR");
		buttons = new JButton[12];
		for (int i = 0; i <= 9 ; i ++)
			buttons[i] = new JButton(""+i);
		buttons[10] = new JButton("#");
		buttons[11] = new JButton("*");
		//set up a panel
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(4,3));
		for (int i = 1; i <= 9; i ++)
			center.add(buttons[i]);
		center.add(buttons[10]);
		center.add(buttons[0]);
		center.add(buttons[11]);
		//set up the layout of the frame
		Container pane = this.getContentPane();
		pane.setLayout (new BorderLayout());
		pane.add(output, BorderLayout.NORTH);
		pane.add(center, BorderLayout.CENTER);
		pane.add(clear, BorderLayout.SOUTH);
		//set up the actionlisteners
		for (int i = 0; i < 12; i ++)
			buttons[i].addActionListener(this);
		clear.addActionListener(this);
		//make it visible
		setVisible (true);
	}

	public void actionPerformed (ActionEvent e)
	{
		String action = e.getActionCommand();
		if (action.equals("CLEAR"))
			output.setText(" ");
		else
			output.setText(output.getText()+action);
	}

	public static void main (String[] args)
	{
		Telephone t = new Telephone();
	}
}