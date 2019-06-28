package com.game.mario.smb;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.game.mario.entity.Coin;
import com.game.mario.entity.Entity;
import com.game.mario.entity.Goomba;
import com.game.mario.entity.Player;
import com.game.mario.tile.BreakableBlock;
import com.game.mario.tile.CoinBlock;
import com.game.mario.tile.EndSign;
import com.game.mario.tile.GroundBlock;
import com.game.mario.tile.GroundBottom;
import com.game.mario.tile.IdCoinBlock;
import com.game.mario.tile.InvisibleBlock;
import com.game.mario.tile.PipeBody;
import com.game.mario.tile.PipeHead;
import com.game.mario.tile.Tile;
import com.game.mario.smb.Game;


/*
 * This is the Handler class.
 * This class is used to save everything in our game
 * Players, Tiles, Entities, etc.
 */
public class Handler {
	private BufferedImage bus = null;
	private BufferedImage busSign = null;
	private BufferedImage bg;
	private BufferedImage gameOverScreen;
	private BufferedImage winScreen;
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Entity> entityToAdd = new LinkedList<Entity>();	
	public LinkedList<Entity> entityToRemove = new LinkedList<Entity>();	
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	public LinkedList<Tile> tileToAdd = new LinkedList<Tile>();
	public LinkedList<Tile> tileToRemove = new LinkedList<Tile>();
	private boolean dead = false;
	private boolean dying = false;
	public Sound die;
	public Sound gameOver;
	public Sound win;
	public boolean won = false;
	
	public void render(Graphics g){
		if(!dead){
			g.drawImage(bg, 300, 0, bg.getWidth(), bg.getHeight()+20, null);
			g.drawImage(bus, 260, 345, bus.getWidth(), bus.getHeight(), null);
			g.drawImage(busSign, 950, 465, busSign.getWidth()*2, busSign.getHeight()*2, null);
			for(int i = 0; i<entity.size();i++){
				Entity en = entity.get(i);
				en.render(g);
			}
			for(int i = 0;i < tile.size();i++){
				Tile ti = tile.get(i);
				ti.render(g);
			}
			if(Game.win){
				g.translate(-Game.cam.getX(), -Game.cam.getY());
				g.drawImage(winScreen, 0, 0, winScreen.getWidth()*2+20, winScreen.getHeight()*2, null);
			}
			
		}else{
			if(Game.gameOver){
				g.translate(-Game.cam.getX(), -Game.cam.getY());
				g.drawImage(gameOverScreen, -100, 0, gameOverScreen.getWidth()*5+200, gameOverScreen.getHeight()*3+50, null);
			}
		}
	}
	public void tick(){
		if(Game.win){
			if(!won){
				win = new Sound("/sound/Level Complete.wav");	
				win.play();
			}
			entity.clear();
			tile.clear();
			stage.stop();
			won = true;
			
			
		}
		if(dead && !Game.gameOver){
			entity.clear();
			tile.clear();
			stage.stop();
			Game.coinsCount = 0;
			Game.liveCount = Game.liveCount-1;
			if(Game.liveCount<0){
				gameOver = new Sound("/sound/Game Over.wav");				
				gameOver.play();
				Game.gameOver = true;
				Game.liveCount = -1;
			}else{
				createLevel(Game.image);
				dead = false;
			}
			dying = false;
			
			
		}
		//for(Entity en:entityToAdd){
			//entity.add(en);
			//entityToAdd.remove(en);
		//}
//		for(Entity en:entityToRemove){
//			entity.remove(en);
//			entityToRemove.remove(en);
//		}
		for(int i = 0; i<entity.size();i++){
			Entity en = entity.get(i);
			en.tick();
		}
//		for(Tile ti:tileToAdd){
//			tile.add(ti);
//			tileToAdd.remove(ti);
//		}
//		for(Tile ti:tileToRemove){
//			tile.remove(ti);
//			tileToRemove.remove(ti);
		for(int i = 0;i < tile.size();i++){
			Tile ti = tile.get(i);
			ti.tick();
		}
	}
	
