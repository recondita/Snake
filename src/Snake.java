public class Snake
{
	private SnakeList snake;
	private int kopfX;
	private int kopfY;
	private int lastX;
	private int lastY;
	private int richtung;

	public Snake(int x, int y, int richtung)
	{
		snake = new SnakeList(x, y);
		kopfX = lastX = x;
		kopfY = lastY = y;
		this.richtung = richtung;
	}

	public boolean links()
	{
		richtung++;
		return gerade();
	}

	public boolean rechts()
	{
		richtung++;
		return gerade();
	}

	public boolean gerade()
	{
		synchronized(this)
		{
			return snake.move(kopfX+(richtung&1-(richtung&2*richtung&1)),kopfY+((((richtung&2)-(richtung&4))/2)*(1-(richtung&1))));
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

		public boolean move(int x, int y)
		{

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

	public int getRichtung()
	{
		return richtung;
	}
}
