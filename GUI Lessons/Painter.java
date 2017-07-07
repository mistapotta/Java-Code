import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.GridLayout;

public class Painter extends JFrame implements MouseMotionListener, ActionListener
{
	private int x = -10, y = -10;
	private Color col = Color.BLACK;

	public Painter()
	{

		//jframe setup
		setSize(800,600);
		setTitle("Painter");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//set the layout
		JPanel p = new JPanel();
		p.setLayout (new GridLayout(4,1));
		JButton red = new JButton("red");
		red.setBackground(Color.RED);
		JButton yellow = new JButton("yellow");
		yellow.setBackground(Color.YELLOW);
		JButton blue = new JButton("blue");
		blue.setBackground(new Color (51, 51, 204));
		JButton black = new JButton("black");
		black.setBackground(Color.BLACK);
		p.add(red);
		p.add(yellow);
		p.add(blue);
		p.add(black);
		red.addActionListener(this);
		blue.addActionListener(this);
		yellow.addActionListener(this);
		black.addActionListener(this);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		JLabel direc =new JLabel("Drag to draw", JLabel.RIGHT);
		c.add(direc, BorderLayout.SOUTH);
		c.add(p, BorderLayout.EAST);
		//add mousemotionlistener
		c.addMouseMotionListener(this);
		//the final step
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		String act = e.getActionCommand();
		if (act.equals("blue"))
			col = new Color(51, 51, 204);
		else if (act.equals("red"))
			col = Color.RED;
		else if (act.equals("yellow"))
			col = Color.YELLOW;
		else //choose black
			col = Color.BLACK;
	}

	public void mouseMoved(MouseEvent e)
	{
		//empty
	}

	public void mouseDragged(MouseEvent e)
	{
		x = e.getX(); y = e.getY();
		repaint();
	}

	public void paint (Graphics g)
	{
		g.setColor(col);
		g.fillOval(x, y, 4, 4);
	}

	public static void main (String[] args)
	{
		Painter p = new Painter();
	}
}