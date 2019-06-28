package com.game.mario.tile;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public class PipeHead extends Tile{
	private SpriteSheet sheetHead;
	private Sprite head;
	public PipeHead(int x, int y, int width, int heigth, boolean solid, Id id, Handler handler) {
		super(x-10, y+10, width, heigth, solid, id, handler);
		sheetHead = new SpriteSheet("/tiles/pipe_head.png",32,15);
		head = new Sprite(sheetHead,1,1);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(head.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void tick() {
		
	}
	
}