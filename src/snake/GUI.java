package snake;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GUI extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1L;
	
	Spielbrett brett;
	
	public GUI(Spielbrett brett)
	{
		this.brett=brett;
		setTitle("Super-Snake");
		setLayout(new GridLayout(1,1));
		add(brett);
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(brett.getBreite()*40, brett.getHoehe()*40);
		setVisible(true);
		JOptionPane.showMessageDialog(null, "Start",
				"Super Snake", JOptionPane.WARNING_MESSAGE);		
	}
	
	public GUI()
	{
		this(new Spielbrett());
	}
	
	
	public void start()
	{
		brett.start();
	}

	public void keyPressed(KeyEvent e)
	{

	}

	public void keyReleased(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e)
	{
		char taste=(char) Character.toUpperCase(e.getKeyChar());
		if (taste == 'W')
		{
			brett.snake.hoch();
		}
		if (taste == 'D')
		{
			brett.snake.rechts();
		}
		if (taste=='A')
		{
			brett.snake.links();
		}
		if (taste == 'S')
		{
			brett.snake.runter();
		}
		if (taste=='P')
		{
			synchronized(brett.snake)
			{
			 brett.snake.togglePause();
			}
		}
	}
	
	public static void main(String args[])
	{
		GUI gui =new GUI();
		gui.start();
	}
	
}
