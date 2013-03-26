package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

public class Levels implements Screen {

	WhackAMouse g;
	
	Levels(WhackAMouse g)
	{
		this.g = g;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		g.batch.begin();
		g.DrawButton(g.batch, "LEVEL 01", 128);
		g.DrawButton(g.batch, "LEVEL 02", 64);
		g.DrawButton(g.batch, "LEVEL 03", 0);
		g.DrawButton(g.batch, "LEVEL 04", -64);
		g.DrawButton(g.batch, "LEVEL 05", -128);
		g.batch.end();
		
		if(Gdx.input.justTouched())
		{
			float y = g.ScreenToWorldY(Gdx.input.getY());
			
			g.gameScreen.currentLevel = -1;
			if(y <  128+30 && y >  128-30) g.gameScreen.currentLevel = 0;
			if(y <   64+30 && y >   64-30) g.gameScreen.currentLevel = 1;
			if(y <    0+30 && y >    0-30) g.gameScreen.currentLevel = 2;
			if(y <  -64+30 && y >  -64-30) g.gameScreen.currentLevel = 3;
			if(y < -128+30 && y > -128-30) g.gameScreen.currentLevel = 4;
			
			if(g.gameScreen.currentLevel != -1)
			{
				g.gameScreen.Reset();
				g.setScreen(g.gameScreen);
			}
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