package com.game.mario.entity;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public class RedMushroom extends Entity{

	private Sprite mush;

	public RedMushroom(int x, int y, int width, int height, int scale, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, scale, solid, id, handler);
		SpriteSheet sheet = new SpriteSheet("/powerups/red_mushroom.png",16,16);
		mush = new Sprite(sheet,1,1);
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(mush.getBufferedImage(), x, y, width,height, null);
		
	}

	@Override
	public void tick() {
		x += velX;
	}
	
}
