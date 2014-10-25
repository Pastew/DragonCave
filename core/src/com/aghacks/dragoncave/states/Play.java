package com.aghacks.dragoncave.states;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

import java.util.ArrayList;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.entities.Dragon;
import com.aghacks.dragoncave.entities.Entity;
import com.aghacks.dragoncave.entities.Ground;
import com.aghacks.dragoncave.entities.Meteor;
import com.aghacks.dragoncave.entities.Stalactite;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.aghacks.dragoncave.handlers.Background;
import com.aghacks.dragoncave.handlers.GameStateManager;
import com.aghacks.dragoncave.handlers.MyContactListener;
import com.aghacks.dragoncave.handlers.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class Play extends GameState{
	
	// box2d
	private World world;
	private MyContactListener cl;

	// camera
	public static Box2DDebugRenderer b2dr;	
	public static OrthographicCamera b2dCam;
	private float camXPos;
	
	// entities
	public static Dragon dragon;
	private Timer meteorTimer;
	private Timer stalactiteTimer;
	
	private Array<Meteor> meteors = new Array<Meteor>();
	private Array<Stalactite> stalactites = new Array<Stalactite>();
	
	// bg
	private static Background bg;
	
	// respawn
	private float stalactiteRespawnTime = 11.5f;
	private float meteorRespawnTime = 10.5f;
	
	// slow motion
	private static boolean slowMotionOn = false;
	private Sprite slowMotionSprite;
	private static float camShift = B2DVars.X_SPEED;
	
	private Array<Body> tmpBodies = new Array<Body>();
	
	// sounds
	Music music;
	
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
		
		bg = new Background();
		slowMotionSprite = new Sprite(Game.res.getTexture("slowMotion"));	
		slowMotionSprite.setSize(Game.V_WIDTH/10, Game.V_WIDTH/10);
		slowMotionSprite.setPosition(Game.V_WIDTH - slowMotionSprite.getWidth(),0);
		
		// camera pos X
		camXPos = dragon.getPosition().x * PPM + Game.V_WIDTH / 4;
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music/20141025-hackaton01.mp3"));
		music.play();
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
			meteors.add(new Meteor(world, newMeteorPos));
			if(meteors.size > 5){
				meteors.get(0).destroy(world);
				meteors.removeIndex(0);
			}
		}
	}
	
	private void produceStalactites() {
		if(stalactiteTimer.isDone()){
			stalactiteTimer.start();
			
			float newStalactiteX = dragon.getWorldX()+ Game.V_WIDTH/2 / PPM;
		
			stalactites.add(new Stalactite(world, newStalactiteX));
			if(stalactites.size > 5)
				stalactites.get(0).destroy(world);
				stalactites.removeIndex(0);
		}
	}
	
	@Override	
	public void update(float dt) {
		float dt2 = dt;
		if(slowMotionOn)
			dt2 = 1/150f;
		
		camXPos += camShift;
		
		dragon.fly();
		produceMeteors();
		produceStalactites();
		
		dragon.update(dt);
		
		handleInput();
		world.step(dt2, 6, 2);		
	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(hudCam.combined); // ??

		bg.draw(sb);

		//cam.position.set(dragon.getPosition().x * PPM + Game.V_WIDTH / 4, 
		cam.position.set(camXPos, 
				Game.V_HEIGHT/2, 
				0);
		cam.update();
		
		/*
		if(Math.abs(dragon.getPosition().x * PPM + Game.V_WIDTH / 4 - camXPos) > Game.V_WIDTH/3)
			shiftCamera();
			*/
		
		// draw box2d world
		b2dr.render(world, b2dCam.combined);
		
		// draw dragon
		sb.setProjectionMatrix(cam.combined);
		dragon.render(sb);	
		
		sb.setProjectionMatrix(cam.combined);
		
		// draw objects
		sb.begin();
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies)
			if(body.getUserData() != null && body.getUserData() instanceof Sprite){
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x*PPM - sprite.getWidth()/2,
						body.getPosition().y*PPM - sprite.getHeight()/2);
				sprite.setRotation((body.getAngle() * MathUtils.radiansToDegrees));
				sprite.draw(sb);
			}
		sb.setProjectionMatrix(hudCam.combined); // ??
		slowMotionSprite.draw(sb);
		sb.end();
	}
	void shiftCamera(){
		float shift =5f;
		int left;
		if(dragon.getPosition().x * PPM + Game.V_WIDTH / 4 - camXPos > 0)
			left = 1;
		else left = -1;			
		cam.position.set(cam.position.x + shift * left, Game.V_HEIGHT/2, 0);
		cam.update();
	}

	@Override
	public void dispose() {
		
		
	}
	
	public static void slowMotionStart(){
		//if(!slowMotionOn)
		//	Game.STEP = 1/15f;
		slowMotionOn = true;
		bg.slowMotionOn();
		camShift /= 2;
		dragon.getBody().setLinearVelocity(dragon.getBody().getLinearVelocity().x*2,
				dragon.getBody().getLinearVelocity().y);

	}
	
	public static void slowMotionStop(){
		//if(slowMotionOn)
		//	Game.STEP = 1/60f;
		if(slowMotionOn==true){
			bg.slowMotionOff();
			camShift *= 2;
		}
		slowMotionOn = false;

	}
	
	public static void swipe(Vector2 v1, Vector2 v2){
		//System.out.println("swipe!");
		
		Vector2 impulse = new Vector2(v2.sub(v1));
		impulse.x /= PPM;
		impulse.y /= -PPM;	// - bo os Y jest w druga strone
		System.out.println("przed " + impulse);
		//float limit = 5f;
		//impulse.x = impulse.x % limit;
		//impulse.y = impulse.y % limit;
		System.out.println(impulse.x);
		float power = 10;
		if(impulse.x < -1 ) 
			impulse.x = -power;
		else 
			impulse.x = power;
		
		impulse.y = 0;
		dragon.swipe(impulse);
		//System.out.println("po " + impulse);

	}
	
}
