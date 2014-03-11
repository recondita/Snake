public class Snake
{
	private SnakeList snake;
	private int kopfX;
	private int kopfY;
	private int lastX;
	private int lastY;

	public Snake(int x, int y)
	{
		snake = new SnakeList(x, y);
		kopfX = lastX = x;
		kopfY = lastY = y;
	}

	public boolean links()
	{
		synchronized (this)
		{
			return snake.move(kopfX+1, kopfY);
		}
	}

	public boolean rechts()
	{
		synchronized (this)
		{
			return snake.move(kopfX-1, kopfY);
		}
	}

	public boolean hoch()
	{
		synchronized (this)
		{
			return snake.move(kopfX, kopfY + 1);
		}
	}
	
	public boolean runter()
	{
		synchronized (this)
		{
			return snake.move(kopfX, kopfY + -1);
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

}
