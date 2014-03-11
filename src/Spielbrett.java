import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Spielbrett extends JPanel
{
	private static final long serialVersionUID = 1L;
	int breite=40;
	int hoehe=30;
	int[][] feld = new int[breite][hoehe];
	
	/*
	 * 0=nichts
	 * 1=Apfel
	 * 10=Schlangenkopf
	 * 	11=Schlangenkopf nach oben
	 * 	12=Schlangenkopf nach rechts
	 * 	13=Schlangenkopf nach unten
	 * 	14=Schlangenkopf nach links
	 * 20=Schlangenschwanz
	 * 	21=Schlangenschwanz nach oben
	 * 	22=Schlangenschwanz nach rechts
	 * 	23=Schlangenschwanz nach unten
	 * 	24=Schlangenschwanz nach links
	 * 30=Schlange
	 * 	31=Schlange nach oben
	 * 	32=Schlange nach rechts
	 * 	33=Schlange nach unten
	 * 	34=Schlange nach links
	 */
	
	public Spielbrett()
	{
		for(int i=0;i<breite;i++)
		{
			for(int j=0;j<hoehe;j++)
			{
				feld[i][j]=0;
			}
		}
		neuerApfel();
	}
	
	public void neuerApfel()
	{
		if(!voll())
		{
			int x=(int)(Math.random()*breite+1);
			int y=(int)(Math.random()*hoehe+1);
			if(feld[x][y]==0)
			{
				feld[x][y]=1;
			}
		}
	}
	
	public boolean voll()
	{
		for(int i=0;i<breite;i++)
		{
			for(int j=0;j<hoehe;j++)
			{
				if(feld[i][j]==0)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void anzeigen()
	{
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		Image ground=new Image();
		for(int i=0;i<breite;i++)
		{
			for(int j=0;j<hoehe;j++)
			{
				g2d.drawImage(ground, x, y, width, height, observer)
			}
		}
	}
}
