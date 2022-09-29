package ie.dit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Mblock extends Rectangle
{
	boolean flip = true;
	private static final long serialVersionUID = 1L;
	public static final int blocksize = 32;
	public static final int range = 150;
	int move = 1;
	int counter;
	
	
	public Mblock(int x, int y)
	{
		setBounds(x,y, blocksize, blocksize);
	}

	//method to draw and move the block
	public void draw(Graphics g, Image block)
	{
		g.drawImage(block, x, y, width, height, null);
		x = x+move;
		counter++;
		if(counter ==range) 
		{
			flip = !flip;
			move = 1;
			counter = 0;
			
		}
		if(flip)
		{
			move = -1;
			
		}
}
	
	
	public Rectangle getBounds() 
	{
		return new Rectangle(x, y, blocksize, blocksize);
	}
	
}