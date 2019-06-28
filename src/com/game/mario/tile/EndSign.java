package com.game.mario.tile;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;


public class EndSign extends Tile{
	private SpriteSheet sheet;
	private Sprite sign;
	public EndSign(int x, int y, int width, int heigth, boolean solid, Id id, Handler handler){
		super(x, y, width, heigth, solid, id, handler);
		sheet = new SpriteSheet("/tiles/ufal_sign.png",23,24);
		sign = new Sprite(sheet,1,1);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sign.getBufferedImage(), x, y, 64, 64, null);
	}

	@Override
	public void tick() {
			
	}
}
