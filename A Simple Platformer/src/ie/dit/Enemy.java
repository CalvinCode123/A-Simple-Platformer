package ie.dit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy extends Rectangle{
	private static final long serialVersionUID = 1L;
	public static final int blocksize = 30;
	public static final int range = 150;
	int move = 1;
	int counter;
	boolean flip = true;
	
	
	public Enemy( int x, int y)
	{
		setBounds(x,y, blocksize, blocksize);
	}

	//method to move and draw the enemy
	public void draw(Graphics g, Image ghost, Image ghost2)
	{
		if(flip) 
		{
			g.drawImage(ghost2, x, y, width, height, null);
		}
		if(flip == false){
			g.drawImage(ghost, x, y, width, height, null);
		}
		
		
		x = x+move;
		counter++;
		if(counter ==range) {
			flip = !flip;
			move = -1;
			counter = 0;
			
		}
		if(flip)
		{
			move = 1;
		}
}
	
	//returns the shape of the box
	public Rectangle getBounds() {
		return new Rectangle(x, y, blocksize, blocksize);
	}
	
	
}

