import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Spielbrett extends JPanel
{
	private static final long serialVersionUID = 1L;
	int breite = 40;
	int hoehe = 30;
	int[][] feld = new int[breite][hoehe];

	/*
	 * 0=nichts 1=Apfel 10=Schlangenkopf 11=Schlangenkopf nach oben
	 * 12=Schlangenkopf nach rechts 13=Schlangenkopf nach unten 14=Schlangenkopf
	 * nach links 20=Schlangenschwanz 21=Schlangenschwanz nach oben
	 * 22=Schlangenschwanz nach rechts 23=Schlangenschwanz nach unten
	 * 24=Schlangenschwanz nach links 30=Schlange 31=Schlange nach oben
	 * 32=Schlange nach rechts 33=Schlange nach unten 34=Schlange nach links
	 */

	public Spielbrett()
	{
		for (int i = 0; i < breite; i++)
		{
			for (int j = 0; j < hoehe; j++)
			{
				feld[i][j] = 0;
			}
		}
		neuerApfel();
	}

	public void neuerApfel()
	{
		if (!voll())
		{
			int x = (int) (Math.random() * breite + 1);
			int y = (int) (Math.random() * hoehe + 1);
			if (feld[x][y] == 0)
			{
				feld[x][y] = 1;
			}
		}
	}

	public boolean voll()
	{
		for (int i = 0; i < breite; i++)
		{
			for (int j = 0; j < hoehe; j++)
			{
				if (feld[i][j] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}

	public BufferedImage anzeigen()
	{
		BufferedImage image = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		int width = getWidth() / breite;
		int height = getHeight() / hoehe;
		BufferedImage grund = null;
		BufferedImage kopf = null;
		BufferedImage schwanz = null;
		BufferedImage koerper = null;
		BufferedImage kurve = null;
		BufferedImage apfel = null;
		try
		{
			grund = ImageIO.read(new File("/bilder/Boden.png"));
			kopf = ImageIO.read(new File("/bilder/Snake_Kopf.png"));
			schwanz = ImageIO.read(new File("/bilder/Snake_Schwanz.png"));
			koerper = ImageIO.read(new File("/bilder/Snake_Koerper.png"));
			kurve = ImageIO.read(new File("/bilder/Snake_Kurve.png"));
			apfel = ImageIO.read(new File("/bilder/apfel.png"));
		} catch (IOException e)
		{
			System.out.println("Bild nicht gefunden!");
		}
		for (int i = 0; i < breite; i++)
		{
			int x = getWidth() / breite * i;

			for (int j = 0; j < hoehe; j++)
			{
				int y = getHeight() / hoehe * j;
				g2d.drawImage(grund, x, y, width, height, null);
				if(feld[i][j]==1)
				{
					g2d.drawImage(apfel, x, y, width, height, null);
				}
				else if(feld[i][j]<20)
				{
					g2d.drawImage(kopf, x, y, width, height, null);
				}
				else if(feld[i][j]<30)
				{
					g2d.drawImage(schwanz, x, y, width, height, null);
				}
			}
		}
		return image;
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(anzeigen(), 0, 0, null);
	}
}
