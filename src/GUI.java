import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class GUI extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1L;
	
	Spielbrett brett=new Spielbrett();
	Snake snake=new Snake(brett.getBreite()/2,brett.getHoehe()/2, brett);

	public void keyPressed(KeyEvent e)
	{

	}

	public void keyReleased(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == 'w')
		{
			snake.hoch();
		}
		if (e.getKeyChar() == 'd')
		{
			snake.rechts();
		}
		if (e.getKeyChar() == 'a')
		{
			snake.links();
		}
		if (e.getKeyChar() == 's')
		{
			snake.runter();
		}
	}
	
	
}
