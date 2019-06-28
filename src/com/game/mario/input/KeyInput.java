package com.game.mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.mario.entity.Entity;
import com.game.mario.entity.Player;
import com.game.mario.smb.Game;

public class KeyInput implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(Entity en:Game.handler.entity){
			if(en instanceof Player && ((Player) en).size > 0){
				switch(key){
				case KeyEvent.VK_SPACE:
					en.jump();
					break;
				case KeyEvent.VK_A:
					en.setVelX(-5);
					en.facing = 0;
					break;
				case KeyEvent.VK_D:
					en.setVelX(5);
					en.facing = 1;
					break;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(Entity en:Game.handler.entity){
			if(en instanceof Player){
				switch(key){
				case KeyEvent.VK_W:
					en.setVelY(0);
					break;
				case KeyEvent.VK_S:
					en.setVelY(0);
					break;
				case KeyEvent.VK_A:
					en.setVelX(0);
					break;
				case KeyEvent.VK_D:
					en.setVelX(0);
					break;
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//not using
	}

}
