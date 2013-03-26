package com.raver.wam;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Life {
	private int count;
	private int startingCount;
	private Sprite lifeSprite;
	private float[] tweenTable;
	
	Life(Sprite sprite, int count)
	{
		this.count = count;
		this.lifeSprite = sprite;
		this.startingCount = count;
		tweenTable = new float[count];
		
		lifeSprite.setOrigin(lifeSprite.getWidth()/2.0f, lifeSprite.getHeight()/2.0f);
	}
	
	float Tween(float t)
	{
		float s = 1.70158f;
		return t*t*((s+1)*t - s);
	}
	
	void Draw(SpriteBatch batch)
	{
		for(int i=0; i<startingCount; i++)
		{
			if(i >= count && tweenTable[i] <= 0) continue;
			
			if(i >= count)
			{
				lifeSprite.setScale(Tween(1-tweenTable[i])+1);
				lifeSprite.setColor(1, 1, 1, tweenTable[i]);
			} else
			{
				lifeSprite.setScale(1);
				lifeSprite.setColor(1, 1, 1, 1);
			}
			
			float xOffset = 37;
			lifeSprite.setPosition(xOffset*i - GameScreen.screenWidth()/2.0f-10,
					GameScreen.screenHeight()/2.0f - 54);
			lifeSprite.draw(batch);
		}
	}
	
	void Step(float delta)
	{
		for(int i=0; i<startingCount; i++)
		{
			tweenTable[i] -= delta*2;
		}
	}
	
	void Sub()
	{
		count--;
		if(count >= 0)
		{
			tweenTable[count] = 1;
		}
	}
	
	void Zero()
	{
		while(!Dead())
		{
			Sub();
		}
	}
	
	boolean Dead()
	{
		return count <= 0;
	}
}
