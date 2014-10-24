package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Dragon{
	
	private Body body;
	private float desiredHeight = V_HEIGHT / 2 / PPM;
	
	private final float bodyWidth = V_HEIGHT/16 / PPM;
	private final float bodyHeight = V_HEIGHT/16 / PPM;
	
	private boolean canJump = true;
	
	public Dragon(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(20 / PPM, V_HEIGHT/2 / PPM); // TODO: Change x
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyWidth, bodyHeight); 	// setAsBox (halfWidth, halfHeight)
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		
		body.createFixture(fdef).setUserData(B2DVars.DRAGON);
		
		body.setLinearVelocity(B2DVars.X_SPEED, 0);
		
		//fdef.filter.categoryBits = B2DVars.BIT_DRAGON;
		//fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_OBJECT;
		
		
	}

	public void fly(){
		if(body.getPosition().y < desiredHeight && canJump){
			//body.setLinearVelocity(B2DVars.X_SPEED, 3);
			body.applyLinearImpulse(new Vector2(0, 3), 
					body.getWorldCenter(), true);
			
			canJump = false;
			//if(body.getLinearVelocity().x > B2DVars.X_SPEED*1.5f)
			//	body.applyLinearImpulse( new Vector2(-B2DVars.X_SPEED/2, 0), 
			//			body.getWorldCenter(), true);
			
		}
		//System.out.println(body.getPosition().y + " , " + V_HEIGHT / 2 / PPM); 
		//System.out.println();
		
		if(body.getLinearVelocity().y < 0)
			canJump = true;
	}

	public float getWorldX() {
		return body.getPosition().x;
	}
	
	public float getWorldY() {
		return body.getPosition().y;
	}
}
