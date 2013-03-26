package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Hole {
	private Sprite holeSprite;
	private Sprite holeFgSprite;
	private Sprite moleSprite;
	private Sprite femoleSprite;
	private Sprite tntSprite;
	private Sprite currSprite;
	private boolean spriteReversed;
	private float x;
	private float y;
	private float timeToChangeState;
	private float stateTime;
	private float touchedTweenTime;
	private int state;
	private boolean moleJustHid = false;
	private Rectangle collisionRect = new Rectangle();
	final int DOWN    = 0;
	final int UP      = 1;
	final int GO_DOWN = 2;
	final int GO_UP   = 3;
	final int TOUCHED = 4;
	
	final float FADE_OUT_TIME = 0.5f;
	
	public Hole(Sprite hole, Sprite holeFg,
				Sprite mole, Sprite femole, Sprite tnt,
				float x, float y)
	{
		holeSprite = hole;
		holeFgSprite = holeFg;
		moleSprite = mole;
		femoleSprite = femole;
		tntSprite = tnt;
		this.x = x;
		this.y = y;
		timeToChangeState = 3;
		stateTime = 0;
		state = DOWN;
		
		currSprite = RandSprite();
	}
	
	Sprite RandSprite()
	{
		spriteReversed = (MathUtils.random(2)) == 0;
		
		if(femoleSprite == null && tntSprite == null)
			return moleSprite;
		
		int rnd = MathUtils.random(6);
		
		if(femoleSprite != null && tntSprite == null)
			return (rnd%2) == 0 ? moleSprite : femoleSprite;
		
		if(femoleSprite == null && tntSprite != null)
			return (rnd%2) == 0 ? moleSprite : tntSprite;
		
		if((rnd%3) == 0) return moleSprite;
		if((rnd%3) == 1) return femoleSprite;
		
		return tntSprite;
	}
	
	public final float TweenJump(float t) {
		float s = 1.70158f;
        return t*t*((s+1)*t - s);
	}
	
	private float getYSize()
	{
		float yDiff = 64;
		
		if(state == UP) yDiff = 0;
		else if(state == GO_UP)   yDiff *= TweenJump(1-stateTime);
		else if(state == GO_DOWN) yDiff *= TweenJump(stateTime);
		else if(state == TOUCHED) yDiff *= TweenJump(touchedTweenTime);
		
		return 64 - yDiff;
	}

	
	void Draw(SpriteBatch batch)
	{
		holeSprite.setPosition( x - holeSprite.getWidth()/2.0f,
								y - holeSprite.getHeight()/2.0f);
		holeSprite.draw(batch);
		
		currSprite.setRegion(0, 0, 64, (int)(getYSize()));
		currSprite.setSize(64, (int)(getYSize()));
		if(state == TOUCHED) currSprite.setColor(1.0f, 1.0f, 1.0f, 1.0f-stateTime/FADE_OUT_TIME);
		else currSprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		currSprite.setPosition( x - currSprite.getWidth()/2.0f,
								y - 4);
		
		if(IsTNT() == false)
		{
			if(spriteReversed)
				currSprite.setScale(-1, 1);
			else
				currSprite.setScale( 1, 1);
		}
		
		currSprite.draw(batch);
		
		holeFgSprite.setPosition( x - holeFgSprite.getWidth()/2.0f,
								  y - holeFgSprite.getHeight()/2.0f);
		holeFgSprite.draw(batch);
	}
	
	Rectangle CalcCollisionRect()
	{
		float ySub = 10;
		collisionRect.width = 30;
		collisionRect.height = getYSize() - ySub*2;
		if(collisionRect.height < 0) collisionRect.height = 0;
		collisionRect.y = y - 4 + ySub;
		collisionRect.x = x - collisionRect.width/2.0f;
		return collisionRect;
	}
	
	void DrawDebug(ShapeRenderer renderer)
	{
		Rectangle r = CalcCollisionRect();
		renderer.begin(ShapeType.Rectangle);
		renderer.setColor(1, 1, 0, 1);
		renderer.rect(r.x, r.y, r.width, r.height);
		renderer.end();
	}
	
	void Step(float delta)
	{
		timeToChangeState -= delta;
		stateTime += delta;
		moleJustHid = false;
		if(timeToChangeState <= 0)
		{
			int lastState = state;
			
			// zmien stan
			if(state == DOWN)    state = GO_UP;   else
			if(state == GO_UP)   state = UP;      else
			if(state == UP)      state = GO_DOWN; else
			if(state == GO_DOWN) state = DOWN;	  else
			if(state == TOUCHED) state = DOWN;
			
			if(state == DOWN || state == UP)
				timeToChangeState = 3;
			else
				timeToChangeState = 1;
			
			if(state == DOWN)
			{
				if(currSprite == moleSprite && lastState == GO_DOWN)
					moleJustHid = true;
				
				currSprite = RandSprite();
			}
			
			stateTime = 0;
		}
	}
	
	void Touched()
	{
		if(state != TOUCHED)
		{
			if(state == UP) touchedTweenTime = 0;
			else if(state == GO_UP)   touchedTweenTime = 1-stateTime;
			else if(state == GO_DOWN) touchedTweenTime = stateTime;
			else touchedTweenTime = 1;
			
			state = TOUCHED;
			stateTime = 0;
			timeToChangeState = FADE_OUT_TIME;
		}
	}
	
	boolean IsTouched()
	{
		return state == TOUCHED;
	}
	
	boolean MoleJustHid()
	{
		return moleJustHid;
	}
	
	boolean IsMole()
	{
		return currSprite == moleSprite;
	}
	
	boolean IsFemole()
	{
		return currSprite == femoleSprite;
	}
	
	boolean IsTNT()
	{
		return currSprite == tntSprite;
	}
}
