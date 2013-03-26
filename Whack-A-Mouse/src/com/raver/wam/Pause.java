package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

public class Pause implements Screen {

	WhackAMouse g;
	
	Pause(WhackAMouse g)
	{
		this.g = g;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
		g.batch.begin();
		g.DrawButton(g.batch, "RESUME", 64);
		g.DrawButton(g.batch, "RESTART", 0);
		g.DrawButton(g.batch, "EXIT", -64);
		g.batch.end();
		
		if(Gdx.input.justTouched())
		{
			float y = g.ScreenToWorldY(Gdx.input.getY());
			
			if(y < 64+30 &&  y > 64-30) g.setScreen(g.gameScreen);
			if(y < 0+30  &&  y > 0-30) { g.gameScreen.Reset(); g.setScreen(g.gameScreen); }
			if(y < -64+30 && y > -64-30) g.setScreen(g.menu);
		}
		
		if(g.isBackJustPressed())
		{
			g.setScreen(g.gameScreen);
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
