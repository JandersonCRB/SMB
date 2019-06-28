package com.game.mario.tile;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import com.game.mario.entity.Coin;
import com.game.mario.entity.Entity;
import com.game.mario.entity.RedMushroom;
import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;
import com.game.mario.smb.Sound;

public class CoinBlock extends Tile{
	int frame = 0;
	private Sprite[] shine = new Sprite[3];
	private Sprite activated;
	boolean active = true;
	Sound breaking;
	private Sound bump;
	IdCoinBlock reward;
	
	public CoinBlock(int x, int y, int width, int heigth, boolean solid, Id id, IdCoinBlock reward, Handler handler) {
		super(x, y, width, heigth, solid, id, handler);
		this.reward = reward;
		//Load Sprites
		SpriteSheet sheetShine = new SpriteSheet("/tiles/coin_block.png",16,16);
		
		for(int i = 0;i < shine.length;i++){
			shine[i] = new Sprite(sheetShine,i+1,1);
		}
		SpriteSheet sheetActivated = new SpriteSheet("/tiles/coin_block_activated.png",16,16);
		
		
		activated = new Sprite(sheetActivated,1,1); 
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				if(frame<2){
					frame++;
				}else{
					frame = 0;
				}
			}
		},700,700);
		breaking = new Sound("/sound/coin.wav");
		bump = new Sound("/sound/bump.wav");
	}
	
	public void activateSwitch() {
		if(active){
			active = false;
			Entity rw = null;
			switch(reward){
			case coin:
				rw = new Coin(x,y-64,64,64,1,false,Id.coin,handler);
				break;
			case greenMushroom:
				rw = new RedMushroom(x-32,y-32,32,32,1,false,Id.powerup,handler);
				break;
			case redMushroom:
				rw = new RedMushroom(x,y-64,64,64,1,false,Id.powerup,handler);
				break;
			}
			handler.addEntity(rw);
		}else{
			if(!bump.isRunning()){
				bump.play();
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(active){
			g.drawImage(shine[frame].getBufferedImage(), x, y, 64, 64, null);
		}else{
			g.drawImage(activated.getBufferedImage(), x, y, 64, 64, null);
		}
	}

	@Override
	public void tick() {
	}

}
