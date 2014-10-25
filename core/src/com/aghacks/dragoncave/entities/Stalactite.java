package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.aghacks.dragoncave.Game;
public class Stalactite{
		
	private static float bodyHeight = V_HEIGHT/15 / PPM;
	private static float bodyWidth = bodyHeight/4;
	Body body;
	
	public Stalactite (World world, float posX){		
		//super("images/stalactite.png", V_HEIGHT/10 / PPM, V_HEIGHT/10 / 4 / PPM);
		
		BodyDef bdef = new BodyDef();
		float posY = (Game.V_HEIGHT - bodyHeight/2) / PPM;
		bdef.position.set(posX, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyWidth, bodyHeight);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.2f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.1f;
		
		body.createFixture(fdef).setUserData(B2DVars.ENEMY);
		Sprite boxSprite = new Sprite(Game.res.getTexture("rock1"));
		boxSprite.setSize(bodyWidth*2 * PPM, bodyHeight*2 * PPM);
		
		boxSprite.setOrigin((boxSprite.getWidth()/2) , 
							(boxSprite.getHeight()/2) );

		body.setUserData(boxSprite);
	}
	
	public void destroy(World world){
		world.destroyBody(body);
	}
}