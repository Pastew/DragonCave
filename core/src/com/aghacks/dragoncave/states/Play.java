package com.aghacks.dragoncave.states;

import static com.aghacks.dragoncave.handlers.B2DVars.PPM;

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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Play extends GameState{
	
	private boolean gameOver = false;

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
	private float stalactiteRespawnTime = 11.5f;
	private float meteorRespawnTime = 1.5f;
	
	// slow motion
	private static boolean slowMotionOn = false;
	private static float camShift = B2DVars.X_SPEED;	
	private Array<Body> tmpBodies = new Array<Body>();
	
	// sounds
	private static Music intro;
	private static Music loop;
	private static Music slowMotionSound;
	
	// slow motion bar
	public static SlowMotionBar smBar;
	
	// gameOver dialog
	private GameOverDialog gameOverDialogProcessor;
	
	BitmapFont font;
	
	public Play(GameStateManager gsm) {
		super(gsm);
		
		world = new World(new Vector2(0,-9.81f), true);
		
		bg = new Background();

		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		// box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
		gameOverDialogProcessor = new GameOverDialog(this);
		
		// ===== SETTING OBJECTS =======
		new Ground(world, 0);
		new Ground(world, Game.V_HEIGHT / PPM);
		
		intro = Game.res.getMusic("intro");
		loop = Game.res.getMusic("loop");
		slowMotionSound = Game.res.getMusic("slowMotion");
		
		smBar = new SlowMotionBar();	
		
		gameOver=false;
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		// ========= GAME OBJECTS ========
		dragon = new Dragon(world);
		
		// meteors
		meteorTimer = new Timer(meteorRespawnTime);
		meteorTimer.start();
		
		// stalactites
		stalactiteTimer = new Timer(stalactiteRespawnTime);
		stalactiteTimer.start();		
			
		// camera pos X
		camXPos = dragon.getPosition().x * PPM + Game.V_WIDTH / 4;		
		
		intro.play();	
		
		// font
		//font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"),
		//         Gdx.files.internal("fonts/font.png"), false);
	}
	
	@Override
	public void handleInput() {	
	}

	private void produceMeteors() {
		if(meteorTimer.isDone()){
			meteorTimer.start();
			
			Vector2 newMeteorPos = 
					new Vector2(
							dragon.getWorldX()+ Game.V_WIDTH/10 / PPM, 
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
			dt2 = 1/300f;
		
		camXPos += camShift;
		
		dragon.fly();
		produceMeteors();
		produceStalactites();
		
		dragon.update(dt);
		smBar.update();
		
		handleInput();
		world.step(dt2, 6, 2);	
		
		if(!intro.isPlaying()){
			loop.setLooping(true);
			loop.play();
		}		
		if(dragon.isDead())
			gameOver();
	}

	@Override
	public void render() {

		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(hudCam.combined);	
		
		bg.draw(sb);		
		cam.position.set(camXPos, Game.V_HEIGHT/2,0);
		cam.update();
		
		// debug renderer
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
		sb.end();	
		smBar.render(sb);
		
		if(gameOver){
			gameOverDialogProcessor.render(sb);
		}
		// some  text TODO delete	
		//sb.begin();
		//font.draw(sb, "Jestem bardzo ladna czcionka hehe", 20, Game.V_HEIGHT - 50);
		//sb.end();	
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
		//this.font.dispose();	
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
			else if(intro.isPlaying())
				turnVolumeDown(intro);
			
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
		else if(intro.isPlaying())
			turnVolumeUp(intro);
		
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

		impulse.y = 0;
		dragon.swipe(impulse);
	}

	public void gameOver() {
		gameOver=true;		
		Gdx.input.setInputProcessor(gameOverDialogProcessor);
	}
	
	public  void playAgain(){
		Gdx.input.setInputProcessor(Game.gameInputProcessor);
		System.out.println("PLAY AGAIN");
	}	
	
	/*
	private void cleanUpBodies(){
		//dragon.createBody(world);
		world.destroyBody(dragon.getBody());
		
		System.out.println("new drag");
		for(Meteor m : meteors)
			m.destroy(world);
		
		for(Stalactite s : stalactites)
			s.destroy(world);	
		
	}
	*/
}
