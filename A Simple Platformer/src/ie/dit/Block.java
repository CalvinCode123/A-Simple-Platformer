package ie.dit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Block extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	public static final int blocksize = 32;

	
	public Block(int x, int y)
	{
		setBounds(x,y, blocksize, blocksize);
	}

	//method to draw the block
	public void draw(Graphics g, Image block)
	{
		g.drawImage(block, x, y, width, height, null);
	}
	
	//returns the shape of the block
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, blocksize, blocksize);
	}

	
}
