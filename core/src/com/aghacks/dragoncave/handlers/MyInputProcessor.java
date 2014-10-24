package com.aghacks.dragoncave.handlers;

import com.badlogic.gdx.InputProcessor;
import static com.aghacks.dragoncave.states.Play.b2dCam;
import static com.aghacks.dragoncave.states.Play.b2dr;

public class MyInputProcessor implements InputProcessor{

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
		System.out.println("Dotknales mnie");
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
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
		b2dCam.zoom += amount;
		return false;
	}
	
	
}
