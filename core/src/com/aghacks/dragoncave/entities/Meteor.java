package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;
import static com.aghacks.dragoncave.handlers.Calculate.random;

import java.util.Random;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DVars;
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
	public Meteor(World world, float x){
		
		float posY = (Game.V_HEIGHT + bodyWidth*2*PPM) / PPM;
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(bodyWidth);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.1f;
		
		body.createFixture(fdef).setUserData(B2DVars.ENEMY);
		
		float ranX = random(-B2DVars.X_SPEED, B2DVars.X_SPEED);
		Vector2 vel = new Vector2(ranX, 0);
		body.setLinearVelocity(vel);	
		
		int number = (new Random()).nextInt(2);
		String name;
		if(number==0)
			name="rock2";
		else
			name="rock3";
		boxSprite = new Sprite(Game.res.getTexture(name));
		boxSprite.setSize(bodyWidth*2 * PPM, bodyWidth*2 * PPM);
		
		boxSprite.setOrigin((boxSprite.getWidth()/2) , 
							(boxSprite.getHeight()/2) );

		body.setUserData(boxSprite);		
	}	
	public void destroy(World world){
		world.destroyBody(body);
	}
}


