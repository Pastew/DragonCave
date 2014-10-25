package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity {
	public Body body;
	protected float bodyWidth;
	protected float bodyHeight;
	
	public Entity(String imgPath, float bodyWidth, float bodyHeight){
		Sprite boxSprite = new Sprite(new Texture(imgPath));
		this.bodyWidth = bodyWidth;
		this.bodyHeight = bodyHeight;
		boxSprite.setSize(bodyWidth*2 * PPM, bodyHeight*2 * PPM);
		
		boxSprite.setOrigin((boxSprite.getWidth()/2) , 
							(boxSprite.getHeight()/2) );

		if(boxSprite!=null)
			body.setUserData(boxSprite);
	}	
}
