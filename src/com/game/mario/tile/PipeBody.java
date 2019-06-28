package com.game.mario.tile;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public class PipeBody extends Tile{
	private SpriteSheet sheetBody;
	private Sprite body;
	public PipeBody(int x, int y, int width, int heigth, boolean solid, Id id, Handler handler) {
		super(x, y+10, width, heigth, solid, id, handler);
		sheetBody = new SpriteSheet("/tiles/pipe_body.png",31,17);
		body = new Sprite(sheetBody,1,1);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(body.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void tick() {
		
	}
	
}
