import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextAdder extends JFrame implements ActionListener
{
	private JLabel labelPlus, labelSum;
	private JButton buttonEquals;
	private JTextField text1, text2;

	//  _4______ + _________ = [              ]

	public TextAdder()
	{
		//JFrame Stuff
		setSize(500,100);
		setTitle("Text Adder");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//instantiate my JComponents
		labelPlus = new JLabel("+", JLabel.CENTER);//overloaded constructor for label using alignment
		labelSum = new JLabel ("", JLabel.RIGHT);//we can align center or right (default is left)
		buttonEquals = new JButton ("=");
		text1 = new JTextField("4");//overloaded constructor with pre-data
		text2 = new JTextField();//default constructor with no data
		//set up pane and layout
		Container pane = this.getContentPane();
		pane.setLayout(new GridLayout(1,5));
		//added elements to the layout
		pane.add(text1);
		pane.add(labelPlus);
		pane.add(text2);
		pane.add(buttonEquals);
		pane.add(labelSum);
		//set up the action listener/button
		buttonEquals.addActionListener(this);
		//make it all visible
		setVisible (true);
	}

	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equals("="))
		{
			int num1 = Integer.parseInt(text1.getText());
			int num2 = Integer.parseInt(text2.getText());
			int sum = num1 + num2;
			labelSum.setText(String.valueOf(sum));
		}
	}

	public static void main (String[] args)
	{
		TextAdder t = new TextAdder();
	}
}