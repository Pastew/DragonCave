package com.aghacks.dragoncave.states;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import java.util.ArrayList;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.entities.Dragon;
import com.aghacks.dragoncave.entities.Entity;
import com.aghacks.dragoncave.entities.Ground;
import com.aghacks.dragoncave.entities.Meteor;
import com.aghacks.dragoncave.entities.Stalactite;
import com.aghacks.dragoncave.handlers.GameStateManager;
import com.aghacks.dragoncave.handlers.MyContactListener;
import com.aghacks.dragoncave.handlers.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


public class Play extends GameState{
	
	// box2d
	private World world;
	private MyContactListener cl;

	// camera
	public static Box2DDebugRenderer b2dr;	
	public static OrthographicCamera b2dCam;
	
	// entities
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private Dragon dragon;
	private Timer meteorTimer;
	private Timer stalactiteTimer;
	
	// respawn
	private float stalactiteRespawnTime = 1.5f;
	private float meteorRespawnTime = 1f;
	
	public Play(GameStateManager gsm) {
		super(gsm);
		
		world = new World(new Vector2(0,-9.81f), true);
		
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		// box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
		// ========= GAME OBJECTS ========
		dragon = new Dragon(world);
		new Ground(world, 0);
		new Ground(world, Game.V_HEIGHT / PPM);
		
		// meteors
		meteorTimer = new Timer(meteorRespawnTime);
		meteorTimer.start();
		// stalactites
		stalactiteTimer = new Timer(stalactiteRespawnTime);
		stalactiteTimer.start();
	}

	@Override
	public void handleInput() {	
		/*
		if(MyInput.isPressed(MyInput.BUTTON1 )){
			
		}
			
		if(MyInput.isDown(MyInput.BUTTON2 )){
			
		}
		*/
	}

	private void produceMeteors() {
		if(meteorTimer.isDone()){
			meteorTimer.start();
			
			Vector2 newMeteorPos = 
					new Vector2(
							dragon.getWorldX()+ Game.V_WIDTH/2 / PPM, 
							Game.V_HEIGHT / PPM);
			entities.add(new Meteor(world, newMeteorPos));
		}
	}
	
	private void produceStalactites() {
		if(stalactiteTimer.isDone()){
			stalactiteTimer.start();
			
			float newStalactiteX = dragon.getWorldX()+ Game.V_WIDTH/2 / PPM;
		
			entities.add(new Stalactite(world, newStalactiteX));
		}
	}
	@Override
	public void update(float dt) {
		dragon.fly();
		produceMeteors();
		produceStalactites();
		
		handleInput();
		world.step(dt, 6, 2);		
	}

	@Override
	public void render() {
		//clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw box2d world
		b2dr.render(world, b2dCam.combined); 
	}

	@Override
	public void dispose() {
		
		
	}
	
	
	
	
}
