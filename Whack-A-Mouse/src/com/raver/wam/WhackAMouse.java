package com.raver.wam;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class WhackAMouse extends Game {

	GameScreen gameScreen;
	Pause pauseScreen;
	MainMenu menu;
	Levels levels;
	Options options;
	Credits credits;
	GameOver gameOver;
	HowToPlay howToPlay;
	ParticleEffectPool bombEffectPool;
	ParticleEffectPool bloodEffectPool;
	Array<PooledEffect> effects = new Array<PooledEffect>();
	Sound hahahaSound;
	Sound boomSound;
	Sound knockSound;
	Sound splashSound;
	
	BitmapFont font;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	
	private boolean backPressed = false;
	
	boolean isBackJustPressed()
	{
		boolean nowPressed = Gdx.input.isKeyPressed(Keys.BACK);
		if(backPressed == true && nowPressed == true) return false;
		backPressed = nowPressed;
		return backPressed;
	}
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		
		options = new Options(this);
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music.ogg"));
		bgMusic.setLooping(true);
		if(options.music)
			bgMusic.play();
		
		gameScreen = new GameScreen(this);
		pauseScreen = new Pause(this);
		menu = new MainMenu(this);
		levels = new Levels(this);
		credits = new Credits(this);
		gameOver = new GameOver(this);
		howToPlay = new HowToPlay(this);
		
		ParticleEffect bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("data/particle.txt"), Gdx.files.internal("data"));
		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
		ParticleEffect bloodEffect = new ParticleEffect();
		bloodEffect.load(Gdx.files.internal("data/blood.txt"), Gdx.files.internal("data"));
		bloodEffectPool = new ParticleEffectPool(bloodEffect, 1, 2);
		
		loadCommonStuff();
		font = gameScreen.font;
		batch = gameScreen.batch;
		shapeRenderer = gameScreen.shapeRenderer;
		
		hahahaSound = Gdx.audio.newSound(Gdx.files.internal("data/hahaha.wav"));
		boomSound = Gdx.audio.newSound(Gdx.files.internal("data/boom.wav"));
		knockSound = Gdx.audio.newSound(Gdx.files.internal("data/knock.wav"));
		splashSound = Gdx.audio.newSound(Gdx.files.internal("data/splash.wav"));
		
		setScreen(menu);
	}
	
	// common stuff
	
	private Texture buttonTexture;
	private Sprite buttonSprite;
	Music bgMusic;

	void loadCommonStuff()
	{
		buttonTexture = new Texture(Gdx.files.internal("data/button.png"));
		buttonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(buttonTexture, 0, 0, 256, 64);
		buttonSprite = new Sprite(region);
	}
	
	void DrawButton(SpriteBatch batch, CharSequence chars, float centerY)
	{
		buttonSprite.setPosition(-256/2.0f, centerY - 32.0f);
		buttonSprite.draw(batch);

		TextBounds bounds = font.getBounds(chars);
		font.draw(batch, chars, -bounds.width/2.0f, centerY + bounds.height/2.0f);
	}
	
	float ScreenToWorldX(float screenX)
	{
		float x = screenX;
		x /= Gdx.graphics.getWidth();
		x *= GameScreen.screenWidth();
		x -= GameScreen.screenWidth()/2.0f;
		return x;
	}
	
	float ScreenToWorldY(float screenY)
	{
		float y = Gdx.graphics.getHeight() - screenY;
		y /= Gdx.graphics.getHeight();
		y *= GameScreen.screenHeight();
		y -= GameScreen.screenHeight()/2.0f;
		return y;
	}
}
