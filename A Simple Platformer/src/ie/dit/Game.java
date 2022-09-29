package ie.dit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {


	public static int GameWidth = 816;
	public static int GameHeight = 608;
	
	//declaring arraylists
	static ArrayList<Block> block = new ArrayList<Block>();
	static ArrayList<Obstacle> obstacle = new ArrayList<Obstacle>();
	static ArrayList<Flag> flag = new ArrayList<Flag>();
	static ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	static ArrayList<Mblock> mblock = new ArrayList<Mblock>();
	
	//declaring images
	public static BufferedImage blocks;
	public static BufferedImage spike;
	public static BufferedImage finish_img;
	public static BufferedImage ghost;
	public static BufferedImage ghost2;
	public static BufferedImage jump;
	public static BufferedImage fall;
	public static BufferedImage walk;
	public static BufferedImage walk2;
	public static BufferedImage still;
	 
	//declaring files
	static File file1 = new File("res/screen1.txt");
	static File file2 = new File("res/screen2.txt");
	static File file3 = new File("res/screen3.txt");
	
	public static int lives = 5;
	static public int level = 1;
	

	private void move() throws IOException, InterruptedException
	{
		Player.move(block,mblock, obstacle, enemy, flag);
	}
	
	//method to load images
	public static void loadimg() throws IOException{
		 blocks = ImageIO.read(new File("res/Brick.png"));
		 spike = ImageIO.read(new File("res/Spikes.png"));
		 finish_img = ImageIO.read(new File("res/flag.png"));
		 ghost = ImageIO.read(new File("res/ghost.png"));
		 ghost2 = ImageIO.read(new File("res/ghost2.png"));
		 jump = ImageIO.read(new File("res/jump.png"));
		 walk = ImageIO.read(new File("res/walk.png"));
		 fall = ImageIO.read(new File("res/fall.png"));
		 walk2 = ImageIO.read(new File("res/walk2.png"));
		 still = ImageIO.read(new File("res/still.png"));
	}
	

	public Game() {
		//key listner
		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				Player.keyReleased(e);
			}

			public void keyPressed(KeyEvent e) {
				Player.keyPressed(e);
			}
		});
		setFocusable(true);
	}
	
	
	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		//background colour
		g2d.setColor(new Color(204,255,255));
		g2d.fillRect(0,0,getWidth(), getHeight());
		//drawing the player, passing in 5 images for different animations
		Player.paint(g2d, jump, walk, fall, walk2, still);

		//drawing blocks
		for(int i = 0; i<block.size(); i++)
		{
			block.get(i).draw(g, blocks);
		}
		
		//drawing moving blocks
		for(int i = 0; i<mblock.size(); i++)
		{
			mblock.get(i).draw(g, blocks);
		}
		
		//drawing enemies
		for(int i = 0; i<enemy.size(); i++)
		{
			enemy.get(i).draw(g, ghost, ghost2);
		}

		//drawing spikes
		for(int i = 0; i<obstacle.size(); i++)
		{
			obstacle.get(i).draw(g, spike);
		}
		
		//drawing flags
		for(int i = 0; i<flag.size(); i++)
		{
			flag.get(i).draw(g, finish_img);
		}
		
		//printing lives
		g2d.setColor(Color.black);
		g.setFont(new Font("Impact", Font.PLAIN, 30)); 
		g.drawString("Lives: "+ lives, 40, 60);
		g.setFont(new Font("Impact", Font.PLAIN, 200));
}
	
	public static void gameOver() throws IOException, InterruptedException 
	{
		//moves player on death
		TimeUnit.SECONDS.sleep(1);
		Player.x= 32;
		Player.Y = 350;
		Player.moveRight = false;
		Player.moveLeft = false;
		lives--;
		clearLevel();
		System.out.println(lives);
		if(lives==0) 
		{
			//resets lives sets level to 1 when lives run out
			level = 1;
			clearLevel();
			lives = 5;
			JOptionPane.showMessageDialog(null , "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
			Player.moveRight = false;
			Player.moveLeft = false;
		}
	}
	


	public static void levelComplete() throws IOException
	{
		//move player to stating position and increases level counter
		level++;
		clearLevel();
		JOptionPane.showMessageDialog(null , "Level Complete!", "Level Complete!", JOptionPane.YES_NO_OPTION);

		System.out.println("level complete");
		Player.x = GameWidth - Player.width;
		System.out.println("level complete");
		
		Player.x= 32;
		Player.Y = 350;
		Player.moveRight = false;
		Player.moveLeft = false;	
	}
	
	//method to clear the level and draw new level
	public static void clearLevel() throws IOException
	{
		if(level==4) 
		{
			//if all levels are completed exits game.
			JOptionPane.showMessageDialog(null , "You Win!", "You Win!", JOptionPane.YES_NO_OPTION);
			System.exit(0);
		}
		block.clear();
		obstacle.clear();
		flag.clear();
		enemy.clear();
		mblock.clear();
		if(level==1) {loadmap(file1);}
		if(level==2) {loadmap(file2);}
		if(level==3) {loadmap(file3);}

	}
	
	//method to load map file form a file
	//then reads each character and creates new objects depending on the contents of the map file
	//eg: 1 = block, 2 = spikes
	public static void loadmap(File file) throws IOException 
	{
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(file);
		int w = 0;//width of file
		int h = 0;//height of file
		while(in.hasNext())
		{
			String temp = in.next();
			char[] temparr = temp.toCharArray();
			for(Character j: temparr)
			{
				//next line
				 if(w==25) 
				 {
					 w=0;
					 h++;
				 }
				 if(j=='1') 
				 {
						block.add(new Block(w*32,h*32));	
				 }
							 
				 if(j=='0') 
				 {
		
				 }
				 if(j=='2') 
				 {
					 obstacle.add(new Obstacle(w*32,(h*32)+4));
				 }
						 
				 if(j=='3') 
				 {
					 flag.add(new Flag(w*32,(h*32)+4));
				 }
						 
				 if(j=='4') 
				 {	
				 enemy.add(new Enemy(w*32,h*32));
				 }
						 
				 if(j=='5') 
				 {
						mblock.add(new Mblock(w*32,h*32));	
				 }		 
				 w++;
			}
		}
	}
	

	
	//main method
	public static void main(String[] args) throws InterruptedException , IOException
	{
		JFrame frame = new JFrame("A simple platformer");
		Game game = new Game();
		frame.add(game);
		frame.setSize(GameWidth, GameHeight);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		@SuppressWarnings("unused")
		Player player = new Player(30,50);
		loadimg();
		loadmap(file1);
	
		while(true)
		{
			game.move();
			game.repaint();
			Thread.sleep(10);
		}	
	}
}