package com.game.mario.tile;

import java.awt.Graphics;

import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public class InvisibleBlock extends Tile{

	public InvisibleBlock(int x, int y, int width, int heigth, boolean solid, Id id, Handler handler){
		super(x, y, width, heigth, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		//Do you wanna render something invisible???
	}

	@Override
	public void tick() {
			
	}

}
