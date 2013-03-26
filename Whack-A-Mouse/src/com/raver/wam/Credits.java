package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Credits implements Screen {

	WhackAMouse g;
	
	Credits(WhackAMouse g)
	{
		this.g = g;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		CharSequence chars = "Rafal Gawel\nTomasz Jaskiewicz\n\nMusic by unreal_dm\n\"Blue Circles\"\n\nSounds from\nwww.freesound.org";
		
		TextBounds bounds = g.font.getMultiLineBounds(chars);
		
		g.batch.begin();
		
		g.font.drawMultiLine(g.batch, chars, 0, bounds.height/2.0f, 0, HAlignment.CENTER );
		
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