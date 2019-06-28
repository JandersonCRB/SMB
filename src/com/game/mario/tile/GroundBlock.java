package com.game.mario.tile;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public class GroundBlock extends Tile{
	private SpriteSheet sheet;
	private Sprite wall;
	public GroundBlock(int x, int y, int width, int heigth, boolean solid, Id id, Handler handler) {
		super(x, y, width, heigth, solid, id, handler);
		sheet = new SpriteSheet("/tiles/city_ground.png",20,20);
		wall = new Sprite(sheet,1,1);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(wall.getBufferedImage(), x, y, 64, 64, null);
	}

	@Override
	public void tick() {
		
	}
	
}
