package main;

public class Snake extends Thread
{
	private SnakeList snake;
	private int kopfX;
	private int kopfY;
	private int lastX = -1;
	private int lastY = -1;
	private Spielbrett brett;
	private int richtung;
	private long wait;
	public int schwanzX;
	public int schwanzY;
	private int laenge=1;

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
		start();
	}

	public void links()
	{
		richtung = 3;
	}

	public void rechts()
	{
		richtung = 1;
	}

	public void hoch()
	{
		richtung = 2;
	}

	public void runter()
	{
		richtung = 0;
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
			if (x < 0 || y < 0 || x >= brett.getBreite()
					|| y >= brett.getHoehe() || brett.belegt(x, y))
				return -1;
			if (x != brett.apfelX || y != brett.apfelY)
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
				lastX = last.x;
				lastY = last.y;
				kopfX = last.x = x;
				kopfY = last.y = y;
				snake = last;

				return 0;
			}

			lastX = -1;
			lastY = -1;
			snake = new SnakeList(x, y, snake);
			kopfX = x;
			kopfY = y;
			laenge++;
			return 1;

		}

	}


	public void run()
	{
		int ok = 0;
		while (ok >= 0)
		{
			aktualisiereBrett();
			try
			{
				Thread.sleep(wait);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			ok = snake.move(kopfX + (richtung & 1) * (1 - (richtung & 2)),
					kopfY + (1 - (richtung & 1)) * (1 - (richtung & 2)));
			if (ok == 1)
				brett.neuerApfel();
		}
		brett.verloren(laenge);
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
}
