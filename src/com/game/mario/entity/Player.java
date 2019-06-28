package com.game.mario.entity;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Game;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;
import com.game.mario.smb.Sound;
import com.game.mario.tile.BreakableBlock;
import com.game.mario.tile.CoinBlock;
import com.game.mario.tile.Tile;

public class Player extends Entity {
	private int frame = 0;
	private int frameDelay = 0;
	public int size = 1;
	
	private Sprite[] moveRight = new Sprite[2];
	private Sprite[] moveLeft = new Sprite[2];
	private Sprite jumpRight;
	private Sprite jumpLeft;
	private Sprite dead;
	private Sprite[] bigMoveRight = new Sprite[3];
	private Sprite[] bigMoveLeft = new Sprite[3];
	private Sprite bigJumpRight;
	private Sprite bigJumpLeft;
	
	private Sound getRedMush;
	private Sound jump;
	private Sound getCoin;
	private Sound shrink;
	private boolean animate = false;
	public Player(int x, int y, int width, int height,int scale, boolean solid, Id id, Handler handler) {
		super(x, y, width*scale, height*scale, scale, solid, id, handler);
		
		//Load Sprites
		SpriteSheet sheetRight = new SpriteSheet("/marcao/small/marcao_walk.png",17,23);
		SpriteSheet sheetLeft =  new SpriteSheet("/marcao/small/marcao_walk_left.png",17,23);
		
		for(int i = 0;i < moveRight.length;i++){
			moveRight[i] = new Sprite(sheetRight,i+1,1);
			moveLeft[i] = new Sprite(sheetLeft,i+1,1);
		}
		SpriteSheet sheetRight2 = new SpriteSheet("/marcao/small/marcao_jump.png",17,23);
		SpriteSheet sheetLeft2 = new SpriteSheet("/marcao/small/marcao_jump_left.png",17,23);
		
		jumpRight = new Sprite(sheetRight2,1,1);
		jumpLeft = new Sprite(sheetLeft2,1,1);
		
		SpriteSheet bigSheetRight = new SpriteSheet("/marcao/big/big_marcao_walk.png",18,30);
		SpriteSheet bigSheetLeft =  new SpriteSheet("/marcao/big/big_marcao_walk_left.png",18,30);
		
		for(int i = 0;i < bigMoveRight.length;i++){
			bigMoveRight[i] = new Sprite(bigSheetRight,i+1,1);
			bigMoveLeft[i] = new Sprite(bigSheetLeft,i+1,1);
		}
		SpriteSheet bigSheetRight2 = new SpriteSheet("/marcao/big/big_marcao_jump.png",18,30);
		SpriteSheet bigSheetLeft2 = new SpriteSheet("/marcao/big/big_marcao_jump_left.png",18,30);
		
		bigJumpRight = new Sprite(bigSheetRight2,1,1);
		bigJumpLeft = new Sprite(bigSheetLeft2,1,1);
		
		
		SpriteSheet sheet = new SpriteSheet("/marcao/small/marcao_dead.png",18,30);
		
		dead = new Sprite(sheet,1,1);
		//Load sounds
		jump = new Sound("/sound/Jump.wav");
		getRedMush = new Sound("/sound/Powerup.wav");
		getCoin = new Sound("/sound/Coin.wav");
	}
	@Override
	public void jump(){
		if(!jumping) {
			jumping = true;
			gravity = 7.0;
			jump.play();
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(size == 0){
			g.drawImage(dead.getBufferedImage(), x, y, 18*scale, 30*scale, null);
		}else{	
			if(size == 1){
				if(facing == 0){
					if(jumping){
						if(frame >= 2) frame = 0;
						g.drawImage(jumpLeft.getBufferedImage(), x, y, width, height, null);
					}else{
						if(frame == 0 && animate == false || frame >= 2) frame = 1; 
						g.drawImage(moveLeft[frame].getBufferedImage(), x, y, width, height, null);
					}
				} else {
					if(jumping){
						g.drawImage(jumpRight.getBufferedImage(), x, y, width, height, null);
					}else{
						if(frame >= 2) frame = 0;
						g.drawImage(moveRight[frame].getBufferedImage(), x, y, width, height, null);
					}
				}
			}else if(size == 2){
				if(facing == 0){
					if(jumping){
						g.drawImage(bigJumpLeft.getBufferedImage(), x, y, width, height, null);
					}else{
						if(frame == 0 && animate == false) frame = 2;
						g.drawImage(bigMoveLeft[frame].getBufferedImage(), x, y, width, height, null);
					}
				} else {
					if(jumping){
						g.drawImage(bigJumpRight.getBufferedImage(), x, y, width,height, null);
					}else{
						g.drawImage(bigMoveRight[frame].getBufferedImage(), x, y, width, height, null);
					}
				}
			}
		}
		//g.setColor(Color.ORANGE);
		//g.fillRect(getBoundsBottom().x, getBoundsBottom().y, getBoundsBottom().width, getBoundsBottom().height);
	}
	private boolean invincible = false;
	private void hitted(){
		if(!invincible){
			invincible = true;
			if(size == 1){
				die();
			}else{
				size--;
				shrink = new Sound("/sound/Warp.wav");
				shrink.play();
				shrink.stop();
				Timer timer = new Timer();
				timer.schedule(new TimerTask(){
	
					@Override
					public void run() {
						invincible = false;
					}
				},1*1000);
			}
		}
	}
	@Override
	public void die(){
		size = 0;
		setVelX(0);
		setVelY(0);
		handler.die();
	}
	@Override
	public void tick() {
		if(size == 2){
			width = 18*scale;
			height = 30*scale;
		}else{
			width = 17*scale;
			height = 23*scale;
		}
		if(y >= 1300){
			die();
		}
		if(size != 0){
			
			x += velX;
			y += velY;
		}
		//if(x <= 0) x = 0;
		//if(x+width >= Game.WIDTH*Game.SCALE) x = Game.WIDTH*Game.SCALE - width;
		//if(y+height >= Game.HEIGHT*Game.SCALE) y = Game.HEIGHT*Game.SCALE - height;
		if(velX!=0){
			animate = true;
		}else{
			animate = false;
			frame = 0;
		}
		LinkedList<Entity> removeList = new LinkedList<Entity>();
		for(int i = 0;i < handler.entity.size();i++){
			Entity en = handler.entity.get(i);
			if(en instanceof RedMushroom){
				if(this.getBounds().intersects(en.getBounds())){
					if(size == 1){
						size++;
					}else{
						Game.liveCount++;
					}
					getRedMush.play();
					removeList.add(en);
				}
			}else if(en instanceof Goomba){
				if(this.getBoundsBottom().intersects(en.getBoundsTop())){
					en.die();
				}
				else if(getBoundsLeft().intersects(en.getBounds()) || getBoundsRight().intersects(en.getBounds())){
					hitted();
				}
			}else if(en instanceof Coin){
				if(this.getBounds().intersects(en.getBounds())){
					getCoin.play();
					Game.coinsCount++;
					removeList.add(en);
				}
			}
		}
		for(int i = 0;i < removeList.size();i++){
			Entity en = removeList.get(i);
			handler.removeEntity(en);
			removeList.remove(i);
		}
		for(int i = 0;i < handler.tile.size();i++){
			Tile t = handler.tile.get(i);
			if(t.getId() == Id.win){
				if(getBounds().intersects(t.getBounds())){
					Game.win = true;
				}
			}
			if(t.getId() == Id.coinBlock){
				if(getBoundsTop().intersects(t.getBounds())){
					CoinBlock c = (CoinBlock) t;
					c.activateSwitch();
				}
			}
			if(t.getId() == Id.breakable){
				if(getBoundsTop().intersects(t.getBounds())){
					BreakableBlock b = (BreakableBlock) t;
					b.activateSwitch();
					if (size == 1){
						b.breakBlock(false);
					}else{
						handler.removeTile(b);
						b.breakBlock(true);
					}
				}
			}
			if(t.solid){
				if(getBoundsTop().intersects(t.getBounds())){
					setVelY(0);
					if(jumping){
						jump.stop();
						jumping = false;
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())){
					setVelY(0);
					if(falling) falling = false;
				} else if(!falling && !jumping){
					gravity = 0.8;
					falling = true;
				}
				
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(0);
					x = t.getX()+t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(0);
					x = t.getX()-t.width;
				}
			}
		}
		if(jumping){
			gravity -= 0.1;
			setVelY((int)-gravity);
		}
		if(gravity <= 0.0){
			if(jumping){
				gravity -= 0.1;
				setVelY((int)-gravity);
				if(gravity <= 0.0){
					jumping = false;
					falling = true;
				}
			}
		}
		if(falling){
			gravity += 0.1;
			setVelY((int)gravity);
		}
		if(animate){
			frameDelay++;
			if(frameDelay >= 10){
				frame++;
				if(size == 1){					
					if(frame >= moveRight.length){
						frame = 0;
					}
				}else{
					if(frame >= bigMoveRight.length){
						frame = 0;
					}
				}
				frameDelay = 0;
			}
		}
		
	}
	
}
