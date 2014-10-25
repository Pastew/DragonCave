package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;
import jdk.nashorn.internal.runtime.regexp.joni.ApplyCaseFoldArg;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DSprite;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.aghacks.dragoncave.handlers.Timer;
import com.aghacks.dragoncave.states.Play;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Dragon extends B2DSprite{
	
	private boolean alive = true;

	//private Timer swipeTimer;
	private float desiredHeight = V_HEIGHT /3 / PPM;
	
	private final static float bodyWidth = V_HEIGHT/16 / PPM;
	private final static float bodyHeight = V_HEIGHT/16 / PPM;
	
	private boolean canJump = true;
	private boolean swipingLeft = false;
	private boolean swipingRight = false;

	
	public Dragon(World world){
		super(createBody(world));
		
		Texture tex = Game.res.getTexture("dragon");
		TextureRegion[] sprites = TextureRegion.split(tex, 319, 228)[0];
		setAnimation(sprites, 1/12f, bodyWidth*PPM*2, bodyHeight*PPM*2);		
		
		//swipeTimer = new Timer(0.5f);
	}
	
	private static Body createBody(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(20 / PPM, V_HEIGHT/2 / PPM); // TODO: Change x
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyWidth, bodyHeight); 	// setAsBox (halfWidth, halfHeight)
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.99f;
		
		body.createFixture(fdef).setUserData(B2DVars.DRAGON);
		
		body.setLinearVelocity(B2DVars.X_SPEED, 0);
		
		return body;
	}

	public void fly(){
		if(!alive)
			return;
		
		if(body.getPosition().y < desiredHeight && canJump){
			body.applyLinearImpulse(new Vector2(0, 4f), 
					body.getWorldCenter(), true);
			System.out.println("JUMP");
			
			canJump = false;
		}
		
		if(body.getLinearVelocity().y < 0 )
			canJump = true;
		
		// ======= SWIPE =============
		
		if(swipingLeft){
			//System.out.println(Play.camXPos);
			if(body.getPosition().x* PPM <  Play.camXPos - Game.V_WIDTH * 0.4f ){
				body.setLinearVelocity(B2DVars.X_SPEED, 0);
				swipingLeft = false;
				System.out.println("LEFT SWIPE END");
				canJump=true;
			}			
		}
		
		if(swipingRight){
			if(body.getPosition().x*PPM >  Play.camXPos - Game.V_WIDTH * 0.1f){
				body.setLinearVelocity(B2DVars.X_SPEED, 0);
				swipingRight = false;
				System.out.println("RIGHT SWIPE END");
				canJump=true;
			}			
		}		
	}

	public void swipe(Vector2 impulse) {
		float swipePower = 10f;
		
		if(impulse.x < -0.5f){
			if(swipingLeft)
				return;
			body.applyLinearImpulse(new Vector2(-swipePower, 0), body.getWorldCenter(), true);
			//body.setLinearVelocity(new Vector2(-swipePower, 0));
			swipingLeft = true;
		}
		else if(impulse.x > 0.5f){
			if(swipingRight)
				return;
			body.applyLinearImpulse(new Vector2(swipePower, 0), body.getWorldCenter(), true);
			//body.setLinearVelocity(new Vector2(swipePower, 0));
			swipingRight = true;
		}
		
	}
	
	public float getWorldX() {
		return body.getPosition().x;
	}
	
	public float getWorldY() {
		return body.getPosition().y;
	}

	public void die() {
		alive = false;		
	}
/*
	private void goBack(Vector2 impulse) {
		goBackImpulse = impulse;
		shouldGoBack = true;		
	}
*/
	
	public Body getBody(){
		return this.body;
	}
	
}
