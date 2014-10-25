package com.aghacks.dragoncave.handlers;

import com.aghacks.dragoncave.Game;
import com.aghacks.dragoncave.states.Play;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverDialog implements InputProcessor{
	Sprite dialog, back, again;
	Play play;
	
	public GameOverDialog(Play play){
		this.play = play;
		dialog = new Sprite(Game.res.getTexture("gameOverDialog"));
		back = new Sprite (Game.res.getTexture("back"));
		again = new Sprite(Game.res.getTexture("again"));
		
		float tmp = dialog.getHeight() / dialog.getWidth();
		
		float h = Gdx.graphics.getHeight()*0.5f;		
		float w = h/tmp;
		
		dialog.setSize(w, h);
		dialog.setPosition(Game.V_WIDTH/2 - w/2, Game.V_HEIGHT/5);
		
		h = Gdx.graphics.getHeight()/8f;
		w = h;
		
		back.setSize(w,h);
		back.setPosition(dialog.getX(),dialog.getY() - back.getHeight()/2f);
		
		again.setSize(w,h);
		again.setPosition(dialog.getX() + dialog.getWidth() - again.getWidth(), dialog.getY() - again.getHeight()/2f);		
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		dialog.draw(sb);
		back.draw(sb);
		again.draw(sb);
		sb.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("GameOverDialog Processor!!!!");

		
		if(touchOn(screenX, Gdx.graphics.getHeight()-screenY, again))
			play.playAgain();
		
		if(touchOn(screenX, Gdx.graphics.getHeight()-screenY, back))
			Game.exit();
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean touchOn(int x1, int y1, Sprite sprite){
		int x2 = (int) sprite.getX();
		int y2 = (int) sprite.getY();
		int w2 = (int) sprite.getWidth();
		int h2 = (int) sprite.getHeight();
		
		System.out.println(x1 + "," + y1 + "   :   " + x2 + "," + y2);
		if(x1 < x2 || x1 > x2+w2)
			return false;
		if(y1 > y2 + h2 || y1 < y1)
			return false;
		
		return true;
	}
}
