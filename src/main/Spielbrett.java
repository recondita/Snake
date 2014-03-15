package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Spielbrett extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int breite = 20;
	private int hoehe = 20;
	private int[][] feld = new int[breite][hoehe];
	private int kopfCacheX;
	private int kopfCacheY;
	Snake snake;
	int apfelX;
	int apfelY;

	// private boolean ausgabe=true;

	/*
	 * 0=nichts 1=Apfel 10=Schlangenkopf 11=Schlangenkopf nach oben
	 * 12=Schlangenkopf nach rechts 13=Schlangenkopf nach unten 14=Schlangenkopf
	 * nach links 20=Schlangenschwanz 21=Schlangenschwanz nach oben
	 * 22=Schlangenschwanz nach rechts 23=Schlangenschwanz nach unten
	 * 24=Schlangenschwanz nach links 30=Schlange 31=Schlange nach oben
	 * 32=Schlange nach rechts 33=Schlange nach unten 34=Schlange nach links
	 * 40+=Ecke
	 */

	public Spielbrett()
	{
		kopfCacheX = getBreite() / 2;
		kopfCacheY = getHoehe() / 2;
		wipe();
		snake = new Snake(kopfCacheX, kopfCacheY, 1, (long) 200, this);
	}

	public void verloren(int laenge)
	{
		JOptionPane.showMessageDialog(null, "Sie haben verloren ;(\nLaenge: "+ laenge,
				"Super Snake", JOptionPane.WARNING_MESSAGE);
		wipe();
		snake=new Snake(getBreite() / 2, getHoehe() / 2, 1, (long) 200, this);
	}

	public boolean belegt(int x, int y)
	{
		return feld[x][y] > 1;
	}

	private void wipe()
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
	
	public void loesche(int x, int y, int schwanzX, int schwanzY)
	{
		loesche(x, y);
		feld[schwanzX][schwanzY] = 20 + feld[schwanzX][schwanzY] % 10;
	}

	public void loesche(int x, int y)
	{
		feld[x][y] = 0;
	}

	public void kopf(int x, int y)
	{
		feld[kopfCacheX][kopfCacheY] = 30 + feld[kopfCacheX][kopfCacheY] % 10;
		feld[x][y] = 10 + ((x != kopfCacheX) ? ((x > kopfCacheX) ? 2 : 4)
				: ((y > kopfCacheY) ? 3 : 1));
		kopfCacheX = x;
		kopfCacheY = y;
	}

	public int getBreite()
	{
		return breite;
	}

	public int getHoehe()
	{
		return hoehe;
	}

	public void neuerApfel()
	{
		if (!voll())
		{
			int x = (int) (Math.random() * breite);
			int y = (int) (Math.random() * hoehe);
			if (feld[x][y] == 0)
			{
				feld[x][y] = 1;
				//snake.apfel(x, y);
				apfelX=x;
				apfelY=y;

			} else
				neuerApfel();
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
		BufferedImage grund = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage kopf = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage schwanz = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage koerper = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage kurve = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage apfel = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		try
		{
			grund.getGraphics().drawImage(
					new ImageIcon(this.getClass().getClassLoader()
							.getResource("main/bilder/Boden.png")).getImage(),
					0, 0, width, height, null);
			kopf.getGraphics()
					.drawImage(
							new ImageIcon(this.getClass().getClassLoader()
									.getResource("main/bilder/Snake_Kopf.png"))
									.getImage(),
							0, 0, width, height, null);
			schwanz.getGraphics()
					.drawImage(
							new ImageIcon(this
									.getClass()
									.getClassLoader()
									.getResource(
											"main/bilder/Snake_Schwanz.png"))
									.getImage(),
							0, 0, width, height, null);
			koerper.getGraphics()
					.drawImage(
							new ImageIcon(this
									.getClass()
									.getClassLoader()
									.getResource(
											"main/bilder/Snake_Koerper.png"))
									.getImage(),
							0, 0, width, height, null);
			kurve.getGraphics()
					.drawImage(
							new ImageIcon(this.getClass().getClassLoader()
									.getResource("main/bilder/Snake_Kurve.png"))
									.getImage(), 0, 0, width, height, null);
			apfel.getGraphics().drawImage(
					new ImageIcon(this.getClass().getClassLoader()
							.getResource("main/bilder/apfel.png")).getImage(),
					0, 0, width, height, null);
		} catch (Exception e)
		{
			System.out.println("Bild nicht gefunden!");
			e.printStackTrace();
		}
		for (int i = 0; i < breite; i++)
		{
			int x = getWidth() / breite * i;

			for (int j = 0; j < hoehe; j++)
			{
				int y = getHeight() / hoehe * j;
				g2d.drawImage(grund, x, y, width, height, null);
				if (feld[i][j] == 1)
				{
					g2d.drawImage(apfel, x, y, width, height, null);
				} else if (feld[i][j] > 5)
				{
					if (feld[i][j] < 20)
					{
						g2d.drawImage(drehen(kopf, deg(feld[i][j] - 20)), x, y,
								width, height, null);
					} else if (feld[i][j] < 30)
					{
						g2d.drawImage(drehen(schwanz, deg(feld[i][j] - 30)), x,
								y, width, height, null);
					} else if (feld[i][j] < 40)
					{
						g2d.drawImage(drehen(koerper, deg(feld[i][j] - 40)), x,
								y, width, height, null);
					} else
					{
						g2d.drawImage(drehen(kurve, deg(feld[i][j] - 50)), x,
								y, width, height, null);
					}
				}
			}
		}
		return image;
	}

	private double deg(int r)
	{
		if ((r - 2) % 10 == 0)
		{
			return 90.0;
		}
		if ((r - 3) % 10 == 0)
		{
			return 180.0;
		}
		if ((r - 4) % 10 == 0)
		{
			return 270.0;
		}
		return 0.0;
	}

	private BufferedImage drehen(BufferedImage src, double degrees)
	{
		AffineTransform affineTransform = AffineTransform.getRotateInstance(
				Math.toRadians(degrees), src.getWidth() / 2,
				src.getHeight() / 2);
		BufferedImage rotatedImage = new BufferedImage(src.getWidth(),
				src.getHeight(), src.getType());
		Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
		g.setTransform(affineTransform);
		g.drawImage(src, 0, 0, null);
		return rotatedImage;
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(anzeigen(), 0, 0, null);
	}
}
