package ie.dit;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;


public class Player {

	public static int GameWidth = 800;
	public static int GameHeight = 600;
	static int Y = 360;//player y position
	static int x = 32;//player x position
	public static boolean moving = false;
	
	private static double jumpspeed = 6; 
	private static double currentjumpspeed = jumpspeed;
	
	private static double maxfallspeed = 5;
	private static double currentfallspeed = .1;
	static int width;
	private static int height;
	

	public Player( int width, int height) {
		Player.width = width;
		Player.height = height;
		
	}

	//passing arraylists to method
	public static void move(ArrayList<Block> block,ArrayList<Mblock> mblock, ArrayList<Obstacle> obstacle, ArrayList<Enemy> enemy,ArrayList<Flag> flag) throws IOException, InterruptedException {
		
		moving = false;
		//when player reaches bottom 
		if (Y + Player.height >= GameHeight)
		{
			Game.gameOver();
		}

		if(moveRight)
		{
			x+=4;
			moving = true;
		}
		
		if(moveLeft){
			x-=4;
			moving = true;
		}
		
		if (jumping)
		{
			//algorithm to perform jump 
			moving = true;
			Y -= currentjumpspeed;
			currentjumpspeed -=.1;
			if(currentjumpspeed<=0)
			{
				currentjumpspeed = jumpspeed;
				jumping = false;
				falling =true;
			}
		}
		
		if (falling)
			{
			moving = true;
				Y += currentfallspeed;
				if(currentfallspeed <maxfallspeed) 
				{
					currentfallspeed +=.1;	
				}		
			}
			
		if(!falling)
		{ moving = false;
			currentfallspeed = .1;
		}
			
			falling = true;
			collision(block);	
			collision_mblock(mblock);
			collision_obst(obstacle);
			collision_enemy(enemy);
			collision_flag(flag);
	}






	private static void collision_flag(ArrayList<Flag> flag) throws IOException {
		for(int i = 0; i< flag.size(); i++)
		{
			if ((getBounds().intersects(Game.flag.get(i).getBounds())))
			{
				falling = false;
				jumping = false;
				Game.levelComplete();
			}
		
			
			if (getBoundsRight().intersects(Game.flag.get(i).getBounds()))
			{

				Game.levelComplete();
			}
			
			if (getBoundsLeft().intersects(Game.flag.get(i).getBounds()))
			{

				Game.levelComplete();
			}
		}	
	}




	public static void paint(Graphics2D g, Image jump, Image walk, Image fall, Image walk2, Image still) {
		//drawing different images for player
		if(moving == false) {
			g.drawImage(still, x, Y, width, height, null);
		}

		if(moveRight) {
			g.drawImage(walk, x, Y, width, height, null);
		}
		if(moveLeft) {
			g.drawImage(walk2, x, Y, width, height, null);
		}
		if(falling) {
			g.drawImage(fall, x, Y, width, height, null);
		}
		if(jumping) {
			g.drawImage(jump, x, Y, width, height, null);
		}
		System.out.println(falling);
	}
	
	//returning the 4 rectangles used to define player hitbox
	public static Rectangle getBounds() 
	{
		return new Rectangle(x+((width/2)-(width/4)), Y+(height/2), width/2, height/2);
	}
	public static Rectangle getBoundsTop()
	{
		return new Rectangle(x+((width/2)-(width/4)), Y, width/2, height/2);
	}
	public static Rectangle getBoundsRight() 
	{
		return new Rectangle(x+(width-5), Y+5, 5, height-10);
	}
	public static Rectangle getBoundsLeft() 
	{
		return new Rectangle(x, Y+5, 5, height-10);
	}
	
	//collision method for blocks
	private static void collision(ArrayList<Block> block) {
		for(int i = 0; i< block.size(); i++)
		{
			if ((getBounds().intersects(Game.block.get(i).getBounds())))
			{
				falling = false;
				jumping = false;
				currentjumpspeed = jumpspeed;
				if(Y<Game.block.get(i).height) {
				Y = Game.block.get(i).y - height;
				}	
			}
			
			if (getBoundsTop().intersects(Game.block.get(i).getBounds()))
			{
				Y = Game.block.get(i).y + 32;
				currentfallspeed = maxfallspeed;
				
			}
			
			if (getBoundsRight().intersects(Game.block.get(i).getBounds()))
			{
				x = Game.block.get(i).x - width;
				
			}
			
			if (getBoundsLeft().intersects(Game.block.get(i).getBounds()))
			{
				x = Game.block.get(i).x + width;
				
			}
		}
	}
	
	//collision method for moving blocks
	private static void collision_mblock(ArrayList<Mblock> mblock) {
		for(int i = 0; i< mblock.size(); i++)
		{
			if ((getBounds().intersects(Game.mblock.get(i).getBounds())))
			{
				falling = false;
				jumping = false;
				currentjumpspeed = jumpspeed;
				if(Y<Game.mblock.get(i).height)
				{
					Y = Game.mblock.get(i).y - height;
				}
				x = x+ Game.mblock.get(i).move;
			}
			
			if (getBoundsTop().intersects(Game.mblock.get(i).getBounds()))
			{
				Y = Game.mblock.get(i).y + 32;
				currentfallspeed = maxfallspeed;
			}
			
			if (getBoundsRight().intersects(Game.mblock.get(i).getBounds()))
			{
				x = Game.mblock.get(i).x - width;	
			}
			
			if (getBoundsLeft().intersects(Game.mblock.get(i).getBounds()))
			{
				x = Game.mblock.get(i).x + width;
			}
		}
	}
	
	//collision method for spikes
	private static void collision_obst(ArrayList<Obstacle> obstacle) throws IOException, InterruptedException
	{
		for(int i = 0; i< obstacle.size(); i++)
		{
			if (getBoundsRight().intersects(Game.obstacle.get(i).getBounds()))
			{
				x = Game.obstacle.get(i).x - width;
				Game.gameOver();	
			}
			
			if (getBoundsLeft().intersects(Game.obstacle.get(i).getBounds()))
			{
				x = Game.obstacle.get(i).x + width;
				Game.gameOver();
			}
			if ((getBounds().intersects(Game.obstacle.get(i).getBounds())))
			{
				falling = false;
				jumping = false;
				Game.gameOver();
			}
		}
	}
			
	//collision method for enemies
	private static void collision_enemy(ArrayList<Enemy> enemy) throws IOException, InterruptedException 
	{
		for(int i = 0; i< enemy.size(); i++)
		{
			if (Game.enemy.get(i).intersects(getBoundsLeft()))
			{
				System.out.println("hit by enemy");
				Game.gameOver();
				falling = false;
				jumping = true;
			}
			if (Game.enemy.get(i).intersects(getBoundsRight()))
			{
				Game.gameOver();
			}
			
			if (Game.enemy.get(i).intersects(getBounds()))
			{
				falling = false;
				jumping = true;
				currentjumpspeed = jumpspeed;
				enemy.remove(Game.enemy.get(i));
			}
		}
			
	}
		
	//key released
	public static void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moveLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
		moveRight = false;
		}
	}
	
	static boolean moveRight = false;
	static boolean moveLeft = false;
	static boolean jumping = false;
	static boolean falling = true;

	//key pressed
	public static void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moveLeft = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveRight = true;
			
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !jumping && !falling )
		{
				jumping = true;
	
		}		
	}

}

