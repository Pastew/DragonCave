package com.aghacks.dragoncave.handlers;

import static com.aghacks.dragoncave.states.Play.b2dCam;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.states.Play;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class MyInputProcessor implements InputProcessor{
	
	private Vector2 v1, v2;
	private boolean startedSwipe = false;
	
	@Override
	public boolean keyDown(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(screenX > Gdx.graphics.getWidth() *0.8f ){
			Play.slowMotionStart();		
			return false;
		}
		
		if(!startedSwipe && screenX < Game.V_WIDTH*0.7){
			v1 = new Vector2(screenX, screenY);
			startedSwipe = true;
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(!startedSwipe)
			Play.slowMotionStop();
		
		if(screenX > Game.V_WIDTH*0.8f)
			return false;
		
		v2 = new Vector2(screenX, screenY);
		Play.swipe(v1, v2);
		startedSwipe = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		Game.cam.zoom += amount;
		return false;
	}
	
	
}
