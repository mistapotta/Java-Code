import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Mousey extends JFrame implements MouseListener, MouseMotionListener
{
	JLabel label;

	public Mousey()
	{
		//JFrame Stuff
		setSize(400,400);
		setTitle("Mousey");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//initialize and set up label
		label = new JLabel(" ", JLabel.CENTER);
		Container pane = this.getContentPane();
		pane.setLayout (new FlowLayout());
		pane.add(label);
		pane.addMouseListener(this);
		pane.addMouseMotionListener(this);
		setVisible(true);
	}

	//five methods for mouseListener
	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		label.setText("mouse Pressed at ("+x+", "+y+")");
	}
	public void mouseReleased(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		label.setText("mouse Released at ("+x+", "+y+")");
	}
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		int b = e.getButton();
		label.setText("mouse Clicked at ("+x+", "+y+"),Button = "+b);
	}
	public void mouseEntered(MouseEvent e)
	{
		label.setText("mouse entered form.");
	}

	public void mouseExited(MouseEvent e)
	{
		label.setText("mouse exited form.");
	}
	//two methods for mouseMotionListener
	public void mouseMoved(MouseEvent e)
	{
		int x = e.getX(); int y = e.getY();
		label.setText("mouse moved to ("+x+", "+y+")");
	}
	public void mouseDragged(MouseEvent e)
	{
		int x = e.getX(); int y = e.getY();
		label.setText("mouse moved to ("+x+", "+y+")");
	}

	public static void main (String[] args)
	{
		Mousey m = new Mousey();
	}
}