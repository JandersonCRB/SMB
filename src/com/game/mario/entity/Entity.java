package com.game.mario.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.mario.smb.Handler;
import com.game.mario.smb.Id;

public abstract class Entity {
	int x, y;
	int width, height;
	int scale;
	public int facing = 0; // 0 = left. 1 = right
	//Variables used to apply the collision
	public boolean solid;
	public boolean jumping = false;
	public boolean falling = true;
	public int velX, velY;
	
	public Id id;
	
	//Used on Jumping
	public double gravity = 0.0;
	
	//Handler class
	public Handler handler;
	
	
	public Entity(int x, int y, int width, int height,int scale, boolean solid, Id id, Handler handler){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	
	public abstract void jump();
	
	//Die
	public abstract void die();
	
	//Render and Tick methods are always updating as fast as possible and they are the heart of code
	public abstract void render(Graphics g);
	public abstract void tick();
	
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
	public Rectangle getBoundsTop(){
		return new Rectangle(getX()+10,getY(),width-20,5);
	}
	public Rectangle getBoundsBottom(){
		return new Rectangle(getX()+10,getY()+height-5,width-20,5);
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle(getX(),getY()+10,5,height-20);
	}
	public Rectangle getBoundsRight(){
		return new Rectangle(getX()+width-5,getY()+10,5,height-20);
	}
}
