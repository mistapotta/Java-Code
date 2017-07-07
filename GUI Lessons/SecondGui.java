import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class SecondGui extends JFrame
{
	public SecondGui()
	{
		setSize(500,500);
		setTitle("SecondGui");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JLabel a = new JLabel("first");
		JLabel b = new JLabel("second");
		JLabel c = new JLabel("third");
		JLabel d = new JLabel("fourth");
		JLabel e = new JLabel("fifth");

		Container pane = this.getContentPane();
		/*
		pane.setLayout(new GridLayout(5,1));
		pane.add(a);
		pane.add(b);
		pane.add(c);
		pane.add(d);
		pane.add(e);
		*/

		/*
		pane.setLayout(new BorderLayout());
		pane.add(d,BorderLayout.WEST);
		pane.add(e,BorderLayout.EAST);
		pane.add(a,BorderLayout.NORTH);
		pane.add(b,BorderLayout.SOUTH);
		pane.add(c,BorderLayout.CENTER);
		*/

		pane.setLayout(new FlowLayout());
		pane.add(d);
		pane.add(e);
		pane.add(a);
		pane.add(b);
		pane.add(c);


		setVisible(true);
	}

	public static void main (String[] args)
	{
		SecondGui s = new SecondGui();
	}
}