/**
 * @(#)PanelTest.java
 *
 *
 * @author
 * @version 1.00 2015/5/14
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class PanelTest extends JFrame implements ActionListener
{
	private JPanel panel;
	private JLabel output;
	private JButton[] buttons;
	private JButton clear;

    public PanelTest()
    {

    	panel = new JPanel();
    	output = new JLabel(" ");
    	buttons = new JButton[12];
    	clear = new JButton("clear");
    	for (int i = 0; i <= 9; i ++)
    		buttons[i] = new JButton(""+i);
    	buttons[10] = new JButton("#");
    	buttons[11] = new JButton("*");
    	clear = new JButton("CLEAR");

    	setSize(400,400);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setTitle("Calculator");

    	panel.setLayout(new GridLayout(4,3));
    	for (int i = 1; i <=9; i ++)
    		panel.add(buttons[i]);
    	panel.add(buttons[10]);
    	panel.add(buttons[0]);
    	panel.add(buttons[11]);

    	Container pane = this.getContentPane();
    	this.setLayout(new BorderLayout());
    	pane.add(output, BorderLayout.NORTH);
    	pane.add(clear, BorderLayout.SOUTH);
    	pane.add(panel, BorderLayout.CENTER);

		for (int i = 0; i < 12; i ++)
			buttons[i].addActionListener(this);
		clear.addActionListener(this);
    	setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {
		if (e.getActionCommand().equals("CLEAR"))
			output.setText(" ");
		else
		{
			String content = e.getActionCommand();
			output.setText(output.getText()+content);
		}
    }

    public static void main (String args[])
    {new PanelTest();}
}