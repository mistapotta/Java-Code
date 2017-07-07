import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ButtonGui extends JFrame implements ActionListener
{
	private JLabel label;
	private JButton button;

	public ButtonGui()
	{
		setSize(500,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Button Gui");

		label = new JLabel("Test");
		button = new JButton("Click Me");

		Container pane = this.getContentPane();
		pane.setLayout(new FlowLayout());

		pane.add(label);
		pane.add(button);

		button.addActionListener(this);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Click Me"))
			label.setText("success");
	}

	public static void main (String args[])
	{
		ButtonGui b = new ButtonGui();
	}
}