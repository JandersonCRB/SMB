package com.game.mario.tile;

import java.awt.Graphics;

import com.game.mario.gfx.Sprite;
import com.game.mario.gfx.SpriteSheet;
import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;
import com.game.mario.smb.Sound;

public class BreakableBlock  extends Tile{
	private SpriteSheet sheet;
	private Sprite block;
	private boolean intact = true;
	private Sound bump;
	private Sound breaking;

	public BreakableBlock(int x, int y, int width, int heigth, boolean solid,Id id, Handler handler) {
		super(x, y, width, heigth, solid, id, handler);
		sheet = new SpriteSheet("/tiles/breakable_block.png",16,16);
		block = new Sprite(sheet,1,1);
		
		bump = new Sound("/sound/Bump.wav");
		breaking = new Sound("/sound/Break.wav");
	}
	
	public void activateSwitch() {
		if(intact){
			intact = false;
		}
		/*if(coin_block.BottomBounderies.intersects(player.TopBounderies){
			g.drawImage(activated.getBufferedImage(), x, y, 18*scale, 16*scale, null);
		}*/
	}
	public void breakBlock(boolean breakable) {
		if(!breakable){
			if(!bump.isRunning()){				
				bump.play();
			}
		}else{
			breaking.play();
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(block.getBufferedImage(), x, y, 64, 64, null);
	}

	@Override
	public void tick() {
	}

	

}
