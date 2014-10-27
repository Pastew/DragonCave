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
	public static OrthographicCamera cam;
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
		res.loadTexture("images/bg.png", "bg");
		res.loadTexture("images/dragon.png", "dragon");
		res.loadTexture("images/slowmo.png", "slowMotion");
		
		res.loadTexture("images/again.png", "again");
		res.loadTexture("images/over.png", "gameOverDialog");
		res.loadTexture("images/menuback.png", "back");
				
		for(int i = 0 ; i < 4 ; ++i){
			res.loadTexture("images/bonus"+(i+1)+".png", "bonus"+(i+1));
			res.loadTexture("images/rock"+(i+1)+".png", "rock"+(i+1));
		}
		
		res.loadTexture("images/fire.png", "fire");
		
		res.loadMusic("music/intro.mp3");
		res.loadMusic("music/loop.mp3");
		res.loadMusic("music/slowMotion.mp3");
		
		res.loadSound("sounds/fall.wav");
		res.loadSound("sounds/fireball.wav");
		res.loadSound("sounds/floor.wav");
		res.loadSound("sounds/menu.wav");
		res.loadSound("sounds/collision.wav");
		res.loadSound("sounds/light.wav", "light");

		
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
		res.removeAll();		
	}
	
	public SpriteBatch getSpriteBatch(){ return sb; };
	public OrthographicCamera getCamera(){ return cam; };
	public OrthographicCamera getHudCamera(){ return hudCam; };	
}
