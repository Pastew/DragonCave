package com.aghacks.dragoncave.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {

	private Sprite bg1, bg2;
	private float bgOffset = 0;
	private float posX;
	
	public Background(){
		bg1 = new Sprite(new Texture("images/bg.png"));
		//bg2 = new Sprite(new Texture("images/bg.png"));
		bg1.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void draw(SpriteBatch sb){
		sb.begin();
		posX-=B2DVars.X_SPEED/2;
		bg1.setPosition(posX, 0);
		bg1.draw(sb);
		
		bg1.setPosition(posX+Gdx.graphics.getWidth(), 0);
		bg1.draw(sb);
		
		if(posX < -Gdx.graphics.getWidth())
			posX = 0;
		sb.end();
	}
}
