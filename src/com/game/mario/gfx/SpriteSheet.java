package com.game.mario.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/*
 * This class is used to save a SpriteSheet
 * A file with many sprites is called SpriteSheet
 */
public class SpriteSheet {
	private BufferedImage sheet;
	
	private int w, h;
	public SpriteSheet(String path, int w, int h){
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.w = w;
		this.h = h;
	}
	
	public BufferedImage getSprite(int x,int y){
		return sheet.getSubimage(x*w-w,y*h-h,w,h);
	}
}
