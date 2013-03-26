package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HowToPlay implements Screen {

	WhackAMouse g;
	
	Texture howtoTex;
	Sprite howtoSpr;
	
	HowToPlay(WhackAMouse g)
	{
		this.g = g;
		
		TextureRegion region;
		howtoTex = new Texture(Gdx.files.internal("data/howto.png"));
		howtoTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(howtoTex, 0, 0, 512, 512);
		howtoSpr = new Sprite(region);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		g.batch.begin();
		
		howtoSpr.setPosition(-256, -256);
		howtoSpr.draw(g.batch);
		
		g.batch.end();
		
		if(Gdx.input.justTouched())
		{
			g.setScreen(g.menu);
		}
		
		if(g.isBackJustPressed())
		{
			g.setScreen(g.menu);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
