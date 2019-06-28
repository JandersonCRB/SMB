package com.game.mario.entity;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public class Coin extends Entity {

	private Sprite coin[] = new Sprite[4];
	private int frame;
	private int frameDelay;
	public Coin(int x, int y, int width, int height, int scale, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, scale, solid, id, handler);
		SpriteSheet sheet = new SpriteSheet("/tiles/coin.png",14,16);
		for(int i = 0;i < coin.length;i++){
			coin[i] = new Sprite(sheet,i+1,1);
		}
	}

	@Override
	public void jump() {
		
	}

	@Override
	public void die() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(coin[frame].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void tick() {
		frameDelay++;
		if(frameDelay >= 10){
			frame++;
			if(frame >= coin.length){
				frame = 0;
			}
			frameDelay = 0;
		}
		
	}

}
