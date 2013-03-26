package com.raver.wam;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hammer {
	private Sprite hammerSprite;
	private float time = 1.5f;
	private float x, y;
	
	public Hammer(Sprite sprite, float x, float y)
	{
		hammerSprite = sprite;
		this.x = x; this.y = y;
	}
	
	void Draw(SpriteBatch batch)
	{
		hammerSprite.setRotation(-(float) (Math.abs(Math.sin(1-time))*60.0f));
		hammerSprite.setPosition(x, y);
		float alpha = time;
		if(alpha > 1) alpha = 1;
		if(alpha < 0) alpha = 0;
		hammerSprite.setColor(1.0f, 1.0f, 1.0f, alpha);
		hammerSprite.draw(batch);
	}
	
	boolean DeleteMe()
	{
		return time <= 0;
	}
	
	void Step(float delta)
	{
		time -= delta*4;
	}
}
