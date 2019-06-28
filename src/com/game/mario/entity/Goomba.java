package com.game.mario.entity;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Game;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;
import com.game.mario.smb.Sound;
import com.game.mario.tile.Tile;

public class Goomba extends Entity{
	private int facing = 0;
	private int frame = 0;
	private int frameDelay = 0;
	private Sound die;
	
	Sprite moveLeft[] = new Sprite[2];
	Sprite moveRight[] = new Sprite[2];
	
	public Goomba(int x, int y, int width, int height, int scale, boolean solid, Id id, Handler handler) {
		super(x, y, width*scale, height*scale, scale, solid, id, handler);
		SpriteSheet sheetLeft = new SpriteSheet("/enemies/goomba_walk_left.png",18,16);
		SpriteSheet sheetRight = new SpriteSheet("/enemies/goomba_walk_right.png",18,16);
		
		for(int i = 0;i < moveLeft.length;i++){
			moveLeft[i] = new Sprite(sheetLeft,i+1,1);
		}
		for(int i = 0;i < moveRight.length;i++){
			moveRight[i] = new Sprite(sheetRight,i+1,1);
		}
		setVelX(-2);
		die = new Sound("/sound/Squish.wav");
	}

	@Override
	public void jump() {
		
	}

	@Override
	public void die() {
		Game.coinsCount = Game.coinsCount + 5;
		die.play();
		handler.removeEntity(this);
	}

	@Override
	public void render(Graphics g) {
		if(facing == 0){
			g.drawImage(moveLeft[frame].getBufferedImage(), x, y, width, height, null);
		}else{
			g.drawImage(moveRight[frame].getBufferedImage(), x, y, width, height, null);			
		}
		
	}

	@Override
	public void tick() {
		x += velX;
		if(velX < 0){
			facing = 0;
		}else{
			facing = 1;
		}
		frameDelay++;
		if(frameDelay >= 10){
			frame++;	
				if(frame >= moveRight.length){
					frame = 0;
				}
			frameDelay = 0;
		}
		for(Tile ti:handler.tile){
			if(ti.solid){
				if(getBoundsLeft().intersects(ti.getBounds())){
					setVelX(2);
				}
				if(getBoundsRight().intersects(ti.getBounds())){
					setVelX(-2);
				}
			}
		}
	}
}
