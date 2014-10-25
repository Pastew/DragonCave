package com.aghacks.dragoncave.handlers;

import com.aghacks.dragoncave.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class SlowMotionBar {
	private final int amountMax = 9000;
	public static final int FROZEN_TIME = 2000;
	
	private int amount;
	private ShapeRenderer sr;
	long lastUpdateTime;
	
	private Sprite sprite;
	
	private long lastClick;
	private State state;
	private Color color;
	
	public SlowMotionBar(){
		this.state = State.NOT_RUNNING;
		this.amount = amountMax;
		sr = new ShapeRenderer();
		
		sprite = new Sprite(Game.res.getTexture("slowMotion"));	
		sprite.setSize(Game.V_WIDTH / 17, Game.V_HEIGHT);
		sprite.setPosition(Game.V_WIDTH - sprite.getWidth(),0);
	}
	
	public void render(SpriteBatch sb){
		// ramka
		sr.begin(ShapeType.Line);
			sr.setColor(color);
			sr.rect(Game.V_WIDTH - Game.V_WIDTH/16, 
					0, 
					
					Game.V_WIDTH/16, 
					Game.V_HEIGHT);		
		sr.end();	
		
		// srodek
		sr.begin(ShapeType.Filled);
		sr.rect(Game.V_WIDTH-Game.V_WIDTH/16, 
				1, 
				
				Game.V_WIDTH/16-1, 
				(Game.V_HEIGHT-1) * ((float)amount/amountMax));	
		sr.end();
		
		// napis
		sb.begin();
		sprite.draw(sb);
		sb.end();
	}
	
	public void update(){
		long currentTime = System.currentTimeMillis();
		float difference = currentTime - lastUpdateTime;
		if(lastUpdateTime != 0) {
			if(state == State.DECREASING && amount > 0){
				amount -= 3*difference;
			}
			else if (state == State.INCREASING && amount < amountMax){
				amount += difference;
			}
		}
		lastUpdateTime = System.currentTimeMillis();
		if(lastUpdateTime - lastClick >= SlowMotionBar.FROZEN_TIME) {
			color = new Color(0.2f, 0.2f, 0.2f, 0.4f);
		}
	}
	
	public void decrease(){
		state = State.DECREASING;
	}	

	public void increase() {
		state = State.INCREASING;
		//timer.setTime(0);
		//timer.start();				
	}
	
	public void froze() {
		color = new Color(0.4f, 0.4f, 0.4f, 0.8f);
	}
	
	public long getLastClick() { return lastClick; }
	public void setLastClick(long time) {
		this.lastClick = time;
	}
	
	enum State { NOT_RUNNING, INCREASING, DECREASING }

	public void reset() {
		this.amount = amountMax;
		
	}
}
