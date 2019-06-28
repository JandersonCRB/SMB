package com.game.mario.tile;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public abstract class Tile {
	int x, y;
	public int width, height;
	
	public boolean solid;
	
	public int velX, velY;
	
	public Id id;
	
	public Handler handler;
	
	public Tile(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	
	//Render and Tick methods are always updating as fast as possible and they are the heart of code
	public abstract void render(Graphics g);
	public abstract void tick();
	
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
	public boolean isSolid() {
		return solid;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public Id getId() {
		return id;
	}
	public Rectangle getBounds(){
		return new Rectangle(getX(),getY(),width,height);
	}
}
