package com.aghacks.dragoncave.handlers;

import com.aghacks.dragoncave.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {

	private Sprite bg;
	private float posX;
	
	private float shift = B2DVars.X_SPEED*2; //TODO: /2
	
	public Background(){
		//bg = new Sprite(new Texture("images/bg.png"));
		bg = new Sprite(Game.res.getTexture("bg"));
		//bg2 = new Sprite(new Texture("images/bg.png"));
		bg.setSize(bg.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void draw(SpriteBatch sb){
		sb.begin();
		posX -= shift;
		bg.setPosition(posX, 0);
		bg.draw(sb);
		
		bg.setPosition(posX+bg.getWidth(), 0);
		bg.draw(sb);
		
		if(posX < -bg.getWidth())
			posX = 0;
		sb.end();
	}

	public void slowMotionOn() {
		shift /= 2;
	}
	public void slowMotionOff() {
		shift *= 2;
	}
}
