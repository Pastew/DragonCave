package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.res;

import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity {
	protected Body body;
	TextureRegion texReg;
	protected float bodyWidth;
	protected float bodyHeight;
	
	public Entity(String key, float bodyWidth, float bodyHeight){
		Texture tex = res.getTexture(key);
		texReg = new TextureRegion(tex);
		this.bodyWidth = bodyWidth;
		this.bodyHeight = bodyHeight;
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		sb.draw(
			texReg,
			body.getPosition().x * B2DVars.PPM - bodyWidth / 2,
			body.getPosition().y * B2DVars.PPM - bodyHeight / 2,
			0,
			0,
			bodyWidth,
			bodyHeight,
			1,
			1,
			(float)(body.getAngle() * (180/Math.PI))
		);/*
		sb.draw(texReg, 
				body.getPosition().x * B2DVars.PPM - bodyWidth /2,
				body.getPosition().y * B2DVars.PPM - bodyHeight /2
				);*/
				
		sb.end();
		
	}
	
}
