package com.game.mario.smb;

import com.game.mario.entity.Entity;

public class Camera {
	public int x, y;

	public void tick(Entity player){
		setX(-player.getX() + Game.getFrameWidth()/2);
		boolean vertical = false; // change to true if you want the camera to chase the player vertically
		if(vertical){			
			setY(-player.getY() + Game.getFrameHeight()/2);
		}else{
			setY(0);			
		}
	}
	
	//GETTERS AND SETTERS
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
