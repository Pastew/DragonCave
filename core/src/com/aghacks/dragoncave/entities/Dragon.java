package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DSprite;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.aghacks.dragoncave.states.Play;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Dragon extends B2DSprite{
	
	private boolean alive = true;

	private static float lowestHeight = V_HEIGHT * 0.35f ;
	private float maxHeight = V_HEIGHT * 0.5f ;
	
	private float yPower = 2f;
	private float xPower = 0.5f;
	
	private final static float bodyWidth = V_HEIGHT / 16 / PPM;
	private final static float bodyHeight = V_HEIGHT / 16 / PPM;
	
	private boolean swipingLeft = false;
	private boolean swipingRight = false;
	
	public Dragon(World world){
		super(createBody(world));
				
		Texture tex = Game.res.getTexture("dragon");
		TextureRegion[] sprites = TextureRegion.split(tex, 319, 228)[0];
		setAnimation(sprites, 1/12f, bodyWidth*PPM*2, bodyHeight*PPM*2);		
	}
	
	private static Body createBody(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(5 / PPM, lowestHeight / PPM); 
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyWidth, bodyHeight); 	// setAsBox (halfWidth, halfHeight)
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		
		
		//android
		//fdef.density = 3.f;
		
		//pc
		fdef.density = 1.5f;
		
		
		fdef.friction= 0.6f;
		
		Fixture fixture = null;
		//should weight 4kg
		//while(true) {
			fixture = body.createFixture(fdef);
		//	if(body.getMass() > 3.8f)
		//		break;
		//	fdef.density*=1.1f;
		//	body.destroyFixture(fixture);
		//}
		fixture.setUserData(B2DVars.DRAGON);
		System.out.println(" OStateczna masa " + body.getMass());
		
		body.setLinearVelocity(B2DVars.X_SPEED, 0);
		
		return body;
	}

	public void fly(){
		if(!alive)
			return;			
		 float posY = body.getPosition().y * PPM;
		// === FLY UP ===
		if(posY  < lowestHeight){
			body.applyLinearImpulse(new Vector2(0, yPower), 
					body.getWorldCenter(), true);
		}
		// if too high
		if(posY > maxHeight){
			body.setLinearVelocity(body.getLinearVelocity().x, 0);
		}
		
		// === DON'T GO OUT OF SCREEN ===	
		//left side
		if(body.getPosition().x * PPM  < Play.camXPos-Game.V_WIDTH/2){
			//body.setLinearVelocity(0,body.getLinearVelocity().y);
			body.applyForceToCenter(new Vector2(2,0),true);
		}
		//right side
		if(body.getPosition().x * PPM  > Play.camXPos +Game.V_WIDTH * 0.9f){
			//body.applyForceToCenter(new Vector2(-xPower, 0), true);
			body.setLinearVelocity(0,body.getLinearVelocity().y);
		}
		
		// ======= SWIPE =============
		
		if(swipingLeft){
			//System.out.println(Play.camXPos);
			if(body.getPosition().x* PPM <  Play.camXPos - Game.V_WIDTH * 0.4f ){
				body.setLinearVelocity(B2DVars.X_SPEED, 0);
				swipingLeft = false;
			}			
		}
		
		if(swipingRight){
			if(body.getPosition().x*PPM >  Play.camXPos + Game.V_WIDTH * 0.1f){
				body.setLinearVelocity(B2DVars.X_SPEED, 0);
				swipingRight = false;
			}			
		}		
	}

	public void swipe(Vector2 impulse, boolean slowMotionOn) {
		if(!alive)
			return;

		float swipePower = 5f;
		if(slowMotionOn)
			swipePower *=4;
		
		if(impulse.x < -0.5f){
			//if(swipingLeft)
			//	return;
			body.applyLinearImpulse(new Vector2(-swipePower, 0), body.getWorldCenter(), true);
			//body.setLinearVelocity(new Vector2(-swipePower, 0));
			swipingLeft = true;
		}
		else if(impulse.x > 0.5f){
			//if(swipingRight)
			//	return;
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
		if(!alive)
			return;
		
		alive = false;		
		this.animation.setAnimationSpeed(0);
		body.setLinearVelocity(-3,0);
	}
	
	public Body getBody(){
		return this.body;
	}
	
	public void slowMotionAnimation(boolean slow){
		if(slow)
			this.animation.setAnimationSpeed(1/8f);
		else
			this.animation.setAnimationSpeed(1/12f);
	}

	public boolean isDead() {
		return !alive;
	}	
}
