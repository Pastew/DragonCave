package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.aghacks.dragoncave.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bulb{
	private float size = 20 / PPM;
	private PointLight light;
	private Body body;
	private Vector2 pos;
	
	public Bulb(World world, Vector2 pos, RayHandler rh){
		super();		
		this.pos = pos;
		
		//======= circle =========
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.StaticBody;
    	bodyDef.position.set(pos);
        
        this.body = world.createBody(bodyDef);
        
        CircleShape	circle = new CircleShape();
        circle.setRadius(size);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.9f; 
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.2f; 
        
        /*Fixture fixture = */this.body.createFixture(fixtureDef);
        
        circle.dispose();
                
		// ========= LIGHT =============
        
	    // final Color c = new Color(MathUtils.random()*0.4f,
		// MathUtils.random()*0.4f,
		// MathUtils.random()*0.4f, 1f);
		light = new PointLight(rh, 64);
        //light= new ConeLight(NoMoreFlapping.rayHandler, 16, Color.WHITE, 
        //		NoMoreFlapping.WIDTH/5, 0, 0,45, 90);
		light.setDistance(Game.V_WIDTH/10 / PPM );
		//light = new ConeLight(NoMoreFlapping.rayHandler, 12, null,
		//32, 0 , 0, 0, 30);
		// light.setStaticLight(true);
		light.attachToBody(body, 0, -size/2);
		light.setColor(Color.WHITE);
		//light.setSoftnessLength(10);
		//light.setColor(MathUtils.random(), MathUtils.random(),
		//	MathUtils.random(), 1f);
		// light.setColor(0.1f,0.1f,0.1f,0.1f);
		
	}
		
	public void dispose(){
	}
}
