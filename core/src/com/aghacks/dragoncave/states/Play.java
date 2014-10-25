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
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	public static Dragon dragon;
	private Timer meteorTimer;
	private Timer stalactiteTimer;
	
	// bg
	private Background bg;
	
	// respawn
	private float stalactiteRespawnTime = 1.5f;
	private float meteorRespawnTime = 1f;
	
	// slow motion
	private static boolean slowMotionOn = false;
	private Sprite slowMotionSprite;
	
	private Array<Body> tmpBodies = new Array<Body>();
	
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
			new Meteor(world, newMeteorPos);
		}
	}
	
	private void produceStalactites() {
		if(stalactiteTimer.isDone()){
			stalactiteTimer.start();
			
			float newStalactiteX = dragon.getWorldX()+ Game.V_WIDTH/2 / PPM;
		
			//new Stalactite(world, newStalactiteX);
		}
	}
	
	@Override	
	public void update(float dt) {
		float dt2 = dt;
		if(slowMotionOn)
			dt2 = 1/200f;
		
		camXPos += B2DVars.X_SPEED;
		
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

	@Override
	public void dispose() {
		
		
	}
	
	public static void slowMotionStart(){
		//if(!slowMotionOn)
		//	Game.STEP = 1/15f;
		slowMotionOn = true;

	}
	
	public static void slowMotionStop(){
		//if(slowMotionOn)
		//	Game.STEP = 1/60f;
		slowMotionOn = false;
	}
	
	public static void swipe(Vector2 v1, Vector2 v2){
		System.out.println("swipe!");
		
		Vector2 impulse = new Vector2(v2.sub(v1));
		impulse.x /= PPM;
		impulse.y /= -PPM;	// - bo os Y jest w druga strone
		System.out.println("przed " + impulse);
		float limit = 5f;
		impulse.x = impulse.x % limit;
		impulse.y = impulse.y % limit;
		dragon.swipe(impulse);
		System.out.println("po " + impulse);

	}
	
}
