package com.game.mario.smb;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.game.mario.entity.Entity;
import com.game.mario.input.KeyInput;

public class Game extends Canvas implements Runnable{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 704;
	public static final int SCALE = 1;
	public static final String TITLE = "Super Marcão Bros";
	
	public static Camera cam;
	private Thread thread;
	private boolean running = false;
	public static BufferedImage image;
	public static BufferedImage hud;
	public static int coinsCount = 0;
	public static int liveCount = 3;
	public static boolean gameOver;
	public static boolean win;
	public static Font statsFont = new Font("Dialog", Font.PLAIN, 50);
	public static Font statsFontStroke = new Font("Dialog", Font.BOLD, 63); 
	
	public static Handler handler;
	private void init(){
		//Here we create our Handler Class
		handler = new Handler();
		//Here we add our Key Listener, which is used to receive commands from the keyboard
		addKeyListener(new KeyInput());
		//handler.addEntity(new Player(300,512,17,23,3,true,Id.player,handler));
		//handler.addTile(new GroundBlock(200,300,64,64,true,Id.wall,handler));
		//handler.addTile(new BreakableBlock(0, Game.getFrameHeight()-20, 99999, 25, true, Id.wall, handler));
		

		
		cam = new Camera();
		//Level loading
		image = null;
		try {
			image = ImageIO.read(getClass().getResource("/levels/1-1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		hud = null;
		try {
		hud = ImageIO.read(getClass().getResource("/HUD/1-1_hud.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		handler.createLevel(image);
	}
	
	private synchronized void start(){
		if(running) return;
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();
	}
	private synchronized void stop(){
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		double ns = 1000000000.0/60.0;
		int frames = 0;
		int ticks = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer>1000){
				timer+= 1000;
				System.out.println(frames + " frames per second " + ticks + " Updates per second");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.translate(cam.getX(), cam.getY());
		handler.render(g);
		g.translate(-cam.getX(), -cam.getY());
		g.drawImage(hud, 0, 0, hud.getWidth(), hud.getHeight(), null);
		
		//Draw number of lives
		g.setFont(statsFontStroke);
		g.setColor(Color.BLACK);
		g.drawString(""+liveCount, 300, 70);
		g.setFont(statsFont);
		g.setColor(Color.GREEN);
		g.drawString(""+liveCount, 302, 66);
		
		//Draw number of coins
		g.setFont(statsFontStroke);
		g.setColor(Color.BLACK);
		g.drawString(""+coinsCount, 909, 70);
		g.setFont(statsFont);
		g.setColor(Color.WHITE);
		g.drawString(""+coinsCount, 912, 66);
		
		g.dispose();
		bs.show();
	}
	
	public void tick(){
		handler.tick();
		
		for(Entity e:handler.entity){
			if(e.getId()==Id.player){
				cam.tick(e);
			}
		}
	}
	
	public static int  getFrameWidth(){
		return WIDTH*SCALE;
	}
	public static int  getFrameHeight(){
		return HEIGHT*SCALE;
	}
	public Game() {
		Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	
	public static void main (String[] args){
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
	}
	
	public static void gameOver(int lives){
		if(lives<0){
			
		}
	}
}
