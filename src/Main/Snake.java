package Main;

public class Snake extends Thread
{
	private SnakeList snake;
	private int kopfX;
	private int kopfY;
	private int lastX;
	private int lastY;
	private Spielbrett brett;
	private int richtung;
	private long wait;
	private int apfelX;
	private int apfelY;

	public Snake(int x, int y, int richtung, long warte, Spielbrett brett)
	{
		snake = new SnakeList(x, y);
		kopfX = lastX = x;
		kopfY = lastY = y;
		this.brett = brett;
		this.richtung = richtung - 1;
		this.wait = warte;
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

	public void apfel(int x, int y)
	{
		apfelX = x;
		apfelY = y;
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
			System.out.println(richtung+1);
			if (x < 0 || y < 0 || x > brett.getHoehe() || y > brett.getBreite())
				return -1;
			if (x != apfelX || y != apfelY)
			{
				SnakeList last = this;
				if (next != null)
				{
					SnakeList temp = this.next;
					while (next.next != null)
					{
						if (x == temp.x && y == temp.y)
							return -1;
						if (temp.next.next == null)
						{
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
			this.next = new SnakeList(x, y, this.next);
			return 1;

		}

	}

	public int getKopfX()
	{
		return kopfX;
	}

	public int getKopfY()
	{
		return kopfY;
	}

	public int getLastX()
	{
		return lastX;
	}

	public int getLastY()
	{
		return lastY;
	}

	public void run()
	{
		int ok = 0;
		while (ok >= 0)
		{
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
			aktualisiereBrett();
		}
	}

	private void aktualisiereBrett()
	{
		if (lastX >= 0 && lastY >= 0)
		{
			brett.loesche(lastX, lastY);
			brett.kopf(kopfX, kopfY);
		}
		brett.repaint();
	}
}
