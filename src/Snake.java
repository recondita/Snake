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
		this.richtung = richtung+1;
		this.wait = warte;
		start();
	}

	public void links()
	{
		richtung = 1;
	}

	public void rechts()
	{
		richtung = 3;
	}

	public void hoch()
	{
		richtung = 0;
	}

	public void runter()
	{
		richtung = 2;
	}
	
	public void apfel(int x, int y)
	{
		apfelX=x;
		apfelY=y;
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

		public boolean move(int x, int y)
		{
			if (x < 0 || y < 0 || x > brett.getHoehe() || y > brett.getBreite())
				return false;
			SnakeList last = this.next;
			if (next != null)
			{
				SnakeList temp = this.next;
				while (next.next != null)
				{
					if (x == temp.x && y == temp.y)
						return false;
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

			return true;
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
		boolean ok = true;
		while (ok)
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
			brett.repaint();
		}
	}
}
