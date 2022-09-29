package ie.dit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Flag extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	public static final int blocksize = 32;

	
	public Flag(int x, int y)
	{
		setBounds(x,y, blocksize, blocksize);
	}

	public void draw(Graphics g, Image flag)
	{
		//draws the flag
		g.drawImage(flag, x, y, width, height, null);
	}
	
	//returns the shape of the rectangle
	public Rectangle getBounds() 
	{
		return new Rectangle(x, y, blocksize, blocksize);
	}

	
}
