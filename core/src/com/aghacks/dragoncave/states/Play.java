package com.aghacks.dragoncave.states;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;
import static com.aghacks.dragoncave.handlers.Calculate.random;

import java.util.Random;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.entities.Dragon;
import com.aghacks.dragoncave.entities.Ground;
import com.aghacks.dragoncave.entities.Meteor;
import com.aghacks.dragoncave.entities.Stalactite;
import com.aghacks.dragoncave.handlers.B2DVars;
import com.aghacks.dragoncave.handlers.Background;
import com.aghacks.dragoncave.handlers.GameOverDialog;
import com.aghacks.dragoncave.handlers.GameStateManager;
import com.aghacks.dragoncave.handlers.MyContactListener;
import com.aghacks.dragoncave.handlers.SlowMotionBar;
import com.aghacks.dragoncave.handlers.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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
	
	private boolean gameOver = false;
	Array<PointLight> lights = new Array<PointLight>();
	
	// box2d
	public static World world;
	private MyContactListener cl;

	// camera
	public static Box2DDebugRenderer b2dr;	
	public static OrthographicCamera b2dCam;
	public static float camXPos;
	
	// entities
	public static Dragon dragon;
	private Timer meteorTimer;
	private Timer stalactiteTimer;
	
	private Array<Meteor> meteors = new Array<Meteor>();
	private Array<Stalactite> stalactites = new Array<Stalactite>();
	
	// bg
	private static Background bg;
	
	// respawn
	private float stalactiteRespawnTime = 2.5f;
	private float meteorRespawnTime = 4.5f;
	
	private float minRespawnTime = 2f;
	private float maxRespawnTime = 4f;
	
	// slow motion
	private static boolean slowMotionOn = false;

	private static float camShift = B2DVars.X_SPEED;
	
	private Array<Body> tmpBodies = new Array<Body>();
	
	// sounds
	//private static Music intro;
	private static Music loop;
	private static Music slowMotionSound;
	private Sound lightSound;
		
	// slow motion bar
	public static SlowMotionBar smBar;
	
	private GameOverDialog gameOverDialog;
	Ground roof;
    static public RayHandler rh;

	public Play(GameStateManager gsm) {
		super(gsm);
		
		world = new World(new Vector2(0,-0.81f), true);
		
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		// box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
				
		// ========= GAME OBJECTS ========
		dragon = new Dragon(world);
		
		new Ground(world, 0);
		//roof = new Ground(world, Game.V_HEIGHT+20 / PPM);
		
		// meteors
		meteorTimer = new Timer(meteorRespawnTime);
		meteorTimer.start();
		// stalactites
		stalactiteTimer = new Timer(stalactiteRespawnTime);
		stalactiteTimer.start();
		
		bg = new Background();
			
		// camera pos X
		camXPos = dragon.getPosition().x * PPM + Game.V_WIDTH / 4;		
		
		loadSounds();
		
		loop.setLooping(true);
		loop.play();
		//intro.play();
		
		// Slow Motion Bar
		smBar = new SlowMotionBar();
		
		// game over dialog
		gameOverDialog = new GameOverDialog(this);
		setUpLights();
	}
	void setUpLights(){
    	RayHandler.setGammaCorrection(true);
 		RayHandler.useDiffuseLight(true);
 		rh = new RayHandler(world);
 		float l = 0.6f;
 		//rh.setAmbientLight(l,l,l, 0.1f);
 		rh.setAmbientLight(0.1f, 0.1f, 0.1f, 0.1f);
 		rh.setCulling(true);		
 		rh.setBlur(true);
 		rh.setBlurNum(1);
 		rh.setShadows(true);
 		cam.update(true);
 		
 		//new DirectionalLight(rh, 24, new Color(1,1,1,1f), 30)
 		//.setPosition(0, 0);
 		//new ConeLight(rh, 128, Color.WHITE, Game.V_HEIGHT, camXPos, Game.V_HEIGHT,0, 120);
 		lights.add(new PointLight(rh,128, Color.WHITE, Game.V_HEIGHT*1.5f, camXPos,Game.V_HEIGHT));
    }

	private void loadSounds(){
		//intro = Game.res.getMusic("intro");
		loop = Game.res.getMusic("loop");
		slowMotionSound = Game.res.getMusic("slowMotion");	
		lightSound = Game.res.getSound("light");
	}
	@Override
	public void handleInput() {	
	}

	private void produceMeteors() {
		if(meteorTimer.isDone()){
			meteorTimer.start();
			
			float divider = random(3,10);
			float xPos = dragon.getWorldX()+ Game.V_WIDTH/divider / PPM; 
			
			meteors.add(new Meteor(world, xPos));
			if(meteors.size > 2){
				meteors.get(0).destroy(world);
				meteors.removeIndex(0);
				
				minRespawnTime-=0.1f;
				maxRespawnTime-=0.1f;
			}
		}
		
		
		meteorRespawnTime = random(minRespawnTime, maxRespawnTime);		
	}
	
	private void produceStalactites() {
		if(stalactiteTimer.isDone()){
			stalactiteTimer.start();
			
		float newStalactiteX = dragon.getWorldX() + Game.V_WIDTH/10 / PPM;
		Stalactite newStalactite = new Stalactite(world, newStalactiteX);
		
		stalactites.add(newStalactite);
		
		if(stalactites.size > 2)
			stalactites.get(0).destroy(world);
			stalactites.removeIndex(0);
		}
		stalactiteRespawnTime = random(minRespawnTime, maxRespawnTime);		
	}
	
	@Override	
	public void update(float dt) {
		float dt2 = dt;
		if(slowMotionOn)
			dt2 = 1/300f;
		
		camXPos += camShift;
		
		dragon.fly();
		//produceMeteors();
		//produceStalactites();
		
		dragon.update(dt);
		smBar.update();
		if(smBar.isEmpty())
			slowMotionStop();
		
		handleInput();
		world.step(dt2, 6, 2);	
				
		if(dragon.isDead())
			gameOver();
		
		if(rh.pointAtShadow(dragon.getWorldX()*PPM, dragon.getWorldY()*PPM)){
			Color color = Color.WHITE;
			if(!dragon.isDead()){
				lightSound.play();
				int rand = (new Random()).nextInt(5);
				switch(rand){
				case 1:
				color = Color.CYAN;
				break;
				case 2:
				color = Color.RED;
				break;
				case 3:
				color = Color.NAVY;
				break;
				case 4:
				color = Color.WHITE;
				break;				
				}
				
				lights.add(new PointLight(rh,128, color, 
						Game.V_HEIGHT*1.5f, camXPos + Game.V_WIDTH/12,Game.V_HEIGHT));
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(hudCam.combined);	
		
		bg.draw(sb);		
		cam.position.set(camXPos, 
				Game.V_HEIGHT/2, 
				0);
		cam.update();
		
		// debug renderer
		//b2dr.render(world, b2dCam.combined);
		
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
			
					
		sb.end();	
		sb.setProjectionMatrix(hudCam.combined); // ??	
		rh.setCombinedMatrix(cam.combined);
		rh.update();
		rh.render();
		smBar.render(sb);
		
		if(gameOver)
			gameOverDialog.render(sb);
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
		sb.dispose();
	}
	
	public static void slowMotionStart(){
		long currentTime = System.currentTimeMillis();
		if(currentTime - smBar.getLastClick() >= SlowMotionBar.FROZEN_TIME) {
			slowMotionOn = true;
			bg.slowMotionOn();
			camShift /= 2;
			
			dragon.slowMotionAnimation(true);
			
			if(loop.isPlaying())
				turnVolumeDown(loop);
			
			slowMotionSound.play();
			
			smBar.decrease();
			smBar.setLastClick(currentTime);
		} else {
			smBar.froze();
		}
	}
	
	public static void slowMotionStop(){
		if(slowMotionOn==true){
			bg.slowMotionOff();
			camShift *= 2;
			if(slowMotionSound.isPlaying())
				slowMotionSound.stop();
		}
		slowMotionOn = false;
		dragon.slowMotionAnimation(false);		
		if(loop.isPlaying())
			turnVolumeUp(loop);
		
		smBar.increase();
		long currentTime = System.currentTimeMillis();
		if(currentTime - smBar.getLastClick() < SlowMotionBar.FROZEN_TIME) {
			smBar.froze();
		}
	}
	
	private static void turnVolumeUp(Music m) {
		m.setVolume(m.getVolume()*4);
	}
	
	private static void turnVolumeDown(Music m) {
		m.setVolume(m.getVolume()/4);
	}

	public static void swipe(Vector2 v1, Vector2 v2){
		if(v1==null || v2==null)
			return;
		
		Vector2 impulse = new Vector2(v2.sub(v1));
		impulse.x /= PPM;
		impulse.y /= -PPM;	// - bo os Y jest w druga strone
		
		if(impulse.x < 0.15f && impulse.y < 0.15f){
			dragon.getBody().setLinearVelocity(0,dragon.getBody().getLinearVelocity().y);
		}
		
		impulse.y = 0;
		dragon.swipe(impulse, slowMotionOn);
	}

	public void gameOver() {
		gameOver=true;
		Gdx.input.setInputProcessor(gameOverDialog);
		
	}

	public void playAgain() {
		//System.out.println("PLAY AGAIN");
	}
}
