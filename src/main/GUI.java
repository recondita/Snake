package main;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class GUI extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1L;
	
	Spielbrett brett;
	
	public GUI()
	{
		brett=new Spielbrett();
		setTitle("Super-Snake");
		setLayout(new GridLayout(1,1));
		add(brett);
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(brett.getBreite()*20, brett.getHoehe()*20);
		setVisible(true);
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
	}
	
	public static void main(String args[])
	{
		new GUI();
	}
	
}
