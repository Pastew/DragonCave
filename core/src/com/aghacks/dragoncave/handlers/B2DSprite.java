package com.aghacks.dragoncave.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2DSprite {
	
	protected Body body;
	protected Animation animation;
	protected float width;
	protected float height;
	
	protected float bodyWidth;
	protected float bodyHeight;
	
	public B2DSprite(Body body) {
		this.body = body;
		animation = new Animation();
	}
	
	public void setAnimation(TextureRegion[] reg, float delay, float bodyWidth, float bodyHeight) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
		
		this.bodyWidth = bodyWidth;
		this.bodyHeight = bodyHeight;
	}
	
	public void update(float dt) {
		animation.update(dt);
	}
	
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(
			animation.getFrame(),
			body.getPosition().x * B2DVars.PPM - bodyWidth / 2,
			body.getPosition().y * B2DVars.PPM - bodyHeight / 2,
			0,
			0,
			bodyWidth,
			bodyHeight,
			1,
			1,
			(float)(body.getAngle() * (180/Math.PI))
		);
		sb.end();
	}
	
	public Body getBody() { return body; }
	public Vector2 getPosition() { return body.getPosition(); }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
}	