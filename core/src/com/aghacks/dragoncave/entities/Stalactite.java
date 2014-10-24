package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Stalactite extends Entity{
	
	private final float length = V_HEIGHT/30 / PPM;
	
	public Stalactite (World world, float posX){
		BodyDef bdef = new BodyDef();
		float posY = (Game.V_HEIGHT - length/2) / PPM;
		bdef.position.set(posX, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(length/8, length/2);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.2f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.1f;
		
		body.createFixture(fdef).setUserData(B2DVars.METEOR);
		
		//Vector2 vel = new Vector2(-B2DVars.X_SPEED, -5f);
		//body.setLinearVelocity(vel);
	}
}