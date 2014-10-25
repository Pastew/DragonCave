package com.aghacks.dragoncave;

import com.aghacks.dragoncave.handlers.Content;
import com.aghacks.dragoncave.handlers.GameStateManager;
import com.aghacks.dragoncave.handlers.MyInputProcessor;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener {

	public static final String TITLE = "Dragon Cave";
	public static int V_WIDTH;
	public static int V_HEIGHT;
	public static final int SCALE = 2;
	
	public static float STEP = 1 / 60f;
	private float accum;

	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	public static Content res;
	
	@Override
	public void create() {
		
		V_WIDTH = Gdx.graphics.getWidth();
	    V_HEIGHT = Gdx.graphics.getHeight();
	    
		Gdx.input.setInputProcessor(new MyInputProcessor());
		sb = new SpriteBatch();
		
		res = new Content();
		res.loadTexture("images/dragon.png", "dragon");
		res.loadTexture("images/stalactite.png", "stalactite");
		res.loadTexture("images/meteor.png", "meteor");
		res.loadTexture("images/ground.png", "ground");
		res.loadTexture("images/slowMotion.png", "slowMotion");

		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP){
			accum -=STEP;
			gsm.update(STEP);
			gsm.render();
			//MyInput.update();
		}
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {
		res.disposeTexture("dragon");
		
	}
	
	public SpriteBatch getSpriteBatch(){ return sb; };
	public OrthographicCamera getCamera(){ return cam; };
	public OrthographicCamera getHudCamera(){ return hudCam; };	
}
