package com.aghacks.dragoncave.entities;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;
import static com.aghacks.dragoncave.Game.V_HEIGHT;
import static com.aghacks.dragoncave.Game.V_WIDTH;

import com.aghacks.dragoncave.handlers.B2DVars;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends Entity{

	public Ground(World world, float y){
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(0 / PPM, y); //TODO: Maybe x to change
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(V_WIDTH / PPM, 5 / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.friction=0.4f;
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(B2DVars.GROUND);
	}
}