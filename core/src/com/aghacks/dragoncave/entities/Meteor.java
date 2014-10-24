package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Meteor extends Entity{
	
	private final float bodySize = V_HEIGHT/22 / PPM;
	
	public Meteor(World world, Vector2 pos){
		BodyDef bdef = new BodyDef();
		bdef.position.set(pos);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(bodySize);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.5f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.1f;
		
		body.createFixture(fdef).setUserData(B2DVars.METEOR);
		
		Vector2 vel = new Vector2(-B2DVars.X_SPEED, -5f);
		body.setLinearVelocity(vel);
		System.out.println(" new meteor on " + pos);
	}
}


