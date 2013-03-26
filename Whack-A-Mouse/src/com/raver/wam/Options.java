package com.raver.wam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

public class Options implements Screen {

	WhackAMouse g;
	
	boolean blood   = true;
	boolean sounds  = true;
	boolean music   = true;
	boolean vibrate = true;
	
	Preferences pref;
	
	Options(WhackAMouse g)
	{
		this.g = g;
		
		pref = Gdx.app.getPreferences("Options");
		
		blood   = pref.getBoolean("blood",   true);
		sounds  = pref.getBoolean("sounds",  true);
		music   = pref.getBoolean("music",   true);
		vibrate = pref.getBoolean("vibrate", true);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
		g.batch.begin();
		if(blood) g.DrawButton(g.batch, "BLOOD ON", 128);
		else g.DrawButton(g.batch, "BLOOD OFF", 128);
		if(sounds) g.DrawButton(g.batch, "SOUNDS ON", 64);
		else g.DrawButton(g.batch, "SOUNDS OFF", 64);
		if(music) g.DrawButton(g.batch, "MUSIC ON", 0);
		else g.DrawButton(g.batch, "MUSIC OFF", 0);
		if(vibrate) g.DrawButton(g.batch, "VIBRATE ON", -64);
		else g.DrawButton(g.batch, "VIBRATE OFF", -64);
		g.DrawButton(g.batch, "EXIT", -138);
		g.batch.end();
		
		if(Gdx.input.justTouched())
		{
			float y = g.ScreenToWorldY(Gdx.input.getY());
			
			boolean wasMusic = music;
			if(y < 128+30 &&  y > 128-30)  blood = !blood;
			if(y < 64+30 &&  y > 64-30)    sounds = !sounds;
			if(y < 0+30  &&  y > 0-30)     music = !music;
			if(y < -64+30  &&  y > -64-30) vibrate = !vibrate;
			if(y < -138+30 && y > -138-30) g.setScreen(g.menu);
			if(wasMusic != music)
			{
				if(music)
					g.bgMusic.play();
				else
					g.bgMusic.pause();
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
		pref.putBoolean("blood",   blood);
		pref.putBoolean("sounds",  sounds);
		pref.putBoolean("music",   music);
		pref.putBoolean("vibrate", vibrate);
		pref.flush();
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
