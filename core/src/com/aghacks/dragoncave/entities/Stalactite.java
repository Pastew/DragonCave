package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DSprite;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Stalactite extends B2DSprite{
		
	private static float bodyHeight = V_HEIGHT/10 / PPM;
	private static float bodyWidth = bodyHeight/4;
	
	public Stalactite (World world, float posX){
		super(createBody(world, posX));
		
		Texture tex = Game.res.getTexture("stalactite");
		TextureRegion[] sprites = TextureRegion.split(tex, 50, 100)[0];
		setAnimation(sprites, 1/12f, bodyWidth*PPM*2, bodyHeight*PPM*2);	
		//Vector2 vel = new Vector2(-B2DVars.X_SPEED, -5f);
		//body.setLinearVelocity(vel);
	}
	
	private static Body createBody(World world, float posX){
		BodyDef bdef = new BodyDef();
		float posY = (Game.V_HEIGHT - bodyHeight/2) / PPM;
		bdef.position.set(posX, posY);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyHeight, bodyWidth);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.2f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.1f;
		
		body.createFixture(fdef).setUserData(B2DVars.METEOR);
		
		return body;
	}
}