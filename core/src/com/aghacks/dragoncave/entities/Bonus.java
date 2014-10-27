package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;
import static com.aghacks.dragoncave.handlers.Calculate.random;

import java.util.Random;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bonus{

	float size;
	Body body;
	Sprite sprite;
	PointLight light;
	
	public Bonus(World world, float x, RayHandler rh) {
		this.size = Gdx.graphics.getHeight()/20;
		float posY = (Game.V_HEIGHT + size*2*PPM) / PPM;
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(size);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0.1f;
		fdef.restitution = 0.2f;
		fdef.friction = 0.1f;
		
		body.createFixture(fdef).setUserData(B2DVars.ENEMY);
		
		float ranX = random(-B2DVars.X_SPEED, B2DVars.X_SPEED);
		Vector2 vel = new Vector2(ranX, 0);
		body.setLinearVelocity(vel);	
		
		int number = (new Random()).nextInt(4);
		initBonus(number);
		
		body.setUserData(sprite);	
		light = new PointLight(rh,64, Color.RED, size*3, x, posY);
		light.attachToBody(body, 0, 0);
	}	
	public void destroy(World world){
		world.destroyBody(body);
	}

	void initBonus(int nr){
		String name = "bonus"+nr;
		sprite = new Sprite(Game.res.getTexture("bonus1"));//TODO jeden kolor
		sprite.setSize(size*2 * PPM, size*2 * PPM);
		
		sprite.setOrigin((sprite.getWidth()/2) , 
							(sprite.getHeight()/2) );
	}

}