	//Add Entity
	public void addEntity(Entity en){
		//entityToAdd.add(en);
		entity.add(en);
	}
	//Remove Entity
	public void removeEntity(Entity en){
		//entityToRemove.add(en);
		entity.remove(en);
	}
	//Add Tile
	public void addTile(Tile ti){
		//tileToAdd.add(ti);
		tile.add(ti);
	}
	//Remove Tile
	public void removeTile(Tile ti){
		//tileToRemove.add(ti);
		tile.remove(ti);
	}
	public void die(){
		if(die == null){
			die = new Sound("/sound/die.wav");
		}
		if(!dying){
			dying = true;
			stage.stop();
			die.play();
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){
				@Override
				public void run(){
					dead = true;
				}
			}, 2*1000);
		}
	}
	
	public Sound stage;
	//In this method we generate our level
	public void createLevel(BufferedImage level){
		
		gameOverScreen = null;
		try {
			gameOverScreen = ImageIO.read(getClass().getResource("/levels/game_over.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		winScreen = null;
		try {
			winScreen = ImageIO.read(getClass().getResource("/levels/ufal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bus = null;
		try {
			bus = ImageIO.read(getClass().getResource("/levels/ufal-ipioca.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		busSign = null;
		try {
			busSign = ImageIO.read(getClass().getResource("/levels/bus_sign.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bg = null;
		try {
		bg = ImageIO.read(getClass().getResource("/levels/1-1_background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage = new Sound("/levels/soundtrack/1-1_soundtrack.wav");
		stage.play();
		/*Generates a level from a image.png */
		int width = level.getWidth();
		int height = level.getHeight();
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int pixel = level.getRGB(x, y);
				
				int red   = (pixel >> 16) & 0xff;
				int green = (pixel >> 8)  & 0xff;
				int blue  = (pixel)       & 0xff;
				
				
				///Building ground
				if(red==0 && green ==0 && blue==0) addTile(new GroundBlock(x*64, y*64, 64, 64, true, Id.wall, this));
				
				///Pipe head
				if(red==200 && green ==141 && blue==0) addTile(new PipeHead(x*64, y*64, 102, 64, true, Id.wall, this));
				
				///Pipe Body
				if(red==255 && green ==180 && blue==0) addTile(new PipeBody(x*64, y*64, 82, 64, true, Id.wall, this));
				
				///Ground bottom
				if(red==99 && green ==99 && blue==99) addTile(new GroundBottom(x*64, y*64, 64, 64, true, Id.wall, this));
				
				///Player
				if(red==0 && green ==0 && blue==255) addEntity(new Player(x*64,y*64,17,23,3,true,Id.player,this));
				
				///Mushroom blocks
				if(red==255 && green ==255 && blue==0) addTile(new CoinBlock(x*64, y*64, 64, 64, true, Id.coinBlock,IdCoinBlock.redMushroom, this));
				
				///Coin blocks
				if(red==255 && green ==246 && blue==134) addTile(new CoinBlock(x*64, y*64, 64, 64, true, Id.coinBlock,IdCoinBlock.coin, this));
				
				///Coin
				if(red==255 && green ==255 && blue==70) addEntity(new Coin(x*64, y*64, 64, 64, 1, false,Id.coin,this));
				
				///Breakable blocks
				if(red==96 && green ==57 && blue==19) addTile(new BreakableBlock(x*64, y*64, 64, 64, true, Id.breakable, this));
				
				///Invisible blocks
				if(red==255 && green ==0 && blue==255) addTile(new InvisibleBlock(x*64, y*64, 64, 64, true, Id.wall, this));
				
				//Win blocks
				///Ufal Sign
				if(red==255 && green ==0 && blue==254) addTile(new EndSign(x*64, y*64, 64, 64, false, Id.win, this));
				
				//Enemy(Goomba)
				if(red==255 && green ==0 && blue==0) addEntity(new Goomba(x*64, (y*64)+33, 18, 16, 2, false, Id.goomba, this));
				
				///Enemy(Turtle)
				//if(red==0 && green ==0 && blue==255) addEntity(new Player(x*64, y*64, 64, 64, false, Id.player, this));
			}
		}
	}
}
