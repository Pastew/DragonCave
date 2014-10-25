package com.aghacks.dragoncave.handlers;

import com.aghacks.dragoncave.states.Play;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
	
	@Override
	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();

		if(fa.getUserData() != null && fa.getUserData().equals(B2DVars.DRAGON) ){
			Play.dragon.die();
		}
		if(fa.getUserData() != null && fb.getUserData().equals(B2DVars.DRAGON) ){
			Play.dragon.die();

		}
	}

	@Override
	public void endContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot") ){
		}
		if(fa.getUserData() != null && fb.getUserData().equals("foot") ){
		}
	}
	
	@Override
	public void preSolve(Contact c, Manifold m) {
		
	}

	@Override
	public void postSolve(Contact c, ContactImpulse ci) {
		
	}
}
