package snake;

import java.util.Timer;
import java.util.TimerTask;

public class Snake
{
	private SnakeList snake;
	private int kopfX;
	private int kopfY;
	private int lastX = -1;
	private int lastY = -1;
	private Spielbrett brett;
	protected int richtung;
	private long wait;
	public int schwanzX;
	public int schwanzY;
	// private int laenge = 1;
	private boolean verarbeitet = true;
	private int rcache = -1;
	protected boolean fPause = true;
	private Timer timer;
	private TimerTask timerTask;
	private boolean wachsen = true;
	private int apfel = 0;

	public Snake(int x, int y, int richtung, long warte, Spielbrett brett)
	{
		snake = new SnakeList(x, y);
		kopfX = x;
		kopfY = y;
		this.brett = brett;
		this.richtung = richtung - 1;
		this.wait = warte;
		schwanzX = -1;
		schwanzY = -1;
	}

	public Snake(int x, int y, int richtung, long warte, Spielbrett brett,
			boolean wachsen)
	{
		this(x, y, richtung, warte, brett);
		this.wachsen = wachsen;
	}

	public void links()
	{

		setRichtung(3);
	}

	public void rechts()
	{
		setRichtung(1);
	}

	public void hoch()
	{
		setRichtung(2);
	}

	public void runter()
	{
		setRichtung(0);
	}

	/**
	 * Setzt die Richtung die durch einen Integer von 0-3 repraesentiert wird
	 */
	private void setRichtung(int r)
	{
		if (!fPause)
			synchronized (this)
			{
				if (verarbeitet)
				{
					richtung = r;
					verarbeitet = false;
				} else
				{
					rcache = r;
				}

			}
	}

	private class SnakeList
	{

		SnakeList next;
		private int x;
		private int y;

		public SnakeList(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		private SnakeList(int x, int y, SnakeList next)
		{
			this.x = x;
			this.y = y;
			this.next = next;
		}

		public byte move(int x, int y)
		{
			synchronized (this)
			{
				if (x < 0 || y < 0 || x >= brett.getBreite()
						|| y >= brett.getHoehe() || brett.belegt(x, y))
					return -1;
				if (x != brett.apfelX || y != brett.apfelY || !wachsen)
				{
					SnakeList last = this;
					if (next != null)
					{
						SnakeList temp = this;
						while (temp != null)
						{
							// if (x == temp.x && y == temp.y)
							// return -1;
							if (temp.next.next == null)
							{
								schwanzX = temp.x;
								schwanzY = temp.y;
								last = temp.next;
								temp.next = null;
							}
							temp = temp.next;
						}

						last.next = this;
					}
					rcache();
					lastX = last.x;
					lastY = last.y;
					kopfX = last.x = x;
					kopfY = last.y = y;
					snake = last;
					if (x == brett.apfelX && y == brett.apfelY)
						return 1;
					return 0;
				}
				rcache();
				lastX = -1;
				lastY = -1;
				snake = new SnakeList(x, y, snake);
				kopfX = x;
				kopfY = y;
				// laenge++;
				return 1;
			}
		}

	}

	private void rcache()
	{
		if (!verarbeitet)
		{
			if (rcache >= 0)
				richtung = rcache;
			else
				verarbeitet = true;
			rcache = -1;
		}
	}

	/**
	 * Startet den Timer der die Schritte ausloest
	 */
	public void start()
	{
		fPause = false;
		timer = new Timer();
		timerTask = new TimerTask()
		{

			@Override
			public void run()
			{
				if (!schritt())
					cancel();
			}

		};
		timer.scheduleAtFixedRate(timerTask, 0, wait);
	}

	public void stopp()
	{
		if (timerTask != null)
			try
			{
				timerTask.cancel();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
	}

	/**
	 * Laesst die Schlange in eine Bewegung in die entsprechende Richtung machen
	 * Ist Synchonisiert
	 */
	protected boolean schritt()
	{
		synchronized (this)
		{
			preMove();
			int ok = 0;
			ok = snake.move(kopfX + (richtung & 1) * (1 - (richtung & 2)),
					kopfY + (1 - (richtung & 1)) * (1 - (richtung & 2)));
			aktualisiereBrett();
			if (ok == 1)
			{
				brett.neuerApfel();
				neuerApfel();
				apfel++;
			}
			afterMove();
			if (ok < 0)
				brett.verloren(apfel);
			return ok >= 0;
		}
	}

	private void aktualisiereBrett()
	{
		brett.kopf(kopfX, kopfY);
		if (lastX >= 0 && lastY >= 0)
		{
			if (schwanzX >= 0 && schwanzY >= 0)
			{
				brett.loesche(lastX, lastY, schwanzX, schwanzY);
			} else
				brett.loesche(lastX, lastY);
		}
		brett.repaint();
	}

	/**
	 * Wird nach jedem Schritt aufgerufen
	 */
	public void afterMove()
	{

	}
	
	/**
	 * Wird vor jedem Schritt aufgerufent
	 */
	public void preMove()
	{
		
	}

	/**
	 * Wird immer nach dem Fressen eines Apfels aufgerufen
	 */
	public void neuerApfel()
	{

	}

	public void togglePause()
	{
		fPause = !fPause;
		if (!fPause)
			start();
		else
			timerTask.cancel();
	}

	public int getRichtung()
	{
		return richtung;
	}

	public int getApfelCount()
	{
		return apfel;
	}

	public int getKopfX()
	{
		return kopfX;
	}

	public int getKopfY()
	{
		return kopfY;
	}
}
