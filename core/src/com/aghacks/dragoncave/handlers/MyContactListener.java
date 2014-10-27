package com.aghacks.dragoncave.handlers;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.states.Play;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
	
	private Sound fall = Game.res.getSound("fall");
	private Sound fireball = Game.res.getSound("fireball");
	private Sound floor = Game.res.getSound("floor");
	private Sound menu = Game.res.getSound("menu");
	private Sound collision = Game.res.getSound("collision");
	
	@Override
	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();

		if(fa.getUserData().equals(B2DVars.ENEMY) && fb.getUserData().equals(B2DVars.DRAGON) ){
			collision.play();
			Play.dragon.die();
			fall.play();
		}
		if(fb.getUserData().equals(B2DVars.ENEMY) && fa.getUserData().equals(B2DVars.DRAGON) ){
			collision.play();
			Play.dragon.die();
			fall.play();
		}
		
		if(fa.getUserData().equals(B2DVars.GROUND) && fb.getUserData().equals(B2DVars.DRAGON) ){
			floor.play();
		}
		if(fb.getUserData().equals(B2DVars.ENEMY) && fa.getUserData().equals(B2DVars.GROUND) ){
			floor.play();
		}		
	}

	@Override
	public void endContact(Contact c) {
	}
	
	@Override
	public void preSolve(Contact c, Manifold m) {
		
	}

	@Override
	public void postSolve(Contact c, ContactImpulse ci) {
		
	}
}
