package com.raver.wam;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {
	WhackAMouse g;
	
	private OrthographicCamera camera;
	SpriteBatch batch;
	
	private Texture bgTexture;
	private Sprite bgSprite;
	private Texture hammerTexture;
	private Sprite hammerSprite;
	private Texture holeTexture;
	private Sprite holeSprite;
	private Texture holeFgTexture;
	private Sprite holeFgSprite;
	private Texture moleTexture;
	private Sprite moleSprite;
	private Texture femoleTexture;
	private Sprite femoleSprite;
	private Texture tntTexture;
	private Sprite tntSprite;
	private Texture lifeTexture;
	private Sprite lifeSprite;
	private Texture pauseTexture;
	private Sprite pauseSprite;
	
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	
	Preferences pref;
	
	int currentLevel = 0;
	
	private Integer points = new Integer(0);
	private Integer best = new Integer(0);
	private Integer showingBest = new Integer(0);
	private Integer tmpPoints = new Integer(0);
	
	private float showingPoints = 0;
	private float pointsTimer = 0;
	private float lastPointsTimer = 0;
	
	private float gameOverTimer;
	private boolean hahahaPlayed;
	
	
	private LinkedList<Hammer> hammers = new LinkedList<Hammer>();
	private LinkedList<Hole> holes = new LinkedList<Hole>();
	private Life life;
	
	int GetScore()
	{
		return points;
	}
	
	int GetBest()
	{
		return showingBest;
	}
	
	private void loadTextures() {
		TextureRegion region;
		//
		bgTexture = new Texture(Gdx.files.internal("data/bg.jpg"));
		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(bgTexture, 0, 0, 320, 590);
		bgSprite = new Sprite(region);
		//
		hammerTexture = new Texture(Gdx.files.internal("data/mlot.png"));
		hammerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(hammerTexture, 0, 0, 109, 50);
		hammerSprite = new Sprite(region);
		//
		holeTexture = new Texture(Gdx.files.internal("data/dziura.png"));
		holeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(holeTexture, 0, 0, 64, 16);
		holeSprite = new Sprite(region);
		holeSprite.setOrigin(64/2, 16/2);
		//
		holeFgTexture = new Texture(Gdx.files.internal("data/dziurafg.png"));
		holeFgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(holeFgTexture, 0, 0, 64, 16);
		holeFgSprite = new Sprite(region);
		holeFgSprite.setOrigin(64/2, 16/2);
		//
		moleTexture = new Texture(Gdx.files.internal("data/mole.png"));
		moleTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(moleTexture, 0, 0, 64, 64);
		moleSprite = new Sprite(region);
		//
		femoleTexture = new Texture(Gdx.files.internal("data/femole.png"));
		femoleTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(femoleTexture, 0, 0, 64, 64);
		femoleSprite = new Sprite(region);
		//
		tntTexture = new Texture(Gdx.files.internal("data/tnt.png"));
		tntTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(tntTexture, 0, 0, 64, 64);
		tntSprite = new Sprite(region);
		//
		lifeTexture = new Texture(Gdx.files.internal("data/serce.png"));
		lifeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(lifeTexture, 0, 0, 64, 64);
		lifeSprite = new Sprite(region);
		lifeSprite.setOrigin(32/2, 32/2);
		//
		pauseTexture = new Texture(Gdx.files.internal("data/pause.png"));
		pauseTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(pauseTexture, 0, 0, 64, 64);
		pauseSprite = new Sprite(region);
		//
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
	}
	
	public GameScreen(WhackAMouse g) {
		this.g = g;
		pref = Gdx.app.getPreferences("Bests");
		create();
	}
	
	void AddHole(float x, float y, boolean mole, boolean femole, boolean tnt)
	{
		holes.add(new Hole(holeSprite, holeFgSprite, 
				mole   ? moleSprite   : null,
				femole ? femoleSprite : null,
				tnt    ? tntSprite    : null,
				x, y));
	}
	
	void Reset()
	{
		holes.clear();
		life = new Life(lifeSprite, 5);
		points = 0;
		showingPoints = 0;
		gameOverTimer = 0;
		hahahaPlayed = false;
		lastPointsTimer = 1000;
		
		if(currentLevel == 0)
		{
			for(int i=-80; i<=80; i+=80)
				AddHole(i, 0, true, false, false);
		}
		if(currentLevel == 1)
		{
			for(int i=-80; i<=80; i+=80)
				for(int j=30; j>=-30; j-=60)
					AddHole(i, j + (i==0?-1:1)*20, true, true, false);
		}
		if(currentLevel == 2)
		{
			for(int i=-100; i<=100; i+=50)
				for(int j=100; j>=-100; j-=50)
				{
					int k =((i+100)/50)%2;
					int m =((j+100)/50)%2;
					if(k+m == 1)
						AddHole(i, j, true, true, false);
				}
		}
		if(currentLevel == 3)
		{
			for(int i=-80; i<=80; i+=80)
				for(int j=80; j>=-80; j-=80)
					AddHole(i, j + (i/80)*20, true, true, true);
		}
		if(currentLevel == 4)
		{
			for(int i=-100; i<=100; i+=50)
				for(int j=100; j>=-100; j-=50)
				{
					int k =((i+100)/50)%2;
					int m =((j+100)/50)%2;
					if(k+m != 1)
						AddHole(i, j, true, true, true);
				}
		}
		
		best = pref.getInteger("points"+new Integer(currentLevel).toString(), 0);
		showingBest = best;
	}
	
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(320, h/w*320);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		this.loadTextures();
	
		Reset();
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void dispose() {
		batch.dispose();
		bgTexture.dispose();
	}

	public static float screenWidth()
	{
		return 320.0f;
	}
	
	public static float screenHeight()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		return h/w*320.0f;
	}

	
	public void render() {
		boolean gameOver = life.Dead();
		boolean pausePressed = g.isBackJustPressed();
		
		if(!gameOver && Gdx.input.justTouched())
		{
			float x = Gdx.input.getX();
			float y = Gdx.graphics.getHeight() - Gdx.input.getY();
			x /= Gdx.graphics.getWidth();
			y /= Gdx.graphics.getHeight();
			x *= screenWidth();
			y *= screenHeight();
			
			x -= screenWidth()/2.0f;
			y -= screenHeight()/2.0f;
			
			// pause?
			{
				float pauseX = screenWidth() /2.0f - 32;
				float pauseY = screenHeight()/2.0f - 32;
				float dx = pauseX - x;
				float dy = pauseY - y;
				float pauseRadius = 30;
				if(dx*dx + dy*dy < pauseRadius * pauseRadius)
				{
					pausePressed = true;
				}
			}
			
			if(pausePressed == false)
			{
				hammers.add(new Hammer(hammerSprite, x, y));
			}
			
			int tapOn = 0;
			
			for(Hole hole: holes)
			{
				if(!hole.IsTouched() && hole.CalcCollisionRect().contains(x, y))
				{	
					if(hole.IsMole()) {  points += 50;  lastPointsTimer=0; }
					if(hole.IsFemole()) life.Sub();
					if(hole.IsTNT())	life.Zero();
					
					if(hole.IsMole() || hole.IsFemole())
					{
						if(g.options.blood)
						{
							PooledEffect effect = g.bloodEffectPool.obtain();
							effect.setPosition(x, y);
							g.effects.add(effect);	
						}
						if(g.options.vibrate) Gdx.input.vibrate(100);
						tapOn = 1;
					}
					
					if(hole.IsTNT())
					{
						PooledEffect effect = g.bombEffectPool.obtain();
						effect.setPosition(x, y);
						g.effects.add(effect);
						if(g.options.vibrate) Gdx.input.vibrate(500);
						tapOn = 2;
					}
					
					hole.Touched();
				}
			}
			
			if(!pausePressed && g.options.sounds)
			{
				if(tapOn == 0) g.knockSound.play();
				if(tapOn == 1) g.splashSound.play();
				if(tapOn == 2) g.boomSound.play();	
			}
		}
		
		while(hammers.isEmpty() == false && hammers.element().DeleteMe())
		{
			hammers.removeFirst();
		}
		
		camera.lookAt(0, 0, 0);
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		bgSprite.setOrigin(0, 0);
		bgSprite.setPosition(-screenWidth()/2.0f,-screenHeight()/2);
		bgSprite.draw(batch);
		
		pauseSprite.setPosition(screenWidth()/2.0f - 64, screenHeight()/2 - 64);
		pauseSprite.draw(batch);
		
		hammerSprite.setOrigin(105, 50-25);
		
		Gdx.graphics.getDeltaTime();
		
		float delta = Gdx.graphics.getDeltaTime();
		
		for(Hole hole: holes)
		{
			hole.Step(delta);
			if(hole.MoleJustHid())
				life.Sub();
		}
		
		for(Hammer hammer: hammers)
		{
			hammer.Step(delta);
		}
		
		for(Hole hole: holes)
		{
			hole.Draw(batch);	
		}
	
		for (int i = g.effects.size - 1; i >= 0; i--) {
	        PooledEffect effect = g.effects.get(i);
	        effect.draw(batch, delta);
	        if (effect.isComplete()) {
	                effect.free();
	                g.effects.removeIndex(i);
	        }
		}
		
		for(Hammer hammer: hammers)
		{
			hammer.Draw(batch);	
		}
		tmpPoints = (int)(showingPoints + 0.5f);
		font.draw(batch, "SCORE: "+tmpPoints.toString(), -screenWidth()/2.0f+2, -screenHeight()/2.0f + 64);
		font.setScale(1);
		font.draw(batch, "BEST: "+showingBest.toString(), -screenWidth()/2.0f+2, -screenHeight()/2.0f + 32);
		//font.draw(batch, points.toString() + "/1250", -screenWidth()/2.0f+2, screenHeight()/2.0f-2);
		life.Draw(batch);
		batch.end();
		if(pausePressed)
			g.setScreen(g.pauseScreen);
		
		if(gameOver)
			gameOverTimer += delta;
		if(gameOver && gameOverTimer >= 2.0f)
			g.setScreen(g.gameOver);
		if(gameOverTimer >= 1.0f && !hahahaPlayed)
		{
			hahahaPlayed = true;
			if(g.options.sounds) g.hahahaSound.play();
		}
		
		life.Step(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(320, h/w*320);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void render(float delta) {
		
		lastPointsTimer += delta;
		pointsTimer += delta;
		
		float step = 1.0f/60.0f;
		if(pointsTimer > step*4) pointsTimer = step*4;
		while(pointsTimer >= step)
		{
			pointsTimer -= step;
			
			showingPoints = 0.97f*showingPoints + 0.03f*points;
		}
		
		render();
	}

	@Override
	public void show() {
		best = pref.getInteger("points"+new Integer(currentLevel).toString(), 0);
	}

	@Override
	public void hide() {
		if(best < points)
		{
			pref.putInteger("points"+new Integer(currentLevel).toString(), points);
			pref.flush();
		}
	}

}
