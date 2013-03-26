package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

public class MainMenu implements Screen {

	WhackAMouse g;
	
	MainMenu(WhackAMouse g)
	{
		this.g = g;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		g.batch.begin();
		g.DrawButton(g.batch, "START", 96);
		g.DrawButton(g.batch, "HOW TO PLAY", 32);
		g.DrawButton(g.batch, "OPTIONS", -32);
		g.DrawButton(g.batch, "ABOUT", -96);
		g.batch.end();
		
		if(Gdx.input.justTouched())
		{
			float y = g.ScreenToWorldY(Gdx.input.getY());
			
			if(y < 96+30  && y >  96-30)  g.setScreen(g.levels);
			if(y < 32+30  && y >  32-30)  g.setScreen(g.howToPlay);
			if(y < -32+30 && y > -32-30)  g.setScreen(g.options);
			if(y < -96+30 && y > -96-30)  g.setScreen(g.credits);
		}
		
		if(g.isBackJustPressed())
		{
			Gdx.app.exit();
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
