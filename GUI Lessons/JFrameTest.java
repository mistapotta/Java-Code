import javax.swing.JFrame;

public class JFrameTest extends JFrame
{
	public JFrameTest()
	{
		setSize(500,200);
		setVisible(true);
		setTitle("JFrameTest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main (String args[])
	{
		JFrameTest test = new JFrameTest();
	}
}