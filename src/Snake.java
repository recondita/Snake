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
		kopfX=lastX=x;
		kopfY=lastY=y;
	}

	public boolean move(int richtung)
	{
		return false;
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

		
		public SnakeList move(int x, int y)
		{

				SnakeList last=this.next,next = this.next;
				while (next != null)
				{
					if(x==next.x&&y==next.y)
						return null;
					if(next.next.next==null)
					{
						last=next.next;
						next.next=null;						
					}
					next = next.next;

				}
				
				return last;
		}


	}
}
