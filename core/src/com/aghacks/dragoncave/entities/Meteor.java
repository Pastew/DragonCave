package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Meteor{
	Body body; 
	
	private static float bodyWidth = V_HEIGHT/22 / PPM;
	Sprite boxSprite;
	public Meteor(World world, Vector2 pos){
		BodyDef bdef = new BodyDef();
		bdef.position.set(pos);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(bodyWidth);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.9f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.1f;
		
		body.createFixture(fdef).setUserData(B2DVars.ENEMY);
		
		Vector2 vel = new Vector2(-B2DVars.X_SPEED, 0);
		body.setLinearVelocity(vel);	
		
		
		boxSprite = new Sprite(Game.res.getTexture("rock2"));
		boxSprite.setSize(bodyWidth*2 * PPM, bodyWidth*2 * PPM);
		
		boxSprite.setOrigin((boxSprite.getWidth()/2) , 
							(boxSprite.getHeight()/2) );

		body.setUserData(boxSprite);		
	}	
	public void destroy(World world){
		world.destroyBody(body);
	}
}


