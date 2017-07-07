import javax.swing.JFrame;

public class FirstGui extends JFrame
{
	public FirstGui()
	{
		setSize(500,200);
		setTitle("My first GUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE); //HIDE_ON_CLOSE, DISPOSE_ON_CLOSE, DO_NOTHING_ON_CLOSE
		setVisible(true);
	}

	public static void main (String[] args)
	{
		FirstGui first = new FirstGui();
	}
}