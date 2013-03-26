package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class GameOver implements Screen {

	WhackAMouse g;
	
	GameOver(WhackAMouse g)
	{
		this.g = g;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
		String chars = "GAME OVER!\n\nSCORE: "+g.gameScreen.GetScore()+"\n";
		if(g.gameScreen.GetScore() > g.gameScreen.GetBest())
			chars += "NEW RECORD!";
		else
			chars += "BEST: "+g.gameScreen.GetBest();
		
		TextBounds bounds = g.font.getMultiLineBounds(chars);
		
		g.batch.begin();
		g.font.drawMultiLine(g.batch, chars, 0, bounds.height+32, 0, HAlignment.CENTER );
		g.DrawButton(g.batch, "RESTART", -64);
		g.DrawButton(g.batch, "EXIT", -128);
		g.batch.end();
		
		if(Gdx.input.justTouched())
		{
			float y = g.ScreenToWorldY(Gdx.input.getY());
			
			if(y < -64+30  &&  y > -64-30) { g.gameScreen.Reset(); g.setScreen(g.gameScreen); }
			if(y < -128+30 && y > -128-30) g.setScreen(g.menu);
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
